package dataStructures.Trie;

public class TrieNode {
    public boolean endOfWord;
    public char letter;
    public TrieNode ant;
    public TrieNode next;

    public TrieNode(char letter, boolean endOfWord){
        this.endOfWord = endOfWord;
        this.letter = letter;
    }

    public void setFather(TrieNode father) {
        ant = father;
    }
    public void setSon(TrieNode son){next = son;}

}
