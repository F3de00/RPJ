package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.List;

public abstract class EntitaBase implements Entita {
    protected final String nome;
    protected final Statistiche statistiche;
    private final List<Azione> azioni;

    protected EntitaBase(String nome, Statistiche statistiche, List<Azione> azioni) {
        this.nome = nome;
        this.statistiche = statistiche;
        this.azioni = List.copyOf(azioni);   // copia non modificabile
    }

    @Override
    public Risultato compiAzione(Azione azione, Entita bersaglio) {
        if (!azioni.contains(azione)) {
            throw new IllegalArgumentException("Azione non posseduta: " + azione.getNome());
        }
        if (!statistiche.haManaSufficiente(azione.getCostoMana())) {
            return new Risultato(Esito.MANA_INSUFFICIENTE, 0);
        }
        statistiche.riduciMana(azione.getCostoMana());
        return azione.esegui(this, bersaglio);
    }

    @Override public List<Azione> getAzioni() { return azioni; }
    @Override public int getAttacco() { return statistiche.getAttacco(); }
    @Override public int subisceDanno(int quantita) { return statistiche.riduciVita(quantita); }
    @Override public void recuperaVita(int quantita) { statistiche.aumentaVita(quantita); }
    @Override public void recuperaMana(int quantita) { statistiche.aumentaMana(quantita); }
    @Override public boolean isViva() { return statistiche.isViva(); }
}