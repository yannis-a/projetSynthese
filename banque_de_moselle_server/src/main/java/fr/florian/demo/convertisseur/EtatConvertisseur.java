package fr.florian.demo.convertisseur;

import fr.florian.demo.modele.etat.EtatCommande;
import fr.florian.demo.modele.etat.Etats;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EtatConvertisseur implements AttributeConverter<EtatCommande, Integer> {
    @Override
    public Integer convertToDatabaseColumn(final EtatCommande etatCommande) {
        if (etatCommande == null) {
            return null;
        }
        return Etats.getNumero(etatCommande.getClass());
    }

    @Override
    public EtatCommande convertToEntityAttribute(final Integer integer) {
        if (integer == null) {
            return null;
        }
        return Etats.getEtat(integer);
    }
}
