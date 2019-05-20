package dataStructures.Trie;

import dataStructures.array.Array;

public class TrieRoot {
    private Array<TrieNode> sons;
    private Object element;

    public TrieRoot() {
        element = null;
        sons = new Array<>();
    }

    public Array<TrieNode> getSons() {
        return sons;
    }

    public void addSon(TrieNode son) {
        sons.add(son);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        int size = sons.size();
        for (int i = 0; i < size; i++){
            builder.append(((TrieNode)sons.get(i)).toString());
        }

        return builder.toString();
    }
}
