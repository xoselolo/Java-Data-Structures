package dataStructures.graph;

import dataStructures.array.Array;

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
    public Array indexYconnectsWith(int y){
        return ((GraphNode)adjacencyList.get(y)).getNext();
    }

    public static void main(String[] args) {

    }

}
