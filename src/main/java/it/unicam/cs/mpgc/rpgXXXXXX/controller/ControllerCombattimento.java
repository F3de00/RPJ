package it.unicam.cs.mpgc.rpgXXXXXX.controller;

import it.unicam.cs.mpgc.rpgXXXXXX.model.Partita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.StatoPartita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.Azione;
import it.unicam.cs.mpgc.rpgXXXXXX.model.combattimento.Combattimento;
import it.unicam.cs.mpgc.rpgXXXXXX.model.combattimento.Turno;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Entita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.CombattimentoIniziato;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.Notifica;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.OsservatorePartita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.PartitaPersa;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.RiposoEffettuato;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.StanzaCompletata;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.TurnoEseguito;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class ControllerCombattimento implements OsservatorePartita {
    private static final Duration PAUSA_TRA_TURNI = Duration.seconds(1);

    private final Partita partita;
    private final Navigatore navigatore;
    private final Combattimento combattimento;
    private Entita bersaglio;

    @FXML
    private HBox contenitoreNemici;
    @FXML
    private Label etichettaEroe;
    @FXML
    private HBox contenitoreAzioni;
    @FXML
    private TextArea registro;
    @FXML
    private Label etichettaEsito;
    @FXML
    private Button pulsanteContinua;

    public ControllerCombattimento(Partita partita, Navigatore navigatore) {
        this.partita = partita;
        this.navigatore = navigatore;
        this.combattimento = partita.getCombattimentoCorrente();
        this.bersaglio = primoNemicoVivo();
    }

    @FXML
    private void initialize() {
        aggiorna();
        avviaTurniNemici();
    }

    @FXML
    private void continua() {
        if (partita.getStato() == StatoPartita.ESPLORAZIONE) {
            navigatore.mostraEsplorazione();
        } else {
            navigatore.mostraFine();
        }
    }

    @Override
    public void riceviNotifica(Notifica notifica) {
        switch (notifica) {
            case TurnoEseguito turnoEseguito -> {
                registro.appendText(descrizione(turnoEseguito.turno()) + "\n");
                if (!bersaglio.isViva()) {
                    bersaglio = primoNemicoVivo();
                }
                aggiorna();
            }
            case StanzaCompletata stanzaCompletata -> mostraEsito("Vittoria!");
            case PartitaPersa partitaPersa -> mostraEsito("Sconfitta...");
            case CombattimentoIniziato combattimentoIniziato -> {}
            case RiposoEffettuato riposoEffettuato -> {}
        }
    }

    private void seleziona(Entita nemico) {
        bersaglio = nemico;
        aggiornaNemici();
    }

    private void agisci(Azione azione) {
        partita.eseguiTurnoGiocatore(azione, bersaglio);
        avviaTurniNemici();
    }

    private void avviaTurniNemici() {
        if (partita.isTurnoNemico()) {
            PauseTransition pausa = new PauseTransition(PAUSA_TRA_TURNI);
            pausa.setOnFinished(evento -> {
                partita.eseguiTurnoNemico();
                avviaTurniNemici();
            });
            pausa.play();
        }
    }

    private void aggiorna() {
        aggiornaNemici();
        aggiornaEroe();
        aggiornaAzioni();
    }

    private void aggiornaNemici() {
        contenitoreNemici.getChildren().clear();
        for (Entita nemico : combattimento.getNemici()) {
            Button pulsante = new Button(nemico.getNome() + "\n" + nemico.getVita() + "/" + nemico.getVitaMassima());
            pulsante.getStyleClass().add(nemico == bersaglio ? "nemico-selezionato" : "nemico");
            pulsante.setDisable(!nemico.isViva());
            pulsante.setOnAction(evento -> seleziona(nemico));
            contenitoreNemici.getChildren().add(pulsante);
        }
    }

    private void aggiornaEroe() {
        Entita eroe = combattimento.getEroe();
        etichettaEroe.setText(eroe.getNome() + " | Vita " + eroe.getVita() + "/" + eroe.getVitaMassima() + " | Mana " + eroe.getMana() + "/" + eroe.getManaMassimo());
    }

    private void aggiornaAzioni() {
        Entita eroe = combattimento.getEroe();
        contenitoreAzioni.getChildren().clear();
        for (Azione azione : eroe.getAzioni()) {
            Button pulsante = new Button(testoAzione(azione));
            pulsante.getStyleClass().add("pulsante");
            pulsante.setDisable(!combattimento.isTurnoGiocatore() || eroe.getMana() < azione.getCostoMana());
            pulsante.setOnAction(evento -> agisci(azione));
            contenitoreAzioni.getChildren().add(pulsante);
        }
    }

    private void mostraEsito(String testo) {
        etichettaEsito.setText(testo);
        pulsanteContinua.setVisible(true);
    }

    private Entita primoNemicoVivo() {
        return combattimento.getNemiciVivi().stream().findFirst().orElse(null);
    }

    private String testoAzione(Azione azione) {
        if (azione.getCostoMana() == 0) {
            return azione.getNome();
        }
        return azione.getNome() + " (" + azione.getCostoMana() + " mana)";
    }

    private String descrizione(Turno turno) {
        String attore = turno.attore().getNome();
        String azione = turno.azione().getNome();
        String bersaglioTurno = turno.bersaglio().getNome();
        int valore = turno.risultato().valore();
        return switch (turno.risultato().esito()) {
            case COLPITO -> attore + " usa " + azione + " su " + bersaglioTurno + ": " + valore + " danni";
            case BERSAGLIO_SCONFITTO -> attore + " usa " + azione + " su " + bersaglioTurno + ": " + valore + " danni, " + bersaglioTurno + " è sconfitto";
            case CURATO -> attore + " usa " + azione + ": +" + valore + " vita";
            case MANA_INSUFFICIENTE -> attore + " non ha abbastanza mana per " + azione;
        };
    }
}
