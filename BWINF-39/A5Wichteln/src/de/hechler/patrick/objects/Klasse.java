package de.hechler.patrick.objects;

import java.util.List;
import java.util.Set;

public interface Klasse extends Iterable <Teilnehmer> {
	
	/**
	 * Gibt die Anzahl an {@link Teilnehmer}n dieser {@link Klasse} zurück.
	 * 
	 * @return Anzahl der {@link Teilnehmer} dieser {@link Klasse}
	 */
	int size();
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, welche eindeutig Zuordnungsbare erst-Wünsche haben.
	 * 
	 * @return Alle {@link Teilnehmer} mit eindeutig Zuordnungsbaren erst-Wünschen.
	 */
	Set <Teilnehmer> einzelnErstWunsch();
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, welche sich {@code gegenstandsNummer} als erstWunsch wünschen.
	 * 
	 * @param gegenstandsNummer
	 *            Der Gegenstand, welcher sich ein {@link Teilnehmer} wünschen muss um hier zurückgegeben zu werden
	 * @return alle {@link Teilnehmer}, welche sich {@code gegenstandsNummer} als erstWunsch wünschen.
	 */
	List <Teilnehmer> erstWunschVon(int gegenstandsNummer);
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, welche sich {@code gegenstandsNummer} als zweitWunsch wünschen und nicht im {@link Set} {@code ignore} enthalten sind.
	 * 
	 * @param ignore
	 *            Alle {@link Teilnehmer}, die definitiv nicht zurückgegeben werden.
	 * @param gegenstandsNummer
	 *            Der Gegenstand, welcher sich ein {@link Teilnehmer} wünschen muss um hier zurückgegeben zu werden, wenn er nicht im {@link Set} {@code ignore} ist.
	 * @return alle {@link Teilnehmer}, welche sich {@code gegenstandsNummer} als zweitWunsch wünschen und nicht im {@link Set} {@code ignore} enthalten sind.
	 */
	List <Teilnehmer> zweitWunschVon(int gegenstandsNummer, Set <Teilnehmer> ignore);
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, welche sich {@code gegenstandsNummer} als drittWunsch wünschen und nicht in {@code ignore1} oder {@code ignore2} enthalten sind.
	 * 
	 * @param ignore1
	 *            {@link Teilnehmer}, die definitiv nicht zurückgegeben werden.
	 * @param ignore2
	 *            {@link Teilnehmer}, die definitiv nicht zurückgegeben werden.
	 * @param gegenstandsNummer
	 *            Der Gegenstand, welcher sich ein {@link Teilnehmer} wünschen muss um hier zurückgegeben zu werden
	 * @return alle {@link Teilnehmer}, welche sich {@code gegenstandsNummer} als drittWunsch wünschen und nicht in {@code ignore1} oder {@code ignore2} enthalten sind.
	 */
	List <Teilnehmer> drittWunschVon(int gegenstandsNummer, Set <Teilnehmer> ignore1, Set <Teilnehmer> ignore2);
	
	/**
	 * Bewertet die {@link Verteilung} {@code verteilung} mit allen {@link Teilnehmer}n der {@link Klasse}
	 * 
	 * @param verteilung
	 *            die zu bewertende {@link Verteilung}
	 * @return das Ergebnis der Bewertung in Form einer {@link Bewertung}
	 */
	Bewertung bewerte(Verteilung verteilung);
	
}
