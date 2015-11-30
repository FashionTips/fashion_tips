package com.bionicuniversity.edu.fashiontips.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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
    * Parameter with actual file content
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

    public Image() {
    }

    public Image(String imgName) {
        this.imgName = imgName;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", imgName='" + imgName + '\'' +
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
}
