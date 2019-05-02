package json;

public abstract class ConstValues {

    // Users Json File const values
    public static final String JSON_USERNAME_TAG = "username";
    public static final String JSON_CREATION_TAG = "creation";
    public static final String JSON_TO_FOLLOW_TAG = "to_follow";
    // Posts Json File const values
    public static final String JSON_ID = "id";
    public static final String JSON_LIKED_BY = "liked_by";
    public static final String JSON_PUBLISHED_WHEN = "published_when";
    public static final String JSON_PUBLISHED_BY = "published_by";
    public static final String JSON_LOCATION = "location";
        public static final String JSON_LATITUDE = "latitude";
        public static final String JSON_LONGITUDE = "longitude";
    public static final String JSON_HASHTAGS = "hashtags";

    // MENU values + Strings
    public static final int IMPORT1 = 1;
    public static final String IMPORT_STRING = "1. Importació de l'estat de les estructures";
    public static final int EXPORT2 = 2;
    public static final String EXPORT_STRING = "2. Exportació de l'estat de les estructures";
    public static final int SHOW3 = 3;
    public static final String SHOW_STRING = "3. Visualització de l'estat de les estructures";
    public static final int INSERT4 = 4;
    public static final String INSERT_STRING = "4. Inserció d'informació";
    public static final int DELETE5 = 5;
    public static final String DELETE_STRING = "5. Esborrar informació";
    public static final int SEARCH6 = 6;
    public static final String SEARCH_STRING = "6. Cercar informació";
    //public static final int AUTOCOMPLETE = 7;
    //public static final String AUTOCOMPLETE_STRING = "7. ";
    public static final int LIMIT_MEMORY7 = 7;
    public static final String LIMIT_MEMORY_STRING = "7. Limitar memòria per autocompletar.";
    public static final int EXIT8 = 8;
    public static final String EXIT_STRING = "8. Sortir";
    public static final int ERROR9 = 9;
    public static final String ERROR_STRING = System.lineSeparator() + "[ERR] - Format incorrecte." + System.lineSeparator() + System.lineSeparator();

}
