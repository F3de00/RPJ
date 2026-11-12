package it.unicam.cs.mpgc.rpgXXXXXX.model.azioni;

import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Entita;

public class Cura implements Azione {
    private final String nome;
    private final int costoMana;
    private final int quantita;

    public Cura(String nome, int costoMana, int quantita) {
        this.nome = nome;
        this.costoMana = costoMana;
        this.quantita = quantita;
    }

    @Override public String getNome() {return nome;}
    @Override public int getCostoMana() {return costoMana;}
    @Override public TipoBersaglio getTipoBersaglio() {return TipoBersaglio.SE_STESSO;}

    @Override
    public Risultato esegui(Entita attore, Entita bersaglio) {
        int vitaRecuperata = attore.recuperaVita(quantita);
        return new Risultato(EsitoAzione.CURATO, vitaRecuperata);
    }
}
