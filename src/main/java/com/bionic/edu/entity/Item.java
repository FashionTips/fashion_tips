package com.bionic.edu.entity;

import javax.persistence.*;

/**
 * Item Entity.
 * Created by maxim on 11/5/15.
 * TODO: add post property
 * TODO: add user property
 */
@Entity
@NamedQuery(name = "Item.getAll", query = "SELECT i FROM Item i")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String brand;
    private int popularity;

    public Item() {
    }

    public Item(String name, String brand, int popularity) {
        this.name = name;
        this.brand = brand;
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", popularity=" + popularity +
                '}';
    }
}
