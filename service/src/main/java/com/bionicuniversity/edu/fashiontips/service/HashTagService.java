package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.HashTag;

import java.util.Set;

/**
 * HashTag Service interface
 */
public interface HashTagService {

    HashTag getByName(String hashTagName);

    /*Takes list of hashtags names, returned set of HashTags*/
    Set<HashTag> saveHashTags(String text);
}