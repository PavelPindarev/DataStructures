package implementations;

import interfaces.AbstractStack;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {

    private Node<E> top;

    private int size;

    private static class Node<E> {
        public E value;
        public Node<E> next;

        public Node(E element) {
            this.value = element;
            this.next = null;
        }
    }

    @Override
    public void push(E element) {
        Node<E> newNode = new Node<>(element);
//first link new element to the top so newNode -> top -> element3 -> ....
        newNode.next = this.top;
//then make new element to be top actually
        this.top = newNode;

        this.size++;
    }

    @Override
    public E pop() {
        ensureNonEmpty();
        Node<E> elementToPop = this.top;
        this.top = elementToPop.next;
        this.size--;

        return elementToPop.value;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.top.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = top;

            @Override
            public boolean hasNext() {
                if (this.current != null) {
                    return this.current.next != null;
                }
                return false;
            }

            @Override
            public E next() {
                E value = this.current.value;
                this.current = this.current.next;
                return value;
            }
        };
    }

    private void ensureNonEmpty() {
        if (this.size == 0) {
            throw new IllegalStateException("Stack is empty. No elements to be showed!");
        }
    }
}
