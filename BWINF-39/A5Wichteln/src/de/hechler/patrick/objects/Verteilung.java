package de.hechler.patrick.objects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Verteilung {
	
	/**
	 * speichert die {@link Verteilung} der Geschenke an die {@link Teilnehmer} einer {@link ModifilableKlasse}.
	 */
	private int[] geschenke;
	
	
	
	/**
	 * Erstellt eine neue {@link Verteilung} der Größe {@code size}. <br>
	 * falls {@code richtig} = {@code true}: werden die einzelnen Elemente von {@link #geschenke} fortlaufend generiert (von 1 bis size) <br>
	 * falls {@code richtig} = {@code false}: werden alle {@link #geschenke} mit 0 (ungültige Zahl) initialisiert.
	 * 
	 * @param size
	 *            Die anzahl an {@link Teilnehmer} der zugehörigen {@link ModifilableKlasse}
	 * @param initialisieren
	 *            falls {@code richtig} = {@code true}, werden die einzelnen Elemente von {@link #geschenke} fortlaufend generiert (von 1 bis size) falls {@code richtig} =
	 *            {@code false}: werden alle {@link #geschenke} mit 0 (ungültige Zahl) initialisiert.
	 */
	public Verteilung(int size, boolean initialisieren) {
		geschenke = new int[size];
		if (initialisieren) {
			int i = 0;
			while (i < geschenke.length) {
				geschenke[i] = ++ i;
			}
		}
	}
	
	/**
	 * Erstellt eine {@link Verteilung} und stellt @{@code this.}{@link #geschenke} auf {@code geschenke}
	 * 
	 * @param geschenke
	 *            Der zukünftige Wert von {@link #geschenke}
	 */
	public Verteilung(int[] geschenke) {
		this.geschenke = geschenke;
	}
	
	
	
	/**
	 * Weist dem {@link Teilnehmer} mit der nummer {@code teilnehmerNummer} das Geschenk mit der nummer {@code geschenkNummer} zu.
	 * 
	 * @param teilnehmerNummer
	 *            Der {@link Teilnehmer} dem ein Geschenk zugewiesen werden soll.
	 * @param geschenkNummer
	 *            Die nummer des Geschenks das dem {@link Teilnehmer} zugewiesen werden soll.
	 */
	public void set(int teilnehmerNummer, int geschenkNummer) {
		geschenke[teilnehmerNummer - 1] = geschenkNummer;
	}
	
	/**
	 * Gibt das Geschenk, welches zu dem {@link Teilnehmer} mit der nummer {@code teilnehmerNummer} gehört, zurück.
	 * 
	 * @param teilnehmerNummer
	 *            Die nummer des {@link Teilnehmer}, dessen GeschenkNummer abgefragt wird.
	 * @return Das Geschenk, welches zu dem {@link Teilnehmer} mit der nummer {@code teilnehmerNummer} gehört.
	 */
	public int get(int teilnehmerNummer) {
		return geschenke[teilnehmerNummer - 1];
	}
	
	/**
	 * Klont diese {@link Verteilung} Ding und gibt eine {@link Verteilung} zurück, welche unabhängig von dieser ist, aber Inhalt hat, wie diese {@link Verteilung} aktuell hat.
	 * 
	 * @return die geklonte {@link Verteilung}
	 */
	@Override
	public Verteilung clone() {
		Verteilung ergebnis;
		try {
			ergebnis = (Verteilung) super.clone();
		} catch (Exception e) {
			return new Verteilung(geschenke.clone());
		}
		ergebnis.geschenke = geschenke.clone();
		return ergebnis;
	}
	
	/**
	 * prüft, ob diese {@link Verteilung} gültig ist.
	 * 
	 * @return <code>true</code>, falls diese {@link Verteilung} gültig ist, ansonsten <code>false</code>
	 */
	public boolean isValid() {
		Set <Integer> tester;
		tester = new HashSet <Integer>(geschenke.length);
		for (int teste : geschenke) {
			if (teste < 1 || teste > geschenke.length || !tester.add(teste)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * TODO hier weitermachen
	 * 
	 * @param teilnehmerNummerEnde
	 */
	public void sortTo(int teilnehmerNummerEnde) {
		Arrays.sort(geschenke, 0, teilnehmerNummerEnde);
	}
	
	public void sortFromTo(int teilnehmerNummerStart, int teilnehmerNummerEnde) {
		Arrays.sort(geschenke, teilnehmerNummerStart - 1, teilnehmerNummerEnde);
	}
	
	public void sortFrom(int teilnehmerNummer) {
		Arrays.sort(geschenke, teilnehmerNummer - 1, geschenke.length);
	}
	
	public void sort() {
		Arrays.sort(geschenke);
	}
	
	public boolean isReversed() {
		for (int i = 0; i < geschenke.length; i ++ ) {
			if (geschenke[i] != geschenke.length - i) {
				return false;
			}
		}
		return true;
	}
	
	public void fillRest() {
		
	}
	
	
	public void print() {
		int len = (geschenke.length + "").length();
		for (int i = 0; i < geschenke.length; i ++ ) {
			String teilnehmer = "" + (i + 1);
			String geschenk = "" + geschenke[i];
			while (teilnehmer.length() < len) {
				teilnehmer += " ";
			}
			while (geschenk.length() < len) {
				geschenk += " ";
			}
			System.out.println("T[" + teilnehmer + "]" + " : G[" + geschenk + "]");
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( !getClass().equals(obj.getClass())) {
			return false;
		}
		return Objects.deepEquals(geschenke, ((Verteilung) obj).geschenke);
	}
	
}
