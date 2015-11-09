package com.bionic.edu.repository;

import com.bionic.edu.model.Request;
import com.bionic.edu.repository.datajpa.ProxyRequestRepository;
import com.bionic.edu.repository.datajpa.ProxyUserRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by VPortianko on 09.11.2015.
 */

@Named
public class RequestRepositoryImpl implements RequestRepository {

    @Inject
    private ProxyRequestRepository proxy;

    @Inject
    private ProxyUserRepository userProxy;

    @Override
    @Transactional
    public Request save(Request request, int userId) {
        if (!request.isNew() && get(request.getId()).getUser().getId() != userId) {
            return null;
        }
        request.setUser(userProxy.getOne(userId));
        return proxy.save(request);
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxy.delete(id, userId) != 0;
    }

    @Override
    public Request get(int id) {
        return proxy.get(id);
    }

    @Override
    public List<Request> getAll(int userId) {
        return proxy.getAll(userId);
    }
}
