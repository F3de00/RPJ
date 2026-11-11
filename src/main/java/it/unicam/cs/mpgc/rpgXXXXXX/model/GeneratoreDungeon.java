package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class GeneratoreDungeon {
    private static final int NEMICI_PER_STANZA = 2;
    private static final int PERCENTUALE_RIPOSO = 50;

    private final CatalogoStatistiche catalogo;
    private final RandomGenerator random;

    public GeneratoreDungeon(CatalogoStatistiche catalogo, RandomGenerator random) {
        this.catalogo = catalogo;
        this.random = random;
    }

    public List<Stanza> genera() {
        List<Stanza> stanze = new ArrayList<>();
        stanze.add(new Stanza(new EventoCombattimento(creaNemici(Categoria.NEMICO_SEMPLICE, NEMICI_PER_STANZA))));
        stanze.add(new Stanza(new EventoCombattimento(creaNemici(Categoria.NEMICO_SEMPLICE, NEMICI_PER_STANZA))));
        stanze.add(new Stanza(new EventoRiposo(PERCENTUALE_RIPOSO)));
        stanze.add(new Stanza(new EventoCombattimento(creaNemici(Categoria.BOSS, 1))));
        return List.copyOf(stanze);
    }

    private List<Entita> creaNemici(Categoria categoria, int quanti) {
        List<StatisticheBase> disponibili = catalogo.getPerCategoria(categoria);
        if (disponibili.isEmpty()) {
            throw new IllegalStateException("Nessun nemico nella categoria " + categoria);
        }
        List<StatisticheBase> scelte = new ArrayList<>();
        for (int i = 0; i < quanti; i++) {
            scelte.add(disponibili.get(random.nextInt(disponibili.size())));
        }
        List<Entita> nemici = new ArrayList<>();
        for (StatisticheBase scelta : scelte) {
            nemici.add(new Creatura(nomeNemico(scelta, scelte, nemici), scelta));
        }
        return nemici;
    }

    private String nomeNemico(StatisticheBase scelta, List<StatisticheBase> scelte, List<Entita> giaCreati) {
        long stessoTipo = scelte.stream().filter(s -> s == scelta).count();
        if (stessoTipo == 1) {
            return scelta.tipo();
        }
        long giaNumerati = giaCreati.stream().filter(e -> e.getNome().startsWith(scelta.tipo() + " ")).count();
        return scelta.tipo() + " " + (giaNumerati + 1);
    }
}
