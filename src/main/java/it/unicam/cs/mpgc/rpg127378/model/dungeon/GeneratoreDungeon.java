package it.unicam.cs.mpgc.rpg127378.model.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import it.unicam.cs.mpgc.rpg127378.model.creature.Bonus;
import it.unicam.cs.mpgc.rpg127378.model.creature.CatalogoStatistiche;
import it.unicam.cs.mpgc.rpg127378.model.creature.Categoria;
import it.unicam.cs.mpgc.rpg127378.model.creature.Creatura;
import it.unicam.cs.mpgc.rpg127378.model.creature.Entita;
import it.unicam.cs.mpgc.rpg127378.model.creature.StatisticheBase;

public class GeneratoreDungeon {
    private static final int STANZE_PER_CICLO = 4;
    private static final int NEMICI_PER_STANZA = 2;
    private static final int PERCENTUALE_RIPOSO = 50;
    private static final int AUMENTO_BONUS_PER_CICLO = 20;

    private final CatalogoStatistiche catalogo;
    private final RandomGenerator random;

    public GeneratoreDungeon(CatalogoStatistiche catalogo, RandomGenerator random) {
        this.catalogo = catalogo;
        this.random = random;
    }

    public Stanza generaStanza(int numero) {
        if (numero < 1) {
            throw new IllegalArgumentException("Numero di stanza non valido: " + numero);
        }
        int ciclo = (numero - 1) / STANZE_PER_CICLO;
        int posizioneNelCiclo = (numero - 1) % STANZE_PER_CICLO + 1;
        return switch (posizioneNelCiclo) {
            case 1, 2 -> new Stanza(new EventoCombattimento(creaNemici(Categoria.NEMICO_SEMPLICE, NEMICI_PER_STANZA, ciclo)));
            case 3 -> new Stanza(new EventoRiposo(PERCENTUALE_RIPOSO));
            default -> new Stanza(new EventoCombattimento(creaNemici(Categoria.BOSS, 1, ciclo)));
        };
    }

    public boolean isUltimaDelCiclo(int numero) {return numero % STANZE_PER_CICLO == 0;}

    private List<Entita> creaNemici(Categoria categoria, int quanti, int ciclo) {
        List<StatisticheBase> disponibili = catalogo.getPerCategoria(categoria);
        if (disponibili.isEmpty()) {
            throw new IllegalStateException("Nessun nemico nella categoria " + categoria);
        }
        List<Entita> nemici = new ArrayList<>();
        for (int i = 0; i < quanti; i++) {
            StatisticheBase scelta = disponibili.get(random.nextInt(disponibili.size()));
            Creatura nemico = new Creatura(scelta.tipo(), scelta);
            nemico.aggiungiBonus(bonusDelCiclo(scelta, ciclo));
            nemici.add(nemico);
        }
        return nemici;
    }

    private Bonus bonusDelCiclo(StatisticheBase tipo, int ciclo) {
        int percentuale = AUMENTO_BONUS_PER_CICLO * ciclo;
        return new Bonus(tipo.vitaMassima() * percentuale / 100, 0, tipo.attacco() * percentuale / 100, 0);
    }
}
