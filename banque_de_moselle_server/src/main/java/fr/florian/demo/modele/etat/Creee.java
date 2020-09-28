package fr.florian.demo.modele.etat;

import fr.florian.demo.modele.Commande;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class Creee implements EtatCommande {
    @Override
    public void creer(Commande commande) {
        commande.setDateCreation(new Date());
        commande.setEtat(new Creee());
    }

    @Override
    public void rediger(Commande commande) {
        commande.setDateRedaction(new Date());
        commande.setEtat(new Redigee());
    }

    @Override
    public void viser(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible de viser une commande au statut Creee");
    }

    @Override
    public void signer(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible de signer une commande au statut Creee");
    }

    @Override
    public void envoyer(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible d'envoyer une commande au statut Creee");
    }

    @Override
    public void receptionner(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible de réceptionner une commande au statut Creee");
    }

    @Override
    public void archiver(Commande commande) {
        throw new ResponseStatusException(BAD_REQUEST, "Impossible d'archiver une commande au statut Creee");
    }
}
