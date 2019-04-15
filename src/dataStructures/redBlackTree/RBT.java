package dataStructures.redBlackTree;

import java.util.Comparator;

public class RBT <T>{
    // Attributes
    private RBTnode root;
    private Comparator<T> comparator;

    // Constructor
    public RBT(Comparator<T> comparator){
        this.root = null;
        this.comparator = comparator;
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
            // Buscar sitio para insertar el nuevo nodo

        }
    }
}
