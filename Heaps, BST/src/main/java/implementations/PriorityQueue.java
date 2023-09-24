package implementations;

import interfaces.AbstractQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {

    private List<E> elements;

    public PriorityQueue() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapifyUp(this.size() - 1);
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.getAt(0);
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        E returnedValue = this.getAt(0);
        Collections.swap(this.elements, 0, this.size() - 1);
        this.elements.remove(this.size() - 1);
        this.heapifyDown(0);
        return returnedValue;
    }

    private void heapifyDown(int index) {
        while (index < this.size() / 2) {
            int childIndex = index * 2 + 1;

            if (childIndex + 1 < this.size() && isLess(childIndex + 1, childIndex)) {
                childIndex++;
            }
            if (isLess(index, childIndex)) {
                break;
            }

            Collections.swap(this.elements, index, childIndex);
            index = childIndex;
        }
    }

    private void heapifyUp(int index) {
        int parentIndex = getParentIndex(index);
        while (index > 0 && isLess(index, parentIndex)) {
            Collections.swap(this.elements, index, parentIndex);
            index = parentIndex;
            parentIndex = getParentIndex(index);
        }
    }

    private boolean isLess(int firstIndex, int secondIndex) {
        return this.getAt(firstIndex).compareTo(this.getAt(secondIndex)) > 0;
    }

    private E getAt(int index) {
        return this.elements.get(index);
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private void ensureNonEmpty() {
        if (this.size() == 0) {
            throw new IllegalStateException("Heap is empty, there is not elements to peek!");
        }
    }
}
