package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dataStructures.array.Array;
import dataStructures.hashTable.HashTable;
import json.ConstValues;
import model.model_interfaces.Hashable;

public class Post implements Hashable {

    // Attributes
    private int id;
    private Array<String> liked_by;
    private long published; //timestamp
    private String published_by;
    private Location location;
    private Array<String> hashtags;

    // Constructor

    public Post (double x, double y){
        this.location = new Location(x, y);
    }

    public Post(int id, Array<String> liked_by, long published, String published_by,
                Location location, Array<String> hashtags){
        this.id = id;
        this.liked_by = liked_by;
        this.published = published;
        this.published_by = published_by;
        this.location = location;
        this.hashtags = hashtags;
    }
    public Post(JsonObject postJsonObject){
        this.id = postJsonObject.get(ConstValues.JSON_ID).getAsInt();

        this.liked_by = new Array<String>();
        JsonArray arrayLikedBy = postJsonObject.get(ConstValues.JSON_LIKED_BY).getAsJsonArray();
        int size = arrayLikedBy.size();
        for (int i = 0; i < size; i++){
            this.liked_by.add(arrayLikedBy.get(i).getAsString());
        }

        this.published = postJsonObject.get(ConstValues.JSON_PUBLISHED_WHEN).getAsLong();
        this.published_by = postJsonObject.get(ConstValues.JSON_PUBLISHED_BY).getAsString();
        this.location = new Location(postJsonObject.get(ConstValues.JSON_LOCATION).getAsJsonArray());

        this.hashtags = new Array<String>();
        JsonArray arrayHashtags = postJsonObject.get(ConstValues.JSON_HASHTAGS).getAsJsonArray();
        size = arrayHashtags.size();
        for (int i = 0; i < size; i++){
            this.hashtags.add(arrayHashtags.get(i).getAsString());
        }
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
    public int hash(int index) {
        String hashtag = (String)hashtags.get(index);
        int length = hashtag.length();
        int value = 0;
        for (int i = 0; i < length; i++){
            value += (int)hashtag.charAt(i);
        }
        return value % HashTable.POSITIONS;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Informació del post:");
        builder.append(System.lineSeparator());
        builder.append("\tId: ").append(this.id);
        builder.append(System.lineSeparator());
        builder.append("\tLiked by:");
        builder.append(System.lineSeparator());
        if (this.liked_by.size() == 0){
            builder.append("\t\tNobody");
            builder.append(System.lineSeparator());
        }else{
            int likedBySize = this.liked_by.size();
            for (int i = 0; i < likedBySize; i++){
                builder.append("\t\t").append((String)this.liked_by.get(i));
                builder.append(System.lineSeparator());
            }
        }
        builder.append("\tPublished: ").append(this.published);
        builder.append(System.lineSeparator());
        builder.append("\tPublished by: ").append(this.published_by);
        builder.append(System.lineSeparator());
        builder.append("\tLocation:");
        builder.append(System.lineSeparator());
        builder.append("\t\tLatitude: ").append(this.location.getLatitude());
        builder.append(System.lineSeparator());
        builder.append("\t\tLongitude: ").append(this.location.getLongitude());
        builder.append(System.lineSeparator());
        builder.append("\tHashtags:");
        builder.append(System.lineSeparator());
        if (this.hashtags.size() == 0){
            builder.append("\t\tAny");
            builder.append(System.lineSeparator());
        }else{
            int sizeHashtags = this.hashtags.size();
            for (int i = 0; i < sizeHashtags; i++){
                builder.append("\t\t").append((String)this.hashtags.get(i));
                builder.append(System.lineSeparator());
            }
        }


        return builder.toString();
    }
}
