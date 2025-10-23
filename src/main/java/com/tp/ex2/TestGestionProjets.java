package com.tp.ex2;

import com.tp.ex2.classes.Employe;
import com.tp.ex2.classes.EmployeTache;
import com.tp.ex2.classes.Projet;
import com.tp.ex2.classes.Tache;
import com.tp.ex2.service.EmployeService;
import com.tp.ex2.service.EmployeTacheService;
import com.tp.ex2.service.ProjetService;
import com.tp.ex2.service.TacheService;
import com.tp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestGestionProjets {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        Transaction transaction = null;

        try {
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();

            // Obtenir la session courante et démarrer une transaction
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();

            System.out.println("=== CRÉATION DES DONNÉES DE TEST ===\n");

            Employe emp1 = new Employe("ALAMI", "Ahmed", "0661234567");
            Employe emp2 = new Employe("BENALI", "Fatima", "0662345678");
            Employe emp3 = new Employe("RAMI", "Karim", "0663456789");
            Employe emp4 = new Employe("IDRISSI", "Salma", "0664567890");
            Employe emp5 = new Employe("TAZI", "Omar", "0665678901");

            employeService.create(emp1);
            employeService.create(emp2);
            employeService.create(emp3);
            employeService.create(emp4);
            employeService.create(emp5);

            System.out.println("✓ 5 employés créés");

            Projet projet1 = new Projet("Gestion de stock",
                    sdf.parse("14/01/2013"),
                    sdf.parse("14/06/2013"),
                    emp1);

            Projet projet2 = new Projet("Application Mobile",
                    sdf.parse("01/03/2013"),
                    sdf.parse("30/08/2013"),
                    emp2);

            Projet projet3 = new Projet("Site Web E-commerce",
                    sdf.parse("15/02/2013"),
                    sdf.parse("15/09/2013"),
                    emp3);

            projetService.create(projet1);
            projetService.create(projet2);
            projetService.create(projet3);

            System.out.println("✓ 3 projets créés");

            Tache tache1 = new Tache("Analyse",
                    sdf.parse("01/02/2013"),
                    sdf.parse("28/02/2013"),
                    1200.0,
                    projet1);

            Tache tache2 = new Tache("Conception",
                    sdf.parse("01/03/2013"),
                    sdf.parse("31/03/2013"),
                    1500.0,
                    projet1);

            Tache tache3 = new Tache("Développement",
                    sdf.parse("01/04/2013"),
                    sdf.parse("30/05/2013"),
                    3000.0,
                    projet1);

            Tache tache4 = new Tache("Tests",
                    sdf.parse("01/06/2013"),
                    sdf.parse("14/06/2013"),
                    800.0,
                    projet1);

            Tache tache5 = new Tache("Analyse",
                    sdf.parse("05/03/2013"),
                    sdf.parse("25/03/2013"),
                    950.0,
                    projet2);

            Tache tache6 = new Tache("Design UI/UX",
                    sdf.parse("26/03/2013"),
                    sdf.parse("15/04/2013"),
                    1100.0,
                    projet2);

            Tache tache7 = new Tache("Développement",
                    sdf.parse("16/04/2013"),
                    sdf.parse("31/07/2013"),
                    2500.0,
                    projet2);

            Tache tache8 = new Tache("Cahier des charges",
                    sdf.parse("20/02/2013"),
                    sdf.parse("10/03/2013"),
                    700.0,
                    projet3);

            tacheService.create(tache1);
            tacheService.create(tache2);
            tacheService.create(tache3);
            tacheService.create(tache4);
            tacheService.create(tache5);
            tacheService.create(tache6);
            tacheService.create(tache7);
            tacheService.create(tache8);

            System.out.println("✓ 8 tâches créées");

            EmployeTache et1 = new EmployeTache(emp2, tache1,
                    sdf.parse("10/02/2013"),
                    sdf.parse("20/02/2013"));

            EmployeTache et2 = new EmployeTache(emp3, tache2,
                    sdf.parse("10/03/2013"),
                    sdf.parse("15/03/2013"));

            EmployeTache et3 = new EmployeTache(emp4, tache3,
                    sdf.parse("10/04/2013"),
                    sdf.parse("25/04/2013"));

            EmployeTache et4 = new EmployeTache(emp5, tache4,
                    sdf.parse("05/06/2013"),
                    sdf.parse("10/06/2013"));

            EmployeTache et5 = new EmployeTache(emp2, tache5,
                    sdf.parse("07/03/2013"),
                    sdf.parse("22/03/2013"));

            EmployeTache et6 = new EmployeTache(emp3, tache6,
                    sdf.parse("27/03/2013"),
                    sdf.parse("12/04/2013"));

            EmployeTache et7 = new EmployeTache(emp4, tache7,
                    sdf.parse("18/04/2013"),
                    sdf.parse("28/07/2013"));

            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            employeTacheService.create(et4);
            employeTacheService.create(et5);
            employeTacheService.create(et6);
            employeTacheService.create(et7);

            System.out.println("✓ 7 affectations employé-tâche créées\n");

            // Commit de la transaction
            transaction.commit();

            String separator = "================================================================================";

            // Nouvelle session et transaction pour les lectures
            Session newSession = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = newSession.beginTransaction();

            System.out.println("\n" + separator);
            System.out.println("TEST 1: Afficher les tâches réalisées par un employé");
            System.out.println(separator);
            employeService.afficherTachesRealisees(emp2.getId());

            System.out.println("\n" + separator);
            System.out.println("TEST 2: Afficher les projets gérés par un employé");
            System.out.println(separator);
            employeService.afficherProjetsGeres(emp1.getId());

            System.out.println("\n" + separator);
            System.out.println("TEST 3: Afficher les tâches réalisées d'un projet avec dates réelles");
            System.out.println(separator);
            projetService.afficherTachesRealisees(projet1.getId());

            System.out.println("\n" + separator);
            System.out.println("TEST 4: Afficher les tâches dont le prix > 1000 DH");
            System.out.println(separator);
            tacheService.afficherTachesPrixSuperieur1000();

            System.out.println("\n" + separator);
            System.out.println("TEST 5: Afficher les tâches réalisées entre deux dates");
            System.out.println(separator);
            tacheService.afficherTachesEntreDeuxDates(
                    sdf.parse("01/03/2013"),
                    sdf.parse("30/04/2013")
            );

            System.out.println("\n" + separator);
            System.out.println("TEST 6: Afficher toutes les tâches planifiées d'un projet");
            System.out.println(separator);
            projetService.afficherTachesPlanifiees(projet1.getId());

            transaction.commit();

            System.out.println("\n\n=== FIN DES TESTS ===");

        } catch (ParseException e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur de format de date: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur lors de l'exécution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}