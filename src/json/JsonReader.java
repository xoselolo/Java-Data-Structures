package json;

import com.google.gson.Gson;
import dataStructures.Trie.Trie;
import dataStructures.Trie.TrieNode;
import dataStructures.Trie.TrieRoot;
import dataStructures.array.Array;
import dataStructures.graph.Graph;
import dataStructures.hashTable.HashTable;
import model.Post;
import model.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class JsonReader {


    public static boolean importFromTrie(String filePath, Trie trie) {
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            TrieRoot result = gson.fromJson(br, TrieRoot.class);
            if (result != null) {
                TrieRoot root = Trie.setImportedInfo(result);
                trie.setRoot(root);
            }
        } catch (FileNotFoundException e) {
            System.out.println("[ERR] - No s'ha pogut trobar l'arxiu importat de l'estructura Trie");
            return false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean importFromRBT(String filePath) {
        return true;
    }

    public static boolean importFromHashtable(String filePath, HashTable hashTable) {
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            Array result = gson.fromJson(br, Array.class);
            if (result != null) {
                hashTable.setImportedInfo(result);
            }
        } catch (FileNotFoundException e) {
            System.out.println("[ERR] - No s'ha pogut trobar l'arxiu importat de l'estructura Hashtable");
            return false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean importFromRTree(String filePath) {
        return true;
    }

    public static boolean importFromGraph(String filePath, Graph<User> graph) {
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            Array result = gson.fromJson(br, Array.class);
            if (result != null) {
                graph.setImportedInfo(result);
            }
        } catch (FileNotFoundException e) {
            System.out.println("[ERR] - No s'ha pogut trobar l'arxiu importat de l'estructura Graph");
            return false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
