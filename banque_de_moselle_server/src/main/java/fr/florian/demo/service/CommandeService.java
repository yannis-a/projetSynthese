package fr.florian.demo.service;

import fr.florian.demo.modele.Commande;
import fr.florian.demo.repo.CommandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;

    public CommandeService(final CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> findOne(final Long id) {
        return commandeRepository.findById(id);
    }

    public Commande ajouter(final Commande commande) {
        commande.creer();
        return commandeRepository.saveAndFlush(commande);
    }

    public Commande modifier(final Commande commande) {
        commande.creer();
        return commandeRepository.saveAndFlush(commande);
    }

    public void supprimer(final Commande commande) {
        commandeRepository.delete(commande);
    }


    // Méthodes liées aux états d'une commande
    // La méthode creer() est présente dans les méthodes ajouter() et modifier()
    public Commande rediger(final Commande commande) {
        commande.rediger();
        return commandeRepository.saveAndFlush(commande);
    }

    public Commande viser(final Commande commande) {
        commande.viser();
        return commandeRepository.saveAndFlush(commande);
    }

    public Commande signer(final Commande commande) {
        commande.signer();
        return commandeRepository.saveAndFlush(commande);
    }

    public Commande envoyer(final Commande commande) {
        commande.envoyer();
        return commandeRepository.saveAndFlush(commande);
    }

    public Commande receptionner(final Commande commande) {
        commande.receptionner();
        return commandeRepository.saveAndFlush(commande);
    }

    public Commande archiver(final Commande commande) {
        commande.archiver();
        return commandeRepository.saveAndFlush(commande);
    }
}
