package it.unicam.cs.mpgc.rpgXXXXXX.controller;

import it.unicam.cs.mpgc.rpgXXXXXX.model.FabbricaPartita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.Partita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.StatisticheBase;
import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Navigatore {
    private static final String CARTELLA_VIEW = "/it/unicam/cs/mpgc/rpgXXXXXX/view/";
    private static final double LARGHEZZA = 960;
    private static final double ALTEZZA = 640;
    private static final double PORZIONE_SCHERMO = 0.9;

    private final Stage stage;
    private final FabbricaPartita fabbrica;
    private Partita partita;

    public Navigatore(Stage stage, FabbricaPartita fabbrica) {
        this.stage = stage;
        this.fabbrica = fabbrica;
    }

    public void mostraSceltaEroe() {
        mostra(carica("SceltaEroe.fxml", new ControllerSceltaEroe(fabbrica.getEroiDisponibili(), this)));
    }

    public void iniziaPartita(StatisticheBase tipoEroe) {
        partita = fabbrica.nuovaPartita(tipoEroe);
        mostraEsplorazione();
    }

    public void mostraEsplorazione() {
        mostra(new StackPane(new Label("Partita iniziata con " + partita.getEroe().getNome() + ": esplorazione in costruzione")));
    }

    private Parent carica(String fileFxml, Object controller) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(CARTELLA_VIEW + fileFxml));
        loader.setController(controller);
        try {
            return loader.load();
        } catch (IOException e) {
            throw new UncheckedIOException("Impossibile caricare " + fileFxml, e);
        }
    }

    private void mostra(Parent radice) {
        if (stage.getScene() == null) {
            Rectangle2D schermo = Screen.getPrimary().getVisualBounds();
            double larghezza = Math.min(LARGHEZZA, schermo.getWidth() * PORZIONE_SCHERMO);
            double altezza = Math.min(ALTEZZA, schermo.getHeight() * PORZIONE_SCHERMO);
            stage.setScene(new Scene(radice, larghezza, altezza));
            stage.setResizable(false);
        } else {
            stage.getScene().setRoot(radice);
        }
    }
}
