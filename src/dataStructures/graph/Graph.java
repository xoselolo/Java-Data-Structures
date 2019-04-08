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

    public static void main(String[] args) {
        Graph<User> userGraph = new Graph<User>();

        Array<String> to_follow = new Array<String>();
        to_follow.add("sitoftonic");
        to_follow.add("oleksiyPutoAmo");

        User user = new User("xoselolo", 123, to_follow);

        userGraph.add(new GraphNode<User>(user)); // Afegim user "xoselolo"

        Array<GraphNode<User>> array = new Array<GraphNode<User>>();
        array.add(new GraphNode<User>(user));

        to_follow.add("albertpv");

        user = new User("elTeteFeka", 1314, to_follow);

        GraphNode<User> node = new GraphNode<User>(user, array);
        userGraph.add(node);

        int connections0 = userGraph.getNodeX(0).getNext().size();
        System.out.println("Index 0 connects with: " + connections0 + " elements");

        int connections1 = userGraph.getNodeX(1).getNext().size();
        System.out.println("Index 0 connects with: " + connections1 + " elements");

        Array<GraphNode<User>> connections = userGraph.getNodeX(1).getNext();
        for (int i = 0; i < connections.size(); i++){
            String username = ((GraphNode<User>)connections.get(i)).getElement().getUsername();
            System.out.println("Connected with " + username);
        }
    }

}
