package com.bionic.edu.repository;

import com.bionic.edu.model.Request;
import com.bionic.edu.repository.datajpa.ProxyRequestRepository;
import com.bionic.edu.repository.datajpa.ProxyUserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by VPortianko on 09.11.2015.
 */

@Named
public class RequestRepositoryImpl implements RequestRepository {

    @Inject
    private ProxyRequestRepository requestRepository;

    @Inject
    private ProxyUserRepository userRepository;

    @Override
    public Request save(Request request, int userId) {
        if (!request.isNew() && get(request.getId(), userId) == null) {
            return null;
        }
        request.setUser(userRepository.getOne(userId));
        return requestRepository.save(request);
    }

    @Override
    public boolean delete(int id, int userId) {
        return requestRepository.delete(id, userId) != 0;
    }

    @Override
    public Request get(int id, int userId) {
        return requestRepository.get(id, userId);
    }

    @Override
    public List<Request> getAll(int userId) {
        return requestRepository.getAll(userId);
    }
}
