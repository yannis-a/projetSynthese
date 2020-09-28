package fr.florian.demo.modele.etat;

import fr.florian.demo.modele.Commande;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Test de la classe {@link Creee}.
 */
class CreeeTest {

    @Test
    void creer() {
        final Commande commande = new Commande();
        final Creee etat = new Creee();

        etat.creer(commande);

        assertThat(commande.getEtat()).isInstanceOf(Creee.class);
    }

    @Test
    void rediger() {
        final Commande commande = new Commande();
        final Creee etat = new Creee();

        etat.rediger(commande);

        assertThat(commande.getEtat()).isInstanceOf(Redigee.class);
    }

    @Test
    void viser() {
        final Commande commande = new Commande();
        final Creee etat = new Creee();

        final Throwable thrown = catchThrowable(() -> etat.viser(commande));

        assertThat(thrown).isInstanceOf(ResponseStatusException.class)
                          .hasNoCause()
                          .hasMessageContaining("Impossible de viser une commande au statut Créée");
    }

    @Test
    void signer() {
        final Commande commande = new Commande();
        final Creee etat = new Creee();

        final Throwable thrown = catchThrowable(() -> etat.signer(commande));

        assertThat(thrown).isInstanceOf(ResponseStatusException.class)
                          .hasNoCause()
                          .hasMessageContaining("Impossible de signer une commande au statut Créée");
    }

    @Test
    void envoyer() {
        final Commande commande = new Commande();
        final Creee etat = new Creee();

        final Throwable thrown = catchThrowable(() -> etat.envoyer(commande));

        assertThat(thrown).isInstanceOf(ResponseStatusException.class)
                          .hasNoCause()
                          .hasMessageContaining("Impossible d'envoyer une commande au statut Créée");
    }

    @Test
    void receptionner() {
        final Commande commande = new Commande();
        final Creee etat = new Creee();

        final Throwable thrown = catchThrowable(() -> etat.receptionner(commande));

        assertThat(thrown).isInstanceOf(ResponseStatusException.class)
                          .hasNoCause()
                          .hasMessageContaining("Impossible de réceptionner une commande au statut Créée");
    }

    @Test
    void archiver() {
        final Commande commande = new Commande();
        final Creee etat = new Creee();

        final Throwable thrown = catchThrowable(() -> etat.archiver(commande));

        assertThat(thrown).isInstanceOf(ResponseStatusException.class)
                          .hasNoCause()
                          .hasMessageContaining("Impossible d'archiver une commande au statut Créée");
    }
}
