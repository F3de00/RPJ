package it.unicam.cs.mpgc.rpgXXXXXX.model.azioni;

import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Entita;

public class Attacco implements Azione {
    private final String nome;
    private final int costoMana;
    private final int dannoBase;

    public Attacco(String nome, int costoMana, int dannoBase) {
        this.nome = nome;
        this.costoMana = costoMana;
        this.dannoBase = dannoBase;
    }

    @Override public String getNome() {return nome;}
    @Override public int getCostoMana() {return costoMana;}
    @Override public TipoBersaglio getTipoBersaglio() {return TipoBersaglio.AVVERSARIO;}

    public int getDannoBase() {return dannoBase;}

    @Override
    public int dannoPrevisto(Entita attore) {
        return dannoBase + attore.getAttacco();
    }

    @Override
    public Risultato esegui(Entita attore, Entita bersaglio) {
        int dannoSubito = bersaglio.subisceDanno(dannoPrevisto(attore));
        EsitoAzione esito = bersaglio.isViva() ? EsitoAzione.COLPITO : EsitoAzione.BERSAGLIO_SCONFITTO;
        return new Risultato(esito, dannoSubito);
    }
}
