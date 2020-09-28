package fr.florian.demo.modele.etat;

import fr.florian.demo.modele.Commande;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class Envoyee implements EtatCommande {
    @Override
    public void creer(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible de créer une commande au statut Envoyee");
    }

    @Override
    public void rediger(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible de rédiger une commande au statut Envoyee");
    }

    @Override
    public void viser(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible de viser une commande au statut Envoyee");
    }

    @Override
    public void signer(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible de signer une commande au statut Envoyee");
    }

    @Override
    public void envoyer(Commande commande) {
        // rien
    }

    @Override
    public void receptionner(Commande commande) {
        commande.setDateReception(new Date());
        commande.setEtat(new Receptionnee());
    }

    @Override
    public void archiver(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible d'archiver une commande au statut Envoyee");
    }
}
