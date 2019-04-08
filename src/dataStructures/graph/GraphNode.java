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
    private Array<GraphNode<T>> next;

    // Constructors
    public GraphNode(T element){
        this.element = element;
        this.next = new Array<GraphNode<T>>();
    }
    public GraphNode(T element, Array<GraphNode<T>> next){
        this.element = element;
        this.next = new Array<GraphNode<T>>(next);
    }

    // Getters & Setters
    public T getElement() {
        return element;
    }
    public void setElement(T element) {
        this.element = element;
    }
    public Array<GraphNode<T>> getNext() {
        return next;
    }
    public void setNext(Array<GraphNode<T>> next) {
        this.next = next;
    }

    // Functions
}
