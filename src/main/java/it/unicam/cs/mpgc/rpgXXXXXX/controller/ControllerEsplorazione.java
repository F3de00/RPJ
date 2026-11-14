package it.unicam.cs.mpgc.rpgXXXXXX.controller;

import it.unicam.cs.mpgc.rpgXXXXXX.model.Partita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.Entita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.CombattimentoIniziato;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.Notifica;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.OsservatorePartita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.PartitaPersa;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.PartitaVinta;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.RiposoEffettuato;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.StanzaCompletata;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.TurnoEseguito;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerEsplorazione implements OsservatorePartita {
    private final Partita partita;
    private final Navigatore navigatore;

    @FXML
    private Label etichettaStanza;
    @FXML
    private Label etichettaEroe;
    @FXML
    private Label etichettaMessaggio;

    public ControllerEsplorazione(Partita partita, Navigatore navigatore) {
        this.partita = partita;
        this.navigatore = navigatore;
    }

    @FXML
    private void initialize() {
        aggiorna();
    }

    @FXML
    private void entra() {
        partita.entraNellaProssimaStanza();
    }

    @Override
    public void riceviNotifica(Notifica notifica) {
        switch (notifica) {
            case CombattimentoIniziato combattimentoIniziato -> navigatore.mostraCombattimento();
            case RiposoEffettuato riposo -> {
                etichettaMessaggio.setText("Ti riposi: +" + riposo.vitaRecuperata() + " vita, +" + riposo.manaRecuperato() + " mana");
                aggiorna();
            }
            case StanzaCompletata stanzaCompletata -> aggiorna();
            case PartitaVinta partitaVinta -> navigatore.mostraFine();
            case TurnoEseguito turnoEseguito -> {}
            case PartitaPersa partitaPersa -> {}
        }
    }

    private void aggiorna() {
        Entita eroe = partita.getEroe();
        etichettaStanza.setText("Stanza " + (partita.getStanzeCompletate() + 1) + " di " + partita.getNumeroStanze());
        etichettaEroe.setText(eroe.getNome() + " | Vita " + eroe.getVita() + "/" + eroe.getVitaMassima() + " | Mana " + eroe.getMana() + "/" + eroe.getManaMassimo());
    }
}
