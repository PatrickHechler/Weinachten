package de.hechler.patrick.objects;

import java.util.Iterator;


public abstract class VerteilungsGenerator implements Iterator <Verteilung> {
	
	/**
	 * speichert die aktuelle {@link Verteilung}
	 */
	protected Verteilung verteilung;
	
	/**
	 * speichert die {@link Klasse} dieses {@link VerteilungsGenerator}s.
	 */
	protected Klasse klasse;
	
	public VerteilungsGenerator(Klasse klasse, boolean initialisierteStartVerteilung) {
		verteilung = new Verteilung(klasse.size(), initialisierteStartVerteilung);
		this.klasse = klasse;
	}
	
	
	/**
	 * bewertet die {@link Verteilung} {@code verteilung} und gibt das Ergebnis als {@link Bewertung} zurück.
	 * 
	 * @return das Ergebnis der {@link Bewertung}
	 */
	public abstract Bewertung bewerte(Verteilung verteilung);
	
	/**
	 * Gibt die beste verbleibende {@link Verteilung} des {@link MobilerVerteilungsGenerator}s zurück.
	 * 
	 * @return Die beste übrige {@link Verteilung}
	 */
	public Verteilung besteVerbleibende() {
		Verteilung beste = null;
		Bewertung bewertung = null;
		while (hasNext()) {
			Verteilung testen;
			Bewertung testbewertung;
			testen = next();
			testbewertung = bewerte(testen);
			if (testbewertung.compareTo(bewertung) > 0) {
				beste = testen.clone();
				bewertung = testbewertung;
			}
		}
		return beste;
	}
	
}
