package de.hechler.patrick.objects;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
			zwischenA = eingang.nextInt();
			zwischenB = eingang.nextInt();
			zwischenC = eingang.nextInt();
			ergebnis.teilnehmer[runde] = new Teilnehmer(runde + 1, zwischenA, zwischenB, zwischenC);
		}
		return ergebnis;
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
	 * Gibt alle {@link Teilnehmer} zurück, die die einzigen sind, welche sich den Gegenstand ihres erstWunsches als erstWunsch wünschen.
	 * 
	 * @return Alle {@link Teilnehmer}, die die einzigen sind, welche sich den Gegenstand ihres erstWunsches als erstWunsch wünschen.
	 */
	public Set <Integer> einzelnErstWunsch() {
		Set <Integer> ergebnis;
		ergebnis = new HashSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if (ergebnis.contains(teste.erstWunsch())) {
				ergebnis.remove(teste.erstWunsch());
			} else {
				ergebnis.add(teste.erstWunsch());
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, die die einzigen sind, welche sich den Gegenstand ihres zweitWunsches als zweitWunsch wünschen.
	 * 
	 * @return Alle {@link Teilnehmer}, die die einzigen sind, welche sich den Gegenstand ihres zweitWunsches als zweitWunsch wünschen.
	 */
	public Set <Integer> einzelnZweitWunsch() {
		Set <Integer> ergebnis;
		ergebnis = new HashSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if (ergebnis.contains(teste.zweitWunsch())) {
				ergebnis.remove(teste.zweitWunsch());
			} else {
				ergebnis.add(teste.zweitWunsch());
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, die die einzigen sind, welche sich den Gegenstand ihres zweitWunsches als zweitWunsch wünschen und nicht in dem {@link Set}
	 * {@code ignore} enthalten sind.
	 * 
	 * @implNote Es muss den gleichen Effekt haben, wie: <br>
	 *           <code>erg = {@link #einzelnZweitWunsch()}; erg.removeAll(ignore); return erg;</code> <br>
	 *           (removeAll(ignore) ist aus: {@link java.util.Collection#removeAll(java.util.Collection)})
	 * 			
	 * @param ignore
	 *            speichert alle {@link Teilnehmer}, welche definitiv nicht zurückgegeben werden.
	 * @return Alle {@link Teilnehmer}, die die einzigen sind, welche sich den Gegenstand ihres zweitWunsches als zweitWunsch wünschen und nicht in dem {@link Set} {@code ignore}
	 *         enthalten sind.
	 * 
	 */
	public Set <Integer> einzelnZweitWunsch(Set <Teilnehmer> ignore) {
		Set <Integer> ergebnis;
		ergebnis = new HashSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !ignore.contains(teste)) {
				if (ergebnis.contains(teste.zweitWunsch())) {
					ergebnis.remove(teste.zweitWunsch());
				} else {
					ergebnis.add(teste.zweitWunsch());
				}
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, die die einzigen sind, welche sich den Gegenstand ihres erstWunsches als erstWunsch wünschen.
	 * 
	 * @return Alle {@link Teilnehmer}, die die einzigen sind, welche sich den Gegenstand ihres erstWunsches als erstWunsch wünschen.
	 */
	public Set <Integer> einzelnDrittWunsch() {
		Set <Integer> ergebnis;
		ergebnis = new HashSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if (ergebnis.contains(teste.drittWunsch())) {
				ergebnis.remove(teste.drittWunsch());
			} else {
				ergebnis.add(teste.drittWunsch());
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, die die einzigen sind, welche sich den Gegenstand ihres drittWunsches als drittWunsch wünschen und nicht in dem {@link Set}
	 * {@code ignore} enthalten sind.
	 * 
	 * @implNote Es muss den gleichen Effekt haben, wie: <br>
	 *           <code>erg = {@link #einzelnDrittWunsch()}; erg.removeAll(ignore1); erg.removeAll(ignore2); return erg;</code> <br>
	 *           (removeAll(ignore) ist aus: {@link java.util.Collection#removeAll(java.util.Collection)})
	 * 			
	 * @param ignore1
	 *            speichert ein Teil der {@link Teilnehmer}, welche definitiv nicht zurückgegeben werden.
	 * @param ignore2
	 *            speichert ein Teil der {@link Teilnehmer}, welche definitiv nicht zurückgegeben werden.
	 * @return Alle {@link Teilnehmer}, die die einzigen sind, welche sich den Gegenstand ihres drittWunsches als drittWunsch wünschen und nicht in dem {@link Set} {@code ignore1}
	 *         oder {@code ignore2} enthalten sind.
	 * 		
	 */
	public Set <Integer> einzelnDrittWunsch(Set <Teilnehmer> ignore1, Set <Teilnehmer> ignore2) {
		Set <Integer> ergebnis;
		ergebnis = new HashSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !ignore1.contains(teste) && !ignore2.contains(teste)) {
				if (ergebnis.contains(teste.drittWunsch())) {
					ergebnis.remove(teste.drittWunsch());
				} else {
					ergebnis.add(teste.drittWunsch());
				}
			}
		}
		return ergebnis;
	}
	
	public List <Teilnehmer> habenErstWunsch(int gegenstand) {
		List <Teilnehmer> ergebnis;
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : ergebnis) {
			if (gegenstand == teste.erstWunsch()) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
		
	}
	
	public List <Teilnehmer> habenZweitWunsch(int gegenstand) {
		List <Teilnehmer> ergebnis;
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : ergebnis) {
			if (gegenstand == teste.zweitWunsch()) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
		
	}
	
	public List <Teilnehmer> habenDrittWunsch(int gegenstand) {
		List <Teilnehmer> ergebnis;
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : ergebnis) {
			if (gegenstand == teste.drittWunsch()) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
		
	}
	
	public Bewertung bewerte(Verteilung verteilung) {
		Bewertung ergebnis;
		ergebnis = new Bewertung();
		for (Teilnehmer bewerten : teilnehmer) {
			bewerten.bewerte(verteilung.get(bewerten.nummer()), ergebnis);
		}
		return ergebnis;
	}
	

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(Integer.toString(size())).append("\n");
		for (int i=1; i<=size(); i++) {
			Teilnehmer t = teilnehmer(i);
			result.append(Integer.toString(t.erstWunsch())).append(" ");
			result.append(Integer.toString(t.zweitWunsch())).append(" ");
			result.append(Integer.toString(t.drittWunsch())).append("\n");
		}
		return result.toString();
	}
	
}
