package it.unicam.cs.mpgc.rpgXXXXXX.controller;

import it.unicam.cs.mpgc.rpgXXXXXX.model.Partita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.StatoPartita;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerFine {
    private final Partita partita;
    private final Navigatore navigatore;

    @FXML
    private Label etichettaEsito;
    @FXML
    private Label etichettaStanze;

    public ControllerFine(Partita partita, Navigatore navigatore) {
        this.partita = partita;
        this.navigatore = navigatore;
    }

    @FXML
    private void initialize() {
        etichettaEsito.setText(partita.getStato() == StatoPartita.VITTORIA ? "Vittoria!" : "Game over");
        etichettaStanze.setText("Stanze completate: " + partita.getStanzeCompletate() + " di " + partita.getNumeroStanze());
    }

    @FXML
    private void giocaAncora() {
        navigatore.mostraSceltaEroe();
    }
}
