import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dataStructures.array.Array;
import dataStructures.graph.Graph;
import dataStructures.graph.GraphNode;
import dataStructures.hashTable.HashTable;
import dataStructures.redBlackTree.RBT;
import dataStructures.redBlackTree.RBTnode;
import json.ConstValues;
import json.JsonReader;
import model.Post;
import model.User;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InstaSalle {
    // Estructuras lineales
    private static Array<User> usersArray;
    private static Array<Post> postsArray;
    // Estructuras no lineales
    private static RBT<Post> RBT;
    private static HashTable<Post> hashTable;
    private static Graph<User> graph;

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

                        break;

                    case ConstValues.LIMIT_MEMORY7:
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
            usersArray = JsonReader.readUsers();
            importIntoGraph();
        } catch (FileNotFoundException e) {
            importOK++;
            usersArray = new Array<User>();
        }finally {
            try {
                postsArray = JsonReader.readPosts();
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
}
