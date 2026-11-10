package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.List;

public interface Entita {
    Risultato compiAzione(Azione azione, Entita bersaglio);
    List<Azione> getAzioni();
    String getNome();
    int getVita();
    int getMana();
    int getAttacco();
    int getVelocita();
    int subisceDanno(int quantita); // restituisce il danno effettivamente subito
    int recuperaVita(int quantita); // restituisce la vita effettivamente recuperata
    int recuperaMana(int quantita); // restituisce il mana effettivamente recuperato
    boolean isViva();
}
