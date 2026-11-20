package it.unicam.cs.mpgc.rpg127378.model;

import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.mpgc.rpg127378.model.azioni.Azione;
import it.unicam.cs.mpgc.rpg127378.model.combattimento.Combattimento;
import it.unicam.cs.mpgc.rpg127378.model.combattimento.StatoCombattimento;
import it.unicam.cs.mpgc.rpg127378.model.combattimento.Turno;
import it.unicam.cs.mpgc.rpg127378.model.creature.Entita;
import it.unicam.cs.mpgc.rpg127378.model.dungeon.EventoCombattimento;
import it.unicam.cs.mpgc.rpg127378.model.dungeon.EventoRiposo;
import it.unicam.cs.mpgc.rpg127378.model.dungeon.GeneratoreDungeon;
import it.unicam.cs.mpgc.rpg127378.model.dungeon.Recupero;
import it.unicam.cs.mpgc.rpg127378.model.dungeon.Stanza;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.CombattimentoIniziato;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.Notifica;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.OsservatorePartita;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.PartitaPersa;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.RiposoEffettuato;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.StanzaCompletata;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.TurnoEseguito;
import it.unicam.cs.mpgc.rpg127378.model.salvataggi.PartitaSalvata;

public class Partita {
    private static final int PERCENTUALE_RECUPERO_POTENZIAMENTO = 50;

    private final Entita eroe;
    private final GeneratoreDungeon generatore;
    private final List<OsservatorePartita> osservatori;
    private int stanzeCompletate;
    private StatoPartita stato;
    private Combattimento combattimentoCorrente;

    public Partita(Entita eroe, GeneratoreDungeon generatore, int stanzeCompletate) {
        if (stanzeCompletate < 0) {
            throw new IllegalArgumentException("Stanze completate non valide: " + stanzeCompletate);
        }
        this.eroe = eroe;
        this.generatore = generatore;
        this.osservatori = new ArrayList<>();
        this.stanzeCompletate = stanzeCompletate;
        this.stato = StatoPartita.ESPLORAZIONE;
    }

    public Entita getEroe() {return eroe;}
    public StatoPartita getStato() {return stato;}
    public int getStanzeCompletate() {return stanzeCompletate;}

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
        int numeroStanza = stanzeCompletate + 1;
        Stanza stanza = generatore.generaStanza(numeroStanza);
        switch (stanza.getEvento()) {
            case EventoCombattimento combattimento -> avviaCombattimento(combattimento.creaCombattimento(eroe));
            case EventoRiposo riposo -> {
                Recupero recupero = riposo.applica(eroe);
                avvisaOsservatori(new RiposoEffettuato(numeroStanza, recupero.vita(), recupero.mana()));
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

    public void scegliPotenziamento(Potenziamento scelta) {
        verificaStato(StatoPartita.POTENZIAMENTO);
        eroe.aggiungiBonus(scelta.getBonus());
        eroe.recuperaVita(eroe.getVitaMassima() * PERCENTUALE_RECUPERO_POTENZIAMENTO / 100);
        eroe.recuperaMana(eroe.getManaMassimo() * PERCENTUALE_RECUPERO_POTENZIAMENTO / 100);
        stato = StatoPartita.ESPLORAZIONE;
    }

    public PartitaSalvata creaSalvataggio() {
        verificaStato(StatoPartita.ESPLORAZIONE);
        return new PartitaSalvata(eroe.getTipo(), eroe.getVita(), eroe.getMana(), eroe.getBonusTotale(), stanzeCompletate);
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
        if (generatore.isUltimaDelCiclo(stanzeCompletate)) {
            stato = StatoPartita.POTENZIAMENTO;
        }
        avvisaOsservatori(new StanzaCompletata(stanzeCompletate));
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
