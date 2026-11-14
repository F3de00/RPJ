package it.unicam.cs.mpgc.rpgXXXXXX.controller;

import it.unicam.cs.mpgc.rpgXXXXXX.model.FabbricaPartita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.Partita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.StatisticheBase;
import it.unicam.cs.mpgc.rpgXXXXXX.model.notifiche.OsservatorePartita;
import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Navigatore {
    private static final String PERCORSO_FXML = "/it/unicam/cs/mpgc/rpgXXXXXX/view/";

    private final Scene scena;
    private final FabbricaPartita fabbrica;
    private Partita partita;
    private OsservatorePartita osservatoreCorrente;

    public Navigatore(Scene scena, FabbricaPartita fabbrica) {
        this.scena = scena;
        this.fabbrica = fabbrica;
    }

    public void mostraSceltaEroe() {
        ControllerSceltaEroe controller = new ControllerSceltaEroe(fabbrica.getEroiDisponibili(), this);
        mostra(carica("SceltaEroe.fxml", controller), null);
    }

    public void iniziaPartita(StatisticheBase tipoEroe) {
        cambiaOsservatore(null);
        partita = fabbrica.nuovaPartita(tipoEroe);
        mostraEsplorazione();
    }

    public void mostraEsplorazione() {
        ControllerEsplorazione controller = new ControllerEsplorazione(partita, this);
        mostra(carica("Esplorazione.fxml", controller), controller);
    }

    public void mostraCombattimento() {
        ControllerCombattimento controller = new ControllerCombattimento(partita, this);
        mostra(carica("Combattimento.fxml", controller), controller);
    }

    public void mostraFine() {
        mostra(new StackPane(new Label("Fine della partita in costruzione")), null);
    }

    private Parent carica(String fileFxml, Object controller) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PERCORSO_FXML + fileFxml));
        loader.setController(controller);
        try {
            return loader.load();
        } catch (IOException e) {
            throw new UncheckedIOException("Impossibile caricare " + fileFxml, e);
        }
    }

    private void mostra(Parent radice, OsservatorePartita osservatore) {
        cambiaOsservatore(osservatore);
        scena.setRoot(radice);
    }

    private void cambiaOsservatore(OsservatorePartita nuovo) {
        if (osservatoreCorrente != null) {
            partita.rimuoviOsservatore(osservatoreCorrente);
        }
        osservatoreCorrente = nuovo;
        if (nuovo != null) {
            partita.aggiungiOsservatore(nuovo);
        }
    }
}
