package it.unicam.cs.mpgc.rpgXXXXXX.model;

public interface Entita {
    Risultato attacca(Entita bersaglio);
    void subisceDanno(int quantita);
    void recuperaVita(int quantita);
    void recuperaMana(int quantita);
    boolean isViva();            
}
