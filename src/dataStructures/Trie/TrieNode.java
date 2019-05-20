package dataStructures.Trie;

import dataStructures.array.Array;

public class TrieNode {
    private boolean endOfWord;
    private char letter;
    private TrieNode ant;
    private Array<TrieNode> sons;

    public TrieNode(char letter, boolean endOfWord){
        this.endOfWord = endOfWord;
        this.letter = letter;
        sons = new Array<>();
    }

    public void setFather(TrieNode father) {
        ant = father;
    }

    public void addSon(TrieNode son) {
        sons.add(son);
    }

    public char getLetter() {
        return this.letter;
    }

    public Array<TrieNode> getSons() {
        return sons;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public boolean hasSons() {
        return this.sons.size() > 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (isEndOfWord()){
            builder.append("End of word" + System.lineSeparator());
        }else{
            builder.append("Letter " + letter + ":" + System.lineSeparator());
            int size = sons.size();
            for (int i = 0; i < size; i++){
                builder.append(((TrieNode)sons.get(i)).toString());
            }
        }

        return builder.toString();
    }
}
