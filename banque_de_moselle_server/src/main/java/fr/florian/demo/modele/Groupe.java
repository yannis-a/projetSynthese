package fr.florian.demo.modele;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.Set;
import java.util.stream.Collectors;

@Entry(objectClasses = {"top", "groupOfUniqueNames"}, base = "ou=groups")
public final class Groupe {

    @Id
    private Name dn;

    @Attribute(name = "cn")
    @DnAttribute("cn")
    private String nom;

    @Attribute(name = "uniqueMember")
    private Set<Name> membres;

    //TODO remplacer ça !
    public String getDn() {
        return dn.toString();
    }

    public void setDn(final Name dn) {
        this.dn = dn;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    //TODO remplacer ça !
    public Set<String> getMembres() {
        return membres.stream().map(Object::toString).collect(Collectors.toSet());
    }

    public void setMembres(final Set<Name> membres) {
        this.membres = membres;
    }

    public void addMembre(final Name membre) {
        membres.add(membre);
    }

    public void removeMembre(final Name membre) {
        membres.remove(membre);
    }
}
