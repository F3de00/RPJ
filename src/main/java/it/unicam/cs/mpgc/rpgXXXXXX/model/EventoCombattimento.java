package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.List;
import java.util.Optional;

public class EventoCombattimento implements EventoStanza {
    private final List<Entita> nemici;

    public EventoCombattimento(List<Entita> nemici) {
        this.nemici = List.copyOf(nemici);
    }

    public List<Entita> getNemici() {return nemici;}

    @Override
    public Optional<Combattimento> esegui(Entita eroe) {
        return Optional.of(new Combattimento(eroe, nemici));
    }
}
