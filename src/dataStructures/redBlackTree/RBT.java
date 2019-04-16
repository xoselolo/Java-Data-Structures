package dataStructures.redBlackTree;

import model.User;

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

    public static void main(String[] args) {
        Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        };
        RBT<User> userRBT = new RBT<User>(userComparator);

        User u1 = new User("luis", 1, null);
        User u3 = new User("oleksiy", 1, null);
        User u2 = new User("xose", 1, null);
        User u4 = new User("sallefest", 1, null);
        User u5 = new User("baltasar", 1, null);
        User u6 = new User("gaspar", 1, null);

        userRBT.insertNode(new RBTnode<User>(u1), userRBT.root, null);
        userRBT.insertNode(new RBTnode<User>(u2), userRBT.root, null);
        userRBT.insertNode(new RBTnode<User>(u3), userRBT.root, null);
        userRBT.insertNode(new RBTnode<User>(u4), userRBT.root, null);
        userRBT.insertNode(new RBTnode<User>(u5), userRBT.root, null);
        userRBT.insertNode(new RBTnode<User>(u6), userRBT.root, null);

        String inOrderTOString = userRBT.inOrderToString();

        System.out.println(inOrderTOString);
    }

    // Functions
    /**
     * Inserts the {@code newNode} as pointer if it's possible or make a recursively call
     * to insert it on the lef/right son.
     * First call be me made to (newNode, root, null)
     * @param newNode Node of the red-black tree to be inserted
     * @param pointer Node we under evaluation
     * @param dad Node father of pointer node
     */
    public void insertNode(RBTnode<T> newNode, RBTnode<T> pointer, RBTnode<T> dad){
        if (root == null){
            root = new RBTnode(newNode.element, null);
            root.red = false;
            root.fillE = null;
            root.fillD = null;
            return;
        }else{
            if (pointer == null){
                // Node is null
                // It's a leaf
                pointer = new RBTnode<T>(newNode.element, dad);
                //pointer.element = newNode.element;
                //pointer.pare = dad;
                pointer.red = true;
                pointer.fillE = null;
                pointer.fillD = null;

                if (comparator.compare(dad.element, pointer.element) > 0){
                    // Serà fill Esquerre
                    dad.fillE = pointer;
                }else{
                    // Serà fill Dret
                    dad.fillD = pointer;
                }

                // TODO: Make Rotations if necessary
            }else{
                // Go down until not a leaf
                if (comparator.compare(newNode.element, pointer.element) < 0){
                    // Down to left son
                    insertNode(newNode, pointer.fillE, pointer);
                }else{
                    // Down to right son
                    insertNode(newNode, pointer.fillD, pointer);
                }
            }
        }
    }

    // In ORDRE (to String)
    /**
     * First call to root
     * @return
     */
    public String inOrderToString(){
        StringBuilder builder = new StringBuilder();

        inOrder(root, builder);

        return builder.toString();
    }


    private void inOrder(RBTnode<T> node, StringBuilder builder){
        if (node != null){
            inOrder(node.fillE, builder);
            builder.append("-").append(node.element.toString());
            inOrder(node.fillD, builder);
        }
    }
}
