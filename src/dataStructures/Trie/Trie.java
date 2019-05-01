package dataStructures.Trie;


public class Trie {
    private TrieRoot root;

    public Trie(){
        this.root = null;
    }

    public void insert(String paraula){
        char[] word = paraula.toCharArray();
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

    public void printStructure() {
        if (root != null) {
            int size = root.getSons().size();
            for (int i = 0; i < size; i++) {
                if (root.getSons().get(i) instanceof TrieNode) {
                    System.out.println(((TrieNode) root.getSons().get(i)).getLetter());
                    printI((TrieNode) root.getSons().get(i));
                }
            }
        }
    }

    private void printI(TrieNode father) {
        if (!father.isEndOfWord()) {
            int numberOfSons = father.getSons().size();
            for (int i = 0; i < numberOfSons; i++) {
                TrieNode newFather = (TrieNode) father.getSons().get(i);
                System.out.println(newFather.getLetter());
                printI(newFather);
            }
        }
        else {
            System.out.println("END OF WORD");
            if (father.getSons().size() > 0) {
                int numberOfSons = father.getSons().size();
                for (int i = 0; i < numberOfSons; i++) {
                    TrieNode newFather = (TrieNode) father.getSons().get(i);
                    System.out.println(newFather.getLetter());
                    printI(newFather);
                }
            }
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("Luis");
        trie.insert("Luisinho");
        trie.insert("Jajajaja");

        trie.printStructure();

        String word = "Luis";
        boolean found = trie.search(word);
        if (found) {
            System.out.println("S'ha trobat la paraula " + word + " a l'estructura");
        }
        else {
            System.out.println("No s'ha trobat la paraula " + word + " a l'estructura");
        }
    }

    public boolean search(String word) {
        char[] wordArray = word.toCharArray();

        if (root == null) {
            return false;
        }
        else {
            int rootSons = root.getSons().size();
            for (int i = 0; i < rootSons; i++) {
                if (root.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) root.getSons().get(i)).getLetter() == wordArray[0]) {
                        return searchI(wordArray,1, (TrieNode) root.getSons().get(i));
                    }
                }
            }
            return false;
        }
    }

    private boolean searchI(char[] word, int indexWord, TrieNode father) {
        if (indexWord == word.length) {
            return true;
        }
        else {
            int sonsSize = father.getSons().size();

            for (int i = 0; i < sonsSize; i++) {
                if (father.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) father.getSons().get(i)).getLetter() == word[indexWord]) {
                        return searchI(word,++indexWord, (TrieNode) father.getSons().get(i));
                    }
                }
            }
            return false;
        }
    }

    // TODO: Delete a word, add information about how many words are within a node and auto-complete functionallity

}
