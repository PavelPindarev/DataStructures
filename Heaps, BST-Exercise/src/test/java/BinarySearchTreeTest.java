import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {
    private BinarySearchTree<Integer> bst;

    @Before
    public void setUp() {
        this.bst = new BinarySearchTree<>(5);
        bst.insert(10);
        bst.insert(15);
        bst.insert(3);
    }


    @Test
    public void testCreation() {
        assertEquals(Integer.valueOf(5), bst.getRoot().getValue());
    }

    @Test
    public void testInsertion() {
        assertEquals(Integer.valueOf(5), bst.getRoot().getValue());
        assertEquals(Integer.valueOf(10), bst.getRoot().getRight().getValue());
        assertEquals(Integer.valueOf(3), bst.getRoot().getLeft().getValue());
    }

    @Test
    public void testContains() {
        assertTrue(bst.contains(15));
        assertTrue(bst.contains(10));
        assertTrue(bst.contains(3));
        assertFalse(bst.contains(100));
        assertFalse(bst.contains(1));
    }

    @Test
    public void testEachInOrder() {
        List<Integer> elements = new ArrayList<>();
        bst.eachInOrder(elements::add);
        assertEquals(4, elements.size());
        assertEquals(Integer.valueOf(3), elements.get(0));
        assertEquals(Integer.valueOf(15), elements.get(3));
    }

    @Test
    public void testSearch() {
        BinarySearchTree<Integer> searchedBST = bst.search(10);
        assertEquals(Integer.valueOf(10), searchedBST.getRoot().getValue());
        assertEquals(Integer.valueOf(15), searchedBST.getRoot().getRight().getValue());
        assertNull(searchedBST.getRoot().getLeft());
    }

    @Test
    public void testRange() {
        int[] expectedElements = {3, 5, 10};
        List<Integer> range1 = bst.range(3, 10);
        List<Integer> range2 = bst.range(10, 3);
        for (int expectedElement : expectedElements) {
            assertTrue(range1.contains(expectedElement));
            assertTrue(range2.contains(expectedElement));
        }
        assertFalse(range1.contains(15));
        assertFalse(range2.contains(15));
    }

    @Test
    public void testDeleteMin() {
        bst.deleteMin();
        assertFalse(bst.contains(3));
    }

    @Test
    public void testDeleteMax() {
        bst.deleteMax();
        assertFalse(bst.contains(15));
    }

    @Test
    public void testCount() {
        int count = bst.count();
        assertEquals(4, count);
    }

    @Test
    public void testRank() {
        int count = bst.rank(15);
        assertEquals(3, count);
    }

    @Test
    public void testRankWithElementOutOfTheTree() {
        int count = bst.rank(-1);
        assertEquals(0, count);
    }

    @Test
    public void testFloor() {
        assertEquals(Integer.valueOf(15), bst.floor(100));
    }

    @Test
    public void testFloorWhenNull() {
        assertNull(bst.floor(-1));
    }

    @Test
    public void testCeil() {
        assertEquals(Integer.valueOf(15), bst.ceil(14));
    }

    @Test
    public void testCeilWhenNull() {
        assertNull(bst.ceil(100));
    }
}
