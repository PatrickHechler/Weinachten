package de.hechler.patrick.objects;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class UnmodifiableKlasse implements KlasseInterface {
	
	/**
	 * speichert alle Schüler/Teilnehmer der Klasse/des Wichtelevents.
	 */
	private Teilnehmer[] teilnehmer;
	
	
	
	public UnmodifiableKlasse(Teilnehmer[] teilnehmer) {
		this.teilnehmer = teilnehmer.clone();
	}
	
	/**
	 * Initialisiert {@link #teilnehmer} mit <code>new {@link Teilnehmer}[size]</code>. Diese werden aber nicht innerhalb diesen Konstruktors gesetzt.
	 * 
	 * @param size
	 *            die anzahl an {@link Teilnehmer}n in der klasse.
	 */
	private UnmodifiableKlasse(int size) {
		teilnehmer = new Teilnehmer[size];
	}
	
	/**
	 * An empty Konstruktor: if something went wrong in the {@link #clone()} method by using {@code super.clone()} this Kunstruktor is used
	 */
	private UnmodifiableKlasse() {
	}
	
	/**
	 * lädt eine {@link UnmodifiableKlasse} aus dem {@link InputStream} {@code in}. <br>
	 * Eine {@link UnmodifiableKlasse} muss diese Struktur haben: <br>
	 * size <br>
	 * 1.Wunsch 2.Wunsch 3.Wunsch <br>
	 * . <br>
	 * . <br>
	 * . <br>
	 * 
	 * @param in
	 *            der {@link InputStream} aus dem die {@link UnmodifiableKlasse} geladen wird.
	 * @return die geladene {@link UnmodifiableKlasse}
	 */
	public static UnmodifiableKlasse lade(InputStream in) {
		return lade(new Scanner(in));
	}
	
	/**
	 * lädt eine {@link UnmodifiableKlasse} aus dem {@link Scanner} {@code eingang}. <br>
	 * Eine {@link UnmodifiableKlasse} muss diese Struktur haben: <br>
	 * size <br>
	 * 1.Wunsch 2.Wunsch 3.Wunsch <br>
	 * . <br>
	 * . <br>
	 * . <br>
	 * 
	 * @param in
	 *            der {@link InputStream} aus dem die {@link UnmodifiableKlasse} geladen wird.
	 * @return die geladene {@link UnmodifiableKlasse}
	 */
	public static UnmodifiableKlasse lade(Scanner eingang) {
		UnmodifiableKlasse ergebnis;
		int zwischenA;
		int zwischenB;
		int zwischenC;
		zwischenA = eingang.nextInt();
		ergebnis = new UnmodifiableKlasse(zwischenA);
		for (int runde = 0; runde < ergebnis.teilnehmer.length; runde ++ ) {
			zwischenA = checkNummer(eingang.nextInt(), ergebnis);
			zwischenB = eingang.nextInt();
			zwischenC = eingang.nextInt();
			ergebnis.teilnehmer[runde] = new Teilnehmer(runde + 1, zwischenA, zwischenB, zwischenC);
		}
		return ergebnis;
	}
	
	
	
	private static int checkNummer(int check, UnmodifiableKlasse with) {
		if (check > 0 || with.teilnehmer.length <= check) {
			return check;
		}
		throw new IndexOutOfBoundsException(check);
	}
	
	/**
	 * Gibt die Anzahl an {@link Teilnehmer}n dieser {@link UnmodifiableKlasse} zurück.
	 * 
	 * @return Anzahl der {@link Teilnehmer} dieser {@link UnmodifiableKlasse}
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
	
	public Set <Integer> erstWunsch() {
		Set <Integer> ergebnis;
		ergebnis = new HashSet <>();
		for (Teilnehmer teste : teilnehmer) {
			ergebnis.add(teste.erstWunsch());
		}
		return ergebnis;
	}
	
	public Set <Teilnehmer> einzelnErstWunsch() {
		Set <Integer> tester;
		Set <Teilnehmer> ergebnis;
		tester = new HashSet <>();
		ergebnis = new HashSet <>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !tester.contains(teste.erstWunsch())) {
				tester.add(teste.erstWunsch());
				ergebnis.add(teste);
			} else {
				ergebnis.remove(teste);
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
	 * @return Alle Wunschnummern, die als zweitWunsch in dieser {@link UnmodifiableKlasse} existieren.
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
	 * @param ignore
	 *            Alle {@link Teilnehmer}, die definitiv nicht zurückgegeben werden.
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
	 * Gibt alle Wunschnummern zurück, welche als drittWunsch auftauchen.
	 * 
	 * @return Alle Wunschnummern, die als drittWunsch in dieser {@link UnmodifiableKlasse} existieren.
	 */
	public Set <Integer> drittWunsch() {
		Set <Integer> ergebnis;
		ergebnis = new TreeSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			ergebnis.add(teste.drittWunsch());
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle Wunschnummern zurück, welche als drittWunsch auftauchen.
	 * 
	 * @return Alle Wunschnummern, die als drittWunsch in dieser {@link UnmodifiableKlasse} existieren.
	 */
	public Set <Integer> drittWunsch(Set <Integer> ignore1, Set <Integer> ignore2) {
		Set <Integer> ergebnis;
		ergebnis = new TreeSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			int drittWunsch = teste.drittWunsch();
			if ( ! (ignore1.contains(drittWunsch) || ignore2.contains(drittWunsch))) {
				ergebnis.add(drittWunsch);
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle Wunschnummern zurück, welche genau einmal als drittWunsch gewünscht werden.
	 * 
	 * @return Alle Wunschnummern, welche genau einmal als drittWunsch gewünscht werden.
	 */
	public Set <Integer> einzelnDrittWunsch() {
		Set <Integer> tester;
		Set <Integer> ergebnis;
		tester = new HashSet <Integer>();
		ergebnis = new TreeSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !tester.contains(teste.drittWunsch())) {
				tester.add(teste.drittWunsch());
				ergebnis.add(teste.drittWunsch());
			} else {
				ergebnis.remove(teste.drittWunsch());
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, die die einzigen sind, welche sich den Gegenstand ihres drittWunsches als drittWunsch wünschen.
	 * 
	 * @return Alle {@link Teilnehmer}, die die einzigen sind, welche sich den Gegenstand ihres drittWunsches als drittWunsch wünschen.
	 */
	public List <Teilnehmer> einzelnDrittWunschTeilnehmer() {
		Set <Integer> tester;
		Set <Integer> merker;
		List <Teilnehmer> ergebnis;
		tester = new HashSet <Integer>();
		merker = new HashSet <Integer>();
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !tester.contains(teste.drittWunsch())) {
				tester.add(teste.drittWunsch());
				merker.add(teste.drittWunsch());
			} else {
				merker.remove(teste.drittWunsch());
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
	 * Gibt alle {@link Teilnehmer} zurück, welche sich {@code gegenstandsNummer} als drittWunsch wünschen.
	 * 
	 * @param gegenstandsNummer
	 *            Der Gegenstand, welcher sich ein {@link Teilnehmer} wünschen muss um hier zurückgegeben zu werden
	 * @return alle {@link Teilnehmer}, welche sich {@code gegenstandsNummer} als drittWunsch wünschen.
	 */
	public List <Teilnehmer> drittWunschVon(int gegenstandsNummer) {
		List <Teilnehmer> ergebnis;
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : teilnehmer) {
			if (gegenstandsNummer == teste.drittWunsch()) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
	}
	
	/**
	 * Gibt alle {@link Teilnehmer} zurück, welche sich {@code gegenstandsNummer} als drittWunsch wünschen.
	 * 
	 * @param ignore1
	 *            {@link Teilnehmer}, die definitiv nicht zurückgegeben werden.
	 * @param ignore2
	 *            {@link Teilnehmer}, die definitiv nicht zurückgegeben werden.
	 * @param gegenstandsNummer
	 *            Der Gegenstand, welcher sich ein {@link Teilnehmer} wünschen muss um hier zurückgegeben zu werden
	 * @return alle {@link Teilnehmer}, welche sich {@code gegenstandsNummer} als drittWunsch wünschen.
	 */
	public List <Teilnehmer> drittWunschVon(int gegenstandsNummer, Set <Teilnehmer> ignore1, Set <Teilnehmer> ignore2) {
		List <Teilnehmer> ergebnis;
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : teilnehmer) {
			if (gegenstandsNummer == teste.drittWunsch() && !ignore1.contains(teste) && !ignore2.contains(teste)) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
	}
	
	/**
	 * Bewertet, wie gut die {@link Verteilung} zu dieser {@link UnmodifiableKlasse} passt und gibt das Ergebnis dann als {@link Bewertung} zurück.
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
	 * Gibt die {@link UnmodifiableKlasse} genau so zurück, wie sie Standardweise gespeichert wird: <br>
	 * 1. Zeile: size <br>
	 * 2-n+1. Zeile: {@link Teilnehmer}[n]
	 * 
	 * @return Die {@link UnmodifiableKlasse} als {@link String}
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
	
	@Override
	public UnmodifiableKlasse clone() {
		UnmodifiableKlasse erg;
		try {
			erg = (UnmodifiableKlasse) super.clone();
		} catch (CloneNotSupportedException e) {
			erg = new UnmodifiableKlasse();
		}
		erg.teilnehmer = teilnehmer.clone();
		for (int i = 0; i < teilnehmer.length; i ++ ) {
			erg.teilnehmer[i] = teilnehmer[i].clone();
		}
		return erg;
	}
	
	@Override
	public Iterator <Teilnehmer> iterator() {
		return new Iter();
	}
	
	private class Iter implements Iterator <Teilnehmer> {
		
		private int index;
		
		
		
		public Iter() {
			index = 0;
		}
		
		
		
		@Override
		public boolean hasNext() {
			return index < teilnehmer.length;
		}
		
		@Override
		public Teilnehmer next() {
			return teilnehmer[index];
		}
		
		
	}
	
}
