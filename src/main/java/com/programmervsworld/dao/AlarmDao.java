package com.programmervsworld.dao;

import com.programmervsworld.api.Alarm;
import io.dropwizard.hibernate.AbstractDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import java.util.Optional;
import org.hibernate.SessionFactory;

import java.util.List;

public class AlarmDao extends AbstractDAO<Alarm> {
    public AlarmDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Alarm> findAll() {
        return list(query("from Alarm"));
    }

    public List<Alarm> finalByTid(String tid) {
        return list(query("from Alarm a where a.tid = :tid")
                .setParameter("tid", tid));
    }

    public Alarm update(Alarm alarm) {
        return persist(alarm);
    }

    public Alarm create(Alarm alarm) {
        return persist(alarm);
    }

    public Optional<Alarm> findById(Integer id) {
        return Optional.ofNullable(get(id));
    }
}
