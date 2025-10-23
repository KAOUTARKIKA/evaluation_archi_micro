package com.tp.ex3.service;

import com.tp.ex3.beans.Femme;
import com.tp.ex3.dao.IDao;
import com.tp.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    private SessionFactory sessionFactory;

    public FemmeService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean create(Femme o) {
        sessionFactory.getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean delete(Femme o) {
        sessionFactory.getCurrentSession().delete(o);
        return true;
    }

    @Override
    public boolean update(Femme o) {
        sessionFactory.getCurrentSession().update(o);
        return true;
    }

    @Override
    public Femme findById(int id) {
        return sessionFactory.getCurrentSession().get(Femme.class, id);
    }

    @Override
    public List<Femme> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Femme", Femme.class)
                .list();
    }

    public Femme getFemmeLaPlusAgee() {
        List<Femme> femmes = findAll();
        if (femmes.isEmpty()) return null;

        return femmes.stream()
                .min(Comparator.comparing(Femme::getDateNaissance))
                .orElse(null);
    }

    public int getNombreEnfantsEntreDates(Femme femme, Date dateDebut, Date dateFin) {
        Query query = sessionFactory.getCurrentSession()
                .createNamedQuery("Femme.nbrEnfantsEntreDates");
        query.setParameter("femmeId", femme.getId());
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);

        Object result = query.getSingleResult();
        if (result instanceof Number) {
            return ((Number) result).intValue();
        }
        return 0;
    }

    public List<Femme> getFemmesMarieeDeuxFois() {
        Query<Femme> query = sessionFactory.getCurrentSession()
                .createNamedQuery("Femme.marieeDeuxFois", Femme.class);
        return query.list();
    }
}