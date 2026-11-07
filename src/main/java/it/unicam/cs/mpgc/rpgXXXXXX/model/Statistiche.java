package it.unicam.cs.mpgc.rpgXXXXXX.model;

public class Statistiche {
    private int vita;
    private final int vitaMassima;
    private int mana;
    private final int manaMassimo;
    private final int attacco;

    public Statistiche(int vitaMassima, int manaMassimo, int attacco) {
        this.vitaMassima = vitaMassima;
        this.manaMassimo = manaMassimo;
        this.attacco = attacco;
        this.vita = vitaMassima;
        this.mana = manaMassimo;
    }

    public int getVita()    { return vita; }
    public int getMana()    { return mana; }
    public int getAttacco() { return attacco; }
    public boolean isViva() { return vita > 0; }
    public int riduciVita(int quantita) {
        int vitaPrima = vita;
        vita = Math.max(0, vita - quantita);
        return vitaPrima - vita;           // danno realmente assorbito
    }
    public void aumentaVita(int quantita) { /* ... */ }
    public void aumentaMana(int quantita) { /* ... */ }
}
