package it.unicam.cs.mpgc.rpgXXXXXX.persistenza;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpgXXXXXX.model.salvataggi.PartitaSalvata;
import it.unicam.cs.mpgc.rpgXXXXXX.model.salvataggi.RepositoryPartite;
import it.unicam.cs.mpgc.rpgXXXXXX.model.salvataggi.RisultatoPartita;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class RepositoryPartiteJson implements RepositoryPartite {
    private static final String FILE_PARTITA_IN_CORSO = "partita.json";
    private static final String FILE_ULTIMA_PARTITA = "ultima.json";
    private static final String FILE_MIGLIORE_PARTITA = "migliore.json";

    private final Path cartella;
    private final Gson gson;

    public RepositoryPartiteJson(Path cartella) {
        this.cartella = cartella;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void salvaPartitaInCorso(PartitaSalvata partita) {
        scrivi(FILE_PARTITA_IN_CORSO, partita);
    }

    @Override
    public Optional<PartitaSalvata> caricaPartitaInCorso() {
        return leggi(FILE_PARTITA_IN_CORSO, PartitaSalvata.class);
    }

    @Override
    public void salvaUltimaPartita(RisultatoPartita partita) {
        scrivi(FILE_ULTIMA_PARTITA, partita);
    }

    @Override
    public Optional<RisultatoPartita> caricaUltimaPartita() {
        return leggi(FILE_ULTIMA_PARTITA, RisultatoPartita.class);
    }

    @Override
    public void salvaMigliorePartita(RisultatoPartita partita) {
        scrivi(FILE_MIGLIORE_PARTITA, partita);
    }

    @Override
    public Optional<RisultatoPartita> caricaMigliorePartita() {
        return leggi(FILE_MIGLIORE_PARTITA, RisultatoPartita.class);
    }

    private void scrivi(String nomeFile, Object datiDaSalvare) {
        try {
            Files.createDirectories(cartella);
            Files.writeString(cartella.resolve(nomeFile), gson.toJson(datiDaSalvare));
        } catch (IOException e) {
            throw new UncheckedIOException("Impossibile salvare " + nomeFile, e);
        }
    }

    private <T> Optional<T> leggi(String nomeFile, Class<T> tipoDati) {
        Path file = cartella.resolve(nomeFile);
        if (!Files.exists(file)) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(gson.fromJson(Files.readString(file), tipoDati));
        } catch (IOException e) {
            throw new UncheckedIOException("Impossibile leggere " + nomeFile, e);
        }
    }
}
