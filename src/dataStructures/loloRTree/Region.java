package dataStructures.loloRTree;

import model.Location;

/**
 * X = latitude
 * Y = longitude
 */
public class Region {
    // Attributes
    private Location minLocation;
    private Location maxLocation;

    // Constructor
    public Region(Location minLocation, Location maxLocation) {
        this.minLocation = new Location(minLocation.getLatitude(), minLocation.getLongitude());
        this.maxLocation = new Location(maxLocation.getLatitude(), maxLocation.getLongitude());
    }

    // Getters & Setters
    public Location getMinLocation() {
        return minLocation;
    }
    public void setMinLocation(Location minLocation) {
        this.minLocation = minLocation;
    }
    public Location getMaxLocation() {
        return maxLocation;
    }
    public void setMaxLocation(Location maxLocation) {
        this.maxLocation = maxLocation;
    }

    // Changers

    /**
     * Updates the region if necessary
     * @param newLocation
     */
    public void update(Location newLocation){
        double x = newLocation.getLatitude();
        double y = newLocation.getLongitude();
        if (x < getMinLocation().getLatitude()){
            changeXmin(x);
        }else if (x > getMaxLocation().getLatitude()){
            changeXmax(x);
        }
        if (y < getMinLocation().getLongitude()){
            changeYmin(y);
        }else if (y > getMaxLocation().getLongitude()){
            changeYmax(y);
        }
    }
    /**
     * Method to update the min latitude of the region
     * @param newXmin The new min latitude
     */
    public void changeXmin(double newXmin){
        this.getMinLocation().setLatitude(newXmin);
    }

    /**
     * Method to update the max latitude of the region
     * @param newXmax The new max latitude
     */
    public void changeXmax(double newXmax){
        this.getMaxLocation().setLatitude(newXmax);
    }

    /**
     * Method to update the min longitude of the region
     * @param newYmin The new min longitude
     */
    public void changeYmin(double newYmin){
        this.getMinLocation().setLongitude(newYmin);
    }

    /**
     * Method to update the max longitude of the region
     * @param newYmax The new max longitude
     */
    public void changeYmax(double newYmax){
        this.getMaxLocation().setLongitude(newYmax);
    }

    // Functions
    /**
     * Function that tells if a Location is inside of the region or not
     * @param location Location to check is is inside
     * @return True if is inside
     */
    public boolean isInside(Location location){
        return (isXinside(location.getLatitude()) && isYinside(location.getLongitude()));
    }
    private boolean isXinside(double x){
        return x >= minLocation.getLatitude() && x <= maxLocation.getLatitude();
    }
    private boolean isYinside(double y){
        return y >= minLocation.getLongitude() && y <= maxLocation.getLongitude();
    }


    /**
     * Function that returns the area of the increase if the location parameter is added to the region
     * @param location Location of the post that should probably be inserted in this region
     * @return the area of the increase
     */
    public double possiblyIncrease(Location location){
        if (isXinside(location.getLatitude())){
            if (isYinside(location.getLongitude())){
                return 0;
            }else{
                if (location.getLongitude() < minLocation.getLongitude()){
                    return (getMaxLocation().getLatitude() - getMinLocation().getLatitude()) * (getMaxLocation().getLongitude() - location.getLongitude());
                }else{
                    return (getMaxLocation().getLatitude() - getMinLocation().getLatitude()) * (location.getLongitude() - getMinLocation().getLongitude());
                }
            }
        }else{
            if (isYinside(location.getLongitude())){
                if (location.getLatitude() < minLocation.getLatitude()){
                    return (getMaxLocation().getLatitude() - location.getLatitude()) * (getMaxLocation().getLongitude() - getMinLocation().getLongitude());
                }else{
                    return (location.getLatitude() - getMinLocation().getLatitude()) * (getMaxLocation().getLongitude() - getMinLocation().getLongitude());
                }
            }else{
                double minX, minY, maxX, maxY;
                if (location.getLatitude() < getMinLocation().getLatitude()){
                    minX = location.getLatitude();
                }else{
                    minX = getMinLocation().getLatitude();
                }
                if (location.getLatitude() > getMaxLocation().getLatitude()){
                    maxX = location.getLatitude();
                }else{
                    maxX = getMaxLocation().getLatitude();
                }
                if (location.getLongitude() < getMinLocation().getLongitude()){
                    minY = location.getLongitude();
                }else{
                    minY = getMinLocation().getLongitude();
                }
                if (location.getLongitude() > getMaxLocation().getLongitude()){
                    maxY = location.getLongitude();
                }else{
                    maxY = getMaxLocation().getLongitude();
                }

                return (maxX - minX) * (maxY - minY);
            }
        }
    }

    /**
     * Function to calculate the are of a region
     * @return
     */
    public double area(){
        double x = getMaxLocation().getLatitude() - getMinLocation().getLatitude();
        double y = getMaxLocation().getLongitude() - getMinLocation().getLongitude();
        if (x == 0 && y == 0){
            return 0;
        }
        return x * y;
    }

    public Location center() {
        double x = getMaxLocation().getLatitude() - getMinLocation().getLatitude();
        double y = getMaxLocation().getLongitude() - getMinLocation().getLongitude();
        return new Location(x, y);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator());
        builder.append("Min: ").append(minLocation.getLatitude()).append(" ,").append(minLocation.getLongitude());
        builder.append(System.lineSeparator());
        builder.append("Max: ").append(maxLocation.getLatitude()).append(" ,").append(maxLocation.getLongitude());
        builder.append(System.lineSeparator());
        return builder.toString();
    }
}
