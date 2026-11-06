package it.unicam.cs.mpgc.rpgXXXXXX.model;

// Evita di ripetere in ogni classe la delega a Statistiche.
public abstract class EntitaBase implements Entita {
    protected final String nome;
    protected final Statistiche statistiche;

    protected EntitaBase(String nome, Statistiche statistiche) {
        this.nome = nome;
        this.statistiche = statistiche;
    }

    @Override public void subisceDanno(int quantita) { statistiche.riduciVita(quantita); }
    @Override public void recuperaVita(int quantita) { statistiche.aumentaVita(quantita); }
    @Override public void recuperaMana(int quantita) { statistiche.aumentaMana(quantita); }
    @Override public boolean isViva() { return statistiche.isViva(); }
    
}
