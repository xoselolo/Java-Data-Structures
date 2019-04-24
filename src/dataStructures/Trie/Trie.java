package dataStructures.Trie;
/** TODO **/
public class Trie {
    private TrieNode root;

    public Trie(){
        root = null;
    }

    public void insert(char[] word){
        TrieNode last = root;
        TrieNode actual = null;

        int match = getLongestMatching(word);

        for(int i = match; i < word.length; i++){
            if (i < word.length-1) {
                actual = insertI(word[i], false);
            }
            else {
                //'#' defines where the word ends
                actual = insertI('#', true);
            }
            actual.setFather(last);
            last = actual;
            last.setSon(actual);
        }
    }

    private TrieNode insertI(char letter, boolean endOfWord){
        TrieNode node = new TrieNode(letter, endOfWord);
        return node;
    }

    public boolean search(char[] word){
        TrieNode last = root;
        TrieNode actual = null;
        boolean found = false, match = false;
        int i = 0;
        while (i < word.length && match){
            match = searchI(word[i], last);
            if (match){
                actual = last;
            }
            i++;
        }

        return found;
    }

    private boolean searchI(char letter, TrieNode node){
        return letter == node.letter;
    }

    public void delete(Trie trie, char[] word){
        TrieNode node = root;

    }

    public int getLongestMatching (char[] word){
        int match=0;

        return match;
    }
}
