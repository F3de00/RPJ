package it.unicam.cs.mpgc.rpgXXXXXX.model;

/**
 * Resoconto di un'azione. Il significato di valore dipende dall'esito:
 * danno subito per COLPITO e BERSAGLIO_SCONFITTO, vita recuperata per CURATO, 0 per MANA_INSUFFICIENTE.
 */
public record Risultato(EsitoAzione esito, int valore) {}
