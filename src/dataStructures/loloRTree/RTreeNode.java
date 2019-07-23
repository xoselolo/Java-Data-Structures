package dataStructures.loloRTree;

import dataStructures.array.Array;
import model.Post;

public abstract class RTreeNode {
    public static final int M = 3;
    public static final int MIN = 2;
    //public static final int MIN = Math.round(M / 2) - 1;

    public static final int LEAF_DEPTH = 0;

    public int numSons;
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
