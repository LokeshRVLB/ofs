package com.objectfrontier.training.java.jdbc;

/**
 * @author Lokesh.
 * @since Sep 21, 2018
 */
public class Address {

    private long id;
    private String street;
    private String city;
    private long postalCode;


    public Address(String street, String city, long postalCode) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }https://docs.google.com/document/d/1HAZ4pAkergweIcU68RCTWowJ5WzXEL4PpKXcnZOvSlY/edit?usp=drivesdk

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Address [id=%s, street=%s, city=%s, postalCode=%s]", id, street, city, postalCode);
    }

    public boolean equals(Address obj) {
        if (obj == null && this == null) { return true; }
        if (obj == null ^ this == null) { return false; }
        if (this.street.equals(obj.getStreet()) &&
            this.city.equals(obj.getCity()) &&
            this.postalCode == obj.getPostalCode()) { return true; }
        return false;
    }

}
