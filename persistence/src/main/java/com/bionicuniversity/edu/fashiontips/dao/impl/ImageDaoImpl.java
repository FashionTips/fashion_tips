package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.ImageDao;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;

/**
 *  PictureDao implementation.
 *  Using database table + disk saving
 */
public class ImageDaoImpl implements ImageDao {

    @PersistenceContext
    private EntityManager em;

    private String imageDirectory;

    public ImageDaoImpl(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }

    @Override
    @Transactional
    public Image save(Image image) throws IOException {
        if (image.getData() == null)
            throw new IllegalArgumentException("Image does not have data to save.");

        /* Saving in database and getting unique ID */
        em.persist(image);
        image.setImgName(String.format("%s-%s", image.getId(), image.getImgName()));
        Image created = em.merge(image);

        /* Saving file on disk */
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(created.getImgName())));
        stream.write(image.getData());
        stream.close();
        return created;
    }
}
