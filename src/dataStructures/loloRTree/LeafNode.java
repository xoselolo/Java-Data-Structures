package dataStructures.loloRTree;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dataStructures.array.Array;
import model.Location;
import model.Post;

public class LeafNode extends RTreeNode {
    // Attributes
    @SerializedName("dad")
    @Expose
    private RegionNode dad;
    @SerializedName("posts")
    @Expose
    private Post[] posts;

    // Constructor
    public LeafNode(RegionNode dad){
        this.dad = dad;
        posts = new Post[M];
        depth = LEAF_DEPTH;
        numSons = 0;
    }

    // Getters & Setters
    public RegionNode getDad() {
        return dad;
    }
    public void setDad(RegionNode dad) {
        this.dad = dad;
    }
    public Post[] getPosts() {
        return posts;
    }
    public void setPosts(Post[] posts) {
        this.posts = posts;
    }
    public Post getSonX(int x){
        return posts[x];
    }

    // Functions

    @Override
    public void insertPost(Post newPost, Array<Integer> indexes, boolean refresh) {
        if (fitsSon()){
            // El post cabe -> lo metemos en este nodo hoja
            this.posts[numSons] = new Post(newPost.getId(), new Array<String>(newPost.getLiked_by()), newPost.getPublished(),
                    newPost.getPublished_by(), new Location(newPost.getLocation()), new Array<String>(newPost.getHashtags()));
            numSons++;
//            if (refresh){
//                if (indexes.size() > 0 && dad != null){
//                    dad.refresh(newPost, indexes);
//                }
//            }
//            actualitzaRegioCapAmunt(newPost, indexes, position);
        }else{
            // Hacemos el split, separando los posts en 2 grupos
            this.split(newPost, indexes);
        }
    }

    @Override
    public void split(Post newPost, Array<Integer> indexes) {
        // Ahora tendremos M hijos + el new Post
        Post[] allPosts = new Post[numSons + 1];
        for (int i = 0; i < numSons; i++){
            allPosts[i] = this.posts[i];
        }
        allPosts[numSons] = newPost;

        //this.posts[numSons] = new Post(newPost.getId(), new Array<String>(newPost.getLiked_by()), newPost.getPublished(), newPost.getPublished_by(), new Location(newPost.getLocation()), new Array<String>(newPost.getHashtags()));
        //this.numSons++;

        // Creamos los dos grupos de Posts
        RegionNode newCouple = new RegionNode(null, this.depth + 1);
        LeafNode man = new LeafNode(newCouple);
        LeafNode woman = new LeafNode(newCouple);

        RTreeNode[] sons = new RTreeNode[M];
        sons[0] = man;
        sons[1] = woman;
        newCouple.setSons(sons);
        newCouple.numSons = 2;

        double maxDistance = 0;
        int indexPostDistant1 = 0, indexPostDistant2 = 0;
        for (int i = 0; i < numSons + 1; i++){
            for (int j = i + 1; j < numSons + 1; j++){
                double auxDistance = allPosts[i].getLocation().distance(allPosts[j].getLocation());
                if (auxDistance > maxDistance){
                    maxDistance = auxDistance;
                    indexPostDistant1 = i;
                    indexPostDistant2 = j;
                }
            }
        }

        // Creem les dues regions
        Region regionMan = new Region(allPosts[indexPostDistant1].getLocation(), allPosts[indexPostDistant1].getLocation());
        Region regionWoman = new Region(allPosts[indexPostDistant2].getLocation(), allPosts[indexPostDistant2].getLocation());

        Region[] regions = new Region[M];
        regions[0] = regionMan;
        regions[1] = regionWoman;
        newCouple.setRegions(regions);

        // Copiem els dos mes distants un a cada grup
        man.insertPost(allPosts[indexPostDistant1], indexes, false);
        woman.insertPost(allPosts[indexPostDistant2], indexes, false);

        // Copiem la resta de posts al grup que mes li convingui
        boolean drawTurn = true;
        int colocats = 2;
        for (int i = 0; i < numSons + 1; i++) {
            if (i != indexPostDistant1 && i != indexPostDistant2) {
                int restants = numSons + 1 - colocats;
                if (man.needsAllRemainingInHere(restants)) {
                    man.insertPost(allPosts[i], indexes, false);
                    regionMan.update(allPosts[i].getLocation());
                } else if (woman.needsAllRemainingInHere(restants)) {
                    woman.insertPost(allPosts[i], indexes, false);
                    regionWoman.update(allPosts[i].getLocation());
                } else {
                    // Decidim a quin dels dos grups posarlo
                    double incrementAreaMan = regionMan.possiblyIncrease(allPosts[i].getLocation());
                    double incrementAreaWoman = regionWoman.possiblyIncrease(allPosts[i].getLocation());
                    if (incrementAreaMan < incrementAreaWoman) {
                        man.insertPost(allPosts[i], indexes, false);
                        regionMan.update(allPosts[i].getLocation());
                    } else if (incrementAreaWoman < incrementAreaMan) {
                        woman.insertPost(allPosts[i], indexes, false);
                        regionWoman.update(allPosts[i].getLocation());
                    } else {
                        // Empate al increment de l'area
                        if (man.numSons < woman.numSons) {
                            man.insertPost(allPosts[i], indexes, false);
                            regionMan.update(allPosts[i].getLocation());
                        } else if (woman.numSons < man.numSons) {
                            woman.insertPost(allPosts[i], indexes, false);
                            regionWoman.update(allPosts[i].getLocation());
                        } else {
                            // Empat en num de fills, fem cada cop a un
                            if (drawTurn) {
                                man.insertPost(allPosts[i], indexes, false);
                                regionMan.update(allPosts[i].getLocation());
                            } else {
                                woman.insertPost(allPosts[i], indexes, false);
                                regionWoman.update(allPosts[i].getLocation());
                            }

                            drawTurn = !drawTurn;
                        }
                    }
                }
                colocats++;
            }
        }

        // Tenim tots els posts colocats
        // Ho tenim tot al region node 'newCouple'
        if (this.dad == null){
            // Solo tenemos un nodo hoja como root
            RTree.root = newCouple;
            this.dad = (RegionNode)RTree.root;
            newCouple.setDad(null);
        }else{
            this.dad.receiveSplit(newCouple, indexes, newPost);
        }

    }

    @Override
    public void receiveSplit(RegionNode newCouple, Array<Integer> indexes, Post newPost) {

    }

    @Override
    public Array<Post> search(Location location) {
        Array<Post> nearPosts = new Array<Post>();

        Array<Integer> nearIndexes = new Array<Integer>();

        int ordenats = 0;
        while (ordenats < this.numSons){
            int index = -1;
            double minDistance = -1;
            for (int i = 0; i < this.numSons; i++){
                if (!nearIndexes.hasElement(i)){
                    double distance = location.distance(this.posts[i].getLocation());
                    if (minDistance == -1){
                        minDistance = distance;
                        index = i;
                    }else{
                        if (distance < minDistance){
                            minDistance = distance;
                            index = i;
                        }
                    }
                }

            }
            ordenats++;
            nearIndexes.add(index);
        }

        for (int i = 0; i < nearIndexes.size(); i++){
            nearPosts.add(this.posts[(int) nearIndexes.get(i)]);
        }

        return nearPosts;
    }

    /*
    @Override
    public void actualitzaRegioCapAmunt(Post newPost, Array<Integer> indexes) {
        if (dad != null && position >= 0){
            dad.actualitzaRegioX(newPost, indexes, position);
        }
    }

    @Override
    public void actualitzaRegioX(Post newPost, Array<Integer> indexes, int position) {

    }
*/

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LeafNode  -  Depth: ").append(this.depth).append(System.lineSeparator());
        for (int i = 0; i < numSons; i++){
            builder.append("Post ").append(posts[i].getId()).append(System.lineSeparator());
        }
        return builder.toString();
    }
}
