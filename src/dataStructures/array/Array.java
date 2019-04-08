package dataStructures.array;

import java.util.Arrays;


public class Array<T> {
    // Attributes
    private Object[] elements;

    // Constructor
    public Array(){
        this.elements = new Object[0];
    }

    public Array(Array<T> prevArray) {
        this.elements = prevArray.getElements();
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

    public void remove(T element) {

    }

    public boolean remove(int index) {
        int newSize = elements.length - 1;
        Object[] newArray = new Object[newSize];

        if (index <= newSize && index >= 0) {
            // If the deleted value is last index
            if (index == newSize) {
                newArray = Arrays.copyOf(elements,elements.length - 1);
            }
            else {
                for (int i = 0; i < index; i++) {
                    newArray[i] = elements[i];
                }
                for (int i = index + 1; i < newSize; i++) {
                    newArray[i] = elements[i];
                }
            }
            elements = newArray;
            return true;
        }
        else {
            return false;
        }
    }
    public Object get(int index){
        return elements[index];
    }
    public int size(){
        return elements.length;
    }

}
