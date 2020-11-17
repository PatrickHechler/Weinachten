package de.hechler.patrick.objects;

import java.util.Iterator;

public abstract class VerteilungsGenerator implements Iterator <Verteilung> {
	
	/**
	 * speichert die aktuelle {@link Verteilung}
	 */
	protected Verteilung verteilung;
	/**
	 * speichert die {@link Klasse}, welche zu diesem {@link VerteilungsGenerator} gehört
	 */
	protected Klasse klasse;
	
	
	
	/**
	 * erstellt einen neuen {@link VerteilungsGenerator} von der {@link Klasse} {@code klasse}. {@code richtigeStartVerteilung} wird dabei an den Konstruktor
	 * {@link Verteilung#Verteilung(int, boolean)} weitergereicht.
	 * 
	 * @param klasse die {@link Klasse}, zu welcher dieser {@link VerteilungsGenerator} {@link Verteilung}en generiert. 
	 * @param richtigeStartVerteilung wird weitergereicht an {@link Verteilung#Verteilung(int, boolean)}.
	 */
	protected VerteilungsGenerator(Klasse klasse, boolean richtigeStartVerteilung) {
		verteilung = new Verteilung(klasse.size(), richtigeStartVerteilung);
		this.klasse = klasse;
	}
	
	
	
	/**
	 * Gibt die beste verbleibende {@link Verteilung} des {@link VerteilungsGenerator}s zurück.
	 * 
	 * @return Die beste übrige {@link Verteilung}
	 */
	public Verteilung besteVerbleibende() {
		Verteilung beste = null;
		Bewertung bewertung = null;
		int cnt = 0;
		while (hasNext()) {
			Verteilung testen;
			Bewertung testbewertung;
			testen = next();
			cnt ++ ;
			testbewertung = klasse.bewerte(testen);
			if (testbewertung.compareTo(bewertung) > 0) {
				beste = testen.clone();
				bewertung = testbewertung;
			}
		}
		System.out.println("CNT:" + cnt);
		return beste;
	}
	
}
