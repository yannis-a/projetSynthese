package fr.florian.demo.controleur;

import fr.florian.demo.form.CommandeForm;
import fr.florian.demo.modele.Commande;
import fr.florian.demo.service.CommandeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/commandes")
public class CommandeControleur {

    private final CommandeService commandeService;

    /**
     * Constructeur.
     */
    public CommandeControleur(final CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping
    public List<Commande> getAll() {
        return commandeService.findAll();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Commande> getOne(final @PathVariable Long id) {
        return commandeService.findOne(id)
                .map(commande -> ResponseEntity.ok().body(commande))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Commande creer(final @Valid @RequestBody CommandeForm commandeForm) {
        final Commande commande = new Commande();
        commande.setNumero(commandeForm.getNumero());
        commande.setFournisseur(commandeForm.getFournisseur());
        commande.setLignesCommandes(commandeForm.getLignesCommandes());
        return commandeService.ajouter(commande);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Commande> modifier(final @PathVariable Long id,
                                             final @Valid @RequestBody CommandeForm commandeForm) {
        return commandeService.findOne(id)
                              .map(commandeAModifier -> {
                                  commandeAModifier.setNumero(commandeForm.getNumero());
                                  commandeAModifier.setFournisseur(commandeForm.getFournisseur());
                                  commandeAModifier.setLignesCommandes(commandeForm.getLignesCommandes());
                                  return ResponseEntity.ok().body(commandeService.modifier(commandeAModifier));
                              })
                              .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> supprimer(final @PathVariable Long id) {
        return commandeService.findOne(id)
                .map(commande -> {
                    commandeService.supprimer(commande);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     *  Il faut ici contrôler les appels clients sur les méthodes des états d'une commande
     *  Contrôles sur rediger, viser, signer, envoyer, receptionner, archiver
     *  La fonction creer() est appelée par le biais des fonctions ajouter() et modifier() du service
     */

    @PutMapping(value = "{id}/rediger")
    @Secured("ROLE_REDACTEURS")
    public ResponseEntity<Commande> rediger(final @PathVariable Long id) {
        return commandeService.findOne(id)
                              .map(commandeAModifier -> ResponseEntity.ok().body(commandeService.rediger(commandeAModifier)))
                              .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "{id}/viser")
    @Secured("ROLE_VISEURS")
    public ResponseEntity<Commande>viser(final @PathVariable Long id) {
        return commandeService.findOne(id)
                .map(commandeAModifier -> ResponseEntity.ok().body(commandeService.viser(commandeAModifier)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "{id}/signer")
    @Secured("ROLE_SIGNATAIRES")
    public ResponseEntity<Commande>signer(final @PathVariable Long id) {
        return commandeService.findOne(id)
                .map(commandeAModifier -> ResponseEntity.ok().body(commandeService.signer(commandeAModifier)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "{id}/envoyer")
    @Secured("ROLE_SIGNATAIRES")
    public ResponseEntity<Commande>envoyer(final @PathVariable Long id) {
        return commandeService.findOne(id)
                .map(commandeAModifier -> ResponseEntity.ok().body(commandeService.envoyer(commandeAModifier)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "{id}/receptionner")
    @Secured("ROLE_RECEPTIONNEURS")
    public ResponseEntity<Commande>receptionner(final @PathVariable Long id) {
        return commandeService.findOne(id)
                .map(commandeAModifier -> ResponseEntity.ok().body(commandeService.receptionner(commandeAModifier)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "{id}/archiver")
    @Secured("ROLE_ADMINS")
    public ResponseEntity<Commande>archiver(final @PathVariable Long id) {
        return commandeService.findOne(id)
                .map(commandeAModifier -> ResponseEntity.ok().body(commandeService.archiver(commandeAModifier)))
                .orElse(ResponseEntity.notFound().build());
    }
}
