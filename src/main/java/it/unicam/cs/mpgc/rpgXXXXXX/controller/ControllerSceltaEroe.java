package it.unicam.cs.mpgc.rpgXXXXXX.controller;

import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.Azione;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.StatisticheBase;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControllerSceltaEroe {
    private final List<StatisticheBase> eroi;
    private final Navigatore navigatore;

    @FXML
    private HBox contenitoreEroi;

    public ControllerSceltaEroe(List<StatisticheBase> eroi, Navigatore navigatore) {
        this.eroi = List.copyOf(eroi);
        this.navigatore = navigatore;
    }

    @FXML
    private void initialize() {
        for (StatisticheBase eroe : eroi) {
            Button pulsante = new Button(descrizione(eroe));
            pulsante.getStyleClass().add("scheda-eroe");
            pulsante.setOnAction(evento -> navigatore.iniziaPartita(eroe));
            contenitoreEroi.getChildren().add(pulsante);
        }
    }

    private String descrizione(StatisticheBase eroe) {
        String azioni = eroe.azioni().stream().map(Azione::getNome).collect(Collectors.joining(", "));
        return eroe.tipo()
                + "\n\nVita " + eroe.vitaMassima()
                + "\nMana " + eroe.manaMassimo()
                + "\nAttacco " + eroe.attacco()
                + "\nVelocità " + eroe.velocita()
                + "\n\n" + azioni;
    }
}
