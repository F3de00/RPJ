package it.unicam.cs.mpgc.rpgXXXXXX.model.dungeon;

import it.unicam.cs.mpgc.rpgXXXXXX.model.combattimento.Combattimento;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Entita;
import java.util.List;

public final class EventoCombattimento implements EventoStanza {
    private final List<Entita> nemici;

    public EventoCombattimento(List<Entita> nemici) {
        this.nemici = List.copyOf(nemici);
    }

    public List<Entita> getNemici() {return nemici;}

    public Combattimento creaCombattimento(Entita eroe) {return new Combattimento(eroe, nemici);}
}
