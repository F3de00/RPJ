package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.List;

public interface Entita {
    Risultato compiAzione(Azione azione, Entita bersaglio);
    List<Azione> getAzioni();
    String getNome();
    int getVita();
    int getMana();
    int getAttacco();
    int subisceDanno(int quantita);
    int recuperaVita(int quantita);
    int recuperaMana(int quantita);
    boolean isViva();
}
