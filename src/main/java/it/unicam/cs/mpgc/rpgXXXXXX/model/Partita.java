package it.unicam.cs.mpgc.rpgXXXXXX.model;

import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.Azione;
import it.unicam.cs.mpgc.rpgXXXXXX.model.combattimento.Combattimento;
import it.unicam.cs.mpgc.rpgXXXXXX.model.combattimento.StatoCombattimento;
import it.unicam.cs.mpgc.rpgXXXXXX.model.combattimento.Turno;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Entita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.dungeon.EventoCombattimento;
import it.unicam.cs.mpgc.rpgXXXXXX.model.dungeon.EventoRiposo;
import it.unicam.cs.mpgc.rpgXXXXXX.model.dungeon.Stanza;
import java.util.List;

public class Partita {
    private final Entita eroe;
    private final List<Stanza> stanze;
    private int stanzeCompletate;
    private StatoPartita stato;
    private Combattimento combattimentoCorrente;

    public Partita(Entita eroe, List<Stanza> stanze) {
        if (stanze.isEmpty()) {
            throw new IllegalArgumentException("Serve almeno una stanza");
        }
        this.eroe = eroe;
        this.stanze = List.copyOf(stanze);
        this.stanzeCompletate = 0;
        this.stato = StatoPartita.ESPLORAZIONE;
    }

    public Entita getEroe() {return eroe;}
    public StatoPartita getStato() {return stato;}
    public int getStanzeCompletate() {return stanzeCompletate;}
    public int getNumeroStanze() {return stanze.size();}

    public Combattimento getCombattimento() {
        verificaStato(StatoPartita.IN_COMBATTIMENTO);
        return combattimentoCorrente;
    }

    public void entraNellaProssimaStanza() {
        verificaStato(StatoPartita.ESPLORAZIONE);
        Stanza stanza = stanze.get(stanzeCompletate);
        switch (stanza.getEvento()) {
            case EventoCombattimento combattimento -> avviaCombattimento(combattimento.creaCombattimento(eroe));
            case EventoRiposo riposo -> {
                riposo.applica(eroe);
                completaStanza();
            }
        }
    }

    public Turno eseguiTurnoGiocatore(Azione azione, Entita bersaglio) {
        verificaStato(StatoPartita.IN_COMBATTIMENTO);
        Turno turno = combattimentoCorrente.eseguiTurnoGiocatore(azione, bersaglio);
        aggiornaDopoTurno();
        return turno;
    }

    public Turno eseguiTurnoNemico() {
        verificaStato(StatoPartita.IN_COMBATTIMENTO);
        Turno turno = combattimentoCorrente.eseguiTurnoNemico();
        aggiornaDopoTurno();
        return turno;
    }

    private void avviaCombattimento(Combattimento combattimento) {
        combattimentoCorrente = combattimento;
        stato = StatoPartita.IN_COMBATTIMENTO;
    }

    private void aggiornaDopoTurno() {
        if (combattimentoCorrente.getStato() == StatoCombattimento.VITTORIA) {
            combattimentoCorrente = null;
            stato = StatoPartita.ESPLORAZIONE;
            completaStanza();
        } else if (combattimentoCorrente.getStato() == StatoCombattimento.SCONFITTA) {
            combattimentoCorrente = null;
            stato = StatoPartita.GAME_OVER;
        }
    }

    private void completaStanza() {
        stanzeCompletate++;
        if (stanzeCompletate == stanze.size()) {
            stato = StatoPartita.VITTORIA;
        }
    }

    private void verificaStato(StatoPartita atteso) {
        if (stato != atteso) {
            throw new IllegalStateException("Operazione non valida nello stato " + stato);
        }
    }
}
