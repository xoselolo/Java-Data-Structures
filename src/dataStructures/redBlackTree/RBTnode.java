package dataStructures.redBlackTree;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import model.Post;

public class RBTnode <T> {
    // Attributes
    public RBTnode pare;

    @SerializedName("red")
    @Expose
    public boolean red;

    @SerializedName("element")
    @Expose
    public T element;

    @SerializedName("fillE")
    @Expose
    public RBTnode<T> fillE;

    @SerializedName("fillD")
    @Expose
    public RBTnode<T> fillD;

    // Constructor
    public RBTnode(T element){
        this.element = element;
        this.red = true;
    }
    public <Post> RBTnode(T element, RBTnode pare){
        this.element = element;
        this.pare = pare;
    }

    @Override
    public String toString() {
        return "{" +
                "\"red\": " + red +
                ",\n\"element\": {" + ((Post)element).toStringExport() + "}" +
                ",\n\"fillE\": " + fillE +
                ",\n\"fillD\": " + fillD +
                '}';
    }
}
