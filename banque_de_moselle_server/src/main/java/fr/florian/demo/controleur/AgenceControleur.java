package fr.florian.demo.controleur;

import fr.florian.demo.form.AgenceForm;;
import fr.florian.demo.modele.Agence;
import fr.florian.demo.service.AgenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/agences")
public class AgenceControleur {

    private final AgenceService agenceService;

    public AgenceControleur(final AgenceService agenceService) {
        this.agenceService = agenceService;
    }

    @GetMapping
    public List<Agence> getAll() { return agenceService.findAll(); }

    @GetMapping(value = "{id}")
    public ResponseEntity<Agence> getOne(final @PathVariable Long id) {
        return agenceService.findOne(id)
                .map(agence -> ResponseEntity.ok().body(agence))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Agence creer(final @Valid @RequestBody AgenceForm agenceForm) {
        final Agence agence = new Agence();
        agence.setNom(agenceForm.getNom());
        agence.setAdresse(agenceForm.getAdresse());
        agence.setTypeAgence(agenceForm.getTypeAgence());
        return agenceService.ajouter(agence);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Agence> modifier(final @PathVariable Long id,
                                           final @Valid @RequestBody AgenceForm agenceForm) {
        return agenceService.findOne(id)
                .map(agenceAModifier -> {
                    agenceAModifier.setNom(agenceForm.getNom());
                    agenceAModifier.setAdresse(agenceForm.getAdresse());
                    agenceAModifier.setTypeAgence(agenceForm.getTypeAgence());
                    return ResponseEntity.ok().body(agenceService.modifier(agenceAModifier));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> supprimer(final @PathVariable Long id) {
        return agenceService.findOne(id)
                .map(agenceASupprimer -> {
                    agenceService.supprimer(agenceASupprimer);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
