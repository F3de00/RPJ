package it.unicam.cs.mpgc.rpgXXXXXX.model;

import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.CatalogoStatistiche;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Categoria;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Creatura;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.StatisticheBase;
import it.unicam.cs.mpgc.rpgXXXXXX.model.dungeon.GeneratoreDungeon;
import java.util.List;

public class FabbricaPartita {
    private final CatalogoStatistiche catalogo;
    private final GeneratoreDungeon generatore;

    public FabbricaPartita(CatalogoStatistiche catalogo, GeneratoreDungeon generatore) {
        this.catalogo = catalogo;
        this.generatore = generatore;
    }

    public List<StatisticheBase> getEroiDisponibili() {return catalogo.getPerCategoria(Categoria.GIOCABILE);}

    public Partita nuovaPartita(StatisticheBase tipoEroe) {
        if (tipoEroe.categoria() != Categoria.GIOCABILE) {
            throw new IllegalArgumentException("Tipo non giocabile: " + tipoEroe.tipo());
        }
        return new Partita(new Creatura(tipoEroe.tipo(), tipoEroe), generatore.genera());
    }
}
