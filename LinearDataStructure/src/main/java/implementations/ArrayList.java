package implementations;

import interfaces.List;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {
    private static final int INIT_CAPACITY = 4;
    private Object[] elements;

    private int size;

    private int capacity;

    public ArrayList() {
        this.elements = new Object[INIT_CAPACITY];
        this.capacity = INIT_CAPACITY;
        this.size = 0;
    }

    @Override
    public boolean add(E element) {
        if (this.size == this.capacity) {
            resize();
        }
        this.elements[this.size++] = element;
        return true;
    }

    @Override
    public boolean add(int index, E element) {
        if (ensureIndex(index)) {
            return false;
        }
        if (this.size == this.capacity) {
            resize();
        }
        shiftElementsToRight(index);
        this.size++;
        this.elements[index] = element;
        return true;
    }


    @Override
    public E get(int index) {
        if (ensureIndex(index)) {
            throw new IndexOutOfBoundsException("Invalid Index for getting element!");
        }
        return (E) this.elements[index];
    }

    @Override
    public E set(int index, E element) {
        if (ensureIndex(index)) {
            throw new IndexOutOfBoundsException("Invalid Index for setting element!");
        }
        Object elementToRemove = this.elements[index];
        this.elements[index] = element;
        return (E) elementToRemove;
    }

    @Override
    public E remove(int index) {
        if (ensureIndex(index)) {
            throw new IndexOutOfBoundsException("Invalid Index to remove!");
        }
        Object elementToRemove = this.elements[index];
        shiftElementsToLeft(index);
        return (E) elementToRemove;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < this.size; i++) {
            Object temp = this.elements[i];
            if (element.equals(temp)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(E element) {
        return this.indexOf(element) >= 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }

    private void resize() {
        this.capacity *= 2;
        Object[] tmp = new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            tmp[i] = this.elements[i];
        }
        this.elements = tmp;
    }

    private void shiftElementsToRight(int index) {
        for (int i = this.size - 1; i >= index; i--) {
            this.elements[i + 1] = this.elements[i];
        }
    }

    private boolean ensureIndex(int index) {
        return index >= this.size || index < 0;
    }

    private void shiftElementsToLeft(int index) {
        for (int i = index + 1; i < this.size; i++) {
            this.elements[i - 1] = this.elements[i];
        }
        this.elements[this.size - 1] = null;
        this.size--;
    }

}
