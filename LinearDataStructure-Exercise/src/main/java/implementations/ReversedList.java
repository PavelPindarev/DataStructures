package implementations;

public class ReversedList<E> {
    private static final int INITIAL_CAPACITY = 2;
    private Object[] elements;

    private int size;

    public ReversedList() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    public void add(E element) {
        if (this.size == this.elements.length) {
            this.elements = grow();
        }
        this.elements[this.size++] = element;
    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.elements.length;
    }

    public E get(int index) {
        int reversedIndex = this.size - 1 - index;
        this.ensureIndex(reversedIndex);
        return this.getAt(reversedIndex);
    }

    public void removeAt(int index) {
        int reversedIndex = this.size - 1 - index;
        this.ensureIndex(reversedIndex);
        this.shiftElements(reversedIndex);
    }

    private void shiftElements(int index) {
        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.removeLast();
    }

    private void removeLast() {
        this.elements[--this.size] = null;
    }

    private void ensureIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Invalid index!");
        }
    }

    private Object[] grow() {
        int newCapacity = this.elements.length * 2;
        Object[] newElements = new Object[newCapacity];
        for (int i = 0; i < this.size; i++) {
            newElements[i] = this.elements[i];
        }
        return newElements;
    }

    @SuppressWarnings("unchecked")
    private E getAt(int index) {
        return (E) this.elements[index];
    }
}
