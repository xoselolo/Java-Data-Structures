package dataStructures.graph;

import dataStructures.array.Array;
import javafx.geometry.Pos;
import model.Post;
import model.User;

import java.util.Comparator;

public class Graph<T> {
    // Attributes
    private Array<GraphNode<T>> adjacencyList;
    private Comparator<T> comparator;

    // Constructor
    public Graph(Comparator<T> comparator){
        this.comparator = comparator;
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

    public void printStructure() {
        int size = adjacencyList.size();
        for (int i = 0; i < size; i++) {
            if (adjacencyList.get(i) instanceof GraphNode) {
                System.out.print(((User)((GraphNode) adjacencyList.get(i)).getElement()).getUsername());
                int followSize = ((Array)((GraphNode) adjacencyList.get(i)).getNext()).getElements().length;
                if (followSize > 0) {
                    System.out.print(" - ");
                    for (int j = 0; j < followSize; j++) {
                        System.out.print(((Array)((GraphNode) adjacencyList.get(i)).getNext()).getElements()[j] + " ");
                    }
                }
                if (i != size - 1) {
                    System.out.println();
                    System.out.println(" | ");
                }
            }
        }
        System.out.println();
    }

    public void remove(String username) {
        int size = adjacencyList.size();
        for (int i = 0; i < size; i++) {
            if (adjacencyList.get(i) instanceof GraphNode) {
                if (((User)((GraphNode) adjacencyList.get(i)).getElement()).getUsername().equals(username)) {
                    adjacencyList.remove(i);
                }
                else {
                    int followSize = ((Array)((GraphNode) adjacencyList.get(i)).getNext()).getElements().length;
                    if (followSize > 0) {
                        for (int j = 0; j < followSize; j++) {
                            if (((Array)((GraphNode) adjacencyList.get(i)).getNext()).getElements()[j].equals(username)) {
                                ((Array)((GraphNode) adjacencyList.get(i)).getNext()).remove(j);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

}
