package fr.florian.demo.controleur;

import fr.florian.demo.form.FournisseurForm;
import fr.florian.demo.modele.Fournisseur;
import fr.florian.demo.service.FournisseurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/fournisseurs")
public class FournisseurControleur {

    private final FournisseurService fournisseurService;

    /**
     * Constructeur.
     */
    public FournisseurControleur(final FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @GetMapping
    public List<Fournisseur> getAll() {
        return fournisseurService.findAll();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Fournisseur> getOne(final @PathVariable Long id) {
        return fournisseurService.findOne(id)
                .map(fournisseur -> ResponseEntity.ok().body(fournisseur))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Fournisseur creer(final @Valid @RequestBody FournisseurForm fournisseurForm) {
        final Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom(fournisseurForm.getNom());
        fournisseur.setSiret(fournisseurForm.getSiret());
        return fournisseurService.ajouter(fournisseur);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Fournisseur> modifier(final @PathVariable Long id,
                                                final @Valid @RequestBody FournisseurForm fournisseurForm) {
        return fournisseurService.findOne(id)
                .map(fournisseurAModifier -> {
                    fournisseurAModifier.setNom(fournisseurForm.getNom());
                    fournisseurAModifier.setSiret(fournisseurForm.getSiret());
                    return ResponseEntity.ok().body(fournisseurService.modifier(fournisseurAModifier));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> supprimer(final @PathVariable Long id) {
        return fournisseurService.findOne(id)
                .map(fournisseur -> {
                    fournisseurService.supprimer(fournisseur);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
