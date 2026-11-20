package it.unicam.cs.mpgc.rpg127378.model.dungeon;

import java.util.List;

import it.unicam.cs.mpgc.rpg127378.model.combattimento.Combattimento;
import it.unicam.cs.mpgc.rpg127378.model.creature.Entita;

public final class EventoCombattimento implements EventoStanza {
    private final List<Entita> nemici;

    public EventoCombattimento(List<Entita> nemici) {
        this.nemici = List.copyOf(nemici);
    }

    public List<Entita> getNemici() {return nemici;}

    public Combattimento creaCombattimento(Entita eroe) {return new Combattimento(eroe, nemici);}
}
