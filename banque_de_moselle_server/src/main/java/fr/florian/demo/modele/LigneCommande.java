package fr.florian.demo.modele;

import javax.persistence.*;

@Entity
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String reference;

    @Column
    private double prix;

    @Column
    private double tva;

    @Column
    private double quantite;

    /**
     * Ajout d'une jointure entre les deux tables
     * Une LigneCommande concerne 1 seul article
     */
    @ManyToOne
    @JoinColumn
    private Article article;


    public LigneCommande() {
    }

    public Long getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(final double prix) {
        this.prix = prix;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(final double tva) {
        this.tva = tva;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(final double quantite) {
        this.quantite = quantite;
    }
}
