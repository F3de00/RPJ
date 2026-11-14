package it.unicam.cs.mpgc.rpgXXXXXX;

import it.unicam.cs.mpgc.rpgXXXXXX.controller.Navigatore;
import it.unicam.cs.mpgc.rpgXXXXXX.model.FabbricaPartita;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.CatalogoPredefinito;
import it.unicam.cs.mpgc.rpgXXXXXX.model.creature.CatalogoStatistiche;
import it.unicam.cs.mpgc.rpgXXXXXX.model.dungeon.GeneratoreDungeon;
import java.util.Random;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        CatalogoStatistiche catalogo = new CatalogoPredefinito();
        GeneratoreDungeon generatore = new GeneratoreDungeon(catalogo, new Random());
        FabbricaPartita fabbrica = new FabbricaPartita(catalogo, generatore);
        Navigatore navigatore = new Navigatore(stage, fabbrica);
        stage.setTitle("RPJ - Dungeon Crawler");
        navigatore.mostraSceltaEroe();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
