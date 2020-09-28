package fr.florian.demo.modele.etat;

import java.util.function.Supplier;

import static java.util.Arrays.stream;

public enum Etats {
    CREEE(10, Creee.class, Creee::new),
    REDIGEE(20, Redigee.class, Redigee::new),
    VISEE(30, Visee.class, Visee::new),
    SIGNEE(40, Signee.class, Signee::new),
    ENVOYEE(50, Envoyee.class, Envoyee::new),
    RECEPTIONNEE(60, Receptionnee.class, Receptionnee::new),
    ARCHIVEE(70, Archivee.class, Archivee::new);

    private final Integer numero;
    private final Class<?> nomClasse;
    private final Supplier<EtatCommande> constructeur;

    Etats(final int numero, final Class<?> nomClasse, final Supplier<EtatCommande> supplier) {
        this.numero = numero;
        this.nomClasse = nomClasse;
        this.constructeur = supplier;
    }

    private Integer getNumero() {
        return numero;
    }

    private Supplier<EtatCommande> getConstructeur() {
        return constructeur;
    }

    public static int getNumero(final Class<?> classeATester) {
        return stream(values()).filter(v -> v.nomClasse.equals(classeATester))
                               .findFirst()
                               .orElseThrow(IllegalArgumentException::new)
                               .getNumero();
    }

    public static EtatCommande getEtat(final Integer numeroATester) {
        Supplier<EtatCommande> supplier = stream(values()).filter(v -> v.numero.equals(numeroATester))
                                                          .findFirst()
                                                          .orElseThrow(IllegalArgumentException::new)
                                                          .getConstructeur();

        return supplier.get();
    }
}
