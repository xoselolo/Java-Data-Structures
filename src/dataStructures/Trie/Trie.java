package dataStructures.Trie;


import dataStructures.array.Array;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Trie {
    private TrieRoot root;
    private int numWords = 2;

    public Trie(){
        this.root = null;
    }

    public void insert(String paraula){
        char[] word = paraula.toLowerCase().toCharArray();
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
            System.out.print("[Root]: , [Childs]: ");
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
        System.out.print("[Father]: " + father.getLetter() + ", [Childs]: ");
        if (!father.isEndOfWord()) {
            int numberOfSons = father.getSons().size();
            for (int i = 0; i < numberOfSons; i++) {
                TrieNode newFather = (TrieNode) father.getSons().get(i);
                System.out.print(newFather.getLetter() + " ");
            }
            System.out.println();
            for (int i = 0; i < numberOfSons; i++) {
                TrieNode newFather = (TrieNode) father.getSons().get(i);
                printI(newFather);
            }
        }
        else {
            if (father.getSons().size() > 0) {
                int numberOfSons = father.getSons().size();
                for (int i = 0; i < numberOfSons; i++) {
                    TrieNode newFather = (TrieNode) father.getSons().get(i);
                    System.out.print(newFather.getLetter() + " ");
                }
                System.out.println();
                for (int i = 0; i < numberOfSons; i++) {
                    TrieNode newFather = (TrieNode) father.getSons().get(i);
                    printI(newFather);
                }
            }
            else {
                System.out.print("-");
                System.out.println();
            }
        }
    }


    /** TESTING **/
    public static void main(String[] args) {
        Trie trie = new Trie();

        System.out.println("Testing del Trie");
        int cas = 0;
        while (cas != 4) {
            System.out.println("\t1. Inserir paraula\n\t2. AutoCompletar\n\t3. Buscar paraula\n\t4. Sortir\nOpcio:");
            Scanner sc = new Scanner(System.in);
            try {
                cas = sc.nextInt();
                switch (cas) {
                    case 1:
                        System.out.println("Quina paraula vols afegir? ");
                        String word = sc.next();
                        trie.insert(word);
                        break;

                    case 2:
                        System.out.println("Escriu una paraula: ");
                        word = sc.next();
                        Array<String> matchings = trie.getMatchingWords(word);
                        int trobades = matchings.size();

                        if (trobades == 0) {
                            System.out.println("No s'han trobat paraules que continguin '" + word + "'");
                        }
                        else {
                            for (int i = 0; i < trobades; i++) {
                                System.out.println(matchings.get(i));
                            }
                        }
                        break;

                    case 3:
                        System.out.println("Escriu una paraula: ");
                        word = sc.next();
                        boolean found = trie.search(word);
                        if (found) {
                            System.out.println("S'ha trobat la paraula");
                        }
                        else {
                            System.out.println("No s'ha trobat la paraula");
                        }
                        break;

                    case 4:
                        break;

                    default:
                        System.out.println("Opcio invalida!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Numbers only");
            }
        }
    }

    public boolean search(String word) {
        char[] wordArray = word.toLowerCase().toCharArray();

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
        if (indexWord == word.length && father.isEndOfWord()) {
            return true;
        }
        else if (indexWord == word.length && !father.isEndOfWord()) {
            return false;
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


    public Array<String> getMatchingWords(String word) {
        char[] wordArray = word.toLowerCase().toCharArray();
        Array<String> matchingWords = new Array<>();

        if (root == null) {
            return matchingWords;
        }
        else {
            int sonSize = root.getSons().size();
            for (int i = 0; i < sonSize; i++) {
                if (root.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) root.getSons().get(i)).getLetter() == wordArray[0]) {
                        char[] actualWord = new char[1];
                        actualWord[0] = wordArray[0];
                        getMatchingWordsI(matchingWords,wordArray,actualWord,1,(TrieNode) root.getSons().get(i));
                    }
                }
            }
        }
        return matchingWords;
    }

    private void getMatchingWordsI(Array<String> matchingWords, char[] word, char[] actualWord, int indexWord, TrieNode father) {
        if (father.isEndOfWord() && word.length == indexWord) {
            String newWord = String.copyValueOf(actualWord);
            matchingWords.add(newWord);
            // A partir de aquí miramos si hay más combinaciones que tengan como principio de palabra la palabra introducida
            getMatchingAux(matchingWords,newWord,father);
        }
        else if (father.hasSons() && indexWord < word.length) {
            int sonsSize = father.getSons().size();
            for (int i = 0; i < sonsSize; i++) {
                if (father.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) father.getSons().get(i)).getLetter() == word[indexWord]) {
                        getMatchingWordsI(matchingWords, word, (String.copyValueOf(actualWord) + ((TrieNode) father.getSons().get(i)).getLetter()).toCharArray(), ++indexWord, (TrieNode) father.getSons().get(i));
                        indexWord--;
                    }
                }
            }
        }
        else if (!father.isEndOfWord() && word.length == indexWord) {
            if (matchingWords.size() < numWords) {
                getMatchingAux(matchingWords,String.copyValueOf(actualWord),father);
            }
        }
    }

    private void getMatchingAux(Array<String> matchingWords, String newWord, TrieNode father) {
        boolean end = false;
        if (father.hasSons()) {
            int sonsSize = father.getSons().size();
            int i = 0;
            while (i < sonsSize && !end)  {
                if (father.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) father.getSons().get(i)).isEndOfWord()) {
                        matchingWords.add(newWord + ((TrieNode) father.getSons().get(i)).getLetter());
                    }
                    if (((TrieNode) father.getSons().get(i)).hasSons()) {
                        getMatchingAux(matchingWords,newWord + ((TrieNode) father.getSons().get(i)).getLetter(),(TrieNode) father.getSons().get(i));
                    }
                    if (matchingWords.size() == numWords) {
                        end = true;
                    }
                    i++;
                }
            }
        }
    }

    // TODO: Delete a word, add information about how many words are within a node

    public void setNumWords(int numWords) {
        this.numWords = numWords;
    }

    public int getNumWords() {
        return numWords;
    }

    public TrieRoot getRoot() {
        return this.root;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
