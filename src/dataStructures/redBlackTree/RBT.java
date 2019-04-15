package dataStructures.redBlackTree;

public class RBT <T>{
    // Attributes
    private RBTnode root;

    // Constructor
    public RBT(){
        this.root = null;
    }

    // Functions
    public void insertNode(RBTnode<T> newNode){
        if (root == null){
            // Árbol vacío
            this.root = newNode;

            this.root.pare = null;
            this.root.fillE = null;
            this.root.fillD = null;

            this.root.red = true;
        }else{

        }
    }
}
