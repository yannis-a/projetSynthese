package fr.florian.demo.modele.etat;

import com.fasterxml.jackson.annotation.JsonValue;
import fr.florian.demo.modele.Commande;

/**
 * Interface représentant les actions possibles pour une {@link Commande}.
 */
public interface EtatCommande {

    void creer(final Commande commande);
    void rediger(final Commande commande);
    void viser(final Commande commande);
    void signer(final Commande commande);
    void envoyer(final Commande commande);
    void receptionner(final Commande commande);
    void archiver(final Commande commande);

    @JsonValue
    default int versJson() {
        return Etats.getNumero(this.getClass());
    }
}
