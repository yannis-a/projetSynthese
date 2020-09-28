package fr.florian.demo.modele;

import javax.persistence.*;

@Entity
public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean actif;

    @Column
    private String nom;

    @Column
    private String adresse;

    @Column
    private String typeAgence;

    public Agence() {}

    public Long getId() {
        return id;
    }

    public boolean isActif() { return actif; }

    public void setActif(final boolean actif) { this.actif = actif; }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTypeAgence() {
        return typeAgence;
    }

    public void setTypeAgence(String typeAgence) {
        this.typeAgence = typeAgence;
    }
}
