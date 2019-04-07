package dataStructures.graph;

import java.util.ArrayList;

/**
 * Classe referent al node de un graf genèric
 * @param <T> Classe de l'objecte que volem emmagatzemar a cada node del graf
 */
public class GraphNode <T>{
    // Attributes
    private T element;
    private ArrayList<GraphNode<T>> next;

    // Constructors
    public GraphNode(T element){
        this.element = element;
        this.next = new ArrayList<GraphNode<T>>();
    }
    public GraphNode(T element, ArrayList<GraphNode<T>> next){
        this.element = element;
        this.next = new ArrayList<GraphNode<T>>(next);
    }

    // Getters & Setters
    public T getElement() {
        return element;
    }
    public void setElement(T element) {
        this.element = element;
    }
    public ArrayList<GraphNode<T>> getNext() {
        return next;
    }
    public void setNext(ArrayList<GraphNode<T>> next) {
        this.next = next;
    }

    // Functions
}
