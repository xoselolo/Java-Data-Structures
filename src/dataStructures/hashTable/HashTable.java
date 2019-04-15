package dataStructures.hashTable;

import dataStructures.array.Array;
import model.User;

public class HashTable <T> implements HashTableInterface{

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
        if (c == User.class){
            return ((User)element).hash();
        }else{
            // TODO: With Post class
            return 0;
        }
    }

    public void add(T element, Class c){
        int hash = hash(element, c);
        getRow(hash).add(element);
    }
}
