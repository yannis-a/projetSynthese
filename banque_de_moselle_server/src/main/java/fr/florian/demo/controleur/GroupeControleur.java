package fr.florian.demo.controleur;

import fr.florian.demo.modele.Groupe;
import fr.florian.demo.service.GroupeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/groupes")
public class GroupeControleur {
    private final GroupeService groupeService;

    /**
     * Constructeur.
     */
    public GroupeControleur(final GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    @GetMapping
    public List<Groupe> getAll() {
        return groupeService.findAll();
    }
}
