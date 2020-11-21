package de.hechler.patrick.objects;

import java.util.Iterator;


public abstract class VerteilungsGenerator implements Iterator <Verteilung> {
	
	/**
	 * speichert die aktuelle {@link Verteilung}
	 */
	protected Verteilung verteilung;
	
	/**
	 * speichert die {@link KlasseInterface} dieses {@link VerteilungsGenerator}s.
	 */
	protected KlasseInterface klasse;
	
	public VerteilungsGenerator(KlasseInterface klasseInterface, boolean initialisierteStartVerteilung) {
		verteilung = new Verteilung(klasseInterface.size(), initialisierteStartVerteilung);
		this.klasse = klasseInterface;
	}
	
	
//	/**
//	 * bewertet die {@link Verteilung} {@code verteilung} und gibt das Ergebnis als {@link Bewertung} zurück.
//	 * 
//	 * @return das Ergebnis der {@link Bewertung}
//	 */
//	public abstract Bewertung bewerte(Verteilung verteilung);
//	
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
			testbewertung = klasse.bewerte(testen);
			if (testbewertung.compareTo(bewertung) > 0) {
				beste = testen.clone();
				bewertung = testbewertung;
			}
		}
		return beste;
	}
	
}
