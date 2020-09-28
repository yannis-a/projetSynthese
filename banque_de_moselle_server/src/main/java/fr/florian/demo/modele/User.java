package fr.florian.demo.modele;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;

import javax.naming.Name;
import java.util.Set;

@Entry(objectClasses = {"person", "top"}, base = "ou=people")
public class User {

    @Id
    private Name dn;

    @Attribute(name = "uid")
    private String id;

    @Attribute(name = "cn")
    private String nomComplet;

    @Attribute(name = "sn")
    private String nom;

    @Attribute(name = "givenName")
    private String prenom;

    @Transient
    private Set<String> roles;

    @JsonIgnore
    public Name getDnInName() {
        return dn;
    }

    public String getDn() {
        return dn.toString();
    }

    public void setDn(final Name dn) {
        this.dn = dn;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(final String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(final String prenom) {
        this.prenom = prenom;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(final Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return nomComplet;
    }
}
