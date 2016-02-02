package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import com.bionicuniversity.edu.fashiontips.entity.Country;
import com.bionicuniversity.edu.fashiontips.entity.TagType;
import com.bionicuniversity.edu.fashiontips.service.DictionaryService;
import com.bionicuniversity.edu.fashiontips.service.TagLineService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Rest controller to manage all requests about any consistent, not changeable data, e.g. countries.
 *
 * @author Maksym Dolia
 * @since 23.12.2015.
 */
@RestController
@CrossOrigin
@RequestMapping("/dictionary")
public class DictionaryController {

    @Inject
    private DictionaryService dictionaryService;

    @Inject
    private TagLineService tagLineService;

    /**
     * Returns the list of countries.
     *
     * @return list of countries
     */
    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    public List<Country> countries() {
        return dictionaryService.findAllCountries();
    }


    @RequestMapping(value = "/clothes", method = RequestMethod.GET)
    public List<Clothes> getClothes() {
        return tagLineService.getAvailableTypeOfClothes();
    }

    @RequestMapping(value = "/tag_types", method = RequestMethod.GET)
    public List<TagType> getTagTypes() {
        return tagLineService.getExistingTagTypes();
    }

}