package implementations;

import interfaces.AbstractBinarySearchTree;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {
    private Node<E> root;
    private Node<E> leftChild;
    private Node<E> rightChild;

    public BinarySearchTree() {
        this.root = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    public BinarySearchTree(Node<E> node) {
        this.root = node;
        this.leftChild = node.leftChild;
        this.rightChild = node.rightChild;
    }

    @Override
    public void insert(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.getRoot() == null) {
            this.root = newNode;
        } else {
            Node<E> current = getRoot();
            Node<E> prev = null;
            while (current != null) {
                prev = current;
                if (element.compareTo(current.value) > 0) {
                    current = current.rightChild;
                } else if (element.compareTo(current.value) < 0) {
                    current = current.leftChild;
                } else {
                    return;
                }
            }
            current = newNode;
            if (element.compareTo(prev.value) > 0) {
                prev.rightChild = current;
            } else if (element.compareTo(prev.value) < 0) {
                prev.leftChild = current;
            }

        }
    }

    @Override
    public boolean contains(E element) {

        Node<E> current = getRoot();
        while (current != null) {
            if (element.compareTo(current.value) < 0) {
                current = current.leftChild;
            } else if (element.compareTo(current.value) > 0) {
                current = current.rightChild;
            } else {
                return true;
            }
        }

        return false;
    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {

        Node<E> current = getRoot();
        while (current != null) {
            if (element.compareTo(current.value) < 0) {
                current = current.leftChild;
            } else if (element.compareTo(current.value) > 0) {
                current = current.rightChild;
            } else {
                return new BinarySearchTree<>(current);
            }
        }

        return new BinarySearchTree<>();
    }

    @Override
    public Node<E> getRoot() {
        return this.root;
    }

    @Override
    public Node<E> getLeft() {
        return this.leftChild;
    }

    @Override
    public Node<E> getRight() {
        return this.rightChild;
    }

    @Override
    public E getValue() {
        return this.root.value;
    }
}
