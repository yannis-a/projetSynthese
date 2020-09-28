package fr.florian.demo.form;

import javax.validation.constraints.NotEmpty;

public class FournisseurForm {
    @NotEmpty
    private String nom;
    @NotEmpty
    private String siret;

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
