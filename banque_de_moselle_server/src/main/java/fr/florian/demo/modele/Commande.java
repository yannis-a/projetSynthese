package fr.florian.demo.modele;

import fr.florian.demo.modele.etat.Creee;
import fr.florian.demo.modele.etat.EtatCommande;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numero;

    @ManyToOne
    @JoinColumn
    private Fournisseur fournisseur;

    /**
     * Ajout d'une jointure entre les deux tables
     * Une commande concerne une seule agence
     */
    @ManyToOne
    @JoinColumn
    private Agence agence;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<LigneCommande> lignesCommandes;

    @Column
    private EtatCommande etat;

    @Column
    private Date dateCreation;

    @Column
    private Date dateRedaction;

    @Column
    private Date dateAppositionVisa;

    @Column
    private Date dateSignature;

    @Column
    private Date dateEnvoi;

    @Column
    private Date dateReception;

    @Column
    private Date dateArchivage;

    // Constructeur
    public Commande() {
        lignesCommandes = new ArrayList<>();
        etat = new Creee();
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(final String numero) {
        this.numero = numero;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(final Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<LigneCommande> getLignesCommandes() {
        return lignesCommandes;
    }

    public void setLignesCommandes(final List<LigneCommande> lignesCommandes) {
        this.lignesCommandes.clear();
        if (lignesCommandes != null) {
            this.lignesCommandes.addAll(lignesCommandes);
        }
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(final Agence agence) {
        this.agence = agence;
    }

    public EtatCommande getEtat() {
        return etat;
    }

    public void setEtat(EtatCommande etat) {
        this.etat = etat;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateRedaction() {
        return dateRedaction;
    }

    public void setDateRedaction(Date dateRedaction) {
        this.dateRedaction = dateRedaction;
    }

    public Date getDateAppositionVisa() {
        return dateAppositionVisa;
    }

    public void setDateAppositionVisa(Date dateAppositionVisa) {
        this.dateAppositionVisa = dateAppositionVisa;
    }

    public Date getDateSignature() {
        return dateSignature;
    }

    public void setDateSignature(Date dateSignature) {
        this.dateSignature = dateSignature;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public Date getDateReception() {
        return dateReception;
    }

    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }

    public Date getDateArchivage() {
        return dateArchivage;
    }

    public void setDateArchivage(Date dateArchivage) {
        this.dateArchivage = dateArchivage;
    }


    // Méthodes de la classe Commande liées au design pattern State
    public void creer() {
        etat.creer(this);
    }

    public void rediger() {
        etat.rediger(this);
    }

    public void viser() {
        etat.viser(this);
    }

    public void signer() {
        etat.signer(this);
    }

    public void envoyer() {
        etat.envoyer(this);
    }

    public void receptionner() {
        etat.receptionner(this);
    }

    public void archiver() {
        etat.archiver(this);
    }
}
