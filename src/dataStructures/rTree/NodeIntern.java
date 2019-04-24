package dataStructures.rTree;

import dataStructures.array.Array;

public class NodeIntern {

    private double height;
    private double weight;
    private NodeIntern son;

    public NodeIntern() {
        height = 0;
        weight = 0;
        son = null;
    }
}
