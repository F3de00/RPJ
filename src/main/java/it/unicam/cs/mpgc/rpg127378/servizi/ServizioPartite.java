package it.unicam.cs.mpgc.rpg127378.servizi;

import java.util.List;
import java.util.Optional;

import it.unicam.cs.mpgc.rpg127378.model.FabbricaPartita;
import it.unicam.cs.mpgc.rpg127378.model.Partita;
import it.unicam.cs.mpgc.rpg127378.model.creature.StatisticheBase;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.PartitaPersa;
import it.unicam.cs.mpgc.rpg127378.model.salvataggi.PartitaSalvata;
import it.unicam.cs.mpgc.rpg127378.model.salvataggi.RepositoryPartite;
import it.unicam.cs.mpgc.rpg127378.model.salvataggi.RisultatoPartita;

public class ServizioPartite {
    private final FabbricaPartita fabbrica;
    private final RepositoryPartite repository;

    public ServizioPartite(FabbricaPartita fabbrica, RepositoryPartite repository) {
        this.fabbrica = fabbrica;
        this.repository = repository;
    }

    public List<StatisticheBase> getEroiDisponibili() {return fabbrica.getEroiDisponibili();}
    public Optional<RisultatoPartita> getUltimaPartita() {return repository.caricaUltimaPartita();}
    public Optional<RisultatoPartita> getMigliorePartita() {return repository.caricaMigliorePartita();}
    public boolean esistePartitaSalvata() {return repository.caricaPartitaInCorso().isPresent();}

    public Partita nuovaPartita(StatisticheBase tipoEroe) {
        return registraRisultatoAllaFine(fabbrica.nuovaPartita(tipoEroe));
    }

    public Partita riprendiPartita() {
        PartitaSalvata partitaSalvata = repository.caricaPartitaInCorso().orElseThrow(() -> new IllegalStateException("Nessuna partita salvata"));
        return registraRisultatoAllaFine(fabbrica.riprendiPartita(partitaSalvata));
    }

    public void salvaPartita(Partita partita) {
        repository.salvaPartitaInCorso(partita.creaSalvataggio());
        registraRisultato(partita);
    }

    private Partita registraRisultatoAllaFine(Partita partita) {
        partita.aggiungiOsservatore(notifica -> {
            if (notifica instanceof PartitaPersa) {
                registraRisultato(partita);
            }
        });
        return partita;
    }

    private void registraRisultato(Partita partitaDaRegistrare) {
        RisultatoPartita risultato = new RisultatoPartita(partitaDaRegistrare.getEroe().getTipo(), partitaDaRegistrare.getStanzeCompletate());
        repository.salvaUltimaPartita(risultato);
        boolean nuovoRecord = repository.caricaMigliorePartita()
                .map(migliore -> risultato.stanzeSuperate() > migliore.stanzeSuperate())
                .orElse(true);
        if (nuovoRecord) {
            repository.salvaMigliorePartita(risultato);
        }
    }
}
