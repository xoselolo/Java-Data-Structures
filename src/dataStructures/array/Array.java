package dataStructures.array;

import java.util.Arrays;

public class Array<T> {
    // Attributes
    private Object[] elements;

    // Constructor
    public Array(){
        this.elements = new Object[0];
    }

    // Getters & Setters
    public Object[] getElements() {
        return elements;
    }
    public void setElements(T[] elements) {
        this.elements = elements;
    }

    // Functions
    public void add(T newElement){
        this.elements = Arrays.copyOf(elements, elements.length + 1);
        this.elements[elements.length - 1] = newElement;
    }
    public Object get(int index){
        return elements[index];
    }
    public int size(){
        return elements.length;
    }

}
