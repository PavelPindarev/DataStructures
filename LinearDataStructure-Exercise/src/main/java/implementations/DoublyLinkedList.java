package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class DoublyLinkedList<E> implements LinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {
        private E element;
        private Node<E> next;

        private Node<E> prev;

        public Node(E value) {
            this.element = value;
            this.next = this.prev = null;
        }
    }

    public DoublyLinkedList() {
        this.head = this.tail = null;
        this.size = 0;
    }

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.isEmpty()) {
            this.head = this.tail = newNode;
        } else {
            newNode.next = this.head;
            this.head.prev = newNode;
            this.head = newNode;
        }
        this.size++;
    }

    @Override
    public void addLast(E element) {
        if (this.isEmpty()) {
            this.addFirst(element);
        } else {
            Node<E> newNode = new Node<>(element);
            newNode.prev = this.tail;
            this.tail.next = newNode;
            this.tail = newNode;
            this.size++;
        }
    }

    @Override
    public E removeFirst() {
        ensureNotEmpty();
        E element = this.head.element;
        if (this.size == 1) {
            this.head = this.tail = null;
        } else {
            Node<E> newHead = this.head.next;
            newHead.prev = null;
            this.head.next = null;
            this.head = newHead;
        }
        this.size--;
        return element;
    }


    @Override
    public E removeLast() {
        ensureNotEmpty();
        if (this.size == 1) {
            return removeFirst();
        }
        Node<E> current = this.tail;
        this.tail = current.prev;
        this.tail.next = null;
        current.prev = null;

        this.size--;
        return current.element;
    }

    @Override
    public E getFirst() {
        ensureNotEmpty();
        return this.head.element;
    }

    @Override
    public E getLast() {
        ensureNotEmpty();
        return this.tail.element;
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
                return current != null;
            }

            @Override
            public E next() {
                E element = current.element;
                current = current.next;
                return element;
            }
        };
    }


    private void ensureNotEmpty() {
        if (this.size == 0) {
            throw new IllegalStateException("Illegal remove for empty LinkedList");
        }
    }
}
