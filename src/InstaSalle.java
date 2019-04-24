import dataStructures.hashTable.HashTable;
import dataStructures.redBlackTree.RBT;
import model.Post;

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
        HashTable<Post> hashTable = new HashTable<>();
    }
}
