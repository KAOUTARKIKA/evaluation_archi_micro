package com.tp.ex3.service;

import com.tp.ex3.dao.IDao;
import com.tp.ex3.beans.Mariage;
import com.tp.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;

public class MariageService implements IDao<Mariage> {

    private SessionFactory sessionFactory;

    public MariageService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean create(Mariage o) {
        sessionFactory.getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean delete(Mariage o) {
        sessionFactory.getCurrentSession().delete(o);
        return true;
    }

    @Override
    public boolean update(Mariage o) {
        sessionFactory.getCurrentSession().update(o);
        return true;
    }

    @Override
    public Mariage findById(int id) {
        return sessionFactory.getCurrentSession().get(Mariage.class, id);
    }

    @Override
    public List<Mariage> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Mariage", Mariage.class)
                .list();
    }
}