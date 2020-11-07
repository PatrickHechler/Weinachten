package de.hechler.patrick.objects.generatorenimpl;

import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.Verteilung;
import de.hechler.patrick.objects.VerteilungsGenerator;

public class AbsoluterBruthForceVerteilungsGenerator extends VerteilungsGenerator {
	
	private boolean puffer;
	
	
	
	public AbsoluterBruthForceVerteilungsGenerator(Klasse klasse) {
		super(klasse, false);
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
		while (verteilung.get(nummer) > klasse.size()) {
			verteilung.set(nummer, 1);
			nummer ++ ;
			verteilung.set(nummer, verteilung.get(nummer) + 1);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("lade die Klasse");
		Klasse klasse = Klasse.lade(System.in);
		System.out.println("Fertig geladen");
		VerteilungsGenerator generator;
//		generator = new AbsoluterBruthForceVerteilungsGenerator(klasse);
		generator = new OptimizedVertilungsGenerator(klasse);
		System.out.println("Suche nun nach der besten Möglichkeit");
		Verteilung verteilung = generator.besteVerbleibende();
		System.out.println("Hier ist sie: (OVG)");
		verteilung.print();
	}
	
}
