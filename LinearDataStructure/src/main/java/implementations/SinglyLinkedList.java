package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class SinglyLinkedList<E> implements LinkedList<E> {
    private Node<E> head;
    private int size;

    public SinglyLinkedList() {
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
    public void addFirst(E element) {
        Node<E> eNode = new Node<>(element);
        eNode.next = this.head;
        this.head = eNode;
        this.size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.isEmpty()) {
            this.head = newNode;

        } else {
            Node<E> current = this.head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        this.size++;
    }

    @Override
    public E removeFirst() {
        ensureNonEmpty();
        Node<E> previousHead = this.head;
        this.head = previousHead.next;
        this.size--;
        return previousHead.value;
    }

    @Override
    public E removeLast() {
        ensureNonEmpty();

        if (this.size == 1) {
            E value = this.head.value;
            this.head = null;

            return value;
        }
        Node<E> preLast = this.head;
        Node<E> toRemove = this.head.next;
        while (toRemove.next != null) {
            preLast = toRemove;
            toRemove = toRemove.next;
        }
        preLast.next = null;
        this.size--;

        return toRemove.value;
    }

    @Override
    public E getFirst() {
        return this.head.value;
    }

    @Override
    public E getLast() {
        Node<E> current = this.head;
        while (current.next != null) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

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
                E value = this.current.value;
                this.current = this.current.next;
                return value;
            }
        };
    }


    private void ensureNonEmpty() {
        if (this.isEmpty()) {
            throw new IllegalStateException("LinkedList is empty!");
        }
    }
}
