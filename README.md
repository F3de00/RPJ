# Endless Depths

Gioco di ruolo a turni.

Il giocatore sceglie un eroe e attraversa un dungeon infinito, stanza dopo stanza: combattimenti a turni contro nemici sempre più forti, stanze di riposo e un boss alla fine di ogni ciclo, dopo il quale l'eroe sceglie un potenziamento. La partita finisce quando l'eroe viene sconfitto: lo scopo è superare più stanze possibile. La partita in corso si può salvare tra una stanza e l'altra e riprendere in seguito; il gioco ricorda l'ultima partita e il record.

## Requisiti

- JDK 25
- Nessuna installazione di Gradle: viene usato il wrapper incluso

## Compilazione ed esecuzione

```bash
./gradlew build
./gradlew run
```

I salvataggi vengono scritti in formato JSON nella cartella `.rpj` della home dell'utente. Per ripartire da zero basta cancellarla.

## Struttura del progetto

```
src/main/java/it/unicam/cs/mpgc/rpg127378/
├── App.java       avvio dell'applicazione JavaFX
├── model/         regole e stato del gioco (creature, azioni, combattimento, dungeon, notifiche, salvataggi)
├── servizi/       casi d'uso della partita: nuova partita, salvataggio, ripresa, risultati
├── persistenza/   salvataggio su file JSON
└── controller/    controller delle schermate e navigazione
src/main/resources/it/unicam/cs/mpgc/rpg127378/view/
                   schermate FXML e foglio di stile
```

## Documentazione

La descrizione delle funzionalità, delle responsabilità, delle classi, della persistenza e dei meccanismi di estensione è nella [Wiki del progetto](https://github.com/F3de00/RPJ/wiki).

## Uso di strumenti di AI

Nella realizzazione del progetto è stato usato Claude Code (Anthropic) come assistente, per discutere le scelte di progettazione, accelerare la scrittura di alcune parti del codice delle classi che venivano definite, verificare il bilanciamento con simulazioni e preparare la documentazione.
