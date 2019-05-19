package dataStructures.rTree;

import dataStructures.array.Array;
import json.JsonReader;
import model.Post;

import java.io.FileNotFoundException;


public class RTree {

    private NodeFulla root;

    public RTree() {
        root = new NodeFulla();
    }

    public NodeFulla getRoot() {
        return root;
    }

    public static void main (String[] args){

        JsonReader jr = new JsonReader();
        RTree rt = new RTree();

        try {
            Array<Post> posts = jr.readPosts();

            for (int i = 0; i < posts.size(); i++){
                Post p  = (Post)posts.get(i);
                rt.root.insertPoint(p, p.getLocation().getLatitude(), p.getLocation().getLongitude());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Impossible llegir le fitxer");
        }

//        rt.root.insertPoint(new Post(0,0), 0, 0);
//        rt.root.insertPoint(new Post(1,1), 1, 1);
//        rt.root.insertPoint(new Post(2,2), 2, 2);
//        rt.root.insertPoint(new Post(3,3), 3, 3);
//        rt.root.insertPoint(new Post(4,4), 4, 4);
//        rt.root.insertPoint(new Post(5,5), 5, 5);
//        rt.root.insertPoint(new Post(6,6), 6, 6);

        System.out.println("funciona?");
    }
}
