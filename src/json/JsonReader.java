package json;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import dataStructures.array.Array;
import model.Post;
import model.User;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonReader {

    // FILENAMES
    public static String USERS_FILENAME = "files/users.json";
    public static String POSTS_FILENAME = "files/posts.json";

    public static Array<User> readUsers() throws FileNotFoundException {
        Array<User> users = new Array<User>();

        JsonParser parser = new JsonParser();
        JsonArray usersJsonArray = (JsonArray) parser.parse(new FileReader(USERS_FILENAME));

        int jsonSize = usersJsonArray.size();
        for (int i = 0; i < jsonSize; i++){
            users.add(new User(usersJsonArray.get(i).getAsJsonObject()));
        }

        return users;
    }
    public static Array<Post> readPosts() throws FileNotFoundException {
        Array<Post> posts = new Array<Post>();

        JsonParser parser = new JsonParser();
        JsonArray postsJsonArray = (JsonArray) parser.parse(new FileReader(POSTS_FILENAME));

        int jsonSize = postsJsonArray.size();
        for (int i = 0; i < jsonSize; i++){
            posts.add(new Post(postsJsonArray.get(i).getAsJsonObject()));
        }

        return posts;
    }
}
