package DisasterCenter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author andrew
 */
public class Location {

    int locationID;
    float latitude;
    float longitude;
    int streetNumber;
    String street;
    String city;
    int zipcode;

    public Location(int locationID) {
        this.locationID = locationID;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setlocationID(int locationID) {
        this.locationID = locationID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcodes(int zipcode) {
        this.zipcode = zipcode;
    }
}
