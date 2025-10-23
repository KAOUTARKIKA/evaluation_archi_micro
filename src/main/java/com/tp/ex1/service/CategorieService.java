package com.tp.ex1.service;

import com.tp.ex1.classes.Categorie;
import com.tp.ex1.dao.IDao;
import com.tp.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;

public class CategorieService implements IDao<Categorie> {

    private SessionFactory sessionFactory;

    public CategorieService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean create(Categorie o) {
        sessionFactory.getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(Categorie o) {
        sessionFactory.getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(Categorie o) {
        sessionFactory.getCurrentSession().delete(o);
        return true;
    }

    @Override
    public Categorie findById(int id) {
        return sessionFactory.getCurrentSession().get(Categorie.class, id);
    }

    @Override
    public List<Categorie> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Categorie", Categorie.class)
                .list();
    }
}