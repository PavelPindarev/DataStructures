package solutions;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    private int key;
    private BinaryTree left;
    private BinaryTree right;

    public BinaryTree(int key, BinaryTree first, BinaryTree second) {
        this.key = key;
        this.left = first;
        this.right = second;
    }

    public Integer findLowestCommonAncestor(int first, int second) {
        int lower = Math.min(first, second);
        int upper = Math.max(first, second);

        BinaryTree current = this;
        while (current != null) {
            if (lower <= current.key && upper >= current.key) {
                return current.key;
            } else if (lower > current.key) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return null;
    }

    public List<Integer> topView() {
        List<Integer> result = new ArrayList<>();
        BinaryTree currentLeft = this;
        while (currentLeft.left != null) {
            currentLeft = currentLeft.left;
            result.add(currentLeft.key);
        }
        result.add(this.key);
        BinaryTree currentRight = this;
        while (currentRight.right != null) {
            currentRight = currentRight.right;
            result.add(currentRight.key);
        }
        return result;
    }
}
