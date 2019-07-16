package dataStructures.redBlackTree;

import model.Post;
import model.User;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * This is our RedBlackTree generic implementation for every class.
 *
 * Features:
 *      - Every node is RED or BLACK
 *      - ROOT is always BLACK
 *      - if a node is RED, its sons are BLACK
 *      - number of black nodes in left = number of black nodes in right
 *
 * Operations:
 *      - Create
 *      - Insert
 *      - Delete
 *      insert and delete use rotations and colorChanging:
 *          - Rotations:
 *               - RR
 *               - LL
 *      - Search
 *
 * Costs:
 *      - Insert = O (log n)
 *      - Search = O (log n)
 *      - Delete = O (log n)
 *      - Preorder, Inorder, postOrder = O (n)
 * @param <T> Class of the element we want to store in the tree
 */
public class RBT <T>{
    // Attributes
    private RBTnode<T> root;
    private Comparator<T> comparator;

    // Constructor
    public RBT(Comparator<T> comparator){
        this.root = null;
        this.comparator = comparator;
    }

    // Root Getter
    public RBTnode<T> getRoot(){
        return root;
    }

    public static void main(String[] args) {
        Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        };
        RBT<User> userRBT = new RBT<User>(userComparator);

        User u1 = new User("Antonio", 1, null);
        User u2 = new User("Benjamin", 1, null);
        User u3 = new User("Claudia", 1, null);
        User u4 = new User("David", 1, null);
        User u5 = new User("Eugeni", 1, null);
        User u6 = new User("Fatima", 1, null);
        User u7 = new User("Gerard", 1, null);

        userRBT.insertNode(new RBTnode<User>(u1), userRBT.root, null);
        userRBT.insertNode(new RBTnode<User>(u2), userRBT.root, null);
        userRBT.insertNode(new RBTnode<User>(u3), userRBT.root, null);
        userRBT.insertNode(new RBTnode<User>(u4), userRBT.root, null);
        userRBT.insertNode(new RBTnode<User>(u5), userRBT.root, null);
        userRBT.insertNode(new RBTnode<User>(u6), userRBT.root, null);
        userRBT.insertNode(new RBTnode<User>(u7), userRBT.root, null);

        String inOrderTOString = userRBT.inOrderToString();

        System.out.println(inOrderTOString);
    }

    // Functions
    // ---------- INSERTION ----------
    /**
     * Inserts the {@code newNode} as pointer if it's possible or make a recursively call
     * to insert it on the lef/right son.
     * First call be me made to (newNode, root, null)
     *
     * Rules and rotations:
     *      - New node is always RED
     *      - Cas 1: Dad and uncle are RED
     *          -> ChangeColor between grandpa and dad+uncle
     *              -> Except when grandpa is the root element
     *      - Cas 2: LR / RL with dad RED and grandpa BLACK
     *          LR: -> RR of dad
     *              -> Cas 3:
     *                  -> LL of grandpa changing colors
     *          RL: -> LL of dad
     *              -> Cas 3:
     *                  -> RR of grandpa changing colors
     *
     * @param newNode Node of the red-black tree to be inserted
     * @param pointer Node we under evaluation
     * @param dad Node father of pointer node
     */
    public void insertNode(RBTnode<T> newNode, RBTnode<T> pointer, RBTnode<T> dad){
        if (root == null){
            root = new RBTnode<T>(newNode.element, null);
            root.red = false;
            root.fillE = null;
            root.fillD = null;
        } else {
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


                if (cas1(pointer)){
                    cas1Rotation(pointer);

                }else if (cas2a(pointer)){
                    cas2aRotation(pointer);
                    cas3aRotation(pointer.fillE);

                }else if (cas2b(pointer)){
                    cas2bRotation(pointer);
                    cas3bRotation(pointer.fillD);

                }else if (cas3a(pointer)){
                    cas3aRotation(pointer);

                }else if (cas3b(pointer)){
                    cas3bRotation(pointer);
                }

            } else {
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

    // ROTATIONS
    private boolean cas1(@NotNull RBTnode<T> pointer) {
        RBTnode<T> dad, uncle, grandpa;
        dad = pointer.pare;

        if (dad == null){
            return false;
        }

        grandpa = dad.pare;

        if (grandpa == null){
            return false;
        }

        if (comparator.compare(dad.element, grandpa.element) < 0){
            // Dad is left son of grandpa
            // uncle will be then the right son of grandpa
            uncle = grandpa.fillD;
        }else{
            // Dad is right son of grandpa
            // uncle will be then the left son of grandpa
            uncle = grandpa.fillE;
        }

        if (uncle == null){
            return false;
        }

        if (dad.red && uncle.red && !grandpa.red){
            return true;
        }

        return false;
    }
    private void cas1Rotation(@NotNull RBTnode<T> pointer) {
        RBTnode<T> dad, uncle, grandpa;
        dad = pointer.pare;

        grandpa = dad.pare;

        if (comparator.compare(dad.element, grandpa.element) < 0){
            // Dad is left son of grandpa
            // uncle will be then the right son of grandpa
            uncle = grandpa.fillD;
        }else{
            // Dad is right son of grandpa
            // uncle will be then the left son of grandpa
            uncle = grandpa.fillE;
        }

        dad.red = false;
        uncle.red = false;

        if (!grandpa.equals(root)){
            grandpa.red = true;
        }
    }

    private boolean cas2a(@NotNull RBTnode<T> pointer) {
        RBTnode<T> dad, grandpa;
        dad = pointer.pare;

        if (dad == null){
            return false;
        }

        grandpa = dad.pare;

        if (grandpa == null){
            return false;
        }

        if (comparator.compare(pointer.element, dad.element) > 0
                && comparator.compare(dad.element, grandpa.element) < 0){
            if (pointer.red && dad.red && !grandpa.red){
                return true;
            }
        }
        return false;
    }
    private void cas2aRotation(@NotNull RBTnode<T> pointer) {
        RBTnode<T> dad, grandpa;
        dad = pointer.pare;
        grandpa = dad.pare;

        pointer.pare = grandpa;
        dad.fillD = pointer.fillE;
        dad.pare = pointer;
        if (dad.fillD != null){
            dad.fillD.pare = dad;
        }
        grandpa.fillE = pointer;
        pointer.fillE = dad;
    }
    private void cas3aRotation(@NotNull RBTnode<T> grandson){
        RBTnode<T> pointer, grandpa;
        pointer = grandson.pare;
        grandpa = pointer.pare;

        // ColorSwapping
        boolean auxColor = grandpa.red;
        grandpa.red = pointer.red;
        pointer.red = auxColor;

        if (grandpa.pare != null){
            pointer.pare = grandpa.pare;
            pointer.pare.fillE = pointer;
        }
        grandpa.pare = pointer;
        grandpa.fillE = pointer.fillD;
        if (grandpa.fillE != null){
            grandpa.fillE.pare = grandpa;
        }
        pointer.fillD = grandpa;

        if (root.element == grandpa.element){
            root = pointer;
        }
    }

    private boolean cas2b(@NotNull RBTnode<T> pointer) {
        RBTnode<T> dad, grandpa;
        dad = pointer.pare;

        if (dad == null){
            return false;
        }

        grandpa = dad.pare;

        if (grandpa == null){
            return false;
        }

        if (comparator.compare(pointer.element, dad.element) < 0
                && comparator.compare(dad.element, grandpa.element) > 0){
            if (pointer.red && dad.red && !grandpa.red){
                return true;
            }
        }
        return false;
    }
    private void cas2bRotation(@NotNull RBTnode<T> pointer) {
        RBTnode<T> dad, grandpa;
        dad = pointer.pare;
        grandpa = dad.pare;

        pointer.pare = grandpa;
        dad.fillE = pointer.fillD;
        dad.pare = pointer;
        if (dad.fillE != null){
            dad.fillE.pare = dad;
        }
        grandpa.fillD = pointer;
        pointer.fillD = dad;
    }
    private void cas3bRotation(@NotNull RBTnode<T> grandson){
        RBTnode<T> pointer, grandpa;
        pointer = grandson.pare;
        grandpa = pointer.pare;

        // ColorSwapping
        boolean auxColor = grandpa.red;
        grandpa.red = pointer.red;
        pointer.red = auxColor;

        if (grandpa.pare != null){
            pointer.pare = grandpa.pare;
            pointer.pare.fillD = pointer;
        }
        else {
            pointer.pare = null;
        }
        grandpa.pare = pointer;
        grandpa.fillD = pointer.fillE;
        if (grandpa.fillD != null){
            grandpa.fillD.pare = grandpa;
        }
        pointer.fillE = grandpa;

        if (root.element == grandpa.element){
            root = pointer;
        }
    }

    private boolean cas3a(@NotNull RBTnode<T> pointer) {
        RBTnode<T> dad = pointer.pare;

        if (dad == null){
            return false;
        }

        RBTnode<T> grandpa = dad.pare;

        if (grandpa == null){
            return false;
        }

        if (grandpa.fillE == dad && dad.fillE == pointer){
            if (pointer.red && dad.red && !grandpa.red){
                return true;
            }
        }

        return false;
    }
    private boolean cas3b(@NotNull RBTnode<T> pointer) {
        RBTnode<T> dad = pointer.pare;

        if (dad == null){
            return false;
        }

        RBTnode<T> grandpa = dad.pare;

        if (grandpa == null){
            return false;
        }

        if (grandpa.fillD == dad && dad.fillD == pointer){
            if (pointer.red && dad.red && !grandpa.red){
                return true;
            }
        }

        return false;
    }


    // ---------- SEARCH ----------
    /**
     * Searches the specified element in the tree
     * @param element Element to be searched in the tree
     * @param pointer Node where we are findind
     * @return {@code null} when element is not in the tree or the {@code T} element if found
     */
    public T search(T element, RBTnode<T> pointer){
        if (pointer == null){
            return null;
        }else{
            if (comparator.compare(element, pointer.element) < 0){
                // Left son search
                return search(element, pointer.fillE);
            }else if (comparator.compare(element, pointer.element) > 0){
                // Right son search
                return search(element, pointer.fillD);
            }else{
                return pointer.element;
            }
        }
    }


    // ---------- TOSTRING ----------
    /**
     * Returns the string of the inOrder track recursively
     * @return inOrdre representation starting by the root node
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
