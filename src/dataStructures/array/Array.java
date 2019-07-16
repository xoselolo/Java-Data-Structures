package dataStructures.array;

import model.User;

import java.util.ArrayList;
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

    public Array(Object[] prevArray) {
        this.elements = prevArray;
    }

    // Getters & Setters
    public Object[] getElements() {
        return elements;
    }
    public void setElements(T[] elements) {
        this.elements = elements;
    }

    // Functions
    /**
     * Add an element to the array
     * @param newElement : element to be added
     */
    public void add(T newElement){
        this.elements = Arrays.copyOf(elements, elements.length + 1);
        this.elements[elements.length - 1] = newElement;
    }

    /**
     * Function that remove an element specified in a position
     * @param index : position of the element we want to remove
     * @return a boolean indicating if was possible to remove or not
     */
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

    /**
     * Get the i-th element of the array
     * @param index : index of the element we want to get
     * @return the element in that position
     */
    public Object get(int index){
        return elements[index];
    }

    /**
     * Calculate size of array
     * @return number of elements
     */
    public int size(){
        return elements.length;
    }

    /**
     * Checks if an element is inside the array
     * @param element : element to check existence
     * @return true if element exists, and false if it doesn't exist
     */
    public boolean hasElement(T element) {
        int size = elements.length;

        for(int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    public int getElementPosition(T element) {
        int size = elements.length;

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }


    public Object[] toArray() {
        return this.elements;
    }


    @Deprecated
    //Returns true if operation is ok
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

    //Returns true if operation is ok
    public boolean remove(String username) {
        int size = elements.length;

        int index = -1;
        for (int i = 0; i < size; i++) {
            if (((User)elements[i]).getUsername().equals(username)) {
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

    @Override
    public String toString() {
        return "{" +
                "" + (elements == null ? null : Arrays.asList(elements)) +
                '}';
    }
}
