package fr.florian.demo.modele;

import javax.persistence.*;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean actif;

    @Column
    private String designation;

    @Column
    private double prixUnitaire;

    @Column
    private double tauxTva;

    /**
     * Ajout d'une jointure entre les deux tables
     * Un article appartient au catalogue d'1 seul fournisseur
     */
    @ManyToOne
    @JoinColumn
    private Fournisseur fournisseur;

    public Article() {}

    public Long getId() {
        return id;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getTauxTva() {
        return tauxTva;
    }

    public void setTauxTva(double tauxTva) {
        this.tauxTva = tauxTva;
    }
}
