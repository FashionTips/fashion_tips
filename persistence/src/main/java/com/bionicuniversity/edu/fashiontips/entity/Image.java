package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity Class Image
 */
@Entity
@Table(name = "images")
public class Image extends BaseEntity<Long> {

    @Column(name = "img_name", nullable = false)
    private String imgName;

    @Transient
    private byte[] data;

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
}
