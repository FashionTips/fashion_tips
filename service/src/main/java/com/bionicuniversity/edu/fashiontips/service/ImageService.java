package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Image;

import java.io.IOException;

/**
 * Interface for working with Images
 *
 * @author Volodymyr Portianko
 * @since 1/12/2015
 */
public interface ImageService {

    Image save(Image image) throws IOException;
}
