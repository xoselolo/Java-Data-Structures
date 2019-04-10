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

    // Returns true if operation is ok
    public boolean remove(T element) {
        int size = elements.length;

        int index = -1;
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                index = i;
            }
        }
        if (index == -1) {
            return false;
        }
        else {
            return remove(index);
        }
    }

    // Returns true if operation is ok
    public boolean remove(int index) {
        int newSize = elements.length - 1;
        Object[] newArray = new Object[newSize];

        if (index <= newSize && index >= 0) {
            // If the deleted value is last index
            if (index == newSize) {
                newArray = Arrays.copyOf(elements,elements.length - 1);
            }
            else {
                System.arraycopy(elements,0,newArray,0,index);
                System.arraycopy(elements,index + 1,newArray,index,newSize - index);
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

    // Return true if element is already inside the array
    public boolean findElement(T element) {
        int size = elements.length;

        for(int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

}
