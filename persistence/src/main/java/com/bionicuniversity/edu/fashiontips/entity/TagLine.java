package com.bionicuniversity.edu.fashiontips.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Entity class, which represents post's tag lines.
 *
 * @author Vadym Golub
 */
@Entity
@Table(name = "tag_lines")
public class TagLine extends BaseEntity<Long> {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @NotNull(message = "TagLine should contain 'Clothes'")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="clothes_id")
    private Clothes clothes;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "tags_tag_lines",
            joinColumns = @JoinColumn(name = "tag_line_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    /**
     * Empty constructor for JPA purposes.
     */
    public TagLine(){
    }
    /**
     * Constructs tag line with given parameters.
     *
     * @param image linked entity Post
     * @param clothes linked entity Clothes
     * @param tags Set of linked Tag entities
     */
    public TagLine(Long id, Image image, Clothes clothes, List<Tag> tags) {
        this.id = id;
        this.image = image;
        this.clothes = clothes;
        this.tags = tags;
    }

    public TagLine(Long id, Clothes clothes, List<Tag> tags) {
        this.id = id;
        this.clothes = clothes;
        this.tags = tags;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Clothes getClothes() {
        return clothes;
    }

    public void setClothes(Clothes clothes) {
        this.clothes = clothes;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TagLine{" +
                "id=" + id +
                ", clothes=" + clothes +
                ", tags=" + tags +
                '}';
    }
}
