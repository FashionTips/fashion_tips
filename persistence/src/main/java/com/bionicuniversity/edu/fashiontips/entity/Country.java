package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represents one of the world country.
 *
 * @author Maksym Dolia
 * @since 19.12.2015.
 */
@Entity
@Table(name = "countries")
public class Country extends BaseEntity<Integer> {

    private String name;

    public Country() {
        //NOP
    }

    /**
     * Convenient constructor to create an instance of Country class with initialized name.
     *
     * @param name country's name
     */
    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}