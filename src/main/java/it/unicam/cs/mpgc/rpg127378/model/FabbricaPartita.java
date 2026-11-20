package it.unicam.cs.mpgc.rpg127378.model;

import java.util.List;

import it.unicam.cs.mpgc.rpg127378.model.creature.CatalogoStatistiche;
import it.unicam.cs.mpgc.rpg127378.model.creature.Categoria;
import it.unicam.cs.mpgc.rpg127378.model.creature.Creatura;
import it.unicam.cs.mpgc.rpg127378.model.creature.StatisticheBase;
import it.unicam.cs.mpgc.rpg127378.model.dungeon.GeneratoreDungeon;
import it.unicam.cs.mpgc.rpg127378.model.salvataggi.PartitaSalvata;

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
        return new Partita(new Creatura(tipoEroe.tipo(), tipoEroe), generatore, 0);
    }

    public Partita riprendiPartita(PartitaSalvata partitaSalvata) {
        StatisticheBase tipoEroe = getEroiDisponibili().stream()
                .filter(tipo -> tipo.tipo().equals(partitaSalvata.tipoEroe()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo di eroe sconosciuto: " + partitaSalvata.tipoEroe()));
        Creatura eroe = new Creatura(tipoEroe.tipo(), tipoEroe, partitaSalvata.bonusTotale(), partitaSalvata.vita(), partitaSalvata.mana());
        return new Partita(eroe, generatore, partitaSalvata.stanzeCompletate());
    }
}
