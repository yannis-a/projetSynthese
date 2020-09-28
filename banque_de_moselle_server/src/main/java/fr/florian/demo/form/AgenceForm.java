package fr.florian.demo.form;

import javax.validation.constraints.NotEmpty;

public class AgenceForm {
    @NotEmpty
    private String nom;
    @NotEmpty
    private String adresse;
    @NotEmpty
    private String typeAgence;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

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
