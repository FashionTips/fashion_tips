package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

import static com.bionicuniversity.edu.fashiontips.entity.Post.Category;

/**
 * Implementation of {@code PostDao} interface for relational database.
 *
 * @author Alexander Laktionov
 * @author Maksym Dolia
 * @since 25/11/2015
 */
@Repository
public class PostDaoImpl extends GenericDaoImpl<Post, Long> implements PostDao {

    @Override
    public List<Post> findByUser(User user) {
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.user = :user AND p.status = :published ORDER BY p.publicationTime DESC", Post.class);
        return query.setParameter("user", user).setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findForAuthor(User author) {
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.user = :author ORDER BY p.created DESC", Post.class);
        return query.setParameter("author", author).getResultList();
    }

    @Override
    public List<Post> findByWord(String word) {
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.textMessage LIKE :pattern AND p.status = :published ORDER BY p.publicationTime DESC", Post.class);
        return query.setParameter("pattern", "%" + word + "%").setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findByCategory(Category category) {
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.category = :category AND p.status = :published ORDER BY p.publicationTime DESC", Post.class);
        return query.setParameter("category", category).setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findAll() {
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.status = :published ORDER BY p.publicationTime DESC", Post.class);
        return query.setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findUnpublished() {
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.status =:wait AND p.publicationTime < :now ORDER BY p.publicationTime ASC ", Post.class);
        return query.setParameter("wait", Post.Status.SCHEDULED).setParameter("now", LocalDateTime.now()).getResultList();
    }

    @Override
    public List<Post> findByTagValueAndTagTypeId(String value, Long tagTypeId) {
        TypedQuery<Post> query = em.createQuery("SELECT DISTINCT p FROM Post p JOIN p.images imgs JOIN imgs.tagLines tagLines JOIN tagLines.tags tags WHERE tags.value = :tagValue AND tags.tagType.id = :tagTypeId AND p.status  = :published ORDER BY p.publicationTime DESC", Post.class);
        return query.setParameter("tagValue", value).setParameter("tagTypeId", tagTypeId).setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findByTagTypeId(Long tagTypeId) {
        TypedQuery<Post> query = em.createQuery("SELECT DISTINCT p " +
                                                "FROM Post p " +
                                                "JOIN p.images imgs " +
                                                "JOIN imgs.tagLines tagLines " +
                                                "JOIN tagLines.tags tags " +
                                                "JOIN tags.tagType tagType " +
                                                "WHERE tagType.id = :tagType_id " +
                                                "AND p.status = :published " +
                                                "ORDER BY p.publicationTime DESC", Post.class);
        return query.setParameter("tagType_id", tagTypeId).setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findByClothesId(Long clothesId) {
        TypedQuery<Post> query = em.createQuery("SELECT DISTINCT p " +
                                                "FROM Post p " +
                                                "JOIN p.images imgs " +
                                                "JOIN imgs.tagLines tagLines " +
                                                "WHERE tagLines.clothes.id = :clothesId " +
                                                "AND p.status = :published " +
                                                "ORDER BY p.publicationTime DESC", Post.class);
        return  query.setParameter("clothesId", clothesId).setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<User> getLikedUsers(Long id) {
        Query query =
                em.createNativeQuery("SELECT DISTINCT u.id, u.login, img.id AS img_id, img.img_name FROM users u " +
                        "   INNER JOIN POST_USER_LIKES pul ON u.id = pul.user_id " +
                        "   LEFT JOIN USER_IMAGES ui ON ui.user_id = u.id " +
                        "   LEFT JOIN IMAGES img ON img.id = ui.img_id " +
                        "   WHERE pul.POST_ID = :id", "post.user.followers").setParameter("id", id);
        List<User> users = query.getResultList();
        return users;
    }

}
