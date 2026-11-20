package it.unicam.cs.mpgc.rpg127378.model.creature;

import java.util.List;

import it.unicam.cs.mpgc.rpg127378.model.azioni.Azione;

public record StatisticheBase(
        String tipo,
        int vitaMassima,
        int manaMassimo,
        int attacco,
        int velocita,
        List<Azione> azioni,
        Categoria categoria) {

    public StatisticheBase {
        azioni = List.copyOf(azioni);
    }
}
