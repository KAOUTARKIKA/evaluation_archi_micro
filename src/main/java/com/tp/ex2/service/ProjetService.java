package com.tp.ex2.service;

import com.tp.ex2.classes.EmployeTache;
import com.tp.ex2.classes.Projet;
import com.tp.ex2.classes.Tache;
import com.tp.ex2.dao.IDao;
import com.tp.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ProjetService implements IDao<Projet> {

    private SessionFactory sessionFactory;

    public ProjetService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean create(Projet projet) {
        sessionFactory.getCurrentSession().save(projet);
        return true;
    }

    @Override
    public boolean delete(Projet projet) {
        sessionFactory.getCurrentSession().delete(projet);
        return true;
    }

    @Override
    public boolean update(Projet projet) {
        sessionFactory.getCurrentSession().update(projet);
        return true;
    }

    @Override
    public Projet findById(int id) {
        return sessionFactory.getCurrentSession().get(Projet.class, id);
    }

    @Override
    public List<Projet> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Projet", Projet.class)
                .list();
    }

    /**
     * Afficher la liste des tâches planifiées pour un projet
     */
    public List<Tache> getTachesPlanifiees(int projetId) {
        Query<Tache> query = sessionFactory.getCurrentSession().createQuery(
                "FROM Tache t WHERE t.projet.id = :projetId ORDER BY t.dateDebut",
                Tache.class
        );
        query.setParameter("projetId", projetId);
        return query.list();
    }

    /**
     * Afficher la liste des tâches réalisées avec les dates réelles
     */
    public List<EmployeTache> getTachesRealisees(int projetId) {
        Query<EmployeTache> query = sessionFactory.getCurrentSession().createQuery(
                "FROM EmployeTache et WHERE et.tache.projet.id = :projetId ORDER BY et.dateDebutReelle",
                EmployeTache.class
        );
        query.setParameter("projetId", projetId);
        return query.list();
    }

    /**
     * Afficher les tâches planifiées pour un projet (formaté)
     */
    public void afficherTachesPlanifiees(int projetId) {
        Projet projet = findById(projetId);
        if (projet == null) {
            System.out.println("Projet introuvable!");
            return;
        }

        System.out.printf("\nProjet : %-5d Nom : %-30s Date début : %td %tB %tY%n",
                projet.getId(),
                projet.getNom(),
                projet.getDateDebut(),
                projet.getDateDebut(),
                projet.getDateDebut()
        );

        List<Tache> taches = getTachesPlanifiees(projetId);

        if (taches.isEmpty()) {
            System.out.println("Aucune tâche planifiée.");
            return;
        }

        System.out.println("Liste des tâches:");
        System.out.printf("%-5s %-20s %-20s %-20s %-15s%n", "Num", "Nom", "Date Début", "Date Fin", "Prix");
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
     * Afficher les tâches réalisées avec les dates réelles (formaté)
     */
    public void afficherTachesRealisees(int projetId) {
        Projet projet = findById(projetId);
        if (projet == null) {
            System.out.println("Projet introuvable!");
            return;
        }

        System.out.printf("\nProjet : %-5d Nom : %-30s Date début : %td %tB %tY%n",
                projet.getId(),
                projet.getNom(),
                projet.getDateDebut(),
                projet.getDateDebut(),
                projet.getDateDebut()
        );

        List<EmployeTache> tachesRealisees = getTachesRealisees(projetId);

        if (tachesRealisees.isEmpty()) {
            System.out.println("Aucune tâche réalisée.");
            return;
        }

        System.out.println("Liste des tâches:");
        System.out.printf("%-5s %-20s %-20s %-20s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
        System.out.println("------------------------------------------------------------------------");

        for (EmployeTache et : tachesRealisees) {
            Tache tache = et.getTache();
            System.out.printf("%-5d %-20s %-20s %-20s%n",
                    tache.getId(),
                    tache.getNom(),
                    String.format("%td/%tm/%tY", et.getDateDebutReelle(), et.getDateDebutReelle(), et.getDateDebutReelle()),
                    String.format("%td/%tm/%tY", et.getDateFinReelle(), et.getDateFinReelle(), et.getDateFinReelle())
            );
        }
    }
}