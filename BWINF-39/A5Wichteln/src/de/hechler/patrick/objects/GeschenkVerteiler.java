package de.hechler.patrick.objects;

import java.util.ArrayList;
import java.util.List;

public abstract class GeschenkVerteiler {
	
	
	/**
	 * speichert eine modifizierbare Version der {@link Klasse}, welche zu diesem {@link GeschenkVerteiler} gehört.
	 */
	protected Klasse klasse;
	
	
	/**
	 * Dieser Konstruktor vertraut darauf, dass die {@link Klasse} {@code modifiable} {@link UnmodifiableKlasse} {@code orig} gehört.
	 * 
	 * @param orig
	 *            Die originale {@link UnmodifiableKlasse}
	 * @param modifyable
	 *            Die {@link Klasse}, welche zu {@code orig} gehört und möglicherweise bereits modifiziert wurde.
	 */
	protected GeschenkVerteiler(Klasse modifyable) {
		this.klasse = modifyable;
	}
	
	/**
	 * erstellt einen neuen {@link GeschenkVerteiler} mit den {@link Teilnehmer}n, welche in der {@link List}<code><{@link Teilnehmer}> teilnehmer</code>. <br>
	 * Wenn in dieser {@link List}e nicht alle {@link Teilnehmer} der {@link Klasse} enthalten sind wird es zu Fehlern kommen können!
	 * 
	 * @param teilnehmer
	 *            alle {@link Teilnehmer} der zu dem {@link GeschenkVerteiler} gehörenden {@link Klasse}
	 */
	public GeschenkVerteiler(List <Teilnehmer> teilnehmer) {
		klasse = new Klasse(teilnehmer, teilnehmer.size());
		Teilnehmer[] array = new Teilnehmer[teilnehmer.size()];
		for (int i = 0; i < array.length; i ++ ) {
			array[i] = teilnehmer.get(i);
		}
	}
	
	/**
	 * erstellt einen neuen {@link GeschenkVerteiler} mit den {@link Teilnehmer}n, welche in dem <code>{@link Teilnehmer}[] teilnehmer</code>. <br>
	 * Wenn in diesem Array nicht alle {@link Teilnehmer} der {@link Klasse} enthalten sind wird es zu Fehlern kommen können!
	 * 
	 * @param teilnehmer
	 *            alle {@link Teilnehmer} der zu dem {@link GeschenkVerteiler} gehörenden {@link Klasse}
	 */
	public GeschenkVerteiler(Teilnehmer[] teilnehmer) {
		List <Teilnehmer> liste = new ArrayList <Teilnehmer>(teilnehmer.length);
		for (int i = 0; i < teilnehmer.length; i ++ ) {
			liste.add(teilnehmer[i]);
		}
		klasse = new Klasse(liste, teilnehmer.length);
	}
	
	
	
	/**
	 * gibt die bestmögliche {@link Verteilung} zurück.
	 * 
	 * @return die bestmögliche {@link Verteilung}
	 */
	public abstract Verteilung beste();
	
}
