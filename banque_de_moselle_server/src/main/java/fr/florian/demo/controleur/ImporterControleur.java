package fr.florian.demo.controleur;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import fr.florian.demo.modele.Agence;
import fr.florian.demo.repo.AgenceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("importer")
@RestController
public class ImporterControleur {
    private final AgenceRepository agenceRepository;

    public ImporterControleur(final AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    @PostMapping
    public ResponseEntity<?> importer(@RequestParam("file") MultipartFile file) {

        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            MappingIterator<Agence> readValues =
                    mapper.readerFor(Agence.class).with(bootstrapSchema).readValues(file.getInputStream());

            final List<Agence> agences = readValues.readAll();
            agences.forEach(a -> a.setActif(true));

            agenceRepository.saveAll(agences);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}
