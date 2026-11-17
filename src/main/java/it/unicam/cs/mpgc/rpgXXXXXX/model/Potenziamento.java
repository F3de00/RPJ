package it.unicam.cs.mpgc.rpgXXXXXX.model;

import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Bonus;

public enum Potenziamento {
    VITA("+20 vita massima", new Bonus(20, 0, 0, 0)),
    MANA("+10 mana massimo", new Bonus(0, 10, 0, 0)),
    ATTACCO("+3 attacco", new Bonus(0, 0, 3, 0)),
    VELOCITA("+1 velocità", new Bonus(0, 0, 0, 1));

    private final String descrizione;
    private final Bonus bonus;

    Potenziamento(String descrizione, Bonus bonus) {
        this.descrizione = descrizione;
        this.bonus = bonus;
    }

    public String getDescrizione() {return descrizione;}
    public Bonus getBonus() {return bonus;}
}
