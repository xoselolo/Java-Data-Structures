package dataStructures.array;

import java.util.Arrays;

public class Array<T> {
    // Attributes
    private Object[] elements;
    private int size;

    // Constructor
    public Array(){
        this.size = 1;
        this.elements = new Object[size];
    }

    // Getters & Setters
    public Object[] getElements() {
        return elements;
    }
    public void setElements(T[] elements) {
        this.elements = elements;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }

    // Functions
    public void add(T newElement){
        this.elements = Arrays.copyOf(elements, size + 1);
        this.elements[size] = newElement;
        this.size++;
    }
    public int size(){
        return this.size;
    }

}
