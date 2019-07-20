package dataStructures.Trie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dataStructures.array.Array;

public class TrieNode {
    @SerializedName("endOfWord")
    @Expose
    private boolean endOfWord;

    @SerializedName("numOfWords")
    @Expose
    private int numOfWords;

    @SerializedName("letter")
    @Expose
    private char letter;

    @SerializedName("sons")
    @Expose
    private Array<TrieNode> sons;

    public TrieNode(char letter, boolean endOfWord, int numOfWords){
        this.endOfWord = endOfWord;
        this.letter = letter;
        sons = new Array<>();
        this.numOfWords = numOfWords;
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

    public void addNumOfWords() {
        this.numOfWords++;
    }

    public void lessNumOfWords() {
        this.numOfWords--;
    }

    public int getNumOfWords() {
        return this.numOfWords;
    }

    /*
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
    */

    @Override
    public String toString() {
        return "{" +
                "\"endOfWord\": " + endOfWord +
                ", \"numOfWords\": " + numOfWords +
                ", \"letter\": \"" + letter + "\"" +
                ", \"sons\": " + sons +
                '}';
    }
}
