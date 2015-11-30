package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.ImageDao;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.service.ImageService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.ImageUploadExeption;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

/**
 * Service class for working with Images
 *
 * @author Volodymyr Portianko
 * @since 1/12/2015
 */
@Named
public class ImageServiceImpl implements ImageService {

    @Value("${application.api.url}")
    private String apiUrl;

    @Inject
    private ImageDao imageDao;

    @Override
    public Image save(Image image) throws IOException {
        imageDao.save(image);
        image.setImgUrl(String.format("%s/images/%s", apiUrl, image.getImgName()));
        return image;
    }
}
