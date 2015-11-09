package com.bionic.edu.repository;

import com.bionic.edu.model.Request;
import com.bionic.edu.model.User;

import java.util.List;

/**
 * Created by VPortianko on 06.11.2015.
 */
public interface RequestRepository {

    //null if updated request do not belong to user or not fount
    Request save(Request request, int userId);

    //fasle if request do not belong to user or not fount
    boolean delete(int id, int userId);

    //null if request with id not found
    Request get(int id);

    List<Request> getAll(int userId);
}
