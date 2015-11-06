package com.bionic.edu.repository.datajpa;

import com.bionic.edu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by VPortianko on 06.11.2015.
 */
public interface ProxyUserRepository extends JpaRepository<User, Integer> {
}
