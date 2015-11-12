package com.bionic.edu.service;

import com.bionic.edu.model.Request;
import com.bionic.edu.util.exception.NotFoundException;

import java.util.List;

/**
 * Created by VPortianko on 09.11.2015.
 */
public interface RequestService {

    Request save(Request request, int userId);

    void update(Request request, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    Request get(int id) throws NotFoundException;

    List<Request> getAllByUserId(int userId);

    List<Request> getAll();
}
