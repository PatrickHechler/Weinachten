package de.hechler.patrick.objects;

public abstract class MobilerVerteilungsGenerator extends VerteilungsGenerator {
	
	/**
	 * speichert die {@link Klasse}, welche zu diesem {@link MobilerVerteilungsGenerator} gehört
	 */
	protected Klasse klasse;
	
	
	
	/**
	 * erstellt einen neuen {@link MobilerVerteilungsGenerator} von der {@link Klasse} {@code klasse}. {@code richtigeStartVerteilung} wird dabei an den Konstruktor
	 * {@link Verteilung#Verteilung(int, boolean)} weitergereicht.
	 * 
	 * @param klasse
	 *            die {@link Klasse}, zu welcher dieser {@link MobilerVerteilungsGenerator} {@link Verteilung}en generiert.
	 * @param initialisierteStartVerteilung
	 *            wird weitergereicht an {@link Verteilung#Verteilung(int, boolean)}.
	 */
	protected MobilerVerteilungsGenerator(Klasse klasse, boolean initialisierteStartVerteilung) {
		super(klasse, initialisierteStartVerteilung);
		this.klasse = klasse;
	}
	
}
