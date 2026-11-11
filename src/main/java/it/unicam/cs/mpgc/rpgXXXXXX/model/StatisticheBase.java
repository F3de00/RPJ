package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.List;

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
