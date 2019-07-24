package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dataStructures.array.Array;
import dataStructures.hashTable.HashTable;
import json.ConstValues;
import model.model_interfaces.Hashable;
import org.jetbrains.annotations.NotNull;

public class Post implements Hashable, Comparable<Post> {

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

    public String toStringExport() {
        StringBuilder sb = new StringBuilder();

        sb.append("\"id\": " + id + ",\"liked_by\": " + liked_by + ",\"published\": " + published + ",\"published_by\": \"" + published_by + "\",\"location\": " + location.toString() + ",\"hashtags\": " + hashtags+ "");

        return sb.toString();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(@NotNull Post p2) {
        return Long.compare(p2.getPublished(), this.published);
    }
}
