package dataStructures.loloRTree;

import dataStructures.array.Array;
import model.Location;
import model.Post;

import javax.swing.plaf.synth.Region;
import java.util.Scanner;

public class RTree {
    // Attributes
    public static RTreeNode root;


    public static void main(String[] args) {
        root = new LeafNode(null);

        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        int IDPOST = 1;
        while (run){
            System.out.println("Latitude: ");
            double lat = scanner.nextDouble();
            System.out.println("Longitude: ");
            double lon = scanner.nextDouble();

            Post newPost = new Post(IDPOST, new Array<String>(), 121212, "P" + IDPOST++, new Location(lat, lon), new Array<String>());

            insertPost(newPost);

            System.out.println("Do you want to insert another post? (1 = YES / 0 = NO)");
            run = scanner.nextInt() == 1;
        }
    }

    /**
     * Method to call externally to insert another post
     * @param newPost New post to be inserted
     */
    public static void insertPost(Post newPost){
        if (root != null) {
            root.insertPost(newPost, new Array<Integer>(), true);
        }
        else {
            root = new LeafNode(null);
            root.insertPost(newPost, new Array<Integer>(), true);
        }
    }

    /**
     * Method to call when the root has changed
     * It must be called when the linking of all nodes has already been done.
     * @param newRoot
     */
    public static void changeRoot(RegionNode newRoot) {
        root = newRoot;
    }

    public static RTreeNode getRoot() {
        return root;
    }

    public void printStructure() {
        if (root == null) {
            System.out.println("null");
        }
        else {
            System.out.println(root.toString());
        }
    }
}
