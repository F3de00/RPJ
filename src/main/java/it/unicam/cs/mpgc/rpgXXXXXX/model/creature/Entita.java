package it.unicam.cs.mpgc.rpgXXXXXX.model.creature;

import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.Azione;
import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.Risultato;
import java.util.List;

public interface Entita {
    Risultato compiAzione(Azione azione, Entita bersaglio);
    List<Azione> getAzioni();
    String getNome();
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
}
