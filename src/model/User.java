package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dataStructures.array.Array;
import dataStructures.hashTable.HashTable;
import json.ConstValues;
import model.model_interfaces.Hashable;

/**
    2.1 CSV d’Usuaris
 * Cada usuari tindra:
 * • username: nom de l’usuari, on aquest sera unic.
 * • creation: data de creacio del perfil en format timestamp.
 * • to follow: array d’usuaris que segueix. Es trobara un array
                amb els usernames dels usuaris als que segueix.
 */
public class User implements Hashable{

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
    public User(JsonObject asJsonObject) {
        this.username = asJsonObject.get(ConstValues.JSON_USERNAME_TAG).getAsString();
        this.creation = asJsonObject.get(ConstValues.JSON_CREATION_TAG).getAsLong();
        this.to_follow = extractToFollow(asJsonObject.get(ConstValues.JSON_TO_FOLLOW_TAG).getAsJsonArray());
    }
    private Array<String> extractToFollow(JsonArray asJsonArray) {
        Array<String> toFollow = new Array<String>();

        int size = asJsonArray.size();
        for (int i = 0; i < size; i++){
            toFollow.add(asJsonArray.get(i).getAsString());
        }

        return toFollow;
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

    // Functions
    @Deprecated
    @Override
    public int hash(int i) {
        return hashCode() % HashTable.POSITIONS;
        //SuperFastHash -> #link: https://github.com/toaler/Hashing/blob/master/org.bpt.hash.provider/src/org/bpt/hash/provider/SuperFastHash.java
    }

    @Override
    public String toString() {
        /*
        int sizeToFollow = to_follow.size();
        StringBuilder sb = new StringBuilder();
        sb.append("User{" + "username='" + username + '\'' + ", creation=" + creation + ", to_follow= [");
        for (int i = 0; i < sizeToFollow; i++) {
            sb.append("'");
            sb.append(to_follow.get(i));
            sb.append("',");
        }
        sb.append("]}");
        return sb.toString();
        */
        return getUsername();
    }
}