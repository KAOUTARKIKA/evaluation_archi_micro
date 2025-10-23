package com.tp.ex1;

import com.tp.ex1.classes.Categorie;
import com.tp.ex1.classes.Commande;
import com.tp.ex1.classes.LigneCommandeProduit;
import com.tp.ex1.classes.Produit;
import com.tp.ex1.service.CategorieService;
import com.tp.ex1.service.CommandeService;
import com.tp.ex1.service.LigneCommandeService;
import com.tp.ex1.service.ProduitService;
import com.tp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestApplication {

    public static void main(String[] args) {
        Transaction transaction = null;

        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Démarrer une session et une transaction
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();

            System.out.println("==============================================");
            System.out.println("     APPLICATION DE GESTION DE STOCK");
            System.out.println("==============================================\n");

            System.out.println("1. CRÉATION DES CATÉGORIES");
            System.out.println("----------------------------");
            Categorie cat1 = new Categorie("INF", "Informatique");
            Categorie cat2 = new Categorie("BUREAU", "Bureautique");
            Categorie cat3 = new Categorie("AUDIO", "Audio/Vidéo");

            categorieService.create(cat1);
            categorieService.create(cat2);
            categorieService.create(cat3);
            System.out.println("✓ 3 catégories créées avec succès\n");

            System.out.println("2. CRÉATION DES PRODUITS");
            System.out.println("----------------------------");
            Produit p1 = new Produit("ES12", 120, cat1);
            Produit p2 = new Produit("ZR85", 100, cat1);
            Produit p3 = new Produit("EE85", 200, cat1);
            Produit p4 = new Produit("CL50", 80, cat2);
            Produit p5 = new Produit("BU45", 150, cat2);
            Produit p6 = new Produit("AV30", 250, cat3);

            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);
            produitService.create(p4);
            produitService.create(p5);
            produitService.create(p6);
            System.out.println("✓ 6 produits créés avec succès\n");

            System.out.println("3. CRÉATION DES COMMANDES");
            System.out.println("----------------------------");
            Date date1 = sdf.parse("14/03/2013");
            Date date2 = sdf.parse("20/05/2013");
            Date date3 = sdf.parse("10/08/2013");

            Commande cmd1 = new Commande(date1);
            Commande cmd2 = new Commande(date2);
            Commande cmd3 = new Commande(date3);

            commandeService.create(cmd1);
            commandeService.create(cmd2);
            commandeService.create(cmd3);
            System.out.println("✓ 3 commandes créées avec succès\n");

            System.out.println("4. CRÉATION DES LIGNES DE COMMANDE");
            System.out.println("----------------------------");
            LigneCommandeProduit lcp1 = new LigneCommandeProduit(p1, cmd1, 7);
            LigneCommandeProduit lcp2 = new LigneCommandeProduit(p2, cmd1, 14);
            LigneCommandeProduit lcp3 = new LigneCommandeProduit(p3, cmd1, 5);
            LigneCommandeProduit lcp4 = new LigneCommandeProduit(p4, cmd2, 10);
            LigneCommandeProduit lcp5 = new LigneCommandeProduit(p5, cmd2, 3);
            LigneCommandeProduit lcp6 = new LigneCommandeProduit(p6, cmd3, 8);

            ligneCommandeService.create(lcp1);
            ligneCommandeService.create(lcp2);
            ligneCommandeService.create(lcp3);
            ligneCommandeService.create(lcp4);
            ligneCommandeService.create(lcp5);
            ligneCommandeService.create(lcp6);
            System.out.println("✓ 6 lignes de commande créées avec succès\n");

            // Commit de la transaction
            transaction.commit();

            // Nouvelle session et transaction pour les lectures
            Session newSession = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = newSession.beginTransaction();

            System.out.println("\n==============================================");
            System.out.println("5. TEST : PRODUITS PAR CATÉGORIE");
            System.out.println("==============================================");
            produitService.afficherProduitsParCategorie(cat1);
            produitService.afficherProduitsParCategorie(cat2);

            System.out.println("\n==============================================");
            System.out.println("6. TEST : PRODUITS COMMANDÉS ENTRE DEUX DATES");
            System.out.println("==============================================");
            Date dateDebut = sdf.parse("01/03/2013");
            Date dateFin = sdf.parse("31/05/2013");
            produitService.afficherProduitsEntreDeuxDates(dateDebut, dateFin);

            System.out.println("\n==============================================");
            System.out.println("7. TEST : PRODUITS D'UNE COMMANDE");
            System.out.println("==============================================");
            produitService.afficherProduitsCommande(cmd1);

            System.out.println("\n==============================================");
            System.out.println("8. TEST : PRODUITS PRIX > 100 DH (Requête nommée)");
            System.out.println("==============================================");
            produitService.afficherProduitsSuperieur100();

            transaction.commit();

            System.out.println("\n==============================================");
            System.out.println("     TESTS TERMINÉS AVEC SUCCÈS !");
            System.out.println("==============================================\n");

        } catch (ParseException e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur de format de date : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur lors de l'exécution : " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}