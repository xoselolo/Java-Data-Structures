package dataStructures.loloRTree;

import model.Location;
import model.Post;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SplitData {
    // Attributes
    private int sonsDepth;
    private Region region1;
    private Region region2;
    private RTreeNode rTreeNode1;
    private RTreeNode rTreeNode2;

    // Constructor
    public SplitData(){
        //region1 = new Region(null, null);
        //region2 = new Region(null, null);
    }
    public SplitData(int sonsDepth, Region region1, Region region2, RTreeNode rTreeNode1, RTreeNode rTreeNode2) {
        this.sonsDepth = sonsDepth;
        this.region1 = region1;
        this.region2 = region2;
        this.rTreeNode1 = rTreeNode1;
        this.rTreeNode2 = rTreeNode2;
    }

    // Getters & Setters
    public int getSonsDepth() {
        return sonsDepth;
    }
    public void setSonsDepth(int sonsDepth) {
        this.sonsDepth = sonsDepth;
    }
    public Region getRegion1() {
        return region1;
    }
    public void setRegion1(Region region1) {
        this.region1 = region1;
    }
    public Region getRegion2() {
        return region2;
    }
    public void setRegion2(Region region2) {
        this.region2 = region2;
    }
    public RTreeNode getrTreeNode1() {
        return rTreeNode1;
    }
    public void setrTreeNode1(RTreeNode rTreeNode1) {
        this.rTreeNode1 = rTreeNode1;
    }
    public RTreeNode getrTreeNode2() {
        return rTreeNode2;
    }
    public void setrTreeNode2(RTreeNode rTreeNode2) {
        this.rTreeNode2 = rTreeNode2;
    }

    // Functions
    /*
    public void createSplitData(RTreeNode nodeToSplit, Post newPost){
        if (nodeToSplit.depth == RTreeNode.LEAF_DEPTH){
            // Volem fer un split a la fulla
            LeafNode leafToSplit = (LeafNode)nodeToSplit;

            // Inicialitzem les dues fulles que crearem
            LeafNode newLeafNode1 = new LeafNode(leafToSplit.getDad());
            LeafNode newLeafNode2 = new LeafNode(leafToSplit.getDad());
            LeafNode newLeafNode1 = new LeafNode(leafToSplit.getDad());
            LeafNode newLeafNode2 = new LeafNode(leafToSplit.getDad());

            // Agafem tots els posts
            ArrayList<Post> allPosts = new ArrayList<Post>();
            int numSons = leafToSplit.numSons;
            for (int i = 0; i < numSons; i++){
                allPosts.add(leafToSplit.getSonX(i));
            }
            allPosts.add(newPost);
            numSons++;

            // Extraemos los dos puntos mas distantes
            double maxDistance = 0;
            int indexP1 = 0, indexP2 = 0;
            Post p1 = null;
            Post p2 = null;
            for (int i = 0; i < numSons; i++){
                for (int j = i + 1; j < numSons; j++){
                    double auxDistance = allPosts.get(i).getLocation().distance(allPosts.get(j).getLocation());
                    if (auxDistance > maxDistance){
                        maxDistance = auxDistance;
                        p1 = allPosts.get(i);
                        indexP1 = i;
                        p2 = allPosts.get(j);
                        indexP2 = j;
                    }
                }
            }

            assert p1 != null && p2 != null;
            // Coloquem els dos posts a les dues regions
            newLeafNode1.insertSon(p1);
            newLeafNode2.insertSon(p2);
            region1 = new Region(p1.getLocation().clone(), p1.getLocation().clone());
            region2 = new Region(p2.getLocation().clone(), p2.getLocation().clone());
            int restants = numSons - 2;
            // OPTIMIZACION: en caso de empate en incremento, area y numSons lo meteremos cada
            // vez en una de las dos regiones
            boolean turnoR1 = true;

            // Ja tenim els dos posts mes distants
            // Coloquem la resta de posts a la regio que li pertoqui
            for (int i = 0; i < numSons; i++){
                if (i != indexP1 && i != indexP2){
                    // No es cap dels dos inicials
                    Post post = allPosts.get(i);

                    if (newLeafNode1.needsAllRemainingInHere(restants)){
                        // Quedan x elementos y a R1 le quedan x para llegar al minimo
                        newLeafNode1.insertSon(post);
                        region1.update(post.getLocation());
                        restants--;
                    }else if (newLeafNode2.needsAllRemainingInHere(restants)){
                        // Quedan x elementos y a R2 le quedan x para llegar al minimo
                        newLeafNode2.insertSon(post);
                        region2.update(post.getLocation());
                        restants--;
                    }else {
                        // Podemos elegir libremente donde insertar el post
                        // es decir, donde menos incremente el area
                        double increaseR1 = region1.possiblyIncrease(post.getLocation());
                        double increaseR2 = region2.possiblyIncrease(post.getLocation());

                        // Veamos donde lo inserimos
                        if (increaseR1 < increaseR2){
                            // El incremento de R1 es menor
                            newLeafNode1.insertSon(post);
                            region1.update(post.getLocation());
                            restants--;
                        }else if (increaseR2 < increaseR1){
                            // El incremento de R2 es menor
                            newLeafNode2.insertSon(post);
                            region2.update(post.getLocation());
                            restants--;
                        }else{
                            // Son iguales, veamos que area es mas pequeña hasta el momento
                            double areaR1 = region1.area();
                            double areaR2 = region2.area();

                            if (areaR1 < areaR2){
                                // area de R1 es mas pequeña
                                newLeafNode1.insertSon(post);
                                region1.update(post.getLocation());
                                restants--;
                            }else if (areaR2 < areaR1){
                                // area de R1 es mas pequeña
                                newLeafNode2.insertSon(post);
                                region2.update(post.getLocation());
                                restants--;
                            }else{
                                // Tambien tienen la misma area
                                // veamos cual tiene menos elementos hasta el momento
                                if (newLeafNode1.numSons < newLeafNode2.numSons){
                                    // R1 tiene menos posts
                                    newLeafNode1.insertSon(post);
                                    region1.update(post.getLocation());
                                    restants--;
                                } else if (newLeafNode2.numSons < newLeafNode1.numSons){
                                    // R1 tiene menos posts
                                    newLeafNode2.insertSon(post);
                                    region2.update(post.getLocation());
                                    restants--;
                                } else {
                                    // insertamos cada vez en un lado siempre que pase esto
                                    if (turnoR1){
                                        // Toca en R1
                                        newLeafNode1.insertSon(post);
                                        region1.update(post.getLocation());
                                        restants--;
                                    }else{
                                        // Toca en R2
                                        newLeafNode2.insertSon(post);
                                        region2.update(post.getLocation());
                                        restants--;
                                    }

                                    // Cambiamos el turno en caso de empate
                                    turnoR1 = !turnoR1;
                                }
                            }
                        }

                    }

                }
            }

            showAuxNewLeafNodes(newLeafNode1, newLeafNode2);

            // Tenemos ambas regiones creadas y ambos LeafNodes llenos
            RegionNode dad = leafToSplit.getDad();
            if (dad == null){
                // Era root, debemos crear un root del tipo RegionNode que contenga los dos LeafNodes que hemos creado
                System.out.println("SPLIT TIPO: CHANGE ROOT PRIMER NIVEL");
                RegionNode newRoot = createRootFromLeafs(newLeafNode1, newLeafNode2);
                RTree.changeRoot(newRoot);

            }else{
                System.out.println("SPLIT TIPO: RECEIVE-SPLIT");
                System.out.println("LEAF TO SPLIT: (SON)");
                System.out.println(System.lineSeparator());
                System.out.println(leafToSplit.toString());
                System.out.println("DAD OF LEAF TO SPLIT: (DAD)");
                System.out.println(System.lineSeparator());
                System.out.println(dad.toString());
                if (dad.fitsSon()){
                    // sustituir el que habia antes por el new1 y ademas añadir el new2
                    int numSonsDad = dad.numSons;
                    //RTreeNode[] sons = (LeafNode[]) dad.getSons(); // Sabemos que son hojas

                    System.out.println("MECAGOENTODONEEEEN");
                    System.out.println(leafToSplit.toString());


                    System.out.println(System.lineSeparator());
                    System.out.println("Depth dad: " + dad.depth);
                    System.out.println(System.lineSeparator());
                    for (int i = 0; i < numSonsDad; i++){
                        System.out.println("HOLA");
                        System.out.println(dad.getSons()[i].toString());

                        // todo (FIX) -> En vez de equals del dad, mirar
                        //  cual de los hijos del leaf to split contiene
                        if (dad.getSons()[i].equals(leafToSplit)){
                            System.out.println("SON TO REMOVE FOUND!");
                            dad.getSons()[i] = newLeafNode1;
                            dad.getRegions()[i] = region1;
                            newLeafNode1.setDad(dad);
                            break;
                        }
                    }
                    dad.getSons()[numSonsDad] = newLeafNode2;
                    dad.getRegions()[numSonsDad] = region2;
                    newLeafNode2.setDad(dad);
                    dad.numSons++;

                }else{
                    // NO cabe la nueva region, hace falta hacer un split en el padre
                    dad.receiveSplit(new SplitData(RTreeNode.LEAF_DEPTH, region1, region2, newLeafNode1, newLeafNode2), leafToSplit);
                }
            }


            // TODO: controlar si el pare es null, crear un RegionNode i substituirlo com a root
            // TODO: si el pare no es null es cridara a la funcio receiveSplit per a mirar si es pot
            // todo: inserir i fer mes crides a split en cas necessari
        }else{

            // EN PRINCIPIO NUNCA DEBERIA ENTRAR AQUI

            // Volem fer un split de regions
            RegionNode regionToSplit = (RegionNode)nodeToSplit;
            regionToSplit.split(newPost); // todo

            System.out.println("SPLIT TIPO: SPLIT DE REGIONES");

        }
    }*/

    private RegionNode createRootFromLeafs(LeafNode newLeafNode1, LeafNode newLeafNode2) {
        RegionNode newRoot = new RegionNode(null, 1);

        newLeafNode1.setDad(newRoot);
        newLeafNode2.setDad(newRoot);

        newRoot.getRegions()[0] = region1;
        newRoot.getRegions()[1] = region2;
        newRoot.getSons()[0] = newLeafNode1;
        newRoot.getSons()[1] = newLeafNode2;

        newRoot.numSons = 2;

        return newRoot;
    }

    private void showAuxNewLeafNodes(@NotNull LeafNode newLeafNode1, LeafNode newLeafNode2) {
        System.out.println("AREA - REGION 1:");
        System.out.println("\tMinX: " + region1.getMinLocation().getLatitude());
        System.out.println("\tMinY: " + region1.getMinLocation().getLongitude());
        System.out.println("\tMaxX: " + region1.getMaxLocation().getLatitude());
        System.out.println("\tMaxY: " + region1.getMaxLocation().getLongitude());
        System.out.println("POST - REGION 1:");
        for (int i = 0; i < newLeafNode1.numSons; i++){
            System.out.println("\tPost id = P" + newLeafNode1.getSonX(i).getId());
        }

        System.out.println("AREA - REGION 2:");
        System.out.println("\tMinX: " + region2.getMinLocation().getLatitude());
        System.out.println("\tMinY: " + region2.getMinLocation().getLongitude());
        System.out.println("\tMaxX: " + region2.getMaxLocation().getLatitude());
        System.out.println("\tMaxY: " + region2.getMaxLocation().getLongitude());
        System.out.println("POST - REGION 2:");
        for (int i = 0; i < newLeafNode2.numSons; i++){
            System.out.println("\tPost id = P" + newLeafNode2.getSonX(i).getId());
        }
    }
}
