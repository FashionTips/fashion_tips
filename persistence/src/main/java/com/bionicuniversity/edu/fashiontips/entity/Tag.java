package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "tags")
public class Tag extends BaseEntity<Long> {

    @NotNull(message = "Tag should contain 'TagType'")
    @ManyToOne
    @JoinColumn(name = "tag_type_id")
    private TagType tagType;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tag_id", nullable = false)
    private List<TagParameter> tagParameters;

    private String value;

    public TagType getTagType() {
        return tagType;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    public List<TagParameter> getTagParameters() {
        return tagParameters;
    }

    public void setTagParameters(List<TagParameter> tagParameters) {
        this.tagParameters = tagParameters;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Tag(Long id, TagType tagType, List<TagParameter> tagParameters, String value) {
        this.id = id;
        this.tagType = tagType;
        this.tagParameters = tagParameters;
        this.value = value;
    }
    public Tag(){

    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagType=" + tagType +
                ", tagParameters=" + tagParameters +
                ", value='" + value + '\'' +
                '}';
    }
}