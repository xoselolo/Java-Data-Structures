package dataStructures.graph;

import com.google.gson.internal.LinkedTreeMap;
import dataStructures.array.Array;
import javafx.geometry.Pos;
import model.Post;
import model.User;

import java.util.ArrayList;
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
        for (int i = 0; i < adjacencyList.size(); i++) {
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

    public void setImportedInfo(Array info) {
        int size = info.size();
        for (int i = 0; i < size; i++) {
            String username = (String) ((LinkedTreeMap)((LinkedTreeMap)info.get(i)).get("element")).get("username");
            long creation = (long) Math.round((double)((LinkedTreeMap)((LinkedTreeMap)info.get(i)).get("element")).get("creation"));
            int toFollowSize = ((ArrayList)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)info.get(i)).get("element")).get("to_follow")).get("elements")).size();
            Array<String> toFollow = new Array<>();
            for (int j = 0; j < toFollowSize; j++) {
                String to_follow = (String) ((ArrayList)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)info.get(i)).get("element")).get("to_follow")).get("elements")).get(j);
                toFollow.add(to_follow);
            }
            User newUser = new User(username, creation, toFollow);
            int nextSize = ((ArrayList)((LinkedTreeMap)((LinkedTreeMap)info.get(i)).get("next")).get("elements")).size();
            Array<String> next = new Array<>();
            for (int j = 0; j < nextSize; j++) {
                String nextUser = (String) ((ArrayList)((LinkedTreeMap)((LinkedTreeMap)info.get(i)).get("next")).get("elements")).get(j);
                next.add(nextUser);
            }
            add((GraphNode<T>) new GraphNode<User>(newUser, next));
        }
    }

}
