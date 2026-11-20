package it.unicam.cs.mpgc.rpg127378.controller;

import java.io.IOException;
import java.io.UncheckedIOException;

import it.unicam.cs.mpgc.rpg127378.model.Partita;
import it.unicam.cs.mpgc.rpg127378.model.creature.StatisticheBase;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.OsservatorePartita;
import it.unicam.cs.mpgc.rpg127378.model.notifiche.RiposoEffettuato;
import it.unicam.cs.mpgc.rpg127378.servizi.ServizioPartite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Navigatore {
    private static final String PERCORSO_FXML = "/it/unicam/cs/mpgc/rpg127378/view/";

    private final Scene scena;
    private final ServizioPartite servizio;
    private Partita partita;
    private OsservatorePartita osservatoreCorrente;

    public Navigatore(Scene scena, ServizioPartite servizio) {
        this.scena = scena;
        this.servizio = servizio;
    }

    public void mostraSceltaEroe() {
        ControllerSceltaEroe controller = new ControllerSceltaEroe(servizio, this);
        mostra(carica("SceltaEroe.fxml", controller), null);
    }

    public void iniziaPartita(StatisticheBase tipoEroe) {
        cambiaOsservatore(null);
        partita = servizio.nuovaPartita(tipoEroe);
        mostraEsplorazione();
    }

    public void riprendiPartita() {
        cambiaOsservatore(null);
        partita = servizio.riprendiPartita();
        mostraEsplorazione();
    }

    public void salvaEdEsci() {
        servizio.salvaPartita(partita);
        mostraSceltaEroe();
    }

    public void mostraEsplorazione() {
        ControllerEsplorazione controller = new ControllerEsplorazione(partita, this);
        mostra(carica("Esplorazione.fxml", controller), controller);
    }

    public void mostraCombattimento() {
        ControllerCombattimento controller = new ControllerCombattimento(partita, this);
        mostra(carica("Combattimento.fxml", controller), controller);
        controller.inizia();
    }

    public void mostraRiposo(RiposoEffettuato riposo) {
        ControllerRiposo controller = new ControllerRiposo(partita, this, riposo);
        mostra(carica("Riposo.fxml", controller), null);
    }

    public void mostraPotenziamento() {
        ControllerPotenziamento controller = new ControllerPotenziamento(partita, this);
        mostra(carica("Potenziamento.fxml", controller), null);
    }

    public void mostraFine() {
        ControllerFine controller = new ControllerFine(partita, this);
        mostra(carica("Fine.fxml", controller), null);
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
