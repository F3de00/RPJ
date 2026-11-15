package it.unicam.cs.mpgc.rpgXXXXXX.controller;

import it.unicam.cs.mpgc.rpgXXXXXX.model.Partita;
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
        etichettaEsito.setText("Game over");
        etichettaStanze.setText("Stanze superate: " + partita.getStanzeCompletate());
    }

    @FXML
    private void giocaAncora() {
        navigatore.mostraSceltaEroe();
    }
}
