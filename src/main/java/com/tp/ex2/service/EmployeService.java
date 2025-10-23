package com.tp.ex2.service;

import com.tp.ex2.classes.Employe;
import com.tp.ex2.classes.EmployeTache;
import com.tp.ex2.classes.Projet;
import com.tp.ex2.classes.Tache;
import com.tp.ex2.dao.IDao;
import com.tp.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class EmployeService implements IDao<Employe> {

    private SessionFactory sessionFactory;

    public EmployeService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean create(Employe employe) {
        sessionFactory.getCurrentSession().save(employe);
        return true;
    }

    @Override
    public boolean delete(Employe employe) {
        sessionFactory.getCurrentSession().delete(employe);
        return true;
    }

    @Override
    public boolean update(Employe employe) {
        sessionFactory.getCurrentSession().update(employe);
        return true;
    }

    @Override
    public Employe findById(int id) {
        return sessionFactory.getCurrentSession().get(Employe.class, id);
    }

    @Override
    public List<Employe> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Employe", Employe.class)
                .list();
    }

    /**
     * Afficher la liste des tâches réalisées par un employé
     */
    public List<EmployeTache> getTachesRealisees(int employeId) {
        Query<EmployeTache> query = sessionFactory.getCurrentSession().createQuery(
                "FROM EmployeTache et WHERE et.employe.id = :employeId",
                EmployeTache.class
        );
        query.setParameter("employeId", employeId);
        return query.list();
    }

    /**
     * Afficher la liste des projets gérés par un employé (en tant que chef de projet)
     */
    public List<Projet> getProjetsGeres(int employeId) {
        Query<Projet> query = sessionFactory.getCurrentSession().createQuery(
                "FROM Projet p WHERE p.chefDeProjet.id = :employeId",
                Projet.class
        );
        query.setParameter("employeId", employeId);
        return query.list();
    }

    /**
     * Afficher les tâches réalisées par un employé (avec formatage)
     */
    public void afficherTachesRealisees(int employeId) {
        Employe employe = findById(employeId);
        if (employe == null) {
            System.out.println("Employé introuvable!");
            return;
        }

        System.out.println("\n=== Tâches réalisées par " + employe.getPrenom() + " " + employe.getNom() + " ===");
        List<EmployeTache> tachesRealisees = getTachesRealisees(employeId);

        if (tachesRealisees.isEmpty()) {
            System.out.println("Aucune tâche réalisée.");
            return;
        }

        System.out.printf("%-5s %-20s %-20s %-20s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
        System.out.println("-----------------------------------------------------------------------");

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

    /**
     * Afficher les projets gérés par un employé (avec formatage)
     */
    public void afficherProjetsGeres(int employeId) {
        Employe employe = findById(employeId);
        if (employe == null) {
            System.out.println("Employé introuvable!");
            return;
        }

        System.out.println("\n=== Projets gérés par " + employe.getPrenom() + " " + employe.getNom() + " ===");
        List<Projet> projets = getProjetsGeres(employeId);

        if (projets.isEmpty()) {
            System.out.println("Aucun projet géré.");
            return;
        }

        for (Projet projet : projets) {
            System.out.printf("Projet : %-5d Nom : %-30s Date début : %td %tB %tY%n",
                    projet.getId(),
                    projet.getNom(),
                    projet.getDateDebut(),
                    projet.getDateDebut(),
                    projet.getDateDebut()
            );
        }
    }
}