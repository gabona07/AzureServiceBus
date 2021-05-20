package com.smartlynx.azuretest;

public class Location {
    String latitude;
    String longitude;
    String sensorID;
    String sensorName;
    String parkingSpaceID;
    String streetName;

    public Location(String latitude, String longitude, String sensorID, String sensorName, String parkingSpaceID, String streetName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.parkingSpaceID = parkingSpaceID;
        this.streetName = streetName;
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", sensorID='" + sensorID + '\'' +
                ", sensorName='" + sensorName + '\'' +
                ", parkingSpaceID='" + parkingSpaceID + '\'' +
                ", streetName='" + streetName + '\'' +
                '}';
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getParkingSpaceID() {
        return parkingSpaceID;
    }

    public void setParkingSpaceID(String parkingSpaceID) {
        this.parkingSpaceID = parkingSpaceID;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}
