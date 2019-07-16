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



    public static boolean writeRBT(RBT rbt) {
        boolean correct;
        /*
        Gson gson = new Gson();
        String jsonToString = gson.toJson(rbt.getRoot());
        try {
            Files.write(Paths.get("files/export/RBTOut.json"), jsonToString.getBytes(), StandardOpenOption.CREATE);
            correct = true;
        } catch (IOException e){
            correct = false;
        }
        */

        try {
            FileWriter fileWriter = new FileWriter("files/export/RBTOut.json");
            fileWriter.write(rbt.getRoot().toString());
            fileWriter.close();
            correct = true;
        } catch (Exception e) {
            e.printStackTrace();
            correct = false;
        }
        return correct;
    }

    public static boolean writeTrie(Trie trie) {
        boolean correct;

        /*
        Gson gson = new Gson();
        String jsonToString = gson.toJson(trie.getRoot());
        try {
            Files.write(Paths.get("files/export/TrieOut.json"), jsonToString.getBytes(), StandardOpenOption.CREATE);
            correct = true;
        } catch (IOException e){
            correct = false;
        }
        */

        try {
            FileWriter fileWriter = new FileWriter("files/export/TrieOut.json");
            //System.out.println(trie.getRoot().toString());
            fileWriter.write(trie.getRoot().toString());
            fileWriter.close();
            correct = true;
        } catch (Exception e) {
            e.printStackTrace();
            correct = false;
        }

        return correct;
    }

    public static boolean writeHashTable(HashTable<Post> hashTable) {
        boolean correct;
        /*
        Gson gson = new Gson();
        String jsonToString = gson.toJson(hashTable.getHashTable());
        try {
            Files.write(Paths.get("files/export/HashtableOut.json"), jsonToString.getBytes(), StandardOpenOption.CREATE);
            correct = true;
        } catch (IOException e){
            correct = false;
        }
        */
        try {
            FileWriter fileWriter = new FileWriter("files/export/HashOut.json");
            //System.out.println(hashTable.getHashTable().toString());
            fileWriter.write(hashTable.getHashTable().toString());
            fileWriter.close();
            correct = true;
        } catch (Exception e) {
            e.printStackTrace();
            correct = false;
        }

        return correct;
    }

    public static boolean writeRTree(RTree rTree) {
        boolean correct;
        /*
        Gson gson = new Gson();
        String jsonToString = gson.toJson(rTree.getRoot());
        try {
            Files.write(Paths.get("files/export/RTreeOut.json"), jsonToString.getBytes(), StandardOpenOption.CREATE);
            correct = true;
        } catch (IOException e){
            correct = false;
        }
        */

        try {
            FileWriter fileWriter = new FileWriter("files/export/HashOut.json");
            //System.out.println(hashTable.getHashTable().toString());
            fileWriter.write(rTree.getRoot().toString());
            fileWriter.close();
            correct = true;
        } catch (Exception e) {
            e.printStackTrace();
            correct = false;
        }

        return correct;
    }

}
