package json;

import com.fasterxml.jackson.core.JsonFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import dataStructures.Trie.Trie;
import dataStructures.array.Array;
import dataStructures.graph.Graph;
import dataStructures.graph.GraphNode;
import dataStructures.hashTable.HashTable;
import dataStructures.rTree.RTree;
import dataStructures.redBlackTree.RBT;
import dataStructures.redBlackTree.RBTnode;
import model.Location;
import model.Post;
import model.User;

import java.io.*;

public class JsonFileReader {

    // FILENAMES
    //public static String USERS_FILENAME = "files/users.json";
    //public static String USERS_FILENAME = "files/datasets/small/users.json";
    //public static String USERS_FILENAME = "files/datasets/medium/users.json";
    public static String USERS_FILENAME = "files/datasets/small/users.json";

    //public static String POSTS_FILENAME = "files/posts.json";
    //public static String POSTS_FILENAME = "files/datasets/small/posts.json";
    //public static String POSTS_FILENAME = "files/datasets/medium/posts.json";
    public static String POSTS_FILENAME = "files/datasets/small/posts.json";

    public static void readUsers(Array<User> userArray, Graph<User> graph, Trie trie) throws FileNotFoundException {
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
                            //System.out.println(username);
                        } else if (token.equals("creation")) {
                            jsonParser.nextToken();
                            creation = jsonParser.getLongValue();
                        } else if (token.equals("to_follow")) {
                            toFollow = new Array<>();
                            jsonParser.nextToken();
                            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                toFollow.add(jsonParser.getText());
                            }

                            User actualUser = new User(username,creation,toFollow);
                            userArray.add(actualUser);
                            graph.add(new GraphNode<User>(actualUser, toFollow));
                            trie.insert(username);
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

    }
    public static void readPosts(Array<Post> postArray, RBT<Post> RBT, HashTable<Post> hashTable, RTree rTree) throws FileNotFoundException {
        //Array<Post> posts = new Array<>();

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
                            //System.out.println(id);
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
                            Post actualPost = new Post(id,likedBy,publishedWhen,publishedBy,
                                    new Location(location[0],location[1]),hashtags);

                            postArray.add(actualPost);
                            RBT.insertNode(new RBTnode<Post>(actualPost), RBT.getRoot(), null);
                            hashTable.add(actualPost, Post.class);
                            rTree.getRoot().insertPoint(actualPost, actualPost.getLocation().getLatitude(), actualPost.getLocation().getLongitude());
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
    }
}
