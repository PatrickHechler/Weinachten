package de.hechler.patrick.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import de.hechler.patrick.objects.generatorenimpl.AbsoluterBruthForceVerteilungsGenerator;
import de.hechler.patrick.objects.generatorenimpl.BruthForceVerteilungsGenerator;

public abstract class VerteilungsGenerator implements Iterator <Verteilung> {
	
	/**
	 * Debugzwecke in {@link #besteVerbleibende()}
	 */
	private List <Verteilung> alle;
	
	
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
			if (alle != null) {
				alle.add(testen.clone());
			}
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
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		List <Verteilung> alle;
		List <Verteilung> fastAlle;
		System.out.println("lade");
		Klasse klasse = Klasse.lade(new FileInputStream(new File("./beispieldaten/wichteln1.txt")));
		System.out.println("Fertig lesen");
		VerteilungsGenerator alleFindender = new AbsoluterBruthForceVerteilungsGenerator(klasse);
		VerteilungsGenerator fastAlleFindender = new BruthForceVerteilungsGenerator(klasse);
		System.out.println("Suche fast alle möglichkeiten durch");
		fastAlleFindender.besteVerbleibende();
		System.out.println("Suche ALLE möglichkeiten durch");
		alleFindender.besteVerbleibende();
		System.out.println("fertig suchen");
		alle = alleFindender.alle;
		fastAlle = fastAlleFindender.alle;
		for (Verteilung teste : alle) {
			if ( !fastAlle.contains(teste)) {
				System.out.println("GEFUNDEN:");
				System.out.println("BEWERTUNG: " + klasse.bewerte(teste));
				teste.print();
			}
		}
	}
	
	public List <Verteilung> getAlle() {
		return alle;
	}
	
}
