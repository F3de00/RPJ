package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.List;

/**
 * Unica classe concreta di entità: rappresenta sia l'eroe sia i nemici.
 * Le differenze tra Mago, Goblin, Drago... arrivano dalle sue StatisticheBase.
 */
public class Creatura implements Entita {
    private final String nome;
    private final StatisticheBase statisticheBase;
    private int vita;
    private int mana;

    public Creatura(String nome, StatisticheBase statisticheBase) {
        this.nome = nome;
        this.statisticheBase = statisticheBase;
        this.vita = statisticheBase.vitaMassima();
        this.mana = statisticheBase.manaMassimo();
    }

    @Override
    public Risultato compiAzione(Azione azione, Entita bersaglio) {
        if (!getAzioni().contains(azione)) {
            throw new IllegalArgumentException("Azione non posseduta: " + azione.getNome());
        }
        if (mana < azione.getCostoMana()) {
            return new Risultato(Esito.MANA_INSUFFICIENTE, 0);
        }
        mana -= azione.getCostoMana();
        return azione.esegui(this, bersaglio);
    }

    @Override
    public int subisceDanno(int quantita) {
        verificaNonNegativa(quantita);
        int vitaPrima = vita;
        vita = Math.max(0, vita - quantita);
        return vitaPrima - vita;
    }

    @Override
    public void recuperaVita(int quantita) {
        verificaNonNegativa(quantita);
        vita = Math.min(statisticheBase.vitaMassima(), vita + quantita);
    }

    @Override
    public void recuperaMana(int quantita) {
        verificaNonNegativa(quantita);
        mana = Math.min(statisticheBase.manaMassimo(), mana + quantita);
    }

    @Override public List<Azione> getAzioni() {return statisticheBase.azioni();}
    @Override public String getNome() {return nome;}
    @Override public int getVita() {return vita;}
    @Override public int getMana() {return mana;}
    @Override public int getAttacco() {return statisticheBase.attacco();}
    @Override public boolean isViva() {return vita > 0;}

    public StatisticheBase getStatisticheBase() {return statisticheBase;}

    private static void verificaNonNegativa(int quantita) {
        if (quantita < 0) {
            throw new IllegalArgumentException("Quantità negativa: " + quantita);
        }
    }
}
