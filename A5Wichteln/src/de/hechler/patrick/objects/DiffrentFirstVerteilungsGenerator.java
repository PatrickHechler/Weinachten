package de.hechler.patrick.objects;

public abstract class DiffrentFirstVerteilungsGenerator extends VerteilungsGenerator {
	
	protected DiffrentFirstVerteilungsGenerator(Klasse klasse, boolean richtigeStartVerteilung) {
		super(klasse, richtigeStartVerteilung);
	}
	
	/**
	 * Wenn vorher {@link #next()} aufgerufen wurde ist es nicht Garantiert, dass diese Methode immernoch die richtige erste {@link Verteilung} zurückgibt.
	 * 
	 * @return die erste Verteilung
	 */
	public abstract Verteilung first();
	
	/**
	 * Gibt die beste {@link Verteilung} des {@link VerteilungsGenerator}s oder die erste, falls diese gültig und besser ist zurück.
	 * 
	 * @return Die beste {@link Verteilung} oder die erste, falls diese gültig und besser ist.
	 */
	@Override
	public Verteilung besteVerbleibende() {
		Verteilung first = first();
		Verteilung ergebnis = super.besteVerbleibende();
		int bewertung = klasse.bewerte(first).compareTo(klasse.bewerte(ergebnis));
		return (bewertung > 0) ? (first.isValid() ? first : ergebnis) : ergebnis;
	}
	
}
