package dataStructures.graph;

import dataStructures.array.Array;

import java.util.ArrayList;

/**
 * Classe referent al node de un graf genèric
 * @param <T> Classe de l'objecte que volem emmagatzemar a cada node del graf
 */
public class GraphNode <T>{
    // Attributes
    private T element;
    private Array<String> next;

    // Constructors
    public GraphNode(T element){
        this.element = element;
        this.next = new Array<String>();
    }
    public GraphNode(T element, Array<String> next){
        this.element = element;
        this.next = new Array<String>(next);
    }

    // Getters & Setters
    public T getElement() {
        return element;
    }
    public void setElement(T element) {
        this.element = element;
    }
    public Array<String> getNext() {
        return next;
    }
    public void setNext(Array<String> next) {
        this.next = next;
    }

    // Functions
}
