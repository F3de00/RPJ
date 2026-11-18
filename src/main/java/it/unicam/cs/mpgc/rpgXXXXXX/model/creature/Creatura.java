package it.unicam.cs.mpgc.rpgXXXXXX.model.creature;

import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.Azione;
import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.EsitoAzione;
import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.Risultato;
import java.util.List;

public class Creatura implements Entita {
    private final String nome;
    private final StatisticheBase statisticheBase;
    private Bonus bonusTotale;
    private int vita;
    private int mana;

    public Creatura(String nome, StatisticheBase statisticheBase) {
        this(nome, statisticheBase, Bonus.NESSUNO, statisticheBase.vitaMassima(), statisticheBase.manaMassimo());
    }

    public Creatura(String nome, StatisticheBase statisticheBase, Bonus bonusTotale, int vita, int mana) {
        this.nome = nome;
        this.statisticheBase = statisticheBase;
        this.bonusTotale = bonusTotale;
        if (vita < 1 || vita > getVitaMassima()) {
            throw new IllegalArgumentException("Vita non valida: " + vita);
        }
        if (mana < 0 || mana > getManaMassimo()) {
            throw new IllegalArgumentException("Mana non valido: " + mana);
        }
        this.vita = vita;
        this.mana = mana;
    }

    @Override
    public Risultato compiAzione(Azione azione, Entita bersaglio) {
        if (!getAzioni().contains(azione)) {
            throw new IllegalArgumentException("Azione non posseduta: " + azione.getNome());
        }
        if (mana < azione.getCostoMana()) {
            return new Risultato(EsitoAzione.MANA_INSUFFICIENTE, 0);
        }
        mana -= azione.getCostoMana();
        return azione.esegui(this, bersaglio);
    }

    @Override
    public int subisceDanno(int quantita) {
        verificaNonNegativa(quantita);
        int vitaPrima = vita;
        vita = Math.max(0, vita - quantita);
        return vitaPrima - vita;
    }

    @Override
    public int recuperaVita(int quantita) {
        verificaNonNegativa(quantita);
        int vitaPrima = vita;
        vita = Math.min(getVitaMassima(), vita + quantita);
        return vita - vitaPrima;
    }

    @Override
    public int recuperaMana(int quantita) {
        verificaNonNegativa(quantita);
        int manaPrima = mana;
        mana = Math.min(getManaMassimo(), mana + quantita);
        return mana - manaPrima;
    }

    @Override
    public void aggiungiBonus(Bonus nuovoBonus) {
        bonusTotale = bonusTotale.aggiungi(nuovoBonus);
        vita += nuovoBonus.vita();
        mana += nuovoBonus.mana();
    }

    @Override public List<Azione> getAzioni() {return statisticheBase.azioni();}
    @Override public String getNome() {return nome;}
    @Override public String getTipo() {return statisticheBase.tipo();}
    @Override public int getVita() {return vita;}
    @Override public int getMana() {return mana;}
    @Override public int getVitaMassima() {return statisticheBase.vitaMassima() + bonusTotale.vita();}
    @Override public int getManaMassimo() {return statisticheBase.manaMassimo() + bonusTotale.mana();}
    @Override public int getAttacco() {return statisticheBase.attacco() + bonusTotale.attacco();}
    @Override public int getVelocita() {return statisticheBase.velocita() + bonusTotale.velocita();}
    @Override public boolean isViva() {return vita > 0;}

    public StatisticheBase getStatisticheBase() {return statisticheBase;}
    @Override public Bonus getBonusTotale() {return bonusTotale;}

    private static void verificaNonNegativa(int quantita) {
        if (quantita < 0) {
            throw new IllegalArgumentException("Quantità negativa: " + quantita);
        }
    }
}
