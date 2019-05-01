import dataStructures.graph.Graph;
import dataStructures.hashTable.HashTable;
import dataStructures.redBlackTree.RBT;
import json.ConstValues;
import model.Post;
import model.User;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InstaSalle {
    public static void main(String[] args) {
        // Inicialización de las estructuras vacías
        // TODO: Trie para autocompletar
        // TODO: R-Tree Para indexar posts por LOCALIZACIÓN

        // Red Black Tree para indexar posts por ID
        RBT<Post> RBT = new RBT<Post>(new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        HashTable<Post> hashTable = new HashTable<Post>();
        Graph<User> graph = new Graph<User>();

        int option = 0;
        while (option != ConstValues.EXIT8){
            mostraMenu();
            try{
                option = demanaOpcio();
            }catch (InputMismatchException e){
                option = ConstValues.ERROR9;
            }finally {
                switch (option){
                    case ConstValues.IMPORT1:
                        // Importació dels JSON a les estructures

                        break;

                    case ConstValues.EXPORT2:
                        // Exportació de les estructures a arxius JSON

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
}
