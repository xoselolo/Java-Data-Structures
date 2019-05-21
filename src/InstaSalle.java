import com.google.gson.Gson;
import dataStructures.Trie.Trie;
import dataStructures.array.Array;
import dataStructures.graph.Graph;
import dataStructures.graph.GraphNode;
import dataStructures.hashTable.HashTable;
import dataStructures.redBlackTree.RBT;
import dataStructures.redBlackTree.RBTnode;
import json.ConstValues;
import json.JsonFileReader;
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

    public static void main(String[] args) {
        // Inicialización de las estructuras vacías

        // Array lineal de usuarios
        usersArray = new Array<User>();
        // Array lineal de posts
        postsArray = new Array<Post>();

        // TODO: Trie para autocompletar
        // TODO: R-Tree Para indexar posts por LOCALIZACIÓN

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
                        showStruct(estructura);
                        // Mostrar l'estructura

                        break;
                    case ConstValues.INSERT4:
                        // Usuari escull quin tipus de dades inserir
                        // Inserir la nova dada a les estructures que pertoquin

                        break;

                    case ConstValues.DELETE5:
                        // Usuari escull quin tipus de dades esborrar
                        // Esborrar la dada de les estructures que pertoquin

                        break;

                    case ConstValues.SEARCH6:
                        // Usuari escull quin tipus de informació cercar
                        // Cercar la dada escollida i mostrar-la
                        searchInfo();

                        break;

                    case ConstValues.LIMIT_MEMORY7:
                        System.out.println("\tQuin limit de paraules vols?\n");
                        trie.setNumWords(new Scanner(System.in).nextInt());
                        System.out.println("\tLimit de paraules canviat a " + trie.getNumWords());
                        // Limitar els tries per a guardar un màxim de paraules
                        // En cas de ser inferior al nombre de paraules ja guardades anteriorment
                        // eliminar aquelles paraules

                        break;

                    case ConstValues.EXIT8:
                        // Sortir (mostrar missatge d'acumiadament)

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

            // TODO: option = demanaOpcio();
        }
    }

    private static void showStruct(int estructura) {
        String output = "--";
        switch (estructura){
            case TRIE:
                output = "Opció escollida: Trie" + System.lineSeparator();
                //output = output + trie.toString();//todo
                break;
            case RTREE:
                output = "Opció escollida: R-Tree" + System.lineSeparator();
                // TODO
                break;
            case REDBLACKTREE:
                output = "Opció escollida: RedBlackTree" + System.lineSeparator();
                output = output + RBT.toString(); //todo
                break;
            case HASHTABLE:
                output = "Opció escollida: Taula de Hash" + System.lineSeparator();
                output = output + hashTable.toString();//TODO
                break;
            case GRAPH:
                output = "Opció escollida: Graf" + System.lineSeparator();
                output = output + graph.toString();//TODO
                break;
            default:
                output = "Opció no vàlida!" + System.lineSeparator();
                break;
        }
        System.out.println(output);
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
        System.out.println(System.lineSeparator() + "[SYS] - MENU");
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
        System.out.println("[OPT1] - Carregant informació...");
        try {
            usersArray = JsonFileReader.readUsers();
            importIntoGraph();
            importIntoTrie();
        } catch (FileNotFoundException e) {
            importOK++;
            usersArray = new Array<User>();
        }finally {
            try {
                postsArray = JsonFileReader.readPosts();
                importIntoRBT();
                importIntoHashTable();
            } catch (FileNotFoundException e) {
                importOK += 2;
                postsArray = new Array<Post>();
            } finally {
                switch (importOK){
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
    }

    private static void importIntoHashTable() {
        int numPosts = postsArray.size();
        for (int i = 0; i < numPosts; i++){
            Post post = (Post)postsArray.get(i);
            hashTable.add(post, Post.class);
        }
    }

    private static void importIntoGraph(){
        int numUsers = usersArray.size();
        for (int i = 0; i < numUsers; i++){
            User user = (User)usersArray.get(i);
            Array<String> next = user.getTo_follow();
            graph.add(new GraphNode<User>(user, next));
        }
    }

    private static void importIntoRBT(){
        int numPosts = postsArray.size();
        for (int i = 0; i < numPosts; i++){
            RBT.insertNode(new RBTnode<Post>((Post)postsArray.get(i)), RBT.getRoot(), null);
        }
    }

    private static void importIntoTrie() {
        int numUsers = usersArray.size();
        for (int i = 0; i < numUsers; i++) {
            if (usersArray.get(i) instanceof User) {
                trie.insert(((User) usersArray.get(i)).getUsername());
            }
        }
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
                    System.out.println("[IN] - Especifiqui la ruta del fitxer a exportar corresponent a usuaris:");
                    String pathUsers = (new Scanner(System.in)).nextLine();
                    System.out.println("[IN] - Especifiqui la ruta del fitxer a exportar corresponent a posts:");
                    String pathPosts = (new Scanner(System.in)).nextLine();

                    // ----- Users -----
                    Gson gson = new Gson();
                    String jsonToString = gson.toJson(usersArray);
                    try{
                        Files.write(Paths.get("files/usersOut.json"), jsonToString.getBytes(), StandardOpenOption.CREATE);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    /*Writer writer = new FileWriter("files/usersOut.json");
                    Gson gson = new GsonBuilder().create();
                    //gson.toJson(usersArray);

                    User user = (User) usersArray.get(0);
                    gson.toJson(user, writer);

                    int numUsers = usersArray.size();*/

                    break;

                case 2:
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



    // --------- OPCIÓN 6 --------------
    private static void searchInfo() {
        int error;
        do {
            System.out.println("[SYS] - Quina informació vols buscar?\n[SYS] - \t\t\t1. Visualitza usuaris");
            try {
                error = 0;
                int option = new Scanner(System.in).nextInt();
                switch (option) {
                    case 1:
                        System.out.println("[USERS] Introdueix username: ");
                        String username = new Scanner(System.in).next();
                        Array<String> usernames = trie.getMatchingWords(username);
                        int foundSize = usernames.size();
                        for (int i = 0; i < foundSize; i++) {
                            System.out.println(usernames.get(i));
                        }

                        break;

                    case  2:
                        System.out.println("[POSTS] Introdueix id: ");
                        try{
                            int postId = new Scanner(System.in).nextInt();
                            Post found = (Post)RBT.search(new Post(postId, null, 0, null, null, null), RBT.getRoot());
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

                        break;
                }
            } catch (InputMismatchException e) {
                error = 1;
                System.out.println("[ERR] - Format incorrecte");
            }
        } while (error == 1);
    }
}
