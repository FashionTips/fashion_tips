package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Role;

/**
 * DAO interface to deal with {@code Role} entity.
 *
 * @author Maksym Dolia
 * @since 17.12.2015.
 */
public interface RoleDao extends GenericDao<Role, Long> {

    /**
     * Looks for and returns a {@code Role} instance by given name.
     *
     * @param name name
     * @return {@code Role} if exist, {@code null} otherwise
     */
    Role find(String name);
}
