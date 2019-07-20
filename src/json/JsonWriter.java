package json;

import com.google.gson.Gson;
import dataStructures.Trie.Trie;
import dataStructures.hashTable.HashTable;
import dataStructures.rTree.RTree;
import dataStructures.redBlackTree.RBT;
import model.Post;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class JsonWriter {

    public static final String TRIE_PATH = "files/export/TrieOut.json";
    public static final String RBT_PATH = "files/export/RBTOut.json";
    public static final String RTREE_PATH = "files/export/RTree.json";
    public static final String HASH_PATH = "files/export/HashtableOut.json";


    public static boolean writeRBT(RBT rbt) {
        boolean correct;

        Gson gson = new Gson();
        String jsonToString = gson.toJson(rbt.getRoot());
        try {
            Files.write(Paths.get("files/export/RBTOut.json"), jsonToString.getBytes(), StandardOpenOption.CREATE);
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
            Files.write(Paths.get("files/export/TrieOut.json"), jsonToString.getBytes(), StandardOpenOption.CREATE);
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
            Files.write(Paths.get("files/export/HashtableOut.json"), jsonToString.getBytes(), StandardOpenOption.CREATE);
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
            Files.write(Paths.get("files/export/RTreeOut.json"), jsonToString.getBytes(), StandardOpenOption.CREATE);
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
