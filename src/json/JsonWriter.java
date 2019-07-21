package json;

import com.google.gson.Gson;
import dataStructures.Trie.Trie;
import dataStructures.graph.Graph;
import dataStructures.hashTable.HashTable;
import dataStructures.rTree.RTree;
import dataStructures.redBlackTree.RBT;
import model.Post;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class JsonWriter {

    public static final String TRIE_PATH = "files/export/TrieOut.json";
    public static final String RBT_PATH = "files/export/RBTOut.json";
    public static final String RTREE_PATH = "files/export/RTree.json";
    public static final String HASH_PATH = "files/export/HashtableOut.json";
    public static final String GRAPH_PATH = "files/export/GraphOut.json";


    public static boolean writeRBT(RBT rbt) {
        boolean correct;

        Gson gson = new Gson();
        String jsonToString = gson.toJson(rbt.getRoot());
        try {
            Files.deleteIfExists(Paths.get(RBT_PATH));
            Files.write(Paths.get(RBT_PATH), jsonToString.getBytes(), StandardOpenOption.CREATE);
            correct = true;
        } catch (IOException e){
            correct = false;
        }

        /*
        try {
            FileWriter fileWriter = new FileWriter(RBT_PATH);
            fileWriter.write(rbt.getRoot().toString());
            fileWriter.close();
            correct = true;
        } catch (Exception e) {
            e.printStackTrace();
            correct = false;
        }
        */
        return correct;
    }

    public static boolean writeTrie(Trie trie) {
        boolean correct;


        Gson gson = new Gson();
        String jsonToString = gson.toJson(trie.getRoot());
        try {
            Files.deleteIfExists(Paths.get(TRIE_PATH));
            Files.write(Paths.get(TRIE_PATH), jsonToString.getBytes(), StandardOpenOption.CREATE);
            correct = true;
        } catch (IOException e){
            correct = false;
        }

        /*
        try {
            FileWriter fileWriter = new FileWriter(TRIE_PATH);
            //System.out.println(trie.getRoot().toString());
            fileWriter.write(trie.getRoot().toString());
            fileWriter.close();
            correct = true;
        } catch (Exception e) {
            e.printStackTrace();
            correct = false;
        }
        */

        return correct;
    }

    public static boolean writeHashTable(HashTable<Post> hashTable) {
        boolean correct;

        Gson gson = new Gson();
        String jsonToString = gson.toJson(hashTable.getHashTable());
        try {
            Files.deleteIfExists(Paths.get(HASH_PATH));
            Files.write(Paths.get(HASH_PATH), jsonToString.getBytes(), StandardOpenOption.CREATE);
            correct = true;
        } catch (IOException e){
            correct = false;
        }

        /*
        try {
            FileWriter fileWriter = new FileWriter(HASH_PATH);
            //System.out.println(hashTable.getHashTable().toString());
            fileWriter.write(hashTable.getHashTable().toString());
            fileWriter.close();
            correct = true;
        } catch (Exception e) {
            e.printStackTrace();
            correct = false;
        }
        */

        return correct;
    }

    public static boolean writeRTree(RTree rTree) {
        boolean correct;

        Gson gson = new Gson();
        String jsonToString = gson.toJson(rTree.getRoot());
        try {
            Files.deleteIfExists(Paths.get(RTREE_PATH));
            Files.write(Paths.get(RTREE_PATH), jsonToString.getBytes(), StandardOpenOption.CREATE);
            correct = true;
        } catch (IOException e){
            correct = false;
        }

        /*
        try {
            FileWriter fileWriter = new FileWriter(RTREE_PATH);
            //System.out.println(hashTable.getHashTable().toString());
            fileWriter.write(rTree.getRoot().toString());
            fileWriter.close();
            correct = true;
        } catch (Exception e) {
            e.printStackTrace();
            correct = false;
        }
        */
        return correct;
    }

    public static boolean writeGraph(Graph graph) {
        boolean correct;

        Gson gson = new Gson();
        String jsonToString = gson.toJson(graph.getAdjacencyList());
        try {
            Files.deleteIfExists(Paths.get(GRAPH_PATH));
            Files.write(Paths.get(GRAPH_PATH), jsonToString.getBytes(), StandardOpenOption.CREATE);
            correct = true;
        } catch (IOException e){
            correct = false;
        }

        /*
        try {
            FileWriter fileWriter = new FileWriter(RTREE_PATH);
            //System.out.println(hashTable.getHashTable().toString());
            fileWriter.write(rTree.getRoot().toString());
            fileWriter.close();
            correct = true;
        } catch (Exception e) {
            e.printStackTrace();
            correct = false;
        }
        */
        return correct;
    }

}
