package dataStructures.Trie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dataStructures.array.Array;

public class TrieRoot {

    @SerializedName("sons")
    @Expose
    private Array<TrieNode> sons;

    @SerializedName("element")
    @Expose
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
            //builder.append(((TrieNode)sons.get(i)).toString());
            builder.append(sons.get(i));
            /*
            if (((TrieNode)sons.get(i)).getSons().size() > 0) {
                toStringI(builder,((TrieNode)sons.get(i)).getSons(),((TrieNode)sons.get(i)).getSons().size());
            }
            */
        }

        return builder.toString();


    }

    private void toStringI(StringBuilder builder, Array<TrieNode> sons, int sonsSize) {
        for (int i = 0; i < sonsSize; i++) {
            builder.append(sons.get(i).toString());
            if (((TrieNode)sons.get(i)).getSons().size() > 0) {
                toStringI(builder,((TrieNode)sons.get(i)).getSons(),((TrieNode)sons.get(i)).getSons().size());
            }
        }
    }

    /*
    @Override
    public String toString() {
        int sonsSize = sons.size();
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\t\"RootSons\": \n\t\t[");
        for (int i = 0; i < sonsSize; i++) {
            sb.append(getSons(((TrieNode)sons.get(i)), 3));
            sb.append("\n\t\t\t}");
            if (i < sonsSize-1) {
                sb.append(",\n");
            }
            else {
                sb.append("\n");
            }
        }
        return sb.append("\t\t]\n}").toString();
    }

    private String getSons(TrieNode node, int tabs) {
        StringBuilder sb = new StringBuilder();
        // Crear String con 'tabs' tabulaciones
        String tabulationLess = new String(new char[tabs-2]).replace("\0", "\t");
        String tabulation = new String(new char[tabs]).replace("\0", "\t");
        String tabulationMore = new String(new char[tabs+1]).replace("\0", "\t");
        int sonsSize = node.getSons().size();
        if (node.getSons().size() == 0) {
            sb.append("null\n" + tabulationLess + "},\n" + tabulationLess + "{");
        }
        else {
            sb.append("\n" + tabulation + "{");
            for (int i = 0; i < sonsSize; i++) {
                sb.append("\n" + tabulationMore + "\"letter\": " + i + "\"" + node.getLetter() + "\",");
                sb.append("\n" + tabulationMore + "\"numOfWords\": " + node.getNumOfWords() + ",");
                sb.append("\n" + tabulationMore + "\"endOfWord\": " + node.isEndOfWord() + ",");
                sb.append("\n" + tabulationMore + "\"sons\": " + getSons((TrieNode) node.getSons().get(i), tabs + 2));
            }
            sb.append("\n" + tabulation + "}");
        }
        return sb.toString();
    }
    */
}
