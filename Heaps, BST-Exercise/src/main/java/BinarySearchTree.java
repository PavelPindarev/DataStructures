import solutions.BinaryTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.function.Consumer;

import java.util.List;

public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;


    public BinarySearchTree() {
        this.root = null;
    }

    public BinarySearchTree(E value) {
        this.root = new Node<>(value);
    }

    public BinarySearchTree(Node<E> node) {
        this.root = node;
    }

    public static class Node<E> {
        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;

        private int size;


        public Node(E value) {
            this.value = value;
            this.leftChild = null;
            this.rightChild = null;
            this.size = 1;
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
        nodeInOrder(this.getRoot(), consumer);
    }

    private void nodeInOrder(Node<E> node, Consumer<E> consumer) {
        if (node == null) {
            return;
        }
        nodeInOrder(node.getLeft(), consumer);
        consumer.accept(node.getValue());
        nodeInOrder(node.getRight(), consumer);

    }

    public Node<E> getRoot() {
        return this.root;
    }

    public void insert(E element) {
        Node<E> root = this.getRoot();
        if (root == null) {
            this.root = new Node<>(element);
        } else {
            insertInto(null, root, element);
            root.size++;
        }
    }

    private void insertInto(Node<E> prev, Node<E> current, E element) {
        if (current == null) {
            current = new Node<>(element);
            if (isGreater(element, prev.getValue())) {
                prev.rightChild = current;
            } else if (isGreater(prev.getValue(), element)) {
                prev.leftChild = current;
            }
            return;
        }
        if (isGreater(element, current.getValue())) {
            insertInto(current, current.rightChild, element);
        } else if (isGreater(current.getValue(), element)) {
            insertInto(current, current.leftChild, element);
        }
    }

    private boolean isGreater(E firstElement, E secondElement) {
        return firstElement.compareTo(secondElement) > 0;
    }

    public boolean contains(E element) {
        Node<E> current = this.getRoot();
        while (current != null) {
            if (isGreater(element, current.getValue())) {
                current = current.getRight();
            } else if (isGreater(current.getValue(), element)) {
                current = current.getLeft();
            } else {
                return true;
            }
        }
        return false;
    }

    public BinarySearchTree<E> search(E element) {
        Node<E> current = this.getRoot();

        while (current != null) {
            if (isGreater(element, current.getValue())) {
                current = current.getRight();
            } else if (isGreater(current.getValue(), element)) {
                current = current.getLeft();
            } else {
                return new BinarySearchTree<>(current);
            }
        }

        return new BinarySearchTree<>();
    }

    public List<E> range(E first, E second) {
        List<E> result = new ArrayList<>();
        E lower = isGreater(first, second) ? second : first;
        E upper = isGreater(first, second) ? first : second;

        Queue<Node<E>> queue = new ArrayDeque<>();
        queue.offer(this.getRoot());

        while (!queue.isEmpty()) {
            Node<E> current = queue.poll();

            if (isInRange(current.getValue(), lower, upper)) {
                result.add(current.getValue());
            }
            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.offer(current.getRight());
            }
        }
        return result;
    }

    private boolean isInRange(E value, E lower, E upper) {
        return value.compareTo(lower) >= 0 && value.compareTo(upper) <= 0;
    }

    public void deleteMin() {
        ensureNonEmpty();
        Node<E> current = this.getRoot();
        Node<E> prev = null;
        while (current.getLeft() != null) {
            prev = current;
            current = current.getLeft();
        }
        if (prev != null) {
            prev.leftChild = null;
            this.root.size--;
        } else if (this.root.getRight() != null) {
            this.root = root.getRight();
            this.root.size--;
        } else {
            this.root = null;
        }


    }

    public void deleteMax() {
        ensureNonEmpty();
        Node<E> current = this.getRoot();
        Node<E> prev = null;
        while (current.getRight() != null) {
            prev = current;
            current = current.getRight();
        }
        if (prev != null) {
            prev.rightChild = null;
            this.root.size--;
        } else if (this.root.getLeft() != null) {
            this.root = root.getLeft();
            this.root.size--;
        } else {
            this.root = null;
        }
    }

    private void ensureNonEmpty() {
        if (this.getRoot() == null) {
            throw new IllegalArgumentException("The tree is empty!");
        }
    }

    public int count() {
        return this.root.size;
    }

    public int rank(E element) {
        return this.nodeRank(this.getRoot(), element);
    }

    private int nodeRank(Node<E> node, E element) {
        if (node == null) {
            return 0;
        }
        if (isGreater(node.getValue(), element)) {
            return nodeRank(node.getLeft(), element);
        } else if (isEqual(element, node.getValue())) {
            return getNodeCount(node.getLeft());
        }
        return getNodeCount(node.getLeft()) + 1 + nodeRank(node.getRight(), element);
    }

    private int getNodeCount(Node<E> node) {
        return node == null ? 0 : node.size;
    }

    private boolean isEqual(E firstElement, E secondElement) {
        return firstElement.compareTo(secondElement) == 0;
    }

    public E floor(E element) {
        if (this.root == null) return null;

        Node<E> current = this.getRoot();
        Node<E> nearestSmaller = null;
        while (current != null) {
            if (isGreater(element, current.getValue())) {
                nearestSmaller = current;
                current = current.getRight();
            } else if (isGreater(current.getValue(), element)) {
                current = current.getLeft();
            } else {
                Node<E> left = current.getLeft();
                if (left != null && nearestSmaller != null) {
                    nearestSmaller = isGreater(left.getValue(), nearestSmaller.getValue()) ? left : nearestSmaller;
                } else if (nearestSmaller == null) {
                    nearestSmaller = left;
                }
                break;
            }

        }
        return nearestSmaller == null ? null : nearestSmaller.getValue();
    }


    public E ceil(E element) {
        if (this.root == null) {
            return null;
        }
        Node<E> current = this.getRoot();
        Node<E> nearestBigger = null;

        while (current != null) {
            if (isGreater(current.getValue(), element)) {
                nearestBigger = current;
                current = current.getLeft();
            } else if (isGreater(element, current.getValue())) {
                current = current.getRight();
            } else {
                Node<E> right = current.getRight();
                if (right != null && nearestBigger != null) {
                    nearestBigger = isGreater(nearestBigger.getValue(), right.getValue()) ? right : nearestBigger;
                } else if (nearestBigger == null) {
                    nearestBigger = right;
                }
                break;
            }
        }
        return nearestBigger == null ? null : nearestBigger.getValue();
    }
}
