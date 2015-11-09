package com.bionic.edu.service;

import com.bionic.edu.LoggerWrapper;
import com.bionic.edu.model.Request;
import com.bionic.edu.repository.RequestRepository;
import com.bionic.edu.util.exception.NotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by VPortianko on 09.11.2015.
 */
@Named
public class RequestServiceImpl implements RequestService {

    private static final LoggerWrapper LOG = LoggerWrapper.get(RequestServiceImpl.class);

    @Inject
    private RequestRepository requestRepository;

    @Override
    public Request save(Request request, int userId) {
        return requestRepository.save(request, userId);
    }

    @Override
    public void update(Request request, int userId) throws NotFoundException {
        if (requestRepository.save(request, userId) == null)
            throw LOG.getNotFoundException(String.format("Could not update request with id=%d and userId=%d",request.getId(), userId));
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (!requestRepository.delete(id, userId))
            throw LOG.getNotFoundException(String.format("Could not update request with id=%d and userId=%d", id, userId));
    }

    @Override
    public Request get(int id, int userId) throws NotFoundException {
        Request request = requestRepository.get(id, userId);
        if (request == null) throw LOG.getNotFoundException(String.format("Could not get request with id=%d and userId=%d",id, userId));
        return request;
    }

    @Override
    public List<Request> getAll(int userId) {
        return requestRepository.getAll(userId);
    }
}
