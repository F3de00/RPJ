package it.unicam.cs.mpgc.rpg127378.model.combattimento;

import it.unicam.cs.mpgc.rpg127378.model.azioni.Azione;
import it.unicam.cs.mpgc.rpg127378.model.azioni.Risultato;
import it.unicam.cs.mpgc.rpg127378.model.creature.Entita;

public record Turno(Entita attore, Azione azione, Entita bersaglio, Risultato risultato) {}
