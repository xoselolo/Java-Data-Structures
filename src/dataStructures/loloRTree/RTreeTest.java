package dataStructures.loloRTree;

import dataStructures.array.Array;
import model.Location;
import model.Post;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RTreeTest {

    public static ArrayList<Location> locations;

    void initLocations(){
        locations = new ArrayList<Location>();
        locations.add(new Location(1, 1));
        locations.add(new Location(4, 1));
        locations.add(new Location(5, 4));
        locations.add(new Location(0, 4));
        locations.add(new Location(2, 3));
        locations.add(new Location(2, 2));
        locations.add(new Location(3, 2));
        locations.add(new Location(3, 3));
        locations.add(new Location(5, 0));
        locations.add(new Location(3, 1));
        locations.add(new Location(0, 3));
        locations.add(new Location(0, 2));
        locations.add(new Location(2, 1));
        locations.add(new Location(2, 0));
        locations.add(new Location(2.5, 2.5)); // 1 - 15
        locations.add(new Location(1, 1));
        locations.add(new Location(1, 1));
        locations.add(new Location(1, 1));
        locations.add(new Location(1, 1));
        locations.add(new Location(1, 1));
        locations.add(new Location(1, 1));
        locations.add(new Location(1, 1));
        locations.add(new Location(1, 1));
        locations.add(new Location(1, 1));
    }

    @Test
    public void testRTree(){
        RTree.root = new LeafNode(null);
        initLocations();

        int IDPOST = 1;
        for (int i = 0; i < 10; i++){
            Post newPost = new Post(IDPOST, new Array<String>(), 121212, "P" + IDPOST++, locations.get(i), new Array<String>());

            System.out.println("Inserim el post " + newPost.getPublished_by());
            if (i == 9){
                System.out.println("Hello wey");
            }
            RTree.insertPost(newPost);
        }

        System.out.println(System.lineSeparator() + System.lineSeparator() + "Vamos a mostrar el resultado final .......");
        System.out.println(RTree.root.toString());


    }

}