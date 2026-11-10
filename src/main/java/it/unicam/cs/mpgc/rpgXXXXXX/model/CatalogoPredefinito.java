package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.List;

/** Statistiche base e azioni di ogni tipo di creatura, scritte nel codice. */
public class CatalogoPredefinito implements CatalogoStatistiche {

    // Azioni disponibili nel gioco
    private static final Azione COLPO = new Attacco("Colpo base", 0, 5);
    private static final Azione FENDENTE = new Attacco("Fendente", 5, 15);
    private static final Azione PALLA_FUOCO = new Attacco("Palla di fuoco", 10, 20);
    private static final Azione MORSO = new Attacco("Morso", 0, 3);
    private static final Azione FIAMMATA = new Attacco("Fiammata", 15, 25);
    private static final Azione CURA = new Cura("Cura", 8, 25);

    // Personaggi giocabili
    private static final StatisticheBase GUERRIERO =
            new StatisticheBase("Guerriero", 120, 20, 12, 6, List.of(COLPO, FENDENTE), true);
    private static final StatisticheBase MAGO =
            new StatisticheBase("Mago", 80, 50, 8, 8, List.of(COLPO, PALLA_FUOCO, CURA), true);

    // Nemici
    private static final StatisticheBase GOBLIN =
            new StatisticheBase("Goblin", 30, 0, 5, 7, List.of(MORSO), false);
    private static final StatisticheBase DRAGO =
            new StatisticheBase("Drago", 200, 40, 18, 4, List.of(MORSO, FIAMMATA), false);

    @Override
    public List<StatisticheBase> getGiocabili() {
        return List.of(GUERRIERO, MAGO);
    }

    @Override
    public List<StatisticheBase> getNemici() {
        return List.of(GOBLIN, DRAGO);
    }
}
