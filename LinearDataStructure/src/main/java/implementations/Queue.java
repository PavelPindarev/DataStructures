package implementations;

import interfaces.AbstractQueue;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {
    private Node<E> head;

    private int size;

    public Queue() {
        this.head = null;
        this.size = 0;
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;

        public Node(E value) {
            this.value = value;
            this.next = null;
        }
    }

    @Override
    public void offer(E element) {
        Node<E> offeredNode = new Node<>(element);
        if (this.isEmpty()) {
            this.head = offeredNode;
            this.size++;
            return;
        }
        Node<E> current = this.head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = offeredNode;
        this.size++;
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        Node<E> polledElement = this.head;
        this.head = polledElement.next;
        this.size--;

        return polledElement.value;
    }


    @Override
    public E peek() {
        ensureNonEmpty();
        return this.head.value;
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
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                if (this.current != null) {
                    return this.current.next != null;
                }
                return false;
            }

            @Override
            public E next() {
                Node<E> tmp = this.current;
                this.current = tmp.next;
                return tmp.value;
            }
        };
    }

    private void ensureNonEmpty() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Queue is empty. No elements to show!");
        }
    }
}
