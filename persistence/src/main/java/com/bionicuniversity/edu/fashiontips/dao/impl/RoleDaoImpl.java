package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.RoleDao;
import com.bionicuniversity.edu.fashiontips.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Implementation of {@code RoleDao} interface.
 *
 * @author Maksym Dolia
 * @since 17.12.2015.
 */
@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role, Long> implements RoleDao {

    @Override
    public Role find(String name) {

        TypedQuery<Role> query = em.createNamedQuery("Role.find", Role.class);

        try {
            return query.setParameter("name", name).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}