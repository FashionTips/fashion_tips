package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity Class HashTag
 */
@Entity
@Table(name = "hashtags")
public class HashTag extends BaseEntity<Long> {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public HashTag() {
    }

    public HashTag(String name) {
        this(null, name);
    }

    public HashTag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "HashTag{" +
                "id = " + id +
                ", name = " + name +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}