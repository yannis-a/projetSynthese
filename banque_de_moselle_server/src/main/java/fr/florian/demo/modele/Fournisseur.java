package fr.florian.demo.modele;

import javax.persistence.*;

@Entity
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean actif;

    @Column
    private String nom;

    @Column
    private String siret;

    public Fournisseur() {
        // vide
    }

    public Long getId() {
        return id;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(final boolean actif) {
        this.actif = actif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(final String siret) {
        this.siret = siret;
    }
}
