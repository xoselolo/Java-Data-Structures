package json;

import com.google.gson.Gson;
import dataStructures.Trie.Trie;
import dataStructures.hashTable.HashTable;
import dataStructures.redBlackTree.RBT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class JsonWriter {



    public static boolean writeRBT(RBT rbt) {
        boolean correct;
        Gson gson = new Gson();
        String jsonToString = gson.toJson(rbt.getRoot());
        try {
            Files.write(Paths.get("files/RBTOut.json"), jsonToString.getBytes(), StandardOpenOption.CREATE);
            correct = true;
        } catch (IOException e){
            correct = false;
        }
        return correct;
    }

    public static boolean writeTrie(Trie trie) {
        boolean correct;
        Gson gson = new Gson();
        String jsonToString = gson.toJson(trie.getRoot());
        try {
            Files.write(Paths.get("files/TrieOut.json"), jsonToString.getBytes(), StandardOpenOption.CREATE);
            correct = true;
        } catch (IOException e){
            correct = false;
        }
        return correct;
    }

    /*
    public static boolean writeHashTable(HashTable<> hashTable) {

    }
    */
}
