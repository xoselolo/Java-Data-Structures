import dataStructures.graph.Graph;
import dataStructures.hashTable.HashTable;
import dataStructures.redBlackTree.RBT;
import json.ConstValues;
import model.Post;
import model.User;

import java.util.Comparator;

public class InstaSalle {
    public static void main(String[] args) {
        // Inicialización de las estructuras vacías
        // TODO: Trie
        // TODO: R-Tree
        RBT<Post> RBT = new RBT<Post>(new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        HashTable<Post> hashTable = new HashTable<Post>();
        Graph<User> graph = new Graph<User>();

        int option = 0;
        while (option != ConstValues.EXIT){
            // TODO: mostraMenu();
            // TODO: option = demanaOpcio();
        }
    }
}
