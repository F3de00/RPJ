package it.unicam.cs.mpgc.rpgXXXXXX.model;

public interface Azione {
    String getNome();
    int getCostoMana();
    TipoBersaglio getTipoBersaglio();
    Risultato esegui(Entita attore, Entita bersaglio);

    /** Danno che l'azione infliggerebbe se eseguita da attore. Le azioni che non fanno danno restituiscono 0. */
    default int dannoPrevisto(Entita attore) {return 0;}
}
