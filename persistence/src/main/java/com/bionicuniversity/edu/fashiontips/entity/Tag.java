package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity Class Tag
 */
@Entity
@Table(name = "tags")
public class Tag extends BaseEntity<Long> {
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
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