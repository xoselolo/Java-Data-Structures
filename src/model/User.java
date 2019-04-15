package model;

import dataStructures.array.Array;
import dataStructures.hashTable.HashTable;
import model.model_interfaces.Hashable;

/**
    2.1 CSV d’Usuaris
 * Cada usuari tindra:
 * • username: nom de l’usuari, on aquest sera unic.
 * • creation: data de creacio del perfil en format timestamp.
 * • to follow: array d’usuaris que segueix. Es trobara un array
                amb els usernames dels usuaris als que segueix.
 */
public class User implements Hashable {
    // Attributes
    private String username;
    private long creation; // timestamp
    private Array<String> to_follow;

    // Constructor
    public User(String username, long creation, Array<String> to_follow){
        this.username = username;
        this.creation = creation;
        this.to_follow = to_follow;
    }

    // Getters & Stters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public long getCreation() {
        return creation;
    }
    public void setCreation(long creation) {
        this.creation = creation;
    }
    public Array<String> getTo_follow() {
        return to_follow;
    }
    public void setTo_follow(Array<String> to_follow) {
        this.to_follow = to_follow;
    }

    @Override
    public int hash() {
        return hashCode() % HashTable.POSITIONS;
        //TODO SuperFastHash -> #link: https://github.com/toaler/Hashing/blob/master/org.bpt.hash.provider/src/org/bpt/hash/provider/SuperFastHash.java
    }

    // Functions

}