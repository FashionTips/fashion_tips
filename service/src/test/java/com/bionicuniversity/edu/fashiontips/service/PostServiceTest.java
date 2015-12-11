package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.Post.Category;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.HashSet;

import static com.bionicuniversity.edu.fashiontips.ServiceTestData.NOT_EXISTING_POST;
import static org.junit.Assert.assertEquals;

/**
 * Class for testing PostService
 * @author Sergiy
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
public class PostServiceTest {
    @Inject
    private UserService userService;
    @Inject
    private PostService postService;

    @Test
    public void testUpdate() throws Exception {
        /*Create checkPost */
        Post checkPost = new Post(4L, userService.get(1L), "new title1", "lalalala!?", Category.POST, new HashSet<>());

        /*Make some changes in Post(ID = 4)*/
        Post testPost = postService.get(4L);
        testPost.setTitle("new title1");
        testPost.setTextMessage("lalalala!?");

        /*Update Post(ID = 4) in DB*/
        postService.update(testPost);
        testPost = postService.get(4L);

        assertEquals(checkPost.toString(), testPost.toString());
    }

    @Test(expected = NotFoundException.class)
    public void notFoundException_InUpdate_IfPostDoesNotExist() throws NotFoundException {
        /*Update not existing post*/
        postService.update(NOT_EXISTING_POST);
    }


    @Test(expected = NotFoundException.class)
    public void notFoundException_InDelete_IfIdDoesNotExist() throws NotFoundException {
        /*Delete not existing post*/
        postService.delete(-35L);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundException_InGet_IfIdDoesNotExist() throws NotFoundException {
        /*Get not existing post*/
        postService.get(-35L);
    }
}
