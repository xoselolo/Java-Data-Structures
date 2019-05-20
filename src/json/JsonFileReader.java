package json;

import com.fasterxml.jackson.core.JsonFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import dataStructures.array.Array;
import model.Location;
import model.Post;
import model.User;

import java.io.*;

public class JsonFileReader {

    // FILENAMES
    public static String USERS_FILENAME = "files/users.json";
    //public static String USERS_FILENAME = "files/datasets/small/users.json";
    public static String POSTS_FILENAME = "files/datasets/small/posts.json";

    public static Array<User> readUsers() throws FileNotFoundException {
        Array<User> users = new Array<>();

        try {
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser jsonParser = jsonFactory.createJsonParser(new File(USERS_FILENAME));
            JsonToken current = jsonParser.nextToken();
            if (current == JsonToken.START_ARRAY) {
                while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                    String username = "";
                    long creation = 0;
                    Array<String> toFollow = new Array<>();

                    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                        String token = jsonParser.getCurrentName();
                        if (token.equals("username")) {
                            jsonParser.nextToken();
                            username = jsonParser.getText();
                            System.out.println(username);
                        } else if (token.equals("creation")) {
                            jsonParser.nextToken();
                            creation = jsonParser.getLongValue();
                        } else if (token.equals("to_follow")) {
                            toFollow = new Array<>();
                            jsonParser.nextToken();
                            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                toFollow.add(jsonParser.getText());
                            }
                        }
                        users.add(new User(username,creation,toFollow));
                    }
                }
            }

            jsonParser.close();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
    public static Array<Post> readPosts() throws FileNotFoundException {
        Array<Post> posts = new Array<>();

        try {
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser jsonParser = jsonFactory.createJsonParser(new File(POSTS_FILENAME));
            JsonToken current = jsonParser.nextToken();
            if (current == JsonToken.START_ARRAY) {
                while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                    int id = -1;
                    Array<String> likedBy = new Array<>();
                    long publishedWhen = 0;
                    String publishedBy = "";
                    double[] location = new double[2];
                    Array<String> hashtags = new Array<>();

                    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                        String token = jsonParser.getCurrentName();
                        if (token.equals("id")) {
                            jsonParser.nextToken();
                            id = jsonParser.getIntValue();
                            System.out.println(id);
                        } else if (token.equals("liked_by")) {
                            likedBy = new Array<>();
                            jsonParser.nextToken();
                            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                likedBy.add(jsonParser.getText());
                            }
                        } else if (token.equals("published_when")) {
                            jsonParser.nextToken();
                            publishedWhen = jsonParser.getLongValue();
                        } else if (token.equals("published_by")) {
                            jsonParser.nextToken();
                            publishedBy = jsonParser.getText();
                        } else if (token.equals("location")) {
                            location = new double[2];
                            jsonParser.nextToken();
                            int i = 0;
                            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                location[i] = jsonParser.getDoubleValue();
                                i++;
                            }
                        } else if (token.equals("hashtags")) {
                            hashtags = new Array<>();
                            jsonParser.nextToken();
                            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                hashtags.add(jsonParser.getText());
                            }
                            posts.add(new Post(id,likedBy,publishedWhen,publishedBy,
                                    new Location(location[0],location[1]),hashtags));
                        }
                    }
                }
            }

            jsonParser.close();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }
}
