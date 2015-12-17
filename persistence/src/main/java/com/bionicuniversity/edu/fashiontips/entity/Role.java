package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The representation of user's role.
 *
 * @author Maksym Dolia
 * @since 16.12.2015.
 */
@Entity
@Table(name = "roles")
public class Role extends BaseEntity<Long> {

    private String name;

    /**
     * Default constructor, mainly for JPA purposes.
     */
    public Role() {
        //NOP
    }

    /**
     * Constructs instance with given name.
     *
     * @param name role's name
     */
    public Role(String name) {
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
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
