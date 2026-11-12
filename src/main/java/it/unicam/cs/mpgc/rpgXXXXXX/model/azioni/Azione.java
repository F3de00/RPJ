package it.unicam.cs.mpgc.rpgXXXXXX.model.azioni;

import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Entita;

public interface Azione {
    String getNome();
    int getCostoMana();
    TipoBersaglio getTipoBersaglio();
    Risultato esegui(Entita attore, Entita bersaglio);

    default int dannoPrevisto(Entita attore) {return 0;}
}
