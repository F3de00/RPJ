package it.unicam.cs.mpgc.rpgXXXXXX;

import it.unicam.cs.mpgc.rpgXXXXXX.controller.Navigatore;
import it.unicam.cs.mpgc.rpgXXXXXX.model.FabbricaPartita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.CatalogoPredefinito;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.CatalogoStatistiche;
import it.unicam.cs.mpgc.rpgXXXXXX.model.dungeon.GeneratoreDungeon;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    private static final double LARGHEZZA = 960;
    private static final double ALTEZZA = 640;
    private static final double PORZIONE_SCHERMO = 0.9;

    @Override
    public void start(Stage stage) {
        CatalogoStatistiche catalogo = new CatalogoPredefinito();
        GeneratoreDungeon generatore = new GeneratoreDungeon(catalogo, new Random());
        FabbricaPartita fabbrica = new FabbricaPartita(catalogo, generatore);
        Scene scena = creaScena();
        Navigatore navigatore = new Navigatore(scena, fabbrica);
        stage.setTitle("RPJ - Dungeon Crawler");
        stage.setScene(scena);
        stage.setResizable(false);
        navigatore.mostraSceltaEroe();
        stage.show();
    }

    private Scene creaScena() {
        Rectangle2D schermo = Screen.getPrimary().getVisualBounds();
        double larghezza = Math.min(LARGHEZZA, schermo.getWidth() * PORZIONE_SCHERMO);
        double altezza = Math.min(ALTEZZA, schermo.getHeight() * PORZIONE_SCHERMO);
        return new Scene(new StackPane(), larghezza, altezza);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
