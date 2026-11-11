package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.List;

public class CatalogoPredefinito implements CatalogoStatistiche {

    private static final Azione COLPO = new Attacco("Colpo base", 0, 5);
    private static final Azione FENDENTE = new Attacco("Fendente", 5, 15);
    private static final Azione PALLA_FUOCO = new Attacco("Palla di fuoco", 10, 20);
    private static final Azione MORSO = new Attacco("Morso", 0, 3);
    private static final Azione COLPO_OSSEO = new Attacco("Colpo osseo", 0, 4);
    private static final Azione FIAMMATA = new Attacco("Fiammata", 15, 25);
    private static final Azione CURA = new Cura("Cura", 8, 25);

    private static final List<StatisticheBase> TUTTE = List.of(
            new StatisticheBase("Guerriero", 120, 20, 12, 6, List.of(COLPO, FENDENTE), Categoria.GIOCABILE),
            new StatisticheBase("Mago", 80, 50, 8, 8, List.of(COLPO, PALLA_FUOCO, CURA), Categoria.GIOCABILE),
            new StatisticheBase("Goblin", 30, 0, 5, 7, List.of(MORSO), Categoria.NEMICO_SEMPLICE),
            new StatisticheBase("Scheletro", 40, 0, 6, 5, List.of(COLPO_OSSEO), Categoria.NEMICO_SEMPLICE),
            new StatisticheBase("Drago", 200, 40, 18, 4, List.of(MORSO, FIAMMATA), Categoria.BOSS));

    @Override
    public List<StatisticheBase> getPerCategoria(Categoria categoria) {
        return TUTTE.stream().filter(s -> s.categoria() == categoria).toList();
    }
}
