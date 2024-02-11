package com.ua.tpm.libraryproject.dbconn.tables.publishing;

public class Publishing {

    private String name;
    private String city;
    private String street;

    public Publishing(String name) {

        this.setName(name);
        this.setCity(city);
        this.setStreet(street);
    }

    public Publishing() {
        this("Видатництво");
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
}
