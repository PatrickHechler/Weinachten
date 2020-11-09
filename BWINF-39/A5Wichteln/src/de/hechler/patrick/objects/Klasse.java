package de.hechler.patrick.objects;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Klasse {
	
	/**
	 * speichert alle Schüler/Teilnehmer der Klasse/des Wichtelevents.
	 */
	private Teilnehmer[] teilnehmer;
	
	
	
	/**
	 * Initialisiert {@link #teilnehmer} mit <code>new {@link Teilnehmer}[size]</code>. Diese werden aber nicht innerhalb diesen Konstruktors gesetzt.
	 * 
	 * @param size
	 *            die anzahl an {@link Teilnehmer}n in der klasse.
	 */
	private Klasse(int size) {
		teilnehmer = new Teilnehmer[size];
	}
	
	/**
	 * lädt eine {@link Klasse} aus dem {@link InputStream} {@code in}. <br>
	 * Eine {@link Klasse} muss diese Struktur haben: <br>
	 * size <br>
	 * 1.Wunsch 2.Wunsch 3.Wunsch <br>
	 * . <br>
	 * . <br>
	 * . <br>
	 * 
	 * @param in
	 *            der {@link InputStream} aus dem die {@link Klasse} geladen wird.
	 * @return die geladene {@link Klasse}
	 */
	public static Klasse lade(InputStream in) {
		return lade(new Scanner(in));
	}
	
	/**
	 * lädt eine {@link Klasse} aus dem {@link Scanner} {@code eingang}. <br>
	 * Eine {@link Klasse} muss diese Struktur haben: <br>
	 * size <br>
	 * 1.Wunsch 2.Wunsch 3.Wunsch <br>
	 * . <br>
	 * . <br>
	 * . <br>
	 * 
	 * @param in
	 *            der {@link InputStream} aus dem die {@link Klasse} geladen wird.
	 * @return die geladene {@link Klasse}
	 */
	public static Klasse lade(Scanner eingang) {
		Klasse ergebnis;
		int zwischenA;
		int zwischenB;
		int zwischenC;
		zwischenA = eingang.nextInt();
		ergebnis = new Klasse(zwischenA);
		for (int runde = 0; runde < ergebnis.teilnehmer.length; runde ++ ) {
			zwischenA = checkNummer(eingang.nextInt(), ergebnis);
			zwischenB = eingang.nextInt();
			zwischenC = eingang.nextInt();
			ergebnis.teilnehmer[runde] = new Teilnehmer(runde + 1, zwischenA, zwischenB, zwischenC);
		}
		return ergebnis;
	}
	
	
	
	private static int checkNummer(int check, Klasse with) {
		if (check > 0 || with.teilnehmer.length <= check) {
			return check;
		}
		throw new IndexOutOfBoundsException(check);
	}
	
	/**
	 * Gibt die Anzahl an {@link Teilnehmer}n dieser {@link Klasse} zurück.
	 * 
	 * @return Anzahl der {@link Teilnehmer} dieser {@link Klasse}
	 */
	public int size() {
		return teilnehmer.length;
	}
	
	/**
	 * Gibt den {@link Teilnehmer} mit der übergebenen {@code teilnehmerNummer} zurück.
	 * 
	 * @param teilnehmerNummer
	 *            Die Nummer des zurückzugebenden {@link Teilnehmer}s.
	 * @return Den {@link Teilnehmer} mit der übergebenen {@code teilnehmerNummer}.
	 */
	public Teilnehmer teilnehmer(int teilnehmerNummer) {
		return teilnehmer[teilnehmerNummer - 1];
	}
	
	/**
	 * Gibt alle Wunschnummern zurück, die die einzigen sind, welche sich den Gegenstand ihres erstWunsches als erstWunsch wünschen.
	 * 
	 * @return Alle {@link Teilnehmer}, die die einzigen sind, welche sich den Gegenstand ihres erstWunsches als erstWunsch wünschen.
	 */
	public Set <Integer> erstWunsch() {
		Set <Integer> ergebnis;
		ergebnis = new TreeSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			ergebnis.add(teste.erstWunsch());
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle Wunschnummern zurück, welche als erstWunsch auftauchen.
	 * 
	 * @return Alle Wunschnummern, die als erstWunsch in dieser {@link Klasse} existieren.
	 */
	public Set <Integer> einzelnErstWunsch() {
		Set <Integer> tester;
		Set <Integer> ergebnis;
		tester = new HashSet <Integer>();
		ergebnis = new TreeSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !tester.contains(teste.erstWunsch())) {
				tester.add(teste.erstWunsch());
				ergebnis.add(teste.erstWunsch());
			} else {
				ergebnis.remove(teste.erstWunsch());
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, die die einzigen sind, welche sich den Gegenstand ihres erstWunsches als erstWunsch wünschen.
	 * 
	 * @return Alle {@link Teilnehmer}, die die einzigen sind, welche sich den Gegenstand ihres erstWunsches als erstWunsch wünschen.
	 */
	public List <Teilnehmer> einzelnErstWunschTeilnehmer() {
		Set <Integer> tester;
		Set <Integer> merker;
		List <Teilnehmer> ergebnis;
		tester = new HashSet <Integer>();
		merker = new HashSet <Integer>();
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !tester.contains(teste.erstWunsch())) {
				tester.add(teste.erstWunsch());
				merker.add(teste.erstWunsch());
			} else {
				merker.remove(teste.erstWunsch());
			}
		}
		for (Teilnehmer teste : ergebnis) {
			if (merker.contains(teste.erstWunsch())) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, welche sich {@code gegenstandsNummer} als erstWunsch wünschen.
	 * 
	 * @param gegenstandsNummer
	 *            Der Gegenstand, welcher sich ein {@link Teilnehmer} wünschen muss um hier zurückgegeben zu werden
	 * @return alle {@link Teilnehmer}, welche sich {@code gegenstandsNummer} als erstWunsch wünschen.
	 */
	public List <Teilnehmer> erstWunschVon(int gegenstandsNummer) {
		List <Teilnehmer> ergebnis;
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : teilnehmer) {
			if (gegenstandsNummer == teste.erstWunsch()) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle Wunschnummern zurück, welche als zweitWunsch auftauchen.
	 * 
	 * @return Alle Wunschnummern, die als zweitWunsch in dieser {@link Klasse} existieren.
	 */
	public Set <Integer> zweitWunsch() {
		Set <Integer> ergebnis;
		ergebnis = new TreeSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			ergebnis.add(teste.zweitWunsch());
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle Wunschnummern zurück, die die einzigen sind, welche sich den Gegenstand ihres zweitWunsches als zweitWunsch wünschen.
	 * 
	 * @return Alle Wunschnummern, die die einzigen sind, welche sich den Gegenstand ihres zweitWunsches als zweitWunsch wünschen.
	 */
	public Set <Integer> einzelnZweitWunsch() {
		Set <Integer> tester;
		Set <Integer> ergebnis;
		tester = new HashSet <Integer>();
		ergebnis = new TreeSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !tester.contains(teste.zweitWunsch())) {
				tester.add(teste.zweitWunsch());
				ergebnis.add(teste.zweitWunsch());
			} else {
				ergebnis.remove(teste.zweitWunsch());
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, die die einzigen sind, welche sich den Gegenstand ihres zweitWunsches als zweitWunsch wünschen.
	 * 
	 * @return Alle {@link Teilnehmer}, die die einzigen sind, welche sich den Gegenstand ihres zweitWunsches als zweitWunsch wünschen.
	 */
	public List <Teilnehmer> einzelnZweitWunschTeilnehmer() {
		Set <Integer> tester;
		Set <Integer> merker;
		List <Teilnehmer> ergebnis;
		tester = new HashSet <Integer>();
		merker = new HashSet <Integer>();
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !tester.contains(teste.zweitWunsch())) {
				tester.add(teste.zweitWunsch());
				merker.add(teste.zweitWunsch());
			} else {
				merker.remove(teste.zweitWunsch());
			}
		}
		for (Teilnehmer teste : ergebnis) {
			if (merker.contains(teste.zweitWunsch())) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, welche sich {@code gegenstandsNummer} als zweitWunsch wünschen.
	 * 
	 * @param gegenstandsNummer
	 *            Der Gegenstand, welcher sich ein {@link Teilnehmer} wünschen muss um hier zurückgegeben zu werden
	 * @return alle {@link Teilnehmer}, welche sich {@code gegenstandsNummer} als zweitWunsch wünschen.
	 */
	public List <Teilnehmer> zweitWunschVon(int gegenstandsNummer) {
		List <Teilnehmer> ergebnis;
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : teilnehmer) {
			if (gegenstandsNummer == teste.zweitWunsch()) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, welche sich {@code gegenstandsNummer} als zweitWunsch wünschen.
	 * 
	 * @param ignore Alle {@link Teilnehmer}, die definitiv nicht zurückgegeben werden.
	 * @param gegenstandsNummer
	 *            Der Gegenstand, welcher sich ein {@link Teilnehmer} wünschen muss um hier zurückgegeben zu werden
	 * @return alle {@link Teilnehmer}, welche sich {@code gegenstandsNummer} als zweitWunsch wünschen.
	 */
	public List <Teilnehmer> zweitWunschVon(int gegenstandsNummer, Set <Teilnehmer> ignore) {
		List <Teilnehmer> ergebnis;
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : teilnehmer) {
			if (gegenstandsNummer == teste.zweitWunsch() && !ignore.contains(teste)) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
	}
	
	/**
	 * Bewertet, wie gut die {@link Verteilung} zu dieser {@link Klasse} passt und gibt das Ergebnis dann als {@link Bewertung} zurück.
	 * 
	 * @param verteilung
	 *            Die zu bewertende {@link Verteilung}
	 * @return Das Ergebnis der {@link Bewertung}
	 */
	public Bewertung bewerte(Verteilung verteilung) {
		Bewertung ergebnis;
		ergebnis = new Bewertung();
		for (Teilnehmer bewerten : teilnehmer) {
			bewerten.bewerte(verteilung.get(bewerten.nummer()), ergebnis);
		}
		return ergebnis;
	}
	
	/**
	 * Gibt die {@link Klasse} genau so zurück, wie sie Standardweise gespeichert wird: <br>
	 * 1. Zeile: size <br>
	 * 2-n+1. Zeile: {@link Teilnehmer}[n]
	 * 
	 * @return Die {@link Klasse} als {@link String}
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(Integer.toString(size())).append("\n");
		for (int i = 1; i <= size(); i ++ ) {
			Teilnehmer t = teilnehmer(i);
			result.append(Integer.toString(t.erstWunsch())).append(" ");
			result.append(Integer.toString(t.zweitWunsch())).append(" ");
			result.append(Integer.toString(t.drittWunsch())).append("\n");
		}
		return result.toString();
	}
	
}
