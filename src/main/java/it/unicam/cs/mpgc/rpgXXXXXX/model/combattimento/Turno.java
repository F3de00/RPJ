package it.unicam.cs.mpgc.rpgXXXXXX.model.combattimento;

import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.Azione;
import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.Risultato;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Entita;

public record Turno(Entita attore, Azione azione, Entita bersaglio, Risultato risultato) {}
