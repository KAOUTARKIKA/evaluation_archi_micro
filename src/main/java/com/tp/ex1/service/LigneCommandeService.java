package com.tp.ex1.service;

import com.tp.ex1.classes.LigneCommandeProduit;
import com.tp.ex1.dao.IDao;
import com.tp.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    private SessionFactory sessionFactory;

    public LigneCommandeService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean create(LigneCommandeProduit o) {
        sessionFactory.getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(LigneCommandeProduit o) {
        sessionFactory.getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(LigneCommandeProduit o) {
        sessionFactory.getCurrentSession().delete(o);
        return true;
    }

    @Override
    public LigneCommandeProduit findById(int id) {
        return sessionFactory.getCurrentSession().get(LigneCommandeProduit.class, id);
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM LigneCommandeProduit", LigneCommandeProduit.class)
                .list();
    }
}