package com.bionicuniversity.edu.fashiontips.service.util;

import com.bionicuniversity.edu.fashiontips.entity.Image;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;

/**
 *  Util class for transformation Image entity parameters
 *
 * @author Volodymyr Portianko
 */
@Named
public class ImageUtil {

    @Value("${application.api.url}")
    private String apiUrl;

    public void createUrlName(Image image) {
        image.setImgUrl(String.format("%s/images/%s", apiUrl, image.getImgName()));
    }
}
