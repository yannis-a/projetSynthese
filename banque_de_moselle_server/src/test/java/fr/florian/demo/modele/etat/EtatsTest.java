package fr.florian.demo.modele.etat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EtatsTest {

    @Test
    void getEtat() {
        final EtatCommande etat = Etats.getEtat(10);

        assertThat(etat).isInstanceOf(Creee.class);
    }
}
