package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.CommentService;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.HashSet;

/**
 * @author alaktionov aka slav9nin
 */

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-persistence.xml"
})
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentServiceImplTest {

    private static User user1 = new User("login1", "email1@example.com", "1111");
    private static Post post1 = new Post(user1, "title1", "what fits me with these pants?", Post.Category.QUESTION);
    private static HashSet<Comment> comments = new HashSet<>();
    static {
        user1.setId(1L);
        post1.setId(1L);
    }

    @Inject
    private CommentService commentService;

    @Inject
    private UserService userService;

    @Inject
    private PostService postService;

    @BeforeClass
    public static void setup() {
        for (int i = 1; i <= 5; i++) {
            Comment comment = new Comment("OK", post1);
            comment.setId(Long.valueOf(""+i));
            comments.add(comment);
        }
    }

//    @Test
//    public void saveCommentsTest() {
//        List<Comment> expected = commentService.getCommentsByPost(post1);
//        post1.setComments(comments);
//        postService.save(post1);
//        expected.addAll(post1.getComments().stream().collect(Collectors.toList()));
//        assertArrayEquals(expected.toArray(new Comment[0]), post1.getComments().toArray(new Comment[0]));
//    }
}
