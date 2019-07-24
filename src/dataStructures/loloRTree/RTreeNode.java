package dataStructures.loloRTree;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dataStructures.array.Array;
import model.Post;

public abstract class RTreeNode {
    public static final int M = 100;
    public static final int MIN = 50;
    //public static final int MIN = Math.round(M / 2);

    public static final int LEAF_DEPTH = 0;

    @SerializedName("numSons")
    @Expose
    public int numSons;

    @SerializedName("depth")
    @Expose
    public int depth;

    public boolean fitsSon(){
        return numSons < M;
    }
    /*public boolean isLeaf(){
        return depth == 0;
    }
    public boolean needsAllRemainingInHere(int remainingElements){
        return MIN - numSons == remainingElements;
    }
    public abstract boolean insertSon(Post newPost);
    public abstract void split(Post newPost);*/


    // NEW IMPLEMENTATION
    public abstract void insertPost(Post newPost, Array<Integer> indexes, boolean refresh);
    public abstract void split(Post newPost, Array<Integer> indexes);
    public abstract void receiveSplit(RegionNode newCouple, Array<Integer> indexes, Post newPost);
    //public abstract void actualitzaRegioCapAmunt(Post newPost, Array<Integer> indexes);
    //public abstract void actualitzaRegioX(Post newPost, Array<Integer> indexes);
    public boolean needsAllRemainingInHere(int remainingElements){
        return MIN - numSons == remainingElements;
    }

    public abstract String toString();
}
