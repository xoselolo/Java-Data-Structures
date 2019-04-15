package dataStructures.graph;

import dataStructures.array.Array;
import model.User;

public class Graph<T> {
    // Attributes
    private Array<GraphNode<T>> adjacencyList;

    // Constructor
    public Graph(){
        this.adjacencyList = new Array<GraphNode<T>>();
    }

    // Getters & Setters
    public Array<GraphNode<T>> getAdjacencyList() {
        return adjacencyList;
    }
    public void setAdjacencyList(Array<GraphNode<T>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    // Functions
    public void add(GraphNode<T> node){
        adjacencyList.add(node);
    }
    public GraphNode<T> getNodeX(int x){
        return (GraphNode<T>)getAdjacencyList().get(x);
    }

}
