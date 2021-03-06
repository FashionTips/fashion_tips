package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.Image;

import java.util.Arrays;

import static com.bionicuniversity.edu.fashiontips.TagLineTestData.*;


/**
 * This class stores default and test values for Image entity
 *
 * @author Volodymyr Portianko
 */
public class ImageTestData {

    /* Default images, which stored in ${application.images.path} */
    public static final Image IMAGE1 = new Image(1L,"1-1kurtka.jpg");
    public static final Image IMAGE2 = new Image(2L,"2-13235_huge_weman_shoes_0.jpg");
    public static final Image IMAGE3 = new Image(3L,"3-jeans1.jpg");
    public static final Image IMAGE4 = new Image(4L,"4-jeans2.jpg");
    public static final Image IMAGE5 = new Image(5L,"5-kurtka.jpg");
    public static final Image IMAGE6 = new Image(6L,"6-sapogi.jpeg");

    /* Other images for testing */
    public static final String TEST_IMAGE_DIRECTORY = "../test_images/";

    public static final Image IMAGE_NEW = new Image(null, "newImage.jpg");
    public static final Long IMAGE_NEW_ID = 7L;

    static {
        IMAGE1.setTagLines(Arrays.asList(TAG_LINE1, TAG_LINE2));
        IMAGE2.setTagLines(Arrays.asList(TAG_LINE3));
        NEW_TAG_LINE_BEFORE_SAVE.setImage(IMAGE3);
        NEW_TAG_LINE_AFTER_SAVE.setImage(IMAGE3);
    }

}
