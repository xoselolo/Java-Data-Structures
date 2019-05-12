package dataStructures.rTree;

import dataStructures.array.Array;
import model.Post;

public class NodeFulla {

    private int max_entries;
    private double[] top_point;
    private double[] bottom_point;
    private Array<NodeFulla> regions;
    private Array<Post> points;

    public NodeFulla(){
        max_entries = 5;
        top_point = new double[2];
        top_point[0] = 0.0;
        top_point[1] = 0.0;
        bottom_point = new double[2];
        bottom_point[0] = 0.0;
        bottom_point[1] = 0.0;
        regions = new Array<>();
        points = new Array<>();
    }

    public NodeFulla(Post post){
        max_entries = 5;
        top_point = new double[2];
        top_point[0] = post.getLocation().getLatitude();
        top_point[1] = post.getLocation().getLongitude();
        bottom_point = new double[2];
        bottom_point[0] = post.getLocation().getLatitude();
        bottom_point[1] = post.getLocation().getLongitude();
        regions = new Array<>();
        points = new Array<>();
        points.add(post);
    }

    public double[] getTop_point() {
        return top_point;
    }

    public void setTop_point(double[] top_point) {
        this.top_point = top_point;
    }

    public double[] getBottom_point() {
        return bottom_point;
    }

    public void setBottom_point(double[] bottom_point) {
        this.bottom_point = bottom_point;
    }

    public Array<NodeFulla> getRegions() {
        return regions;
    }

    public void setRegions(Array<NodeFulla> regions) {
        this.regions = regions;
    }

    public Array<Post> getPoints() {
        return points;
    }

    public void setPoints(Array<Post> points) {
        this.points = points;
    }

    public void insertPoint (Post info, double coord_x, double coord_y){
        if (regions.size() == 0){
            if (points.size() == max_entries){
                split();
                insertInNearRegion(info);
            }else{
                expandRegion(coord_x, coord_y);
                points.add(info);
            }
        }
    }

    public Array<Post> searchPoints (double coord_x, double coord_y){
        Array<Post> posts = new Array<>();
        if (regions.size() == 0){
            posts = points;
        }else{
            for (int i = 0; i < regions.size(); i++) {
                if (isInRegion(coord_x, coord_y, (NodeFulla) regions.get(i))){
                    NodeFulla n = (NodeFulla) regions.get(i);
                    posts = n.searchPoints(coord_x, coord_y);
                }
            }
        }
        return posts;
    }

    private boolean isInRegion(double coord_x, double coord_y, NodeFulla node) {
        return (node.getTop_point()[0] <= coord_x && node.getBottom_point()[0] >= coord_x && node.getTop_point()[1] <= coord_y && node.getBottom_point()[1] >= coord_y);
    }

    private void insertInNearRegion(Post info) {
        int indexRegio = 0;
        double distancia = 999999999;

        for (int i = 0; i < regions.size(); i++){
            if (calcularDistancia(info, (NodeFulla) regions.get(i)) < distancia){
                indexRegio = i;
            }
        }

        NodeFulla node = (NodeFulla) regions.get(indexRegio);
        node.insertPoint(info, info.getLocation().getLatitude(), info.getLocation().getLongitude());
        regions.remove(indexRegio);
        regions.add(node);
    }

    private void split() {
        NodeFulla nodeFulla1;
        NodeFulla nodeFulla2;

        double distancia = 0;
        int index1 = 0;
        int index2 = 0;

        for (int i = 0; i < points.size(); i++){
            for (int j = i + 1; j < points.size(); j++){
                if (calcularDistancia((Post)points.get(i),(Post)points.get(i)) > distancia){
                    distancia = calcularDistancia((Post)points.get(i),(Post)points.get(i));
                    index1 = i;
                    index2 = j;
                }
            }
        }

        nodeFulla1 = new NodeFulla((Post)points.get(index1));
        nodeFulla2 = new NodeFulla((Post)points.get(index2));

        for(int i = 0; i < points.size(); i++){
            if (i != index1 && i != index2){
                if (calcularDistancia((Post)points.get(i),(Post)points.get(index1)) < calcularDistancia((Post)points.get(i),(Post)points.get(index2))){
                    nodeFulla1.insertPoint((Post)points.get(i),((Post) points.get(i)).getLocation().getLatitude(),((Post) points.get(i)).getLocation().getLongitude());
                }else{
                    nodeFulla2.insertPoint((Post)points.get(i),((Post) points.get(i)).getLocation().getLatitude(),((Post) points.get(i)).getLocation().getLongitude());
                }
            }
        }

        regions.add(nodeFulla1);
        regions.add(nodeFulla2);

        for (int i = points.size() - 1; i >= 0; i--){
            points.remove(i);
        }
    }

    private double calcularDistancia(Post post1, Post post2) {
        return Math.sqrt((post2.getLocation().getLatitude()-post1.getLocation().getLatitude())*(post2.getLocation().getLatitude()-post1.getLocation().getLatitude()) + (post2.getLocation().getLongitude()-post1.getLocation().getLongitude())*(post2.getLocation().getLongitude()-post1.getLocation().getLongitude()));
    }

    private double calcularDistancia(Post post1,NodeFulla nf) {
        double center_x = nf.getBottom_point()[0] - nf.getTop_point()[0];
        double center_y = nf.getBottom_point()[1] - nf.getTop_point()[1];
        return Math.sqrt(center_x-post1.getLocation().getLatitude())*(center_x-post1.getLocation().getLatitude()) + (center_y-post1.getLocation().getLongitude())*(center_y-post1.getLocation().getLongitude());
    }

    private void expandRegion(double coord_x, double coord_y) {
        if (coord_x < top_point[0]) top_point[0] = coord_x;
        if (coord_y < top_point[1]) top_point[1] = coord_y;
        if (coord_x > bottom_point[0]) bottom_point[0] = coord_x;
        if (coord_y > bottom_point[1]) bottom_point[1] = coord_y;
    }
}
