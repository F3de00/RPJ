package it.unicam.cs.mpgc.rpg127378.controller;

import it.unicam.cs.mpgc.rpg127378.model.Partita;
import it.unicam.cs.mpgc.rpg127378.model.creature.Entita;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.CombattimentoIniziato;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.Notifica;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.OsservatorePartita;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.PartitaPersa;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.RiposoEffettuato;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.StanzaCompletata;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.TurnoEseguito;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerEsplorazione implements OsservatorePartita {
    private final Partita partita;
    private final Navigatore navigatore;

    @FXML
    private Label etichettaStanza;
    @FXML
    private Label etichettaEroe;

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

    @FXML
    private void salvaEdEsci() {
        navigatore.salvaEdEsci();
    }

    @Override
    public void riceviNotifica(Notifica notifica) {
        switch (notifica) {
            case CombattimentoIniziato combattimentoIniziato -> navigatore.mostraCombattimento();
            case RiposoEffettuato riposo -> navigatore.mostraRiposo(riposo);
            case StanzaCompletata stanzaCompletata -> aggiorna();
            case TurnoEseguito turnoEseguito -> {}
            case PartitaPersa partitaPersa -> {}
        }
    }

    private void aggiorna() {
        Entita eroe = partita.getEroe();
        etichettaStanza.setText("Stanza " + (partita.getStanzeCompletate() + 1));
        etichettaEroe.setText(eroe.getNome() + " | Vita " + eroe.getVita() + "/" + eroe.getVitaMassima() + " | Mana " + eroe.getMana() + "/" + eroe.getManaMassimo());
    }
}
