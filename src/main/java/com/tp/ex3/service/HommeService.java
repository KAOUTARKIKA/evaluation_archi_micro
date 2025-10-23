package com.tp.ex3.service;

import com.tp.ex3.beans.Femme;
import com.tp.ex3.beans.Homme;
import com.tp.ex3.beans.Mariage;
import com.tp.ex3.dao.IDao;
import com.tp.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {

    private SessionFactory sessionFactory;

    public HommeService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean create(Homme o) {
        sessionFactory.getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean delete(Homme o) {
        sessionFactory.getCurrentSession().delete(o);
        return true;
    }

    @Override
    public boolean update(Homme o) {
        sessionFactory.getCurrentSession().update(o);
        return true;
    }

    @Override
    public Homme findById(int id) {
        return sessionFactory.getCurrentSession().get(Homme.class, id);
    }

    @Override
    public List<Homme> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Homme", Homme.class)
                .list();
    }

    // Afficher les épouses d'un homme entre deux dates
    public List<Femme> getEpousesEntreDates(Homme homme, Date dateDebut, Date dateFin) {
        Query<Femme> query = sessionFactory.getCurrentSession().createQuery(
                "SELECT m.femme FROM Mariage m WHERE m.homme = :homme " +
                        "AND m.dateDebut BETWEEN :dateDebut AND :dateFin", Femme.class);
        query.setParameter("homme", homme);
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);
        return query.list();
    }

    // Afficher le nombre d'hommes mariés à quatre femmes entre deux dates (API Criteria)
    public int getNombreHommesMarieQuatreFemmes(Date dateDebut, Date dateFin) {
        CriteriaBuilder cb = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Homme> hommeRoot = cq.from(Homme.class);
        Join<Homme, Mariage> mariageJoin = hommeRoot.join("mariages");

        cq.select(cb.count(hommeRoot));
        cq.where(
                cb.between(mariageJoin.get("dateDebut"), dateDebut, dateFin)
        );
        cq.groupBy(hommeRoot.get("id"));
        cq.having(cb.equal(cb.count(mariageJoin), 4));

        List<Long> results = sessionFactory.getCurrentSession()
                .createQuery(cq)
                .getResultList();
        return results.size();
    }

    // Afficher les mariages d'un homme avec tous les détails
    public void afficherMariagesHomme(Homme homme) {
        Homme h = sessionFactory.getCurrentSession().get(Homme.class, homme.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("\nNom : " + h.getNom() + " " + h.getPrenom());

        List<Mariage> mariagesEnCours = new ArrayList<>();
        List<Mariage> mariagesEchoues = new ArrayList<>();

        for (Mariage m : h.getMariages()) {
            if (m.getDateFin() == null) {
                mariagesEnCours.add(m);
            } else {
                mariagesEchoues.add(m);
            }
        }

        if (!mariagesEnCours.isEmpty()) {
            System.out.println("\nMariages En Cours :");
            int i = 1;
            for (Mariage m : mariagesEnCours) {
                System.out.println(i + ". Femme : " + m.getFemme().getPrenom() + " " +
                        m.getFemme().getNom() + "   Date Début : " +
                        sdf.format(m.getDateDebut()) + "    Nbr Enfants : " +
                        m.getNbrEnfant());
                i++;
            }
        }

        if (!mariagesEchoues.isEmpty()) {
            System.out.println("\nMariages échoués :");
            int i = 1;
            for (Mariage m : mariagesEchoues) {
                System.out.println(i + ". Femme : " + m.getFemme().getPrenom() + " " +
                        m.getFemme().getNom() + "  Date Début : " +
                        sdf.format(m.getDateDebut()));
                System.out.println("Date Fin : " + sdf.format(m.getDateFin()) +
                        "    Nbr Enfants : " + m.getNbrEnfant());
                i++;
            }
        }
    }
}