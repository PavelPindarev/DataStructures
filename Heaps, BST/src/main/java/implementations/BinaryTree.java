package implementations;

import interfaces.AbstractBinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BinaryTree<E> implements AbstractBinaryTree<E> {
    private E key;

    private BinaryTree<E> left;

    private BinaryTree<E> right;

    public BinaryTree(E key, BinaryTree<E> left, BinaryTree<E> right) {
        this.setKey(key);
        this.left = left;
        this.right = right;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public AbstractBinaryTree<E> getLeft() {
        return this.left;
    }

    @Override
    public AbstractBinaryTree<E> getRight() {
        return this.right;
    }

    @Override
    public void setKey(E key) {
        this.key = key;
    }

    @Override
    public String asIndentedPreOrder(int indent) {
        StringBuilder result = new StringBuilder();
        result.append(this.getPadding(indent)).append(this.getKey());

        if (this.getLeft() != null) {
            result.append(System.lineSeparator())
                    .append(this.getLeft().asIndentedPreOrder(indent + 2));
        }

        if (this.getRight() != null) {
            result.append(System.lineSeparator())
                    .append(this.getRight().asIndentedPreOrder(indent + 2));
        }

        return result.toString();
    }

    private String getPadding(int indent) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }


    @Override
    public List<AbstractBinaryTree<E>> preOrder() {
        List<AbstractBinaryTree<E>> trees = new ArrayList<>();

        trees.add(this);
        if (this.getLeft() != null) {
            trees.addAll(this.getLeft().preOrder());
        }
        if (this.getRight() != null) {
            trees.addAll(this.getRight().preOrder());
        }

        return trees;
    }

    @Override
    public List<AbstractBinaryTree<E>> inOrder() {
        List<AbstractBinaryTree<E>> trees = new ArrayList<>();

        if (this.getLeft() != null) {
            trees.addAll(this.getLeft().inOrder());
        }
        trees.add(this);
        if (this.getRight() != null) {
            trees.addAll(this.getRight().inOrder());
        }

        return trees;
    }

    @Override
    public List<AbstractBinaryTree<E>> postOrder() {
        List<AbstractBinaryTree<E>> trees = new ArrayList<>();

        if (this.getLeft() != null) {
            trees.addAll(this.getLeft().postOrder());
        }
        if (this.getRight() != null) {
            trees.addAll(this.getRight().postOrder());
        }
        trees.add(this);

        return trees;
    }

    @Override
    public void forEachInOrder(Consumer<E> consumer) {
        if (this.getLeft() != null) {
            this.getLeft().forEachInOrder(consumer);
        }
        consumer.accept(this.key);
        if (this.getRight() != null) {
            this.getRight().forEachInOrder(consumer);
        }
    }
}
