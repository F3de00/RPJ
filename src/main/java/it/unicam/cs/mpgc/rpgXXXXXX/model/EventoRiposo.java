package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.Optional;

public class EventoRiposo implements EventoStanza {
    private final int percentuale;

    public EventoRiposo(int percentuale) {
        if (percentuale < 0 || percentuale > 100) {
            throw new IllegalArgumentException("Percentuale non valida: " + percentuale);
        }
        this.percentuale = percentuale;
    }

    public int getPercentuale() {return percentuale;}

    @Override
    public Optional<Combattimento> esegui(Entita eroe) {
        eroe.recuperaVita(eroe.getVitaMassima() * percentuale / 100);
        eroe.recuperaMana(eroe.getManaMassimo() * percentuale / 100);
        return Optional.empty();
    }
}
