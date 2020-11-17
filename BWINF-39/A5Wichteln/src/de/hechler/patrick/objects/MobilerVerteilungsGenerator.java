package de.hechler.patrick.objects;

public abstract class MobilerVerteilungsGenerator extends VerteilungsGenerator {
	
	/**
	 * speichert die {@link ModifilableKlasse}, welche zu diesem {@link MobilerVerteilungsGenerator} gehört
	 */
	protected ModifilableKlasse modifilableKlasse;
	
	
	
	/**
	 * erstellt einen neuen {@link MobilerVerteilungsGenerator} von der {@link ModifilableKlasse} {@code klasse}. {@code richtigeStartVerteilung} wird dabei an den Konstruktor
	 * {@link Verteilung#Verteilung(int, boolean)} weitergereicht.
	 * 
	 * @param modifilableKlasse
	 *            die {@link ModifilableKlasse}, zu welcher dieser {@link MobilerVerteilungsGenerator} {@link Verteilung}en generiert.
	 * @param initialisierteStartVerteilung
	 *            wird weitergereicht an {@link Verteilung#Verteilung(int, boolean)}.
	 */
	protected MobilerVerteilungsGenerator(ModifilableKlasse modifilableKlasse, boolean initialisierteStartVerteilung) {
		super(modifilableKlasse.size(), initialisierteStartVerteilung);
		this.modifilableKlasse = modifilableKlasse;
	}
	
	
	
	@Override
	public Bewertung bewerte(Verteilung verteilung) {
		return modifilableKlasse.bewerte(verteilung);
	}
	
}
