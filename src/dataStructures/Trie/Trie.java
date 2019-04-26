package dataStructures.Trie;
/** TODO **/
public class Trie {
    private TrieRoot root;

    public Trie(){
        this.root = null;
    }

    public void insert(char[] word){
        int indexWord = 0;
        int lengthWord = word.length;

        if (root == null) {
            root = new TrieRoot();
            if (lengthWord == 1) {
                root.addSon(new TrieNode(word[indexWord],true));
            }
            else {
                TrieNode fill = new TrieNode(word[indexWord],false);
                root.addSon(fill);
                insertI(word,++indexWord,lengthWord,fill);
            }
        }
        else {
            int sizeSons = root.getSons().size();
            for (int i = 0; i < sizeSons; i++) {
                if (((TrieNode) root.getSons().get(i)).getLetter() == word[indexWord]) {
                    insertI(word,++indexWord,lengthWord,(TrieNode) root.getSons().get(i));
                    return;
                }
            }

            // Si arriba aquí, vol dir que la lletra de la paraula no es troba emmagatzemada com a fill del TrieNode father
            if (lengthWord == 1) {
                root.addSon(new TrieNode(word[indexWord],true));
            }
            else {
                TrieNode newSon = new TrieNode(word[indexWord],false);
                root.addSon(newSon);
                insertI(word,++indexWord,lengthWord,newSon);
            }
        }
    }

    private void insertI(char[] word, int indexWord, int lengthWord, TrieNode father){
        if (indexWord < lengthWord) {
            int sizeSons = father.getSons().size();

            for (int i = 0; i < sizeSons; i++) {
                if (father.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) father.getSons().get(i)).getLetter() == word[indexWord]) {
                        insertI(word,++indexWord,lengthWord,(TrieNode) father.getSons().get(i));
                        return;
                    }
                }
            }

            // Si arriba aquí, vol dir que la lletra de la paraula no es troba emmagatzemada com a fill del TrieNode father
            for (int i = indexWord; i < lengthWord; i++) {
                TrieNode newSon;
                if (i < lengthWord - 1) {
                    newSon = new TrieNode(word[i],false);
                }
                else {
                    newSon = new TrieNode(word[i],true);
                }
                father.addSon(newSon);
                father = newSon;
            }
        }
        else {
            father.setEndOfWord(true);
        }
    }

    private void printStructure() {
        if (root != null) {
            int size = root.getSons().size();
            for (int i = 0; i < size; i++) {
                if (root.getSons().get(i) instanceof TrieNode) {
                    System.out.println(((TrieNode) root.getSons().get(i)).getLetter());
                }
            }
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("Luis".toCharArray());
        trie.insert("Luisinho".toCharArray());
        trie.insert("Jajajaja".toCharArray());

        trie.printStructure();
    }
    /*
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
    */
}
