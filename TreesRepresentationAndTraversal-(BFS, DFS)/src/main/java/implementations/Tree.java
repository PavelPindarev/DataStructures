package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Tree<E> implements AbstractTree<E> {
    private E value;
    private Tree<E> parent;

    private List<Tree<E>> children;


    @SafeVarargs
    public Tree(E value, Tree<E>... subtrees) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();
        for (Tree<E> subtree : subtrees) {
            subtree.parent = this;
            this.children.add(subtree);
        }
    }

    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();
        if (this.value == null) {
            return result;
        }
        Queue<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            result.add(current.value);
            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }
        return result;
    }

    @Override
    public List<E> orderDfs() {
        List<E> result = new ArrayList<>();

        this.doDfs(this, result);

        return result;
    }

    private void doDfs(Tree<E> node, List<E> result) {
        for (Tree<E> child : node.children) {
            this.doDfs(child, result);
        }
        result.add(node.value);
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> parentTree = findTreeBfs(parentKey);
        if (parentTree == null) {
            throw new IllegalArgumentException("Cannot find tree with key + " + parentKey);
        }

        parentTree.children.add(child);
        child.parent = parentTree;
    }

    private Tree<E> findTreeBfs(E parentKey) {
        Queue<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();

            if (current.value.equals(parentKey)) {
                return current;
            }

            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }
        return null;
    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> treeToRemove = this.findTreeBfs(nodeKey);
        if (treeToRemove == null) {
            throw new IllegalArgumentException("Cannot find tree with key + " + nodeKey);
        }
        for (Tree<E> child : treeToRemove.children) {
            child.parent = null;
        }
        treeToRemove.children.clear();
        Tree<E> parent = treeToRemove.parent;
        if (parent != null) {
            parent.children.remove(treeToRemove);
            treeToRemove.parent = null;
        }
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstTree = this.findTreeBfs(firstKey);
        Tree<E> secondTree = this.findTreeBfs(secondKey);

        if (firstTree == null || secondTree == null) {
            throw new IllegalArgumentException("There are invalid keys!");
        }
        //Root cases
        Tree<E> firstParent = firstTree.parent;
        Tree<E> secondParent = secondTree.parent;

        if (firstParent == null) {
            this.swapRoot(secondTree);
        } else if (secondParent == null) {
            this.swapRoot(firstTree);
        } else {
            secondTree.parent = firstParent;
            firstTree.parent = secondParent;

            int indexOfFirstTree = firstParent.children.indexOf(firstTree);
            int indexOfSecondTree = secondParent.children.indexOf(secondTree);

            firstParent.children.set(indexOfFirstTree, secondTree);
            secondParent.children.set(indexOfSecondTree, firstTree);
        }
    }

    private void swapRoot(Tree<E> node) {
        this.value = node.value;
        this.parent = null;
        this.children = node.children;
        node.parent = null;
    }
}



