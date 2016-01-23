package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.*;

/**
 * Entity class, which represents tag line's clothes.
 *
 * @author Vadym Golub
 */
@Entity
@Table(name = "clothes")
public class Clothes extends BaseEntity<Long> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Clothes(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Empty constructor for JPA purposes.
     */
    public Clothes (){

    }

    @Override
    public String toString() {
        return "Clothes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}