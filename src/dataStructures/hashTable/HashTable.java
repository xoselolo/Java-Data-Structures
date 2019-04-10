package dataStructures.hashTable;

import dataStructures.array.Array;
import model.User;

public class HashTable <T> implements HashTableInterface{

    public static void main(String[] args) {
        HashTable<User> hashTable = new HashTable<User>();

        User u1 = new User("xoselolo", 123, null);
        User u2 = new User("adriklk", 123, null);
        User u3 = new User("oscarfava", 123, null);

        hashTable.add(u1, User.class);
        hashTable.add(u2, User.class);
        hashTable.add(u3, User.class);

        User u4 = new User("juanillo", 123, null);

        hashTable.add(u4, User.class);
    }

    // Const Values
    public static final int POSITIONS = 10;

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

    @Override
    public int hash(Object element, Class c) {
        // TODO: With Post class
        if (c == User.class){
            return ((User)element).hash();
        }else{
            return 0;
        }
    }

    public void add(T element, Class c){
        int hash = hash(element, c);
        getRow(hash).add(element);
    }
}
