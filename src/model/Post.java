package model;

import dataStructures.array.Array;
import dataStructures.hashTable.HashTable;
import model.model_interfaces.Hashable;

public class Post implements Hashable {

    // Attributes
    private int id;
    private Array<String> liked_by;
    private long published; //timestamp
    private String published_by;
    private Location location;
    // Todo: Atribut hastags (array de hashtags) , de momento lo pondremos como array de Strings
    private Array<String> hashtags;

    // Constructor
    public Post(int id, Array<String> liked_by, long published, String published_by,
                Location location, Array<String> hashtags){
        this.id = id;
        this.liked_by = liked_by;
        this.published = published;
        this.location = location;
        this.hashtags = hashtags;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Array<String> getLiked_by() {
        return liked_by;
    }
    public void setLiked_by(Array<String> liked_by) {
        this.liked_by = liked_by;
    }
    public long getPublished() {
        return published;
    }
    public void setPublished(long published) {
        this.published = published;
    }
    public String getPublished_by() {
        return published_by;
    }
    public void setPublished_by(String published_by) {
        this.published_by = published_by;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public Array<String> getHashtags() {
        return hashtags;
    }
    public void setHashtags(Array<String> hashtags) {
        this.hashtags = hashtags;
    }

    // Functions
    @Override
    public int hash() {
        return hashCode() % HashTable.POSITIONS;
    }


}
