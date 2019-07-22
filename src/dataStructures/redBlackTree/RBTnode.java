package dataStructures.redBlackTree;

import model.Post;

public class RBTnode <T> {
    // Attributes
    public RBTnode pare;

    public boolean red;
    public T element;

    public RBTnode<T> fillE;
    public RBTnode<T> fillD;

    // Constructor
    public RBTnode(T element){
        this.element = element;
        this.red = true;
    }
    public <Post> RBTnode(T element, RBTnode pare){
        this.element = element;
        this.pare = pare;
    }

    @Override
    public String toString() {
        return "{" +
                "\"red\": " + red +
                ",\n\"element\": {" + ((Post)element).toStringExport() + "}" +
                ",\n\"fillE\": " + fillE +
                ",\n\"fillD\": " + fillD +
                '}';
    }
}
