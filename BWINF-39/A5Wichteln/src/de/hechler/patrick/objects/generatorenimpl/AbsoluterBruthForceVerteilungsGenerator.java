package de.hechler.patrick.objects.generatorenimpl;

import de.hechler.patrick.objects.ModifilableKlasse;
import de.hechler.patrick.objects.Verteilung;
import de.hechler.patrick.objects.MobilerVerteilungsGenerator;

/**
 * iterates to all {@link Verteilung} and returns it in the {@link #next()} method, when returns true by calling {@link Verteilung#isValid()}
 * 
 * @author Patrick
 *
 */
public class AbsoluterBruthForceVerteilungsGenerator extends MobilerVerteilungsGenerator {
	
	private boolean puffer;
	
	
	
	public AbsoluterBruthForceVerteilungsGenerator(ModifilableKlasse modifilableKlasse) {
		super(modifilableKlasse, false);
	}
	
	
	
	@Override
	public boolean hasNext() {
		if ( !puffer) {
			try {
				next();
				puffer = true;
			} catch (Exception e) {
				puffer = false;
			}
		}
		return puffer;
	}
	
	@Override
	public Verteilung next() {
		if (puffer) {
			puffer = false;
			return verteilung;
		}
		do {
			verteilung.set(1, verteilung.get(1) + 1);
			check(1);
		} while ( !verteilung.isValid());
		return verteilung;
	}
	
	private void check(int nummer) {
		while (verteilung.get(nummer) > modifilableKlasse.size()) {
			verteilung.set(nummer, 1);
			nummer ++ ;
			verteilung.set(nummer, verteilung.get(nummer) + 1);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("lade die Klasse");
		ModifilableKlasse modifilableKlasse = ModifilableKlasse.lade(System.in);
		System.out.println("Fertig geladen");
		MobilerVerteilungsGenerator generator;
		generator = new AbsoluterBruthForceVerteilungsGenerator(modifilableKlasse);
//		generator = new OptimierterVerteilungsGenerator(klasse);
		System.out.println("Suche nun nach der besten Möglichkeit");
		Verteilung verteilung = generator.besteVerbleibende();
		System.out.println("Hier ist sie: (OVG)");
		verteilung.print();
	}
	
}
