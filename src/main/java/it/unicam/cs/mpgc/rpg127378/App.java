package it.unicam.cs.mpgc.rpg127378;

import java.nio.file.Path;
import java.util.Random;

import it.unicam.cs.mpgc.rpg127378.controller.Navigatore;
import it.unicam.cs.mpgc.rpg127378.model.FabbricaPartita;
import it.unicam.cs.mpgc.rpg127378.model.creature.CatalogoPredefinito;
import it.unicam.cs.mpgc.rpg127378.model.creature.CatalogoStatistiche;
import it.unicam.cs.mpgc.rpg127378.model.dungeon.GeneratoreDungeon;
import it.unicam.cs.mpgc.rpg127378.persistenza.RepositoryPartiteJson;
import it.unicam.cs.mpgc.rpg127378.servizi.ServizioPartite;
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
    private static final Path CARTELLA_SALVATAGGI = Path.of(System.getProperty("user.home"), ".rpj");

    @Override
    public void start(Stage stage) {
        CatalogoStatistiche catalogo = new CatalogoPredefinito();
        GeneratoreDungeon generatore = new GeneratoreDungeon(catalogo, new Random());
        FabbricaPartita fabbrica = new FabbricaPartita(catalogo, generatore);
        ServizioPartite servizio = new ServizioPartite(fabbrica, new RepositoryPartiteJson(CARTELLA_SALVATAGGI));
        Scene scena = creaScena();
        Navigatore navigatore = new Navigatore(scena, servizio);
        stage.setTitle("Endless Depths");
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
