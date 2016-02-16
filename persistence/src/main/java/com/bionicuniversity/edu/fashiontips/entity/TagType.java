package com.bionicuniversity.edu.fashiontips.entity;


import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity class, which represents tag's type.
 *
 * @author Vadym Golub
 */
@Entity
@Table(name = "tag_types")
@NamedQuery(name = "TagType.findByType", query = "SELECT tagType FROM TagType tagType WHERE tagType.type = :tagType")
public class TagType extends BaseEntity<Long> {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TagType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public TagType(){
        // for JPA
    }

    @Override
    public String toString() {
        return "TagType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}