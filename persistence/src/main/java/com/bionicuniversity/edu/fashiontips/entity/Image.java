package com.bionicuniversity.edu.fashiontips.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity Class Image
 *
 * @author Volodymyr Portianko
 */
@Entity
@Table(name = "images")
public class Image extends BaseEntity<Long> {

    /*
    * Parameter, which represents image name in filesystem
    */
    @JsonIgnore
    @Column(name = "img_name", nullable = false)
    private String imgName;

    /*
    * Parameter with file content
    * Needed only for saving file to the filesystem
    */
    @JsonIgnore
    @Transient
    private byte[] data;

    /*
    * Parameter with http url for brousing image in the internet
    */
    @Transient
    private String imgUrl;

    /**
     * List of posts tag lines
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "image")
    @OrderBy(value = "id ASC")
    private List<TagLine> tagLines = new ArrayList<>();

    /*
    * User, who loaded image
    * */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    public Image() {
    }

    public Image(String imgName) {
        this.imgName = imgName;
    }

    public Image(Long id, String imgName) {
        this.id = id;
        this.imgName = imgName;
    }

    public Image(Long id, String imgName, User owner) {
        this.id = id;
        this.imgName = imgName;
        this.owner = owner;
    }

    public Image(Long id, String imgName, List<TagLine> tagLines) {
        this.id = id;
        this.imgName = imgName;
        this.tagLines = tagLines;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", imgName='" + imgName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", tagLines=" + tagLines + '\'' +
                '}';
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<TagLine> getTagLines() {
        return tagLines;
    }

    public void setTagLines(List<TagLine> tagLines) {
        this.tagLines = tagLines;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}