package com.tp.ex3.beans;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Femme.marieeDeuxFois",
                query = "SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2"
        ),
        @NamedQuery(
                name = "Femme.nbrEnfantsEntreDates",
                query = "SELECT COALESCE(SUM(m.nbrEnfant), 0) FROM Mariage m WHERE m.femme.id = :femmeId AND m.dateDebut BETWEEN :dateDebut AND :dateFin"
        )
})
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", fetch = FetchType.EAGER)
    private List<Mariage> mariages = new ArrayList<>();

    public Femme() {
        super();
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}