package it.unicam.cs.mpgc.rpgXXXXXX.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Combattimento {
    private final Entita eroe;
    private final List<Entita> nemici;
    private final List<Entita> ordine;
    private int indiceTurno;
    private StatoCombattimento stato;

    public Combattimento(Entita eroe, List<Entita> nemici) {
        if (nemici.isEmpty()) {
            throw new IllegalArgumentException("Serve almeno un nemico");
        }
        this.eroe = eroe;
        this.nemici = List.copyOf(nemici);
        this.ordine = calcolaOrdine();
        this.indiceTurno = 0;
        this.stato = StatoCombattimento.IN_CORSO;
    }

    public StatoCombattimento getStato() {return stato;}
    public Entita getEroe() {return eroe;}
    public List<Entita> getNemici() {return nemici;}
    public Entita getCreaturaDiTurno() {return ordine.get(indiceTurno);}

    public boolean isTurnoGiocatore() {
        return stato == StatoCombattimento.IN_CORSO && getCreaturaDiTurno() == eroe;
    }

    public List<Entita> getNemiciVivi() {
        return nemici.stream().filter(Entita::isViva).toList();
    }

    public Turno eseguiTurnoGiocatore(Azione azione, Entita bersaglio) {
        if (!isTurnoGiocatore()) {
            throw new IllegalStateException("Non è il turno del giocatore");
        }
        Entita bersaglioEffettivo = azione.getTipoBersaglio() == TipoBersaglio.SE_STESSO ? eroe : bersaglio;
        if (azione.getTipoBersaglio() == TipoBersaglio.AVVERSARIO && !getNemiciVivi().contains(bersaglioEffettivo)) {
            throw new IllegalArgumentException("Il bersaglio deve essere un nemico vivo");
        }
        return agisci(eroe, azione, bersaglioEffettivo);
    }

    public Turno eseguiTurnoNemico() {
        if (stato != StatoCombattimento.IN_CORSO || isTurnoGiocatore()) {
            throw new IllegalStateException("Non è il turno di un nemico");
        }
        Entita nemico = getCreaturaDiTurno();
        Azione azione = scegliAzioneNemico(nemico);
        Entita bersaglio = azione.getTipoBersaglio() == TipoBersaglio.SE_STESSO ? nemico : eroe;
        return agisci(nemico, azione, bersaglio);
    }

    private Turno agisci(Entita attore, Azione azione, Entita bersaglio) {
        Risultato risultato = attore.compiAzione(azione, bersaglio);
        aggiornaStato();
        if (stato == StatoCombattimento.IN_CORSO) {
            passaAlProssimo();
        }
        return new Turno(attore, azione, bersaglio, risultato);
    }

    private List<Entita> calcolaOrdine() {
        List<Entita> partecipanti = new ArrayList<>();
        partecipanti.add(eroe);
        partecipanti.addAll(nemici);
        partecipanti.sort(Comparator.comparingInt(Entita::getVelocita).reversed());
        return List.copyOf(partecipanti);
    }

    private void passaAlProssimo() {
        do {
            indiceTurno = (indiceTurno + 1) % ordine.size();
        } while (!ordine.get(indiceTurno).isViva());
    }

    private void aggiornaStato() {
        if (!eroe.isViva()) {
            stato = StatoCombattimento.SCONFITTA;
        } else if (getNemiciVivi().isEmpty()) {
            stato = StatoCombattimento.VITTORIA;
        }
    }

    private Azione scegliAzioneNemico(Entita nemico) {
        return nemico.getAzioni().stream()
                .filter(a -> a.getCostoMana() <= nemico.getMana())
                .max(Comparator.comparingInt(a -> a.dannoPrevisto(nemico)))
                .orElseThrow(() -> new IllegalStateException(nemico.getNome() + " non ha azioni utilizzabili"));
    }
}
