package solutions;

import interfaces.Decrease;
import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinHeap<E extends Comparable<E> & Decrease<E>> implements Heap<E> {
    private List<E> data;

    public MinHeap() {
        this.data = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public void add(E element) {
        this.data.add(element);
        this.heapifyUp(this.data.indexOf(element));
    }

    private void heapifyUp(int indexOfAddedValue) {
        int indexOfParent = getParentIndex(indexOfAddedValue);

        while (indexOfAddedValue > 0 && isGreater(indexOfParent, indexOfAddedValue)) {
            Collections.swap(this.data, indexOfAddedValue, indexOfParent);
            indexOfAddedValue = indexOfParent;
            indexOfParent = getParentIndex(indexOfAddedValue);
        }
    }

    private boolean isGreater(int first, int second) {
        E e1 = this.data.get(first);
        E e2 = this.data.get(second);
        return e1.compareTo(e2) > 0;
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.data.get(0);
    }

    private void ensureNonEmpty() {
        if (this.data.isEmpty()) {
            throw new IllegalStateException("this heap is empty!");
        }
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        Collections.swap(this.data, 0, size() - 1);
        E removed = this.data.remove(size() - 1);
        this.heapifyDown();
        return removed;
    }

    private void heapifyDown() {
        int index = 0;
        int swapIndex = this.getLeftChildIndex(index);
        while (isValid(swapIndex)) {
            int rightChildIndex = getRightChildIndex(index);
            if (isValid(rightChildIndex)) {
                swapIndex = isLess(swapIndex, rightChildIndex) ?
                        swapIndex : rightChildIndex;
            }
            if (isLess(index, swapIndex)) {
                break;
            }
            Collections.swap(this.data, index, swapIndex);
            index = swapIndex;
            swapIndex = getLeftChildIndex(index);
        }
    }

    private boolean isLess(int first, int second) {
        E e1 = this.data.get(first);
        E e2 = this.data.get(second);
        return e1.compareTo(e2) < 0;
    }


    private boolean isValid(int swapIndex) {
        return swapIndex < this.size();
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    @Override
    public void decrease(E element) {
        ensureNonEmpty();
        int elementIndex = this.data.indexOf(element);
        if (elementIndex != -1) {
            E heapElement = this.data.get(elementIndex);
            heapElement.decrease();
            this.heapifyUp(elementIndex);
        }
    }
}
