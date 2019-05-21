package dataStructures.rTree;

import dataStructures.array.Array;
import json.JsonFileReader;
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

//    public static void main (String[] args){
//        RTree rt = new RTree();
//
//        try {
//            Array<Post> posts = JsonFileReader.readPosts();
//
//            for (int i = 0; i < posts.size(); i++){
//                Post p  = (Post)posts.get(i);
////                if (i == 171 || i == 172){
////                    System.out.println("wait");
////                }
//                rt.root.insertPoint(p, p.getLocation().getLatitude(), p.getLocation().getLongitude());
//                System.out.println(i);
//            }
//
//        } catch (FileNotFoundException e) {
//            System.out.println("Impossible llegir le fitxer");
//        }
//
//        System.out.println("funciona?");
//    }

}
