package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Objects;

/**
 * The representation of user's role.
 *
 * @author Maksym Dolia
 * @since 16.12.2015.
 */
@Entity
@Table(name = "roles")
@NamedQuery(name = "Role.find", query = "SELECT r FROM Role r WHERE r.name = :name")
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

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}