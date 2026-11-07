package it.unicam.cs.mpgc.rpgXXXXXX.model;

// Evita di ripetere in ogni classe la delega a Statistiche.
public abstract class EntitaBase implements Entita {
    protected final String nome;
    protected final Statistiche statistiche;
    private final StrategiaAttacco strategiaAttacco;

    protected EntitaBase(String nome, Statistiche statistiche, StrategiaAttacco strategiaAttacco) {
        this.nome = nome;
        this.statistiche = statistiche;
        this.strategiaAttacco = strategiaAttacco;
    }

    @Override
    public int subisceDanno(int quantita) {
        return statistiche.riduciVita(quantita);
    }

    @Override
    public Risultato attacca(Entita bersaglio) {
        int dannoCalcolato = strategiaAttacco.calcolaDanno(statistiche.getAttacco());
        int dannoSubito = bersaglio.subisceDanno(dannoCalcolato);
        Esito esito = bersaglio.isViva() ? Esito.COLPITO : Esito.BERSAGLIO_SCONFITTO;
        return new Risultato(esito, dannoSubito);
    }

    @Override public void recuperaVita(int quantita) { statistiche.aumentaVita(quantita); }
    @Override public void recuperaMana(int quantita) { statistiche.aumentaMana(quantita); }
    @Override public boolean isViva() { return statistiche.isViva(); }
    
}
