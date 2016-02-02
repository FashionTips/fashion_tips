package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.ImageDao;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *  PictureDao implementation.
 *  Using database table + disk saving
 *
 *  @author Volodymyr Portianko
 */
@Named
public class ImageDaoImpl implements ImageDao {

    @PersistenceContext
    private EntityManager em;

    @Value("${application.images.path}")
    private String imageDirectory;

    @Override
    @Transactional
    public Image save(Image image) throws IOException {
        if (image.getData() == null)
            throw new IllegalArgumentException("Image does not have data to save.");

        /* Saving in database and getting unique ID */
        em.persist(image);
        image.setImgName(String.format("%s-%s", image.getId(), image.getImgName()));

        /* Saving file on disk */
        Path path = Paths.get(imageDirectory + image.getImgName());
        Files.write(path, image.getData(), StandardOpenOption.CREATE_NEW);
        return image;
    }

    @Override
    public Image getById(Long id) {
        return em.find(Image.class, id);
    }
}
