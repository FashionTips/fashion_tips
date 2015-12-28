package com.bionicuniversity.edu.fashiontips.api.util;


import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;

import java.util.List;

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

    /* Setting imgUrl to images in Post entity */
    public static Post createUrlNameForPost(Post post) {
        post.getImages().stream().forEach(ImageUtil::createUrlName);
        return post;
    }

    /* Setting imgUrl to images in list of Post entities */
    public static List<Post> createUrlNameForPosts(List<Post> posts) {
        posts.stream().forEach(ImageUtil::createUrlNameForPost);
        return posts;
    }

    /* Setting imgUrl to user avatars */
    public static User createUrlNameForUserAvatar(User user) {
        if (user.getAvatar() != null) {
            createUrlName(user.getAvatar());
        }
        return user;
    }
}