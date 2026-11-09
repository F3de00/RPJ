package it.unicam.cs.mpgc.rpgXXXXXX.model;

public class Attacco implements Azione {
    private final String nome;
    private final int costoMana;
    private final StrategiaAttacco strategia;

    public Attacco(String nome, int costoMana, StrategiaAttacco strategia) {
        this.nome = nome;
        this.costoMana = costoMana;
        this.strategia = strategia;
    }

    @Override public String getNome() {return nome;}
    @Override public int getCostoMana() {return costoMana;}
    @Override public TipoBersaglio getTipoBersaglio() {return TipoBersaglio.AVVERSARIO;}

    @Override
    public Risultato esegui(Entita attaccante, Entita bersaglio) {
        int dannoCalcolato = strategia.calcolaDanno(attaccante.getAttacco());
        int dannoSubito = bersaglio.subisceDanno(dannoCalcolato);
        EsitoAzione esito = bersaglio.isViva() ? EsitoAzione.COLPITO : EsitoAzione.BERSAGLIO_SCONFITTO;
        return new Risultato(esito, dannoSubito);
    }
}