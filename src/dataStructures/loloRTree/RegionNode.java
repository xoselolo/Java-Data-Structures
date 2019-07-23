package dataStructures.loloRTree;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dataStructures.array.Array;
import model.Location;
import model.Post;

import java.util.ArrayList;

public class RegionNode extends RTreeNode {
    // Attributes
    @SerializedName("dad")
    @Expose
    private RegionNode dad;

    @SerializedName("regions")
    @Expose
    private Region[] regions;

    @SerializedName("sons")
    @Expose
    private RTreeNode[] sons;

    // Constructor
    public RegionNode(RegionNode dad, int depth) {
        this.dad = dad;
        this.depth = depth;
        this.numSons = 0;
        this.regions = new Region[M];
        if (depth == LEAF_DEPTH + 1){
            this.sons = new LeafNode[M];
        }else {
            this.sons = new RegionNode[M];
        }
    }

    // Getters & Setters
    public RegionNode getDad() {
        return dad;
    }
    public void setDad(RegionNode dad) {
        this.dad = dad;
    }
    public Region[] getRegions() {
        return regions;
    }
    public void setRegions(Region[] regions) {
        this.regions = regions;
    }
    public RTreeNode[] getSons() {
        return sons;
    }
    public void setSons(RTreeNode[] sons) {
        this.sons = sons;
    }
    public int getDepth() {
        return depth;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void refresh(Post newPost, Array<Integer> indexes){
        int index = (int)indexes.get(indexes.size() - 1);
        this.regions[index].update(newPost.getLocation());

        indexes.remove(indexes.size() - 1);

//        if (indexes.size() > 0 && dad != null){
//            this.dad.refresh(newPost, indexes);
//        }
    }

    // Functions
    @Override
    public void insertPost(Post newPost, Array<Integer> indexes, boolean refresh) {
        double minIncrease = -1;
        int indexMinIncrease = 0;
        for (int i = 0; i < this.numSons; i++){
            double auxIncrease = this.regions[i].possiblyIncrease(newPost.getLocation());
            if (minIncrease == -1){
                minIncrease = auxIncrease;
                indexMinIncrease = i;
            }else{
                if (auxIncrease < minIncrease){
                    minIncrease = auxIncrease;
                    indexMinIncrease = i;
                }
            }
        }

        indexes.add(indexMinIncrease);
        this.sons[indexMinIncrease].insertPost(newPost, indexes, refresh);
    }

    @Override
    public void split(Post newPost, Array<Integer> indexes) {

    }

    @Override
    public void receiveSplit(RegionNode newCouple, Array<Integer> indexes, Post newPost) {
        int index = (int)indexes.get(indexes.size() - 1);
        this.sons[index] = newCouple.sons[1];
        this.regions[index] = newCouple.regions[1];
        newCouple.numSons--;
        //this.numSons++;

        indexes.remove(indexes.size() - 1);

        if (fitsSon()){
            this.sons[numSons] = newCouple.sons[0];
            this.regions[numSons] = newCouple.regions[0];
            this.numSons++;

//            if (indexes.size() > 0 && dad != null){
//                this.dad.refresh(newPost, indexes);
//            }
//            if (dad != null){
//                dad.actualitzaRegioCapAmunt(newPost, indexes, position);
//            }
        }else{

            // Ahora tendremos M hijos + el new RTReeNode
            RTreeNode[] allRTreeNodes = new RTreeNode[numSons + 1];
            Region[] allRegions = new Region[numSons + 1];
            for (int i = 0; i < numSons; i++){
                allRTreeNodes[i] = this.sons[i];
                allRegions[i] = this.regions[i];
            }
            allRTreeNodes[numSons] = newCouple.sons[0];
            allRegions[numSons] = newCouple.regions[0];
            //numSons++;

            // Preparem l'array de tots els fills
            RegionNode newLevel = new RegionNode(null, this.depth+1);
            RegionNode man = new RegionNode(newLevel, this.depth);
            RegionNode woman = new RegionNode(newLevel, this.depth);

            RTreeNode[] sons = new RTreeNode[M];
            sons[0] = man;
            sons[1] = woman;
            newLevel.setSons(sons);
            newLevel.numSons = 2;

            // Cogemos las dos regiones mas distantes
            double maxDistance = 0;
            int indexPostDistant1 = 0, indexPostDistant2 = 0;
            for (int i = 0; i < numSons + 1; i++){
                for (int j = i + 1; j < numSons + 1; j++){
                    double auxDistance = allRegions[i].center().distance(allRegions[j].center());
                    if (auxDistance > maxDistance){
                        maxDistance = auxDistance;
                        indexPostDistant1 = i;
                        indexPostDistant2 = j;
                    }
                }
            }

            // Copiem els dos mes distants un a cada grup
            man.regions[man.numSons] = allRegions[indexPostDistant1];
            man.sons[man.numSons] = allRTreeNodes[indexPostDistant1];
            //man.numSons++;
            man.numSons = 1;
            woman.regions[woman.numSons] = allRegions[indexPostDistant2];
            woman.sons[woman.numSons] = allRTreeNodes[indexPostDistant2];
            //woman.numSons++;
            woman.numSons = 1;

            // Creem les dues regions
            Region regionMan = allRegions[indexPostDistant1];
            Region regionWoman = allRegions[indexPostDistant2];

            Region[] regions = new Region[M];
            regions[0] = regionMan;
            regions[1] = regionWoman;
            newLevel.setRegions(regions);

            // Copiem la resta de posts al grup que mes li convingui
            boolean drawTurn = true;
            int colocats = 2;
            for (int i = 0; i < numSons + 1; i++) {
                if (i != indexPostDistant1 && i != indexPostDistant2) {
                    int restants = numSons - colocats;
                    if (man.needsAllRemainingInHere(restants)) {
                        man.sons[man.numSons] = allRTreeNodes[i];
                        man.regions[man.numSons] = allRegions[i];
                        regionMan.update(allRegions[i].getMinLocation());
                        regionMan.update(allRegions[i].getMaxLocation());
                        man.numSons++;
                    } else if (woman.needsAllRemainingInHere(restants)) {
                        woman.sons[woman.numSons] = allRTreeNodes[i];
                        woman.regions[woman.numSons] = allRegions[i];
                        regionWoman.update(allRegions[i].getMinLocation());
                        regionWoman.update(allRegions[i].getMaxLocation());
                        woman.numSons++;
                    } else {
                        // Decidim a quin dels dos grups posarlo
                        double incrementAreaManX = regionMan.possiblyIncrease(allRegions[i].getMinLocation());
                        double incrementAreaManY = regionMan.possiblyIncrease(allRegions[i].getMaxLocation());
                        double incrementTotalMan = incrementAreaManX + incrementAreaManY;
                        double incrementAreaWomanX = regionMan.possiblyIncrease(allRegions[i].getMinLocation());
                        double incrementAreaWomanY = regionMan.possiblyIncrease(allRegions[i].getMaxLocation());
                        double incrementTotalWoman = incrementAreaWomanX + incrementAreaWomanY;

                        if (incrementTotalMan < incrementTotalWoman) {
                            man.sons[man.numSons] = allRTreeNodes[i];
                            man.regions[man.numSons] = allRegions[i];
                            regionMan.update(allRegions[i].getMinLocation());
                            regionMan.update(allRegions[i].getMaxLocation());
                            man.numSons++;
                        } else if (incrementTotalWoman < incrementTotalMan) {
                            woman.sons[woman.numSons] = allRTreeNodes[i];
                            woman.regions[woman.numSons] = allRegions[i];
                            regionWoman.update(allRegions[i].getMinLocation());
                            regionWoman.update(allRegions[i].getMaxLocation());
                            woman.numSons++;
                        } else {
                            // Empate al increment de l'area
                            if (man.numSons < woman.numSons) {
                                man.sons[man.numSons] = allRTreeNodes[i];
                                man.regions[man.numSons] = allRegions[i];
                                regionMan.update(allRegions[i].getMinLocation());
                                regionMan.update(allRegions[i].getMaxLocation());
                                man.numSons++;
                            } else if (woman.numSons < man.numSons) {
                                woman.sons[woman.numSons] = allRTreeNodes[i];
                                woman.regions[woman.numSons] = allRegions[i];
                                regionWoman.update(allRegions[i].getMinLocation());
                                regionWoman.update(allRegions[i].getMaxLocation());
                                woman.numSons++;
                            } else {
                                // Empat en num de fills, fem cada cop a un
                                if (drawTurn) {
                                    man.sons[man.numSons] = allRTreeNodes[i];
                                    man.regions[man.numSons] = allRegions[i];
                                    regionMan.update(allRegions[i].getMinLocation());
                                    regionMan.update(allRegions[i].getMaxLocation());
                                    man.numSons++;
                                } else {
                                    woman.sons[woman.numSons] = allRTreeNodes[i];
                                    woman.regions[woman.numSons] = allRegions[i];
                                    regionWoman.update(allRegions[i].getMinLocation());
                                    regionWoman.update(allRegions[i].getMaxLocation());
                                    woman.numSons++;
                                }

                                drawTurn = !drawTurn;
                            }
                        }
                    }
                    colocats++;
                }
            }

            // Ja tenim tots els fills colocats
            // Ho tenim tot al region node 'newLevel'
            if (this.dad == null){
                // Solo tenemos un nodo hoja como root
                RTree.root = newLevel;
                this.dad = (RegionNode)RTree.root;
                newLevel.setDad(null);
            }else{
                this.dad.receiveSplit(newLevel, indexes, newPost);
            }
        }

    }

    /*@Override
    public void actualitzaRegioCapAmunt(Post newPost, Array<Integer> indexes, int position) {
        RegionNode aux = this.dad;
        indexes.remove(position);
        position--;
        if (position >= 0){
            this.dad.actualitzaRegioX(newPost, indexes, position);
        }
    }

    @Override
    public void actualitzaRegioX(Post newPost, Array<Integer> indexes, int position) {
        RegionNode aux = this.dad;
        if (position >= 0 && this.regions[(int)indexes.get(position)] != null){
            this.regions[(int)indexes.get(position)].update(newPost.getLocation());
            if (dad != null){
                actualitzaRegioCapAmunt(newPost, indexes, position);
            }
        }
    }*/

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RegionNode  -  Depth: ").append(this.depth).append(System.lineSeparator());
        for (int i = 0; i < numSons; i++){
            builder.append("son[").append(i).append("]:").append(this.regions[i].toString());
            builder.append("\t").append(sons[i].toString());
        }
        return builder.toString();
    }
}
