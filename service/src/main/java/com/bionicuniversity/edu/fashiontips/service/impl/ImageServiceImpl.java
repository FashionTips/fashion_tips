package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.ImageDao;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.service.ImageService;
import com.bionicuniversity.edu.fashiontips.service.util.ImageUtil;
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

    @Inject
    private ImageDao imageDao;

    @Inject
    private ImageUtil imageUtil;

    @Override
    public Image save(Image image) throws IOException {
        imageDao.save(image);
        imageUtil.createUrlName(image);
        return image;
    }
}
