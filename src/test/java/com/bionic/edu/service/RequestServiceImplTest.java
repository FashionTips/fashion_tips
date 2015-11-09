package com.bionic.edu.service;

import com.bionic.edu.UserTestData;
import com.bionic.edu.model.Request;
import com.bionic.edu.util.exception.NotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.Arrays;

import static com.bionic.edu.RequestTestData.*;
import static com.bionic.edu.UserTestData.*;

/**
 * Created by VPortianko on 09.11.2015.
 */
@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RequestServiceImplTest {

    @Inject
    private RequestService requestService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSave() throws Exception {
        Request newRequest = new Request(null, null, "Новая курточка.","http://trendy.wmj.ru/uploads/images/00/00/17/2013/04/21/93046b.jpg");
        Request created = requestService.save(newRequest, USER3_ID);
        newRequest.setId(created.getId());
        REQ_MATCHER.assertListEquals(Arrays.asList(newRequest,REQ4),requestService.getAll(USER3_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Request updatedRequest = new Request(REQ2);
        updatedRequest.setDescription("new Description");
        requestService.update(updatedRequest, USER2_ID);
        REQ_MATCHER.assertEquals(updatedRequest, requestService.get(REQ2_ID, USER2_ID));
    }

    @Test
    public void testUpdateWrongUserId() throws Exception {
        Request updatedRequest = new Request(REQ2);
        updatedRequest.setDescription("new Description");
        thrown.expect(NotFoundException.class);
        requestService.update(updatedRequest, USER1_ID);
    }

    @Test
    public void testDelete() throws Exception {
        requestService.delete(REQ3_ID, USER2_ID);
        REQ_MATCHER.assertListEquals(Arrays.asList(REQ2), requestService.getAll(USER2_ID));
    }

    @Test
    public void testGet() throws Exception {
        REQ_MATCHER.assertEquals(REQ4, requestService.get(REQ4_ID, USER3_ID));
    }

    @Test
    public void testGetWrongUserId() throws Exception {
        thrown.expect(NotFoundException.class);
        REQ_MATCHER.assertEquals(REQ4,requestService.get(REQ4_ID,USER2_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        REQ_MATCHER.assertListEquals(Arrays.asList(REQ3,REQ2), requestService.getAll(USER2_ID));
    }
}