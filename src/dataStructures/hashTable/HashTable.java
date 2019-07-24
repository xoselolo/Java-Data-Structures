package dataStructures.hashTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import dataStructures.array.Array;
import dataStructures.struct_interfaces.HashTableInterface;
import model.Location;
import model.Post;

import java.util.ArrayList;

public class HashTable <T> implements HashTableInterface {

    // Const Values
    public static final int POSITIONS = 100;

    // Attributes
    @SerializedName("hashTable")
    @Expose
    private Array<Array<T>> hashTable;

    // Constructor
    public HashTable(){
        this.hashTable = new Array<Array<T>>();
        for (int i = 0; i < POSITIONS; i++){
            hashTable.add(new Array<T>());
        }
    }

    // Functions
    @Override
    public int size(){
        return POSITIONS;
    }

    private boolean existsElement(T element, int hash){
        return ((Array<T>)hashTable.get(hash)).hasElement(element);
    }

    @Override
    public int rowSize(int indexRow){
        return ((Array<T>)hashTable.get(indexRow)).size();
    }

    @Override
    public Array<T> getRow(int indexRow){
        return (Array<T>)hashTable.get(indexRow);
    }

    @Override
    public T getElementXinRowY(int rowY, int elementX){
        return (T)getRow(rowY).get(elementX);
    }

    @Deprecated
    @Override
    public int hash(Object element, Class c) {
        return 0;
    }

    /**
     * Method that adds an element (must be a {@code Post} object) to the table if it doesn't exists in such position
     * @param element: Post to be inserted
     * @param c: Class of the object to be inserted (must be {@code Post}
     */
    public void add(T element, Class c){
        if (c == Post.class){
            Post post = (Post)element;
            int numHashtags = post.getHashtags().size();
            for (int i = 0; i < numHashtags; i++){
                int hashValue = post.hash(i);
                // If object does not exist in the array, we insert it
                if (!getRow(hashValue).hasElement(element)){
                    getRow(hashValue).add(element);
                    return;
                }
            }
        }
    }

    public Array<Post> buscaSegonsHashtag(String hashtag){
        Array<Post> posts;
        int length = hashtag.length();
        int value = 0;
        for (int i = 0; i < length; i++){
            value += (int)hashtag.charAt(i);
        }
        value = value % HashTable.POSITIONS;
        posts = (Array<Post>) getRow(value);
        Array<Post> postsCopy = new Array<Post>(posts);

        Array<Post> orderedPosts = postsCopy.sortPostsByTime();

        Array<Post> orderedPostsWithOnlyThisHastag = new Array<Post>();
        for (int i = 0; i < orderedPosts.size(); i++){
            if (((Post)orderedPosts.get(i)).getHashtags().hasElement(hashtag)){
                orderedPostsWithOnlyThisHastag.add((Post)orderedPosts.get(i));
            }
        }

        return orderedPostsWithOnlyThisHastag;
    }

    public Array<Array<T>> getHashTable() {
        return hashTable;
    }

    public void printStructure() {
        int size = hashTable.size();
        for (int i = 0; i < size; i++) {
            System.out.print("Position " + i + ": ");
            if (hashTable.get(i) instanceof Array) {
                int rowSize = ((Array) hashTable.get(i)).size();
                for (int j = 0; j < rowSize; j++) {
                    System.out.print(((Post) ((Array) hashTable.get(i)).get(j)).getId() + " ");
                }
                System.out.println();
            }
            else {
                if (hashTable.get(i) instanceof LinkedTreeMap) {
                    int rowSize = ((ArrayList)((LinkedTreeMap) hashTable.get(i)).get("elements")).size();
                    for (int j = 0; j < rowSize; j++) {
                        System.out.print((int)Math.round((double)((LinkedTreeMap)((ArrayList)((LinkedTreeMap) hashTable.get(i)).get("elements")).get(j)).get("id")) + " ");
                    }
                    System.out.println();
                }
            }
        }
    }

    public boolean deletePost(Post post) {
        int numHashtags = post.getHashtags().size();
        for (int i = 0; i < numHashtags; i++) {
            int hashValue = post.hash(i);
            if (getRow(hashValue).hasElement((T) post)){
                int deletePos = getRow(hashValue).getElementPosition((T) post);
                getRow(hashValue).remove(deletePos);
                return true;
            }
        }
        return false;
    }

    public void setHashTable(Array<Array<T>> hashTable) {
        this.hashTable = hashTable;
    }

    public void setImportedInfo(Array<LinkedTreeMap> hashTable) {
        this.hashTable = new Array<>();
        int hashSize = hashTable.size();
        for (int i = 0; i < hashSize; i++) {
            Array<Post> row = new Array<>();
            int rowSize = ((ArrayList)((LinkedTreeMap)hashTable.get(i)).get("elements")).size();
            for (int j = 0; j < rowSize; j++) {
                int id = (int)Math.round((double)((LinkedTreeMap)((ArrayList)((LinkedTreeMap)hashTable.get(i)).get("elements")).get(j)).get("id"));
                Array<String> likedBy = new Array<>();
                int likedBySize = ((ArrayList)((LinkedTreeMap)((LinkedTreeMap)((ArrayList)((LinkedTreeMap)hashTable.get(i)).get("elements")).get(j)).get("liked_by")).get("elements")).size();
                for (int z = 0; z < likedBySize; z++) {
                    String value = (String) ((ArrayList)((LinkedTreeMap)((LinkedTreeMap)((ArrayList)((LinkedTreeMap)hashTable.get(i)).get("elements")).get(j)).get("liked_by")).get("elements")).get(z);
                    likedBy.add(value);
                }
                long published = (long)Math.round((double)((LinkedTreeMap)((ArrayList)((LinkedTreeMap)hashTable.get(i)).get("elements")).get(j)).get("published"));
                String publishedBy = (String)((LinkedTreeMap)((ArrayList)((LinkedTreeMap)hashTable.get(i)).get("elements")).get(j)).get("published_by");
                double latitude = (double)((LinkedTreeMap)((LinkedTreeMap)((ArrayList)((LinkedTreeMap)hashTable.get(i)).get("elements")).get(j)).get("location")).get("latitude");
                double longitude = (double)((LinkedTreeMap)((LinkedTreeMap)((ArrayList)((LinkedTreeMap)hashTable.get(i)).get("elements")).get(j)).get("location")).get("longitude");
                Location location = new Location(latitude, longitude);
                Array<String> hashtags = new Array<>();
                int hashtagsSize = ((ArrayList)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)((ArrayList)((LinkedTreeMap)hashTable.get(i)).get("elements")).get(j)).get("hashtags"))).get("elements")).size();
                for (int z = 0; z < hashtagsSize; z++) {
                    String hashtag = (String)((ArrayList)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)((ArrayList)((LinkedTreeMap)hashTable.get(i)).get("elements")).get(j)).get("hashtags"))).get("elements")).get(z);
                    hashtags.add(hashtag);
                }
                Post post = new Post(id, likedBy, published, publishedBy, location, hashtags);
                row.add(post);
            }
            this.hashTable.add((Array<T>) row);
        }
    }
}
