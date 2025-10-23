package com.tp.ex1.service;

import com.tp.ex1.classes.Categorie;
import com.tp.ex1.classes.Commande;
import com.tp.ex1.classes.LigneCommandeProduit;
import com.tp.ex1.classes.Produit;
import com.tp.ex1.dao.IDao;
import com.tp.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    private SessionFactory sessionFactory;

    public ProduitService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean create(Produit o) {
        sessionFactory.getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(Produit o) {
        sessionFactory.getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(Produit o) {
        sessionFactory.getCurrentSession().delete(o);
        return true;
    }

    @Override
    public Produit findById(int id) {
        return sessionFactory.getCurrentSession().get(Produit.class, id);
    }

    @Override
    public List<Produit> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Produit", Produit.class)
                .list();
    }

    public List<Produit> findByCategorie(Categorie categorie) {
        Query<Produit> query = sessionFactory.getCurrentSession().createQuery(
                "FROM Produit p WHERE p.categorie = :categorie", Produit.class);
        query.setParameter("categorie", categorie);
        return query.list();
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
        Query<Produit> query = sessionFactory.getCurrentSession().createQuery(
                "SELECT DISTINCT lcp.produit FROM LigneCommandeProduit lcp " +
                        "WHERE lcp.commande.date BETWEEN :dateDebut AND :dateFin",
                Produit.class);
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);
        return query.list();
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
        Commande cmd = sessionFactory.getCurrentSession().get(Commande.class, commande.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

        System.out.println("\nCommande : " + cmd.getId() +
                "\tDate : " + sdf.format(cmd.getDate()));
        System.out.println("Liste des produits :");
        System.out.println("Référence\tPrix\t\tQuantité");
        System.out.println("----------------------------------------");

        Query<LigneCommandeProduit> query = sessionFactory.getCurrentSession().createQuery(
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

    public List<Produit> findByPrixSuperieur(float prix) {
        Query<Produit> query = sessionFactory.getCurrentSession()
                .createNamedQuery("Produit.findByPrixSuperieur", Produit.class);
        query.setParameter("prix", prix);
        return query.list();
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