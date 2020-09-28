package fr.florian.demo.form;

import fr.florian.demo.modele.Fournisseur;
import fr.florian.demo.modele.LigneCommande;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CommandeForm {
    @NotEmpty
    private String numero;
    @NotNull
    private Fournisseur fournisseur;
    private List<LigneCommande> lignesCommandes;

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
        this.lignesCommandes = lignesCommandes;
    }
}
