package com.programmervsworld.dao;

import com.programmervsworld.api.Alarm;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class AlarmDao extends AbstractDAO<Alarm> {
    public AlarmDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Alarm> findAll() {
        return list(query("from Alarm"));
    }
}
