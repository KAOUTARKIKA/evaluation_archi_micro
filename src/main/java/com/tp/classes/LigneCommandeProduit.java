package com.tp.classes;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ligne_commande_produit")
public class LigneCommandeProduit {

    @EmbeddedId
    private LigneCommandeProduitPK pk;

    @ManyToOne
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @MapsId("commandeId")
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @Column(nullable = false)
    private int quantite;

    public LigneCommandeProduit() {}

    public LigneCommandeProduit(Produit produit, Commande commande, int quantite) {
        this.produit = produit;
        this.commande = commande;
        this.quantite = quantite;
        this.pk = new LigneCommandeProduitPK(produit.getId(), commande.getId());
    }

    public LigneCommandeProduitPK getPk() {
        return pk;
    }

    public void setPk(LigneCommandeProduitPK pk) {
        this.pk = pk;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

@Embeddable
class LigneCommandeProduitPK implements Serializable {
    private int produitId;
    private int commandeId;

    public LigneCommandeProduitPK() {}

    public LigneCommandeProduitPK(int produitId, int commandeId) {
        this.produitId = produitId;
        this.commandeId = commandeId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LigneCommandeProduitPK)) return false;
        LigneCommandeProduitPK that = (LigneCommandeProduitPK) o;
        return produitId == that.produitId && commandeId == that.commandeId;
    }

    @Override
    public int hashCode() {
        return 31 * produitId + commandeId;
    }
}