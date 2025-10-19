package com.tp.service;

import com.tp.classes.Categorie;
import com.tp.classes.Commande;
import com.tp.classes.LigneCommandeProduit;
import com.tp.classes.Produit;
import com.tp.dao.IDao;
import com.tp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Produit o) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Produit o) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produit findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Produit.class, id);
        }
    }

    @Override
    public List<Produit> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Produit", Produit.class).list();
        }
    }

    public List<Produit> findByCategorie(Categorie categorie) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Produit> query = session.createQuery(
                    "FROM Produit p WHERE p.categorie = :categorie", Produit.class);
            query.setParameter("categorie", categorie);
            return query.list();
        }
    }

    public void afficherProduitsParCategorie(Categorie categorie) {
        List<Produit> produits = findByCategorie(categorie);
        System.out.println("\n=== Produits de la catégorie: " + categorie.getLibelle() + " ===");
        System.out.println("Référence\tPrix");
        System.out.println("--------------------------------");
        for (Produit p : produits) {
            System.out.printf("%s\t\t%.2f DH\n", p.getReference(), p.getPrix());
        }
    }

    public List<Produit> findProduitsEntreDeuxDates(Date dateDebut, Date dateFin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Produit> query = session.createQuery(
                    "SELECT DISTINCT lcp.produit FROM LigneCommandeProduit lcp " +
                            "WHERE lcp.commande.date BETWEEN :dateDebut AND :dateFin",
                    Produit.class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        }
    }

    public void afficherProduitsEntreDeuxDates(Date dateDebut, Date dateFin) {
        List<Produit> produits = findProduitsEntreDeuxDates(dateDebut, dateFin);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\n=== Produits commandés entre " +
                sdf.format(dateDebut) + " et " + sdf.format(dateFin) + " ===");
        System.out.println("Référence\tPrix");
        System.out.println("--------------------------------");
        for (Produit p : produits) {
            System.out.printf("%s\t\t%.2f DH\n", p.getReference(), p.getPrix());
        }
    }

    public void afficherProduitsCommande(Commande commande) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Commande cmd = session.get(Commande.class, commande.getId());
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

            System.out.println("\nCommande : " + cmd.getId() +
                    "\tDate : " + sdf.format(cmd.getDate()));
            System.out.println("Liste des produits :");
            System.out.println("Référence\tPrix\t\tQuantité");
            System.out.println("----------------------------------------");

            Query<LigneCommandeProduit> query = session.createQuery(
                    "FROM LigneCommandeProduit lcp WHERE lcp.commande.id = :commandeId",
                    LigneCommandeProduit.class);
            query.setParameter("commandeId", cmd.getId());

            List<LigneCommandeProduit> lignes = query.list();
            for (LigneCommandeProduit lcp : lignes) {
                System.out.printf("%s\t\t%.0f DH\t\t%d\n",
                        lcp.getProduit().getReference(),
                        lcp.getProduit().getPrix(),
                        lcp.getQuantite());
            }
        }
    }

    public List<Produit> findByPrixSuperieur(float prix) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Produit> query = session.createNamedQuery("Produit.findByPrixSuperieur", Produit.class);
            query.setParameter("prix", prix);
            return query.list();
        }
    }

    public void afficherProduitsSuperieur100() {
        List<Produit> produits = findByPrixSuperieur(100.0f);
        System.out.println("\n=== Produits dont le prix est supérieur à 100 DH ===");
        System.out.println("Référence\tPrix");
        System.out.println("--------------------------------");
        for (Produit p : produits) {
            System.out.printf("%s\t\t%.2f DH\n", p.getReference(), p.getPrix());
        }
    }
}