package com.bionicuniversity.edu.fashiontips.service.util;


import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;

import java.util.Collection;
import java.util.List;

/**
 * Util class for transformation Post entity parameters
 *
 * @author Volodymyr Portianko
 */
public class PostUtil {

    /**
     * Add or transform Post entity parameters for transferring to client side
     *
     * @param post post entity
     * @param loggedUser entity of logged user
     */
    public static void normalizeForClient(Post post, User loggedUser) {
        /* Setting necessary for clients parameters, related to "likes" */
        post.setLikes((long) post.getLikedByUsers().size());
        if (loggedUser != null)
            if (!post.getUser().equals(loggedUser))
                post.setIsLikedByAuthUser(post.getLikedByUsers().contains(loggedUser) ?
                        Boolean.TRUE : Boolean.FALSE);
    }

    public static void normalizeForClient(List<Post> posts, User loggedUser) {
        posts.stream().forEach(post -> normalizeForClient(post,loggedUser));
    }
}
