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
        max_entries = 200;
        top_point = new double[2];
        top_point[0] = 0.0;
        top_point[1] = 0.0;
        bottom_point = new double[2];
        bottom_point[0] = 0.0;
        bottom_point[1] = 0.0;
        regions = new Array<>();
        points = new Array<>();
    }

    public NodeFulla(Post post, int entries){
        max_entries = entries;
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

    public void insertPoint (Post info, double coord_x, double coord_y){
        if (regions.size() == 0){
            if (points.size() == max_entries){
                split();
                insertInNearRegion(info);
            }else{
                expandRegion(coord_x, coord_y);
                points.add(info);
            }
        }else{
            insertInNearRegion(info);
        }
    }

    public Array<Post> searchPoints (Array<Post> posts, double coord_x, double coord_y){
        if (regions.size() == 0){
            for (int i = 0; i < points.size(); i++) {
                posts.add((Post) points.get(i));
            }
        }else{
            for (int i = 0; i < regions.size(); i++) {
                if (isInRegion(coord_x, coord_y, (NodeFulla) regions.get(i))){
                    NodeFulla n = (NodeFulla) regions.get(i);
                    posts = n.searchPoints(posts, coord_x, coord_y);
                }
            }
        }
        return posts;
    }

    public void deletePoint (int id, double coord_x, double coord_y){
        if (regions.size() == 0){
            for (int i = 0; i < points.size(); i++){
                Post p = (Post) points.get(i);
                if(p.getLocation().getLatitude() == coord_x && p.getLocation().getLongitude() == coord_y && id == p.getId()){
                    points.remove(i);
                    i--;
                }
            }
        }else{
            for (int i = 0; i < regions.size(); i++) {
                if (isInRegion(coord_x, coord_y, (NodeFulla) regions.get(i))){
                    NodeFulla n = (NodeFulla) regions.get(i);
                    n.deletePoint(id, coord_x, coord_y);
                }
            }
        }
    }

    public void deletePoint(int id) {
        if (regions.size() == 0){
            for (int i = 0; i < points.size(); i++){
                Post p = (Post) points.get(i);
                if(id == p.getId()){
                    points.remove(i);
                    i--;
                }
            }
        }else{
            for (int i = 0; i < regions.size(); i++) {
                NodeFulla n = (NodeFulla) regions.get(i);
                n.deletePoint(id);
            }
        }
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
                double dist = calcularDistancia((Post)points.get(i),(Post)points.get(j));
                if (dist > distancia){
                    distancia = calcularDistancia((Post)points.get(i),(Post)points.get(j));
                    index1 = i;
                    index2 = j;
                }
            }
        }

        nodeFulla1 = new NodeFulla((Post)points.get(index1), max_entries);
        nodeFulla2 = new NodeFulla((Post)points.get(index2), max_entries);

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
        double x2 = post2.getLocation().getLatitude();
        double x1 = post1.getLocation().getLatitude();
        double y2 = post2.getLocation().getLongitude();
        double y1 = post1.getLocation().getLongitude();
        return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
    }

    private double calcularDistancia(Post post1,NodeFulla nf) {
        double center_x = nf.getTop_point()[0] + (nf.getBottom_point()[0] - nf.getTop_point()[0]) / 2;
        double center_y = nf.getTop_point()[1] + (nf.getBottom_point()[1] - nf.getTop_point()[1]) / 2;
        double x1 = post1.getLocation().getLatitude();
        double y1 = post1.getLocation().getLongitude();
        return Math.sqrt((center_x-x1)*(center_x-x1) + (center_y-y1)*(center_y-y1));
    }

    private void expandRegion(double coord_x, double coord_y) {
        if (coord_x < top_point[0]) top_point[0] = coord_x;
        if (coord_y < top_point[1]) top_point[1] = coord_y;
        if (coord_x > bottom_point[0]) bottom_point[0] = coord_x;
        if (coord_y > bottom_point[1]) bottom_point[1] = coord_y;
    }
}
