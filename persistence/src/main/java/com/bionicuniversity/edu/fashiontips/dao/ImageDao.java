package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Image;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *  Interface for Image entity dao
 *
 *  @author Volodymyr Portianko
 */
public interface ImageDao {

    Image save(Image image) throws IOException;

}
