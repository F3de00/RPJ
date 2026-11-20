package it.unicam.cs.mpgc.rpg127378.model.creature;

import java.util.List;

public interface CatalogoStatistiche {
    List<StatisticheBase> getPerCategoria(Categoria categoria);
}
