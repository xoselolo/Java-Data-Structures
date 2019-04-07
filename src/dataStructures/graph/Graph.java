package dataStructures.graph;

import dataStructures.array.Array;

public class Graph {
    public static void main(String[] args) {
        Array<Integer> list = new Array<Integer>();

        for (int i = 0; i < 5; i++){
            list.add(i);
        }

        for (int i = 0; i < list.size(); i++){
            int num = (int) list.get(i);
            System.out.println("Element in index " + i + " is " + num);
        }
    }
}
