package it.unicam.cs.mpgc.rpgXXXXXX.model;

public interface Entita {
    Risultato attacca(Entita bersaglio);
    int subisceDanno(int quantita);   // restituisce il danno effettivamente subito
    void recuperaVita(int quantita);
    void recuperaMana(int quantita);
    boolean isViva();
}
