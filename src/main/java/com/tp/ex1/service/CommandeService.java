package com.tp.ex1.service;

import com.tp.ex1.classes.Commande;
import com.tp.ex1.dao.IDao;
import com.tp.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;

public class CommandeService implements IDao<Commande> {

    private SessionFactory sessionFactory;

    public CommandeService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean create(Commande o) {
        sessionFactory.getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(Commande o) {
        sessionFactory.getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(Commande o) {
        sessionFactory.getCurrentSession().delete(o);
        return true;
    }

    @Override
    public Commande findById(int id) {
        return sessionFactory.getCurrentSession().get(Commande.class, id);
    }

    @Override
    public List<Commande> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Commande", Commande.class)
                .list();
    }
}