package it.unicam.cs.mpgc.rpgXXXXXX.model.salvataggi;

import java.util.Optional;

public interface RepositoryPartite {
    void salvaPartitaInCorso(PartitaSalvata partita);
    Optional<PartitaSalvata> caricaPartitaInCorso();
    void salvaUltimaPartita(RisultatoPartita partita);
    Optional<RisultatoPartita> caricaUltimaPartita();
    void salvaMigliorePartita(RisultatoPartita partita);
    Optional<RisultatoPartita> caricaMigliorePartita();
}
