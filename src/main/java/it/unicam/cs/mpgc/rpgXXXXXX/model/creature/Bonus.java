package it.unicam.cs.mpgc.rpgXXXXXX.model.creature;

public record Bonus(int vita, int mana, int attacco, int velocita) {
    public static final Bonus NESSUNO = new Bonus(0, 0, 0, 0);

    public Bonus aggiungi(Bonus nuovoBonus) {
        return new Bonus(vita + nuovoBonus.vita, mana + nuovoBonus.mana, attacco + nuovoBonus.attacco, velocita + nuovoBonus.velocita);
    }
}
