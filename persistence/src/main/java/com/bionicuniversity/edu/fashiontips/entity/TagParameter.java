package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.*;

/**
 * Entity class, which represents tag's parameters.
 *
 * @author Vadym Golub
 */
@Entity
@Table(name = "tag_parameters")
public class TagParameter extends BaseEntity<Long> {

    private String value;

    private String name;

    public TagParameter(Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
    public TagParameter() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagParameter{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}