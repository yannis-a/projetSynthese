package fr.florian.demo.modele.etat;

import fr.florian.demo.modele.Commande;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class Visee implements EtatCommande {
    @Override
    public void creer(Commande commande) {
        commande.setDateCreation(new Date());
        commande.setEtat(new Creee());
    }

    @Override
    public void rediger(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible de rédiger une commande au statut Visee");
    }

    @Override
    public void viser(Commande commande) {
        // rien
    }

    @Override
    public void signer(Commande commande) {
        commande.setDateSignature(new Date());
        commande.setEtat(new Signee());
    }

    @Override
    public void envoyer(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible d'envoyer une commande au statut Visee");
    }

    @Override
    public void receptionner(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible de réceptionner une commande au statut Visee");
    }

    @Override
    public void archiver(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible d'archiver une commande au statut Visee");
    }
}
