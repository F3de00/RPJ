package it.unicam.cs.mpgc.rpgXXXXXX.model;

import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.Azione;
import it.unicam.cs.mpgc.rpgXXXXXX.model.combattimento.Combattimento;
import it.unicam.cs.mpgc.rpgXXXXXX.model.combattimento.StatoCombattimento;
import it.unicam.cs.mpgc.rpgXXXXXX.model.combattimento.Turno;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Entita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.dungeon.EventoCombattimento;
import it.unicam.cs.mpgc.rpgXXXXXX.model.dungeon.EventoRiposo;
import it.unicam.cs.mpgc.rpgXXXXXX.model.dungeon.Recupero;
import it.unicam.cs.mpgc.rpgXXXXXX.model.dungeon.Stanza;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.CombattimentoIniziato;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.Notifica;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.OsservatorePartita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.PartitaPersa;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.PartitaVinta;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.RiposoEffettuato;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.StanzaCompletata;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.TurnoEseguito;
import java.util.ArrayList;
import java.util.List;

public class Partita {
    private final Entita eroe;
    private final List<Stanza> stanze;
    private final List<OsservatorePartita> osservatori;
    private int stanzeCompletate;
    private StatoPartita stato;
    private Combattimento combattimentoCorrente;

    public Partita(Entita eroe, List<Stanza> stanze) {
        if (stanze.isEmpty()) {
            throw new IllegalArgumentException("Serve almeno una stanza");
        }
        this.eroe = eroe;
        this.stanze = List.copyOf(stanze);
        this.osservatori = new ArrayList<>();
        this.stanzeCompletate = 0;
        this.stato = StatoPartita.ESPLORAZIONE;
    }

    public Entita getEroe() {return eroe;}
    public StatoPartita getStato() {return stato;}
    public int getStanzeCompletate() {return stanzeCompletate;}
    public int getNumeroStanze() {return stanze.size();}

    public Combattimento getCombattimentoCorrente() {
        verificaStato(StatoPartita.IN_COMBATTIMENTO);
        return combattimentoCorrente;
    }

    public boolean isTurnoNemico() {
        return stato == StatoPartita.IN_COMBATTIMENTO && !combattimentoCorrente.isTurnoGiocatore();
    }

    public void aggiungiOsservatore(OsservatorePartita osservatore) {osservatori.add(osservatore);}
    public void rimuoviOsservatore(OsservatorePartita osservatore) {osservatori.remove(osservatore);}

    public void entraNellaProssimaStanza() {
        verificaStato(StatoPartita.ESPLORAZIONE);
        Stanza stanza = stanze.get(stanzeCompletate);
        switch (stanza.getEvento()) {
            case EventoCombattimento combattimento -> avviaCombattimento(combattimento.creaCombattimento(eroe));
            case EventoRiposo riposo -> {
                Recupero recupero = riposo.applica(eroe);
                avvisaOsservatori(new RiposoEffettuato(recupero.vita(), recupero.mana()));
                completaStanza();
            }
        }
    }

    public void eseguiTurnoGiocatore(Azione azione, Entita bersaglio) {
        verificaStato(StatoPartita.IN_COMBATTIMENTO);
        Turno turno = combattimentoCorrente.eseguiTurnoGiocatore(azione, bersaglio);
        avvisaOsservatori(new TurnoEseguito(turno));
        aggiornaDopoTurno();
    }

    public void eseguiTurnoNemico() {
        verificaStato(StatoPartita.IN_COMBATTIMENTO);
        Turno turno = combattimentoCorrente.eseguiTurnoNemico();
        avvisaOsservatori(new TurnoEseguito(turno));
        aggiornaDopoTurno();
    }

    private void avviaCombattimento(Combattimento combattimento) {
        combattimentoCorrente = combattimento;
        stato = StatoPartita.IN_COMBATTIMENTO;
        avvisaOsservatori(new CombattimentoIniziato(combattimento));
    }

    private void aggiornaDopoTurno() {
        if (combattimentoCorrente.getStato() == StatoCombattimento.VITTORIA) {
            combattimentoCorrente = null;
            stato = StatoPartita.ESPLORAZIONE;
            completaStanza();
        } else if (combattimentoCorrente.getStato() == StatoCombattimento.SCONFITTA) {
            combattimentoCorrente = null;
            stato = StatoPartita.GAME_OVER;
            avvisaOsservatori(new PartitaPersa());
        }
    }

    private void completaStanza() {
        stanzeCompletate++;
        if (stanzeCompletate == stanze.size()) {
            stato = StatoPartita.VITTORIA;
        }
        avvisaOsservatori(new StanzaCompletata(stanzeCompletate));
        if (stato == StatoPartita.VITTORIA) {
            avvisaOsservatori(new PartitaVinta());
        }
    }

    private void avvisaOsservatori(Notifica notifica) {
        for (OsservatorePartita osservatore : List.copyOf(osservatori)) {
            osservatore.riceviNotifica(notifica);
        }
    }

    private void verificaStato(StatoPartita atteso) {
        if (stato != atteso) {
            throw new IllegalStateException("Operazione non valida nello stato " + stato);
        }
    }
}
