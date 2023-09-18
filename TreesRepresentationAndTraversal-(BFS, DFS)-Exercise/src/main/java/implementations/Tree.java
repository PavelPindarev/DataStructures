package implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {
    private E key;

    private Tree<E> parent;

    private List<Tree<E>> children;

    public Tree(E key) {
        this.key = key;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder result = new StringBuilder();

        traverseTreeWithRecurrence(result, 0, this);

        return result.toString().trim();
    }


    @Override
    public List<E> getLeafKeys() {
        return this.getLeafTrees()
                .stream()
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<E> getMiddleKeys() {
        return this.getMiddleTrees()
                .stream()
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }


    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> leafTrees = this.getLeafTrees();
        int maxLeafPath = 0;
        Tree<E> deepestNode = null;
        for (Tree<E> tree : leafTrees) {
            int leafPath = this.getLeafPath(tree);
            if (leafPath > maxLeafPath) {
                maxLeafPath = leafPath;
                deepestNode = tree;
            }
        }
        return deepestNode;
    }


    @Override
    public List<E> getLongestPath() {
        List<E> result = new ArrayList<>();

        Tree<E> current = this.getDeepestLeftmostNode();
        while (current != null) {
            result.add(0, current.getKey());
            current = current.parent;
        }
        return result;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<List<E>> paths = new ArrayList<>();

        List<Tree<E>> leafTrees = this.getLeafTrees();

        for (Tree<E> tree : leafTrees) {
            List<E> path = new ArrayList<>();
            int totalSum = 0;

            Tree<E> current = tree;
            while (current != null) {
                totalSum += (int) current.getKey();
                path.add(0, current.getKey());
                current = current.parent;
            }
            if (totalSum == sum) {
                paths.add(path);
            }
        }
        return paths;
    }


    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        List<Tree<E>> result = new ArrayList<>();
        List<Tree<E>> subtrees = this.getMiddleTrees();

        for (Tree<E> tree : subtrees) {
            Deque<Tree<E>> queue = new ArrayDeque<>();
            int totalSum = 0;
            queue.offer(tree);
            while (!queue.isEmpty()) {
                Tree<E> current = queue.pop();
                totalSum += (int) current.getKey();
                for (Tree<E> child : current.children) {
                    queue.offer(child);
                }
            }
            if (totalSum == sum) {
                result.add(tree);
            }
        }

        return result;
    }

    private void traverseTreeWithRecurrence(StringBuilder builder, int indent, Tree<E> tree) {
        builder.append(" ".repeat(indent))
                .append(tree.getKey())
                .append(System.lineSeparator());

        for (Tree<E> child : tree.children) {
            traverseTreeWithRecurrence(builder, indent + 2, child);
        }
    }

    public List<Tree<E>> getLeafTrees() {
        List<Tree<E>> leafsTrees = new ArrayList<>();

        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);
        while (!queue.isEmpty()) {
            Tree<E> current = queue.pop();
            if (current.children.isEmpty()) {
                leafsTrees.add(current);
            }
            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }

        return leafsTrees;
    }

    public List<Tree<E>> getMiddleTrees() {
        List<Tree<E>> middleTrees = new ArrayList<>();

        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);
        while (!queue.isEmpty()) {
            Tree<E> current = queue.pop();
            if (!current.children.isEmpty() && current.getParent() != null) {
                middleTrees.add(current);
            }
            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }
        return middleTrees;
    }

    private int getLeafPath(Tree<E> tree) {
        int counter = 0;
        Tree<E> current = tree;
        while (current.parent != null) {
            counter++;
            current = current.parent;
        }
        return counter;
    }

}



