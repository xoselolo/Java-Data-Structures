package dataStructures.struct_interfaces;

import dataStructures.array.Array;

public interface HashTableInterface <T>{
    int size();

    int rowSize(int indexRow);

    Array<T> getRow(int indexRow);

    T getElementXinRowY(int rowY, int elementX);

    int hash(T element, Class c);
}
