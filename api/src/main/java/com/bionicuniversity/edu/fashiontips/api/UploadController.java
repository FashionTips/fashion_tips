package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.api.util.ImageUtil;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.service.ImageService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.ImageUploadExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

/**
 *  This class handles http requests that relate to uploading files
 *
 *  @author Volodymyr Portianko
 */
@RestController
@CrossOrigin
@RequestMapping("/upload")
public class UploadController {

    @Inject
    private ImageService imageService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity imageUpload(@RequestParam("file") MultipartFile file) {
        if(!file.getContentType().startsWith("image/"))
            throw new ImageUploadExeption(String.format("Cannot load file %s, it is not an image", file.getOriginalFilename()));

        if (!file.isEmpty()) {
            Image image = new Image(file.getOriginalFilename());
            try {
                image.setData(file.getBytes());
                imageService.save(image);
                ImageUtil.createUrlName(image);
            } catch (Exception e) {
                throw new ImageUploadExeption(String.format("Cannot load image %s", file.getOriginalFilename()));
            }
            return new ResponseEntity<>(image, HttpStatus.CREATED);
        }
        throw new ImageUploadExeption("Uploaded file is empty");
    }
}