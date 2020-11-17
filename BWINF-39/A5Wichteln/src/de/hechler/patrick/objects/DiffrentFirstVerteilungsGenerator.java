package de.hechler.patrick.objects;

public abstract class DiffrentFirstVerteilungsGenerator extends MobilerVerteilungsGenerator {
	
	protected DiffrentFirstVerteilungsGenerator(ModifilableKlasse modifilableKlasse, boolean richtigeStartVerteilung) {
		super(modifilableKlasse, richtigeStartVerteilung);
	}
	
	/**
	 * Wenn vorher {@link #next()} aufgerufen wurde ist es nicht Garantiert, dass diese Methode immernoch die richtige erste {@link Verteilung} zurückgibt.
	 * 
	 * @return die erste Verteilung
	 */
	public abstract Verteilung first();
	
	/**
	 * Gibt die beste {@link Verteilung} des {@link MobilerVerteilungsGenerator}s oder die erste, falls diese gültig und besser ist zurück.
	 * 
	 * @return Die beste {@link Verteilung} oder die erste, falls diese gültig und besser ist.
	 * @override {@link MobilerVerteilungsGenerator#besteVerbleibende()}
	 */
	@Override
	public Verteilung besteVerbleibende() {
		Verteilung first = first();
		Verteilung ergebnis = super.besteVerbleibende();
		int bewertung = modifilableKlasse.bewerte(first).compareTo(modifilableKlasse.bewerte(ergebnis));
		return (bewertung > 0) ? (first.isValid() ? first : ergebnis) : ergebnis;
	}
	
}
