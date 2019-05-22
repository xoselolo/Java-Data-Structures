package dataStructures.hashTable;

import dataStructures.array.Array;
import dataStructures.struct_interfaces.HashTableInterface;
import model.Post;

public class HashTable <T> implements HashTableInterface {

    // Const Values
    public static final int POSITIONS = 100;

    // Attributes
    private Array<Array<T>> hashTable;

    // Constructor
    public HashTable(){
        this.hashTable = new Array<Array<T>>();
        for (int i = 0; i < POSITIONS; i++){
            hashTable.add(new Array<T>());
        }
    }

    // Functions
    @Override
    public int size(){
        return POSITIONS;
    }

    private boolean existsElement(T element, int hash){
        return ((Array<T>)hashTable.get(hash)).hasElement(element);
    }

    @Override
    public int rowSize(int indexRow){
        return ((Array<T>)hashTable.get(indexRow)).size();
    }

    @Override
    public Array<T> getRow(int indexRow){
        return (Array<T>)hashTable.get(indexRow);
    }

    @Override
    public T getElementXinRowY(int rowY, int elementX){
        return (T)getRow(rowY).get(elementX);
    }

    @Deprecated
    @Override
    public int hash(Object element, Class c) {
        return 0;
    }

    /**
     * Method that adds an element (must be a {@code Post} object) to the table if it doesn't exists in such position
     * @param element: Post to be inserted
     * @param c: Class of the object to be inserted (must be {@code Post}
     */
    public void add(T element, Class c){
        if (c == Post.class){
            Post post = (Post)element;
            int numHashtags = post.getHashtags().size();
            for (int i = 0; i < numHashtags; i++){
                int hashValue = post.hash(i);
                // If object does not exist in the array, we insert it
                if (!getRow(hashValue).hasElement(element)){
                    getRow(hashValue).add(element);
                    return;
                    // TODO : Change for addOrdered method of array
                }
            }
        }
    }

    public Array<Array<T>> getHashTable() {
        return hashTable;
    }

    public void printStructure() {
        int size = hashTable.size();
        for (int i = 0; i < size; i++) {
            System.out.print("Position " + i + ": ");
            int rowSize = ((Array)hashTable.get(i)).size();
            for (int j = 0; j < rowSize; j++) {
                System.out.print(((Post)((Array)hashTable.get(i)).get(j)).getId() + " ");
            }
            System.out.println();
        }
    }

    public boolean deletePost(Post post) {
        int numHashtags = post.getHashtags().size();
        for (int i = 0; i < numHashtags; i++) {
            int hashValue = post.hash(i);
            if (getRow(hashValue).hasElement((T) post)){
                int deletePos = getRow(hashValue).getElementPosition((T) post);
                getRow(hashValue).remove(deletePos);
                return true;
                // TODO : Change for addOrdered method of array
            }
        }
        return false;
    }
}
