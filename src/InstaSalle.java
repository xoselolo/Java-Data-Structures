import com.google.gson.Gson;
//import com.google.gson.stream.JsonReader;
import dataStructures.Trie.Trie;
import dataStructures.array.Array;
import dataStructures.graph.Graph;
import dataStructures.graph.GraphNode;
import dataStructures.hashTable.HashTable;
import dataStructures.loloRTree.RTree;
import dataStructures.redBlackTree.RBT;
import dataStructures.redBlackTree.RBTnode;
import json.JsonReader;
import json.ConstValues;
import json.JsonFileReader;
import json.JsonWriter;
import model.Location;
import model.Post;
import model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InstaSalle {
    // Constantes de estructuras para opcion 3 del menu
    private static final int ERROR = 0;
    private static final int TRIE = 1;
    private static final int RTREE = 2;
    private static final int REDBLACKTREE = 3;
    private static final int HASHTABLE = 4;
    private static final int GRAPH = 5;
    // Estructuras lineales
    private static Array<User> usersArray;
    private static Array<Post> postsArray;
    // Estructuras no lineales
    private static RBT<Post> RBT;
    private static HashTable<Post> hashTable;
    private static Graph<User> graph;
    private static Trie trie;
    private static RTree rTree;

    public static void main(String[] args) {
        // Inicialización de las estructuras vacías

        // Array lineal de usuarios
        usersArray = new Array<User>();
        // Array lineal de posts
        postsArray = new Array<Post>();

        // Red Black Tree para indexar posts por ID
        RBT = new RBT<Post>(new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });

        // Tabla de hash para indexar los posts según los hashtags
        hashTable = new HashTable<Post>();

        // Grafo para representar la relación entre los usuarios
        graph = new Graph<User>(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });

        trie = new Trie();

        rTree = new RTree();

        // Bucle principal del programa
        int option = 0;
        while (option != ConstValues.EXIT8){
            // Mostrem menu
            mostraMenu();
            try{
                // Demanem opcio
                option = demanaOpcio();
            }catch (InputMismatchException e){
                option = ConstValues.ERROR9;
            }finally {

                switch (option){
                    case ConstValues.IMPORT1:
                        // Importació dels JSON a les estructures
                        importaJson();
                        break;

                    case ConstValues.EXPORT2:
                        // Exportació de les estructures a arxius JSON
                        exportaJson();
                        break;

                    case ConstValues.SHOW3:
                        // Usuari escull quina estructura
                        int estructura = mostraMenuEstructures();
                        // Mostrar l'estructura
                        showStruct(estructura);

                        break;
                    case ConstValues.INSERT4:
                        // Usuari escull quin tipus de dades inserir
                        // Inserir la nova dada a les estructures que pertoquin
                        insertInfo();
                        break;

                    case ConstValues.DELETE5:
                        // Usuari escull quin tipus de dades esborrar
                        // Esborrar la dada de les estructures que pertoquin
                        deleteInfo();
                        break;

                    case ConstValues.SEARCH6:
                        // Usuari escull quin tipus de informació cercar
                        // Cercar la dada escollida i mostrar-la
                        searchInfo();

                        break;

                    case ConstValues.LIMIT_MEMORY7:
                        System.out.print("Quin limit de paraules vols?\n> ");
                        try {
                            int numParaules = new Scanner(System.in).nextInt();
                            trie.setNumWords(numParaules);
                            System.out.println("Limit de paraules canviat a " + trie.getNumWords());
                        } catch (InputMismatchException e) {
                            System.out.println("[ERR] - Valor invàlid");
                        }
                        // Limitar els tries per a guardar un màxim de paraules
                        // En cas de ser inferior al nombre de paraules ja guardades anteriorment
                        // eliminar aquelles paraules

                        break;

                    case ConstValues.EXIT8:
                        // Sortir
                        System.out.println("Fins aviat!");

                        break;

                    case ConstValues.ERROR9:
                        // Mostrar missatge d'error (lletres)
                        System.out.println(System.lineSeparator() + "[ERR] - Format incorrecte. Valors vàlids numèrics entre 1 i 8 ambdós inclosos" + System.lineSeparator());
                        break;

                    default:
                        // Mostrar missatge d'error (fora de rang)
                        System.out.println(System.lineSeparator() + "[ERR] - Valor escollit fora del rang (1, 8)" + System.lineSeparator());
                        break;
                }
            }
        }
    }

    private static void showStruct(int estructura) {
        switch (estructura){
            case TRIE:
                trie.printStructure();
                break;
            case RTREE:
                rTree.printStructure();
                break;
            case REDBLACKTREE:
                RBT.printStructure();
                break;
            case HASHTABLE:
                hashTable.printStructure();
                break;
            case GRAPH:
                graph.printStructure();
                break;
            default:
                System.out.println("[ERR] - Opcio invàlida");
                break;
        }
    }

    private static int mostraMenuEstructures() {
        System.out.println("Quina estructura vols visualitzar?");
        System.out.println("\t1 - Trie");
        System.out.println("\t2 - R-Tree");
        System.out.println("\t3 - Red Black Tree");
        System.out.println("\t4 - Taula de Hash");
        System.out.println("\t5 - Graf");
        try{
            return (new Scanner(System.in)).nextInt();
        }catch (Exception e){
            return 0;
        }
    }


    // --------- MENÚ --------------
    private static void mostraMenu() {
        System.out.println("\n[SYS] - MENU");
        System.out.println("[SYS] - \t" + ConstValues.IMPORT_STRING);
        System.out.println("[SYS] - \t" + ConstValues.EXPORT_STRING);
        System.out.println("[SYS] - \t" + ConstValues.SHOW_STRING);
        System.out.println("[SYS] - \t" + ConstValues.INSERT_STRING);
        System.out.println("[SYS] - \t" + ConstValues.DELETE_STRING);
        System.out.println("[SYS] - \t" + ConstValues.SEARCH_STRING);
        System.out.println("[SYS] - \t" + ConstValues.LIMIT_MEMORY_STRING);
        System.out.println("[SYS] - \t" + ConstValues.EXIT_STRING);
    }

    private static int demanaOpcio() throws InputMismatchException {
        System.out.println("[IN] - Opció escollida?");
        return new Scanner(System.in).nextInt();
    }
    // --------- MENÚ --------------

    // --------- OPCIÓN 1 --------------
    private static void importaJson() {
        System.out.println("[OPT1] - Importació de fitxers!");
        int importOK = 0;
        int error;
        do {
            System.out.println("[OPT1] - De quin fitxer vol importar les dades?");
            System.out.println("[OPT1] - \t1. Datasets");
            System.out.println("[OPT1] - \t2. Estructura exportada");
            try {
                error = 0;
                int opcio = new Scanner(System.in).nextInt();
                switch (opcio) {
                    case 1:
                        System.out.println("[OPT1] - Carregant informació...");
                        try {
                            trie = new Trie();
                            JsonFileReader.readUsers(usersArray, graph, trie);
                            //importIntoGraph();
                            //importIntoTrie();
                        } catch (FileNotFoundException e) {
                            importOK++;
                            usersArray = new Array<User>();
                        } finally {
                            try {
                                JsonFileReader.readPosts(postsArray, RBT, hashTable, rTree);
                                //importIntoRBT();
                                //importIntoHashTable();
                            } catch (FileNotFoundException e) {
                                importOK += 2;
                                postsArray = new Array<Post>();
                            } finally {
                                switch (importOK) {
                                    case 0:
                                        System.out.println("[OPT1] - Usuaris i Posts carregats satisfactoriament!");
                                        break;
                                    case 1:
                                        System.out.println("[OPT1] - Posts carregats satisfactoriament!");
                                        break;
                                    case 2:
                                        System.out.println("[OPT1] - Usuaris carregats satisfactoriament!");
                                        break;
                                    case 3:
                                        System.out.println("[OPT1] - No s'han carregat ni usuaris ni posts!");
                                        break;
                                }
                            }
                        }

                        break;

                    case 2:
                        boolean ok;
                        do {
                            ok = false;
                            error = 0;
                            System.out.println("\n[OPT1] - De quina estructura vols importar les dades?");
                            System.out.println("[OPT1] - \t1. Trie");
                            System.out.println("[OPT1] - \t2. Red-Black Tree");
                            System.out.println("[OPT1] - \t3. Hashtable");
                            System.out.println("[OPT1] - \t4. RTree");
                            System.out.println("[OPT1] - \t5. Graph");
                            try {
                                int opcio2 = new Scanner(System.in).nextInt();
                                switch (opcio2) {
                                    case 1:
                                        trie = new Trie();
                                        ok = JsonReader.importFromTrie(JsonWriter.TRIE_PATH,trie);
                                        //System.out.println(trie.getRoot().toString());
                                        break;

                                    case 2:
                                        ok = JsonReader.importFromRBT(JsonWriter.RBT_PATH,RBT);
                                        break;

                                    case 3:
                                        hashTable = new HashTable<>();
                                        ok = JsonReader.importFromHashtable(JsonWriter.HASH_PATH,hashTable);
                                        break;

                                    case 4:
                                        // TODO: RTree JSON importation
                                        rTree = new RTree();
                                        ok = JsonReader.importFromRTree(JsonWriter.RTREE_PATH,rTree);
                                        break;

                                    case 5:
                                        graph = new Graph<>(new Comparator<User>() {
                                            @Override
                                            public int compare(User o1, User o2) {
                                                return o1.getUsername().compareTo(o2.getUsername());
                                            }
                                        });
                                        ok = JsonReader.importFromGraph(JsonWriter.GRAPH_PATH,graph);
                                        break;

                                    default:
                                        System.out.println("[ERR] - Aquesta es una opcio no vàlida.");
                                        error = 1;
                                        break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("[ERR] - Aquesta es una opcio no vàlida.");
                                error = 1;
                            }
                        } while (error == 1);

                        if (!ok) {
                            System.out.println("[ERR] - No s'ha pogut fer la importació de l'estructura");
                        }
                        else {
                            System.out.println("[OK] - S'ha realitzat la importació amb èxit!");
                        }


                        break;

                    default:
                        System.out.println("[ERR] - Aquesta es una opcio no vàlida.");
                        error = 1;
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERR] - Aquesta es una opcio no vàlida.");
                error = 1;
            }
        } while (error == 1);

    }

    // --------- OPCIÓN 1 --------------

    // --------- OPCIÓN 2 --------------
    private static void exportaJson(){
        System.out.println("[OPT2] - Exportació de fitxers");
        System.out.println("[OPT2] - \t1. Exportació de fitxers en format usuaris i posts");
        System.out.println("[OPT2] - \t2. Exportació de fitxers en format de totes les estructures");

        try {
            int opcio = (new Scanner(System.in)).nextInt();
            switch (opcio){
                case 1:
                    // Cas usuaris + posts
                    System.out.println("[IN] - Especifiqui la ruta del fitxer a exportar corresponent a usuaris (Recomanació: files/___.json): ");
                    String pathUsers = (new Scanner(System.in)).nextLine();
                    System.out.println("[IN] - Especifiqui la ruta del fitxer a exportar corresponent a posts (Recomanació: files/___.json): ");
                    String pathPosts = (new Scanner(System.in)).nextLine();

                    // ----- Users -----
                    Gson gson = new Gson();
                    String jsonToString = gson.toJson(usersArray.toArray());
                    try {
                        Files.write(Paths.get(pathUsers), jsonToString.getBytes(), StandardOpenOption.CREATE);
                    } catch (IOException e){
                        System.out.println("[OPT2] - Opció no vàlida");
                    }

                    // ----- Posts -----
                    gson = new Gson();
                    jsonToString = gson.toJson(postsArray.toArray());
                    try {
                        Files.write(Paths.get(pathPosts), jsonToString.getBytes(), StandardOpenOption.CREATE);
                    } catch (IOException e){
                        System.out.println("[OPT2] - Opció no vàlida");
                    }

                    break;

                case 2:
                    System.out.println("[IN] - Especifiqui l'estructura que desitja exportar:");
                    System.out.println("\t\t1. Trie\n\t\t2. Red-Black Tree\n\t\t3. Hashtable\n\t\t4. RTree\n\t\t5. Graph");
                    try {
                        int option = (new Scanner(System.in)).nextInt();
                        boolean correct = false;
                        switch (option) {
                            case 1:
                                correct = JsonWriter.writeTrie(trie);
                                break;

                            case 2:
                                correct = JsonWriter.writeRBT(RBT);
                                break;

                            case 3:
                                correct = JsonWriter.writeHashTable(hashTable);
                                break;

                            case 4:
                                correct = JsonWriter.writeRTree(rTree);
                                break;

                            case 5:
                                correct = JsonWriter.writeGraph(graph);
                                break;

                        }
                        if (!correct) {
                            System.out.println("[ERR] - No s'ha pogut exportar el JSON d'aquesta estructura");
                        }
                        else {
                            System.out.println("[OK] - S'ha realitzat correctament la exportació");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("[OPT2] - Opció no vàlida");
                    }
                    break;

                default:
                    System.out.println("[OPT2] - Opció no vàlida");
                    break;
            }
        }catch (Exception e){
            System.out.println("[OPT2] - Opció no vàlida");
        }

    }
    // --------- OPCIÓN 2 --------------


    // --------- OPCIÓN 5 --------------
    private static void deleteInfo() {
        boolean error;
        do {
            System.out.println("[SYS] - Quina informació vols eliminar?\n[SYS] - \t1. Post\n[SYS] - \t2. Usuari");
            try {
                error = false;
                int option = new Scanner(System.in).nextInt();
                int id = -1;
                switch (option) {
                    case 1:
                        System.out.println("Saps la localitzacio del Post? [Y/N]");
                        String ubi = new Scanner(System.in).next().toLowerCase();
                        if (ubi.equals("y") || ubi.equals("n")){
                            if (ubi.equals("y")){
                                System.out.println("Latitude:\n> ");
                                double latitude = new Scanner(System.in).nextDouble();
                                System.out.print("Longitud:\n> ");
                                double longitud = new Scanner(System.in).nextDouble();
                                System.out.print("Id:\n> ");
                                id = new Scanner(System.in).nextInt();
                                //rTree.getRoot().deletePoint(id, latitude, longitud);
                            }
                            else {
                                System.out.println("Id:\n> ");
                                id = new Scanner(System.in).nextInt();
                                //rTree.getRoot().deletePoint(id);
                            }
                            int postsSize = postsArray.size();
                            int indexPost = -1;
                            for (int i = 0; i < postsSize; i++) {
                                if (((Post)(postsArray.get(i))).getId() == id) {
                                    indexPost = i;
                                    break;
                                }
                            }
                            if (indexPost == -1) {
                                System.out.println("[ERR] - Error eliminació RTree");
                            }
                            else {
                                Post post = (Post) postsArray.get(indexPost);
                                // TODO: delete from RBT
                                long initTime = System.nanoTime();
                                postsArray.remove(indexPost);
                                System.out.println("Temps de eliminació en Array: " + (System.nanoTime() - initTime));
                                initTime = System.nanoTime();
                                hashTable.deletePost(post);
                                System.out.println("Temps de eliminació en Hashtable: " + (System.nanoTime() - initTime));
                                System.out.println("Post borrat del sistema amb èxit!");
                            }
                        }
                        else {
                            System.out.println("[ERR] - Format incorrecte");
                        }

                        break;

                    case 2:
                        System.out.print("Quin username té l'usuari que vols eliminar?\n> ");
                        String username = new Scanner(System.in).next();
                        if (trie.search(username)) {
                            // Si existeix aquest usuari dins l'estructura
                            long initTime = System.nanoTime();
                            trie.deleteWord(username);
                            System.out.println("Temps de eliminacio en Trie: " + (System.nanoTime() - initTime));
                            usersArray.remove(username);
                            initTime = System.nanoTime();
                            graph.remove(username);
                            System.out.println("Temps de eliminacio en Graph: " + (System.nanoTime() - initTime));
                            System.out.println("Usuari borrat del sistema amb èxit!");
                        }
                        else {
                            System.out.println("[ERR] - Aquest usuari no existeix al nostre sistema");
                        }
                        break;
                }


            }catch (InputMismatchException e){
                error = true;
                System.out.println("[ERR] - Format incorrecte");
            }

        } while (error);
    }


    // --------- OPCIÓN 6 --------------
    private static void searchInfo() {
        int error;
        do {
            System.out.println("[SYS] - Quina informació vols buscar?\n[SYS] - \t1. Visualitza usuaris per username\n[SYS] - \t2. Visualitza post per id\n[SYS] - \t3. Visualitza posts per coordenades\n[SYS] - \t4. Visualitza per hashtags");
            try {
                error = 0;
                int option = new Scanner(System.in).nextInt();
                switch (option) {
                    case 1:
                        System.out.println("[USERS] Introdueix username: ");
                        String username = new Scanner(System.in).next();
                        long initTime = System.nanoTime();
                        Array<String> usernames = trie.getMatchingWords(username);
                        System.out.println("Temps de cerca en Trie: " + (System.nanoTime() - initTime));
                        int foundSize = usernames.size();
                        if (foundSize == 0) {
                            System.out.println("No hi ha cap usuari que coincideixi amb la teva búsqueda");
                        }
                        else {
                            for (int i = 0; i < foundSize; i++) {
                                System.out.println(usernames.get(i));
                            }
                        }

                        break;

                    case  2:
                        System.out.println("[POSTS] Introdueix id: ");
                        try{
                            int postId = new Scanner(System.in).nextInt();
                            initTime = System.nanoTime();
                            Post found = (Post)RBT.search(new Post(postId, null, 0, null, null, null), RBT.getRoot());
                            System.out.println("Temps de cerca en RBT: " + (System.nanoTime() - initTime));
                            if (found == null){
                                System.out.println("[POSTS] - Post no trobat :(");
                            }else{
                                System.out.println("[POSTS] - Post trobat!");
                                System.out.println(found.toString());
                            }
                        }catch (Exception e){
                            System.out.println("[SYS] - Valor no vàlid. Només són vàlids valors numèrics enters superiors a 0.");
                        }


                        break;

                    case 3:
                        System.out.print("[POSTS] Introdueix latitud: \n> ");
                        double latitud = new Scanner(System.in).nextDouble();
                        System.out.print("[POSTS] Introdueix longitud: \n> ");
                        double longitud = new Scanner(System.in).nextDouble();
                        /*
                        try{
                            Array<Post> posts = rTree.getRoot().searchPoints(new Array<>(), latitud, longitud);
                            System.out.println("Shan trobat " + posts.size() + " posts a prop d'aquesta localitzacio:");
                            for(int i = 0; i < posts.size(); i++){
                                Post print_post = (Post) posts.get(i);
                                print_post.toString();
                            }
                        }catch (Exception e){
                            System.out.println("[SYS] - Coordenades invàlides");
                        }
                        */
                        break;

                    case 4:
                        // TODO PER HASHTAG
                        break;

                    default:
                        System.out.println("[ERR] - Aquesta opcio no es vàlida");
                        error = 1;
                        break;
                }
            } catch (InputMismatchException e) {
                error = 1;
                System.out.println("[ERR] - Format incorrecte");
            }
        } while (error == 1);
    }

    private static void insertInfo() {
        System.out.println("Quin tipus d'informació vol inserir?");
        System.out.print("\t1. Nou Usuari\n\t2. Nou Post\n> ");
        int option = new Scanner(System.in).nextInt();
        try {
            switch (option) {
                case 1:
                    insertUser();
                    break;

                case 2:
                    insertPost();
                    break;

                default:
                    System.out.println("[ERR] - Aquesta opció no és vàlida\n");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("[ERR] - Aquesta opció no és vàlida\n");
        }
    }

    private static void insertUser() {
        boolean error;
        System.out.print("Nom d'usuari:\n> ");
        String nomUser = new Scanner(System.in).next();
        long dataCreacio = -1;
        do {
            try {
                System.out.print("Data de creació:\n> ");
                dataCreacio = new Scanner(System.in).nextLong();
                error = false;
            } catch (InputMismatchException e) {
                System.out.println("[ERR] - Has d'introduir un valor numèric");
                error = true;
            }
        } while (error);
        char aux = 'Y';
        Array<String> usersFollowed = new Array<>();
        do {
            do {
                try {
                    System.out.println("Segueix a algun usuari? [Y/N]");
                    String input = new Scanner(System.in).next();
                    if (input.length() > 1) {
                        error = true;
                    }
                    else {
                        aux = input.charAt(0);
                        if (aux == 'Y' || aux == 'y') {
                            System.out.print("> ");
                            String userFollowed = new Scanner(System.in).next();
                            usersFollowed.add(userFollowed);
                        } else if (aux != 'n' && aux != 'N') {
                            error = true;
                        } else {
                            error = false;
                        }
                    }
                } catch (InputMismatchException e) {
                    error = true;
                }
                if (error) {
                    System.out.println("[ERR] - Has d'introduir Y/y o N/n");
                }
            } while (error);
        } while (aux == 'Y' || aux == 'y');
        long initTime = System.nanoTime();
        usersArray.add(new User(nomUser,dataCreacio,usersFollowed));
        System.out.println("Temps d'inserció en Array: " + (System.nanoTime() - initTime));
        initTime = System.nanoTime();
        graph.add(new GraphNode<>(new User(nomUser, dataCreacio, usersFollowed),usersFollowed));
        System.out.println("Temps d'inserció en Graph: " + (System.nanoTime() - initTime));
        initTime = System.nanoTime();
        trie.insert(nomUser);
        System.out.println("Temps d'inserció en Trie: " + (System.nanoTime() - initTime));
        System.out.println("[SYS] - Inserció realitzada amb èxit!\n");
    }

    private static void insertPost() {
        boolean error = false;
        int idPost = -1;
        do {
            try {
                System.out.print("ID del post:\n> ");
                idPost = new Scanner(System.in).nextInt();
                error = false;
            } catch (InputMismatchException e) {
                System.out.println("[ERR] - Has d'introduir un valor numèric");
                error = true;
            }
        } while (error);
        char aux = 'Y';
        Array<String> likedBy = new Array<>();
        do {
            do {
                try {
                    System.out.println("Té likes? [Y/N]");
                    String input = new Scanner(System.in).next();
                    if (input.length() > 1) {
                        error = true;
                    }
                    else {
                        aux = input.charAt(0);
                        if (aux == 'Y' || aux == 'y') {
                            System.out.print("> ");
                            String userLiked = new Scanner(System.in).next();
                            likedBy.add(userLiked);
                        } else if (aux != 'n' && aux != 'N') {
                            error = true;
                        } else {
                            error = false;
                        }
                    }
                } catch (InputMismatchException e) {
                    error = true;
                }
                if (error) {
                    System.out.println("[ERR] - Has d'introduir Y/y o N/n");
                }
            } while (error);
        } while (aux == 'Y' || aux == 'y');
        long dataPublished = -1;
        do {
            try {
                System.out.print("Data de publicació:\n> ");
                dataPublished = new Scanner(System.in).nextLong();
                error = false;
            } catch (InputMismatchException e) {
                System.out.println("[ERR] - Has d'introduir un valor numèric");
                error = true;
            }
        } while (error);
        System.out.print("Qui és l'autor?\n> ");
        String publishedBy = new Scanner(System.in).next();
        Location location = new Location(-1, -1);
        do {
            try {
                System.out.print("Coordenada longitud:\n> ");
                double longitud = new Scanner(System.in).nextDouble();
                System.out.print("Coordenada latitud:\n> ");
                double latitud = new Scanner(System.in).nextDouble();
                location = new Location(latitud, longitud);
                error = false;
            } catch (InputMismatchException e) {
                System.out.println("[ERR] - Has d'introduir un valor numèric");
                error = true;
            }
        } while (error);
        aux = 'Y';
        Array<String> hashtags = new Array<>();
        do {
            try {
                System.out.println("Té hashtags? [Y/N]");
                String input = new Scanner(System.in).next();
                if (input.length() > 1) {
                    error = true;
                }
                else {
                    aux = input.charAt(0);
                    if (aux == 'Y' || aux == 'y') {
                        System.out.println("Introdueix tots els hashtags separats per un espai:");
                        System.out.print("> ");
                        String hashtgs = new Scanner(System.in).nextLine();
                        String[] hashtagsAux = hashtgs.split(" ");
                        int size = hashtagsAux.length;
                        for (int i = 0; i < size; i++) {
                            hashtags.add(hashtagsAux[i]);
                        }
                        error = false;
                    } else if (aux != 'n' && aux != 'N') {
                        error = true;
                    } else {
                        error = false;
                    }
                }
            } catch (InputMismatchException e) {
                error = true;
            }
            if (error) {
                System.out.println("[ERR] - Has d'introduir Y/y o N/n");
            }
        } while (error);
        long initTime = System.nanoTime();
        postsArray.add(new Post(idPost, likedBy, dataPublished, publishedBy, location, hashtags));
        System.out.println("Temps d'inserció en Array: " + (System.nanoTime() - initTime));
        initTime = System.nanoTime();
        RBT.insertNode(new RBTnode<Post>(new Post(idPost, likedBy, dataPublished, publishedBy, location, hashtags)), RBT.getRoot(), null);
        System.out.println("Temps d'inserció en RBT: " + (System.nanoTime() - initTime));
        initTime = System.nanoTime();
        hashTable.add(new Post(idPost, likedBy, dataPublished, publishedBy, location, hashtags), Post.class);
        System.out.println("Temps d'inserció en Hashtable: " + (System.nanoTime() - initTime));
        initTime = System.nanoTime();
        rTree.insertPost(new Post(idPost, likedBy, dataPublished, publishedBy, location, hashtags));
        //rTree.getRoot().insertPoint(new Post(idPost, likedBy, dataPublished, publishedBy, location, hashtags), location.getLongitude(), location.getLatitude());
        System.out.println("Temps d'inserció en RTree: " + (System.nanoTime() - initTime));
        System.out.println("[SYS] - Inserció realitzada amb èxit!\n");
    }
}
