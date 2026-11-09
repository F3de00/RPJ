package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.List;

/**
 * Statistiche base di un tipo di creatura (Mago, Guerriero, Goblin, Drago...):
 * contiene solo i valori di partenza e le azioni disponibili. Non cambia mai.
 */
public record StatisticheBase(
        String tipo,
        int vitaMassima,
        int manaMassimo,
        int attacco,
        List<Azione> azioni,
        boolean giocabile) {

    public StatisticheBase {
        azioni = List.copyOf(azioni);
    }
}
