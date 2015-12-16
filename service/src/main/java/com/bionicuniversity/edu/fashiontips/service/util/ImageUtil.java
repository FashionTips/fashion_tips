package com.bionicuniversity.edu.fashiontips.service.util;

import com.bionicuniversity.edu.fashiontips.entity.Image;

/**
 *  Util class for transformation Image entity parameters
 *
 * @author Volodymyr Portianko
 */
public class ImageUtil {

    private static String apiUrl;

    /**
     * Initialize apiUrl (method invokes during Spring initialization)
     */
    public static void setApiUrl(String apiUrlValue) {
        apiUrl = apiUrlValue;

    }

    public static void createUrlName(Image image) {
        image.setImgUrl(String.format("%s/images/%s", apiUrl, image.getImgName()));
    }


}
