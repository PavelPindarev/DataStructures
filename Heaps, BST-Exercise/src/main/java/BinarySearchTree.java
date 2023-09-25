import solutions.BinaryTree;

import java.util.function.Consumer;

import java.util.List;

public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;

    public static class Node<E> {
        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;

		public Node(E value) {
            this.value = value;
        }

        public Node<E> getLeft() {
            return this.leftChild;
        }

        public Node<E> getRight() {
            return this.rightChild;
        }

        public E getValue() {
            return this.value;
        }
    }
	
	public void eachInOrder(Consumer<E> consumer) {
            
    }

    public Node<E> getRoot() {
        return null;
    }

    public void insert(E element) {

    }

    public boolean contains(E element) {
        return false;
    }
    public BinarySearchTree<E> search(E element) {
        return null;
    }
    public List<E> range(E first, E second) {
      return null;
    }
    public void deleteMin() {

    }

    public void deleteMax() {

    }

    public int count() {
        return 0;
    }

    public int rank(E element) {
        return 0;
    }

    public E ceil(E element) {
        return null;
    }

    public E floor(E element) {
        return null;
    }
}
