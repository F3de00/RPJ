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
    public boolean haManaSufficiente(int costo) { return mana >= costo; }

    //Restituisce il danno realmente assorbito.
    public int riduciVita(int quantita) {
        verificaNonNegativa(quantita);
        int vitaPrima = vita;
        vita = Math.max(0, vita - quantita);
        return vitaPrima - vita;
    }

    public void aumentaVita(int quantita) {
        verificaNonNegativa(quantita);
        vita = Math.min(vitaMassima, vita + quantita);
    }

    public void aumentaMana(int quantita) {
        verificaNonNegativa(quantita);
        mana = Math.min(manaMassimo, mana + quantita);
    }


    public void riduciMana(int quantita) {
        verificaNonNegativa(quantita);
        if (quantita > mana) {
            throw new IllegalStateException("Mana insufficiente");
        }
        mana -= quantita;
    }

    private static void verificaNonNegativa(int quantita) {
        if (quantita < 0) {
            throw new IllegalArgumentException("Quantità negativa: " + quantita);
        }
    }
}