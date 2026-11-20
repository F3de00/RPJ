package it.unicam.cs.mpgc.rpg127378.model.creature;

import java.util.List;

import it.unicam.cs.mpgc.rpg127378.model.azioni.Azione;
import it.unicam.cs.mpgc.rpg127378.model.azioni.Risultato;

public interface Entita {
    Risultato compiAzione(Azione azione, Entita bersaglio);
    List<Azione> getAzioni();
    String getNome();
    String getTipo();
    int getVita();
    int getMana();
    int getVitaMassima();
    int getManaMassimo();
    int getAttacco();
    int getVelocita();
    int subisceDanno(int quantita);
    int recuperaVita(int quantita);
    int recuperaMana(int quantita);
    boolean isViva();
    void aggiungiBonus(Bonus nuovoBonus);
    Bonus getBonusTotale();
}
