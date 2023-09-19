package implementations;

import interfaces.AbstractBinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BinaryTree<E> implements AbstractBinaryTree<E> {

    @Override
    public E getKey() {
        return null;
    }

    @Override
    public AbstractBinaryTree<E> getLeft() {
        return null;
    }

    @Override
    public AbstractBinaryTree<E> getRight() {
        return null;
    }

    @Override
    public void setKey(E key) {

    }

    @Override
    public String asIndentedPreOrder(int indent) {
        return null;
    }

    @Override
    public List<AbstractBinaryTree<E>> preOrder() {
        return null;
    }

    @Override
    public List<AbstractBinaryTree<E>> inOrder() {
        return null;
    }

    @Override
    public List<AbstractBinaryTree<E>> postOrder() {
        return null;
    }

    @Override
    public void forEachInOrder(Consumer<E> consumer) {

    }
}
