package de.hechler.patrick.objects;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Klasse implements KlasseInterface {
	
	/**
	 * speichert alle Schüler/Teilnehmer der Klasse/des Wichtelevents.
	 */
	private List <Teilnehmer> teilnehmer;
	/**
	 * speichert die Anzahl an ursprünglichen {@link Teilnehmer} der Klasse
	 */
	private final int size;
	
	
	
	/**
	 * erzeugt eine Klasse mit den {@link Teilnehmer} {@code teilnehmer} und der Größe {@code size}
	 * 
	 * @param teilnehmer
	 *            Die {@link Teilnehmer} der Klasse
	 * @param size
	 *            Die anzahl an ursprünglichen {@link Teilnehmer} der Klasse
	 */
	public Klasse(List <Teilnehmer> teilnehmer, int size) {
		this.teilnehmer = new ArrayList <Teilnehmer>(teilnehmer);
		this.size = size;
	}
	
	/**
	 * Initialisiert {@link #teilnehmer} mit <code>new {@link Teilnehmer}[size]</code>. Diese werden aber nicht innerhalb diesen Konstruktors gesetzt.
	 * 
	 * @param size
	 *            die anzahl an {@link Teilnehmer}n in der klasse.
	 */
	private Klasse(int size) {
		teilnehmer = new ArrayList <Teilnehmer>(size);
		this.size = size;
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
		int size = eingang.nextInt();
		zwischenA = size;
		ergebnis = new Klasse(zwischenA);
		for (int runde = 0; runde < size; runde ++ ) {
			zwischenA = checkNummer(eingang.nextInt(), ergebnis);
			zwischenB = checkNummer(eingang.nextInt(), ergebnis);
			zwischenC = checkNummer(eingang.nextInt(), ergebnis);
			ergebnis.teilnehmer.add(new Teilnehmer(runde + 1, zwischenA, zwischenB, zwischenC));
		}
		return ergebnis;
	}
	
	
	
	private static int checkNummer(int check, Klasse with) {
		if (check > 0 || with.size <= check) {
			return check;
		}
		throw new IndexOutOfBoundsException(check);
	}
	
	public int size() {
		return size;
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
		Set <Integer> merker;
		Set <Integer> tester;
		Set <Teilnehmer> ergebnis;
		tester = new HashSet <>();
		merker = new HashSet <>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !merker.contains(teste.erstWunsch())) {
				merker.add(teste.erstWunsch());
				tester.add(teste.erstWunsch());
			} else {
				tester.remove(teste.erstWunsch());
			}
		}
		ergebnis = new HashSet <>();
		for (Teilnehmer teilnehmer : teilnehmer) {
			if (tester.contains(teilnehmer.erstWunsch())) {
				ergebnis.add(teilnehmer);
			}
		}
		return ergebnis;
	}
	//unbenutzt und unnötig (oben ist fast die gleiche Methode)
//	public List <Teilnehmer> einzelnErstWunschTeilnehmer() {
//		Set <Integer> tester;
//		Set <Integer> merker;
//		List <Teilnehmer> ergebnis;
//		tester = new HashSet <Integer>();
//		merker = new HashSet <Integer>();
//		ergebnis = new ArrayList <Teilnehmer>();
//		for (Teilnehmer teste : teilnehmer) {
//			if ( !tester.contains(teste.erstWunsch())) {
//				tester.add(teste.erstWunsch());
//				merker.add(teste.erstWunsch());
//			} else {
//				merker.remove(teste.erstWunsch());
//			}
//		}
//		for (Teilnehmer teste : ergebnis) {
//			if (merker.contains(teste.erstWunsch())) {
//				ergebnis.add(teste);
//			}
//		}
//		return ergebnis;
//	}
//	
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
	
	public Set <Teilnehmer> einzelnZweitWunsch() {
		Set <Integer> tester;
		Set <Integer> merker;
		Set<Teilnehmer> ergebnis;
		tester = new HashSet <Integer>();
		merker = new TreeSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !tester.contains(teste.zweitWunsch())) {
				tester.add(teste.zweitWunsch());
				merker.add(teste.zweitWunsch());
			} else {
				merker.remove(teste.zweitWunsch());
			}
		}
		ergebnis = new HashSet <Teilnehmer>();
		for (Teilnehmer teste : teilnehmer) {
			if (merker.contains(teste.zweitWunsch())) {
				ergebnis.add(teste);
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
	 * @return Alle Wunschnummern, die als drittWunsch in dieser {@link Klasse} existieren.
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
	 * @return Alle Wunschnummern, die als drittWunsch in dieser {@link Klasse} existieren.
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
		for (Teilnehmer t : teilnehmer) {
			result.append(Integer.toString(t.erstWunsch())).append(" ");
			result.append(Integer.toString(t.zweitWunsch())).append(" ");
			result.append(Integer.toString(t.drittWunsch())).append("\n");
		}
		return result.toString();
	}
	
	@Override
	public Iterator <Teilnehmer> iterator() {
		return teilnehmer.iterator();
	}
	
	public void removeAll(Collection <Teilnehmer> collection) {
		teilnehmer.removeAll(collection);
	}

}
