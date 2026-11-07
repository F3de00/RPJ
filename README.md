# RPJ - Dungeon Crawler

Gioco di ruolo a turni in stile dungeon crawler, sviluppato in Java con interfaccia JavaFX.

## Requisiti

- JDK 25 o superiore
- Nessuna installazione di Gradle necessaria (viene usato il wrapper)

## Compilazione ed esecuzione

```bash
./gradlew build
./gradlew run
```

## Struttura del progetto

```
.
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
├── docs/                 # specifica del progetto
└── src/
    ├── main/java/it/unicam/cs/mpgc/rpgXXXXXX/
    │   ├── App.java      # punto di ingresso JavaFX
    │   ├── model/        # dominio e regole di gioco
    │   ├── view/         # interfaccia grafica
    │   ├── controller/   # orchestrazione dei casi d'uso
    │   └── persistence/  # salvataggio e caricamento
    └── test/java/        # test automatici
```

Il package contiene il segnaposto `rpgXXXXXX`: va sostituito con `rpg` seguito dalla propria matricola.

## Documentazione

- [Specifica del progetto](docs/specifica-progetto-aa-2025-26.pdf)

## Uso di strumenti di AI

_Dichiarazione da completare durante lo sviluppo: strumenti utilizzati e scopo._
