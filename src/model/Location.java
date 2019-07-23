package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import json.ConstValues;

public class Location {
    private static final int EARTH_RAD = 6371;

    // Attributes
    private double latitude;
    private double longitude;

    // Constructor
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Location(JsonArray jsonLocation){
        this.latitude = jsonLocation.get(0).getAsDouble();
        this.longitude = jsonLocation.get(1).getAsDouble();
    }

    public Location(JsonObject asJsonObject) {
        this.latitude = asJsonObject.get(ConstValues.JSON_LATITUDE).getAsDouble();
        this.longitude = asJsonObject.get(ConstValues.JSON_LONGITUDE).getAsDouble();
    }

    public Location(Location location){
        this.latitude = location.latitude;
        this.longitude = location.longitude;
    }


    //Getters & Setters
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    // Functions
    /**
     * HAVERSINE to calculate distance between 2 points in the globe
     * @param l2: Location we are comparing to
     * @return double: the distance between this location and l2 location
     */
    public double distance(Location l2) {
        Double lat = aRadians(getLatitude() - l2.getLatitude());
        Double lon = aRadians(getLongitude() - l2.getLongitude());

        Double rA = Math.sin(lat / 2) * Math.sin(lat / 2) + Math.cos(aRadians(getLatitude())) *
                Math.cos(aRadians(l2.getLatitude())) * Math.sin(lon / 2) * Math.sin(lon / 2);

        Double rC = Math.atan2(Math.sqrt(rA), Math.sqrt(1 - rA)) * 2;

        return EARTH_RAD * rC;

        //return Math.sqrt(Math.pow(this.latitude - l2.latitude, 2) + Math.pow(this.longitude - l2.longitude, 2));
    }
    private Double aRadians(Double d){
        return (d * Math.PI) / 180;
    }

    @Override
    public String toString() {
        return "{" +
                "\"latitude\": " + latitude +
                ", \"longitude\": " + longitude +
                '}';
    }

    public boolean isInside(Location downLeftCorner, Location upRightCorner) {
        return this.latitude > downLeftCorner.getLatitude() && this.longitude > downLeftCorner.getLongitude()
                && this.latitude < upRightCorner.getLatitude() && this.longitude < upRightCorner.getLongitude();
    }

    public Location clone(){
        return new Location(getLatitude(), getLongitude());
    }
}
