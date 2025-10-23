package com.tp.ex2.service;

import com.tp.ex2.classes.Tache;
import com.tp.ex2.dao.IDao;
import com.tp.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {

    private SessionFactory sessionFactory;

    public TacheService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean create(Tache tache) {
        sessionFactory.getCurrentSession().save(tache);
        return true;
    }

    @Override
    public boolean delete(Tache tache) {
        sessionFactory.getCurrentSession().delete(tache);
        return true;
    }

    @Override
    public boolean update(Tache tache) {
        sessionFactory.getCurrentSession().update(tache);
        return true;
    }

    @Override
    public Tache findById(int id) {
        return sessionFactory.getCurrentSession().get(Tache.class, id);
    }

    @Override
    public List<Tache> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Tache", Tache.class)
                .list();
    }

    /**
     * Afficher les tâches dont le prix est supérieur à 1000 DH (requête nommée)
     */
    public List<Tache> getTachesPrixSuperieur(double prixMin) {
        Query<Tache> query = sessionFactory.getCurrentSession()
                .createNamedQuery("Tache.findByPrixSuperieur", Tache.class);
        query.setParameter("prixMin", prixMin);
        return query.list();
    }

    /**
     * Afficher les tâches réalisées entre deux dates
     */
    public List<Tache> getTachesEntreDeuxDates(Date dateDebut, Date dateFin) {
        Query<Tache> query = sessionFactory.getCurrentSession().createQuery(
                "SELECT DISTINCT t FROM Tache t " +
                        "JOIN t.employeTaches et " +
                        "WHERE et.dateDebutReelle >= :dateDebut AND et.dateFinReelle <= :dateFin",
                Tache.class
        );
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);
        return query.list();
    }

    /**
     * Afficher les tâches dont le prix est supérieur à 1000 DH (formaté)
     */
    public void afficherTachesPrixSuperieur1000() {
        System.out.println("\n=== Tâches dont le prix est supérieur à 1000 DH ===");
        List<Tache> taches = getTachesPrixSuperieur(1000);

        if (taches.isEmpty()) {
            System.out.println("Aucune tâche trouvée.");
            return;
        }

        System.out.printf("%-5s %-20s %-20s %-20s %-15s%n", "Num", "Nom", "Date Début", "Date Fin", "Prix (DH)");
        System.out.println("--------------------------------------------------------------------------------");

        for (Tache tache : taches) {
            System.out.printf("%-5d %-20s %-20s %-20s %-15.2f%n",
                    tache.getId(),
                    tache.getNom(),
                    String.format("%td/%tm/%tY", tache.getDateDebut(), tache.getDateDebut(), tache.getDateDebut()),
                    String.format("%td/%tm/%tY", tache.getDateFin(), tache.getDateFin(), tache.getDateFin()),
                    tache.getPrix()
            );
        }
    }

    /**
     * Afficher les tâches réalisées entre deux dates (formaté)
     */
    public void afficherTachesEntreDeuxDates(Date dateDebut, Date dateFin) {
        System.out.printf("\n=== Tâches réalisées entre %td/%tm/%tY et %td/%tm/%tY ===%n",
                dateDebut, dateDebut, dateDebut, dateFin, dateFin, dateFin);

        List<Tache> taches = getTachesEntreDeuxDates(dateDebut, dateFin);

        if (taches.isEmpty()) {
            System.out.println("Aucune tâche réalisée pendant cette période.");
            return;
        }

        System.out.printf("%-5s %-20s %-25s %-15s%n", "Num", "Nom", "Projet", "Prix (DH)");
        System.out.println("------------------------------------------------------------------------");

        for (Tache tache : taches) {
            System.out.printf("%-5d %-20s %-25s %-15.2f%n",
                    tache.getId(),
                    tache.getNom(),
                    tache.getProjet().getNom(),
                    tache.getPrix()
            );
        }
    }
}