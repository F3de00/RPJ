package it.unicam.cs.mpgc.rpgXXXXXX.controller;

import it.unicam.cs.mpgc.rpgXXXXXX.model.azioni.Azione;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.StatisticheBase;
import it.unicam.cs.mpgc.rpgXXXXXX.model.salvataggi.RisultatoPartita;
import it.unicam.cs.mpgc.rpgXXXXXX.servizi.ServizioPartite;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ControllerSceltaEroe {
    private final ServizioPartite servizio;
    private final Navigatore navigatore;

    @FXML
    private HBox contenitoreEroi;
    @FXML
    private Label etichettaUltimaPartita;
    @FXML
    private Label etichettaMigliorePartita;
    @FXML
    private Button pulsanteContinua;

    public ControllerSceltaEroe(ServizioPartite servizio, Navigatore navigatore) {
        this.servizio = servizio;
        this.navigatore = navigatore;
    }

    @FXML
    private void initialize() {
        etichettaUltimaPartita.setText("Ultima partita: " + descrizione(servizio.getUltimaPartita()));
        etichettaMigliorePartita.setText("Record: " + descrizione(servizio.getMigliorePartita()));
        boolean esistePartitaSalvata = servizio.esistePartitaSalvata();
        pulsanteContinua.setVisible(esistePartitaSalvata);
        pulsanteContinua.setManaged(esistePartitaSalvata);
        for (StatisticheBase eroe : servizio.getEroiDisponibili()) {
            Button pulsante = new Button(descrizione(eroe));
            pulsante.getStyleClass().add("scheda-eroe");
            pulsante.setOnAction(evento -> navigatore.iniziaPartita(eroe));
            contenitoreEroi.getChildren().add(pulsante);
        }
    }

    @FXML
    private void continuaPartita() {
        navigatore.riprendiPartita();
    }

    private String descrizione(Optional<RisultatoPartita> risultato) {
        return risultato.map(partita -> partita.tipoEroe() + ", " + partita.stanzeSuperate() + " stanze").orElse("nessuna");
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
