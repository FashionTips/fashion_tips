package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.OutboxEmailDao;
import com.bionicuniversity.edu.fashiontips.entity.OutboxEmail;
import org.springframework.stereotype.Repository;

/**
 * Created by Sergiy on 2/4/2016.
 */
@Repository
public class OutboxEmailDaoImpl extends GenericDaoImpl<OutboxEmail, Long> implements OutboxEmailDao {
}
