package fr.florian.demo.service;

import fr.florian.demo.modele.Agence;
import fr.florian.demo.repo.AgenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenceService {

    private final AgenceRepository agenceRepository;

    public AgenceService(final AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    public List<Agence> findAll() { return agenceRepository.findAll(); }

    public Optional<Agence> findOne(final Long id) { return agenceRepository.findById(id); }

    public Agence ajouter(final Agence agence) {
        agence.setActif(true);
        return agenceRepository.saveAndFlush(agence);
    }

    public Agence modifier(final Agence agence) {
        return agenceRepository.saveAndFlush(agence);
    }

    public void supprimer(final Agence agence) {
        agence.setActif(false);
        agenceRepository.saveAndFlush(agence);
    }
}
