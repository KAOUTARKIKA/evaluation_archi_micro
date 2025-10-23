package com.tp.ex2.service;

import com.tp.ex2.classes.EmployeTache;
import com.tp.ex2.dao.IDao;
import com.tp.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache> {

    private SessionFactory sessionFactory;

    public EmployeTacheService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean create(EmployeTache employeTache) {
        sessionFactory.getCurrentSession().save(employeTache);
        return true;
    }

    @Override
    public boolean delete(EmployeTache employeTache) {
        sessionFactory.getCurrentSession().delete(employeTache);
        return true;
    }

    @Override
    public boolean update(EmployeTache employeTache) {
        sessionFactory.getCurrentSession().update(employeTache);
        return true;
    }

    @Override
    public EmployeTache findById(int id) {
        return sessionFactory.getCurrentSession().get(EmployeTache.class, id);
    }

    @Override
    public List<EmployeTache> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM EmployeTache", EmployeTache.class)
                .list();
    }
}