package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.util.List;
import java.util.function.Consumer;

/**
 * Post Service Implementation.
 *
 * @author Sergiy
 * @author Volodymyr Portianko
 */
@Named
public class PostServiceImpl extends GenericServiceImpl<Post, Long> implements PostService {

    @Value("${application.api.url}")
    private String apiUrl;

    @Override
    public List<Post> getAllByUser(User user) {
        return ((PostDao) repository).getAllByUser(user);
    }

    @Override
    public Post get(Long id) {
        Post post =  super.get(id);
        post.getImages().stream().
                forEach(image -> image.setImgUrl(String.format("%s/images/%s", apiUrl, image.getImgName())));
        return post;
    }
}
