package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.ImageDao;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.service.ImageService;
import com.bionicuniversity.edu.fashiontips.service.util.ImageUtil;

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

    @Override
    public Image save(Image image) throws IOException {
        imageDao.save(image);
        ImageUtil.createUrlName(image);
        return image;
    }
}
