package com.bionicuniversity.edu.fashiontips.dao.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Bean for initialization image folder from scratch with default images
 * For development usage only
 * This class need "/initialize_images" folder with default content next to "images" folder
 *
 * @author Volodymyr Portianko
 */
public class InitializeImageStorage implements InitializingBean {

    @Value("${application.images.path}")
    private String imageDirectory;

    @Override
    public void afterPropertiesSet() throws Exception {
        Path imagesPath = Paths.get(imageDirectory);
        Path initializeImagesPath = Paths.get(imageDirectory + "../initialize_images/");
        if (!Files.exists(initializeImagesPath))
            throw new FileNotFoundException(String.format("Directory '%s' is not found",initializeImagesPath.toString()));

        /* Deleting image folder with all content in it */
        Files.walkFileTree(imagesPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if(exc == null){
                    if (dir.equals(imagesPath)) return FileVisitResult.CONTINUE;
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
                throw exc;
            }
        });

        /* Fill images folder with default files from "../initialize_images" folder */
        Files.walkFileTree(initializeImagesPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetPath = imagesPath.resolve(initializeImagesPath.relativize(dir));
                if(!Files.exists(targetPath)){
                    Files.createDirectory(targetPath);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, imagesPath.resolve(initializeImagesPath.relativize(file)), StandardCopyOption.COPY_ATTRIBUTES);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
