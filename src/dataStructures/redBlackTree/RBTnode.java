package dataStructures.redBlackTree;

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
    public RBTnode(T element, RBTnode pare){
        this(element);
        this.pare = pare;
    }
}
