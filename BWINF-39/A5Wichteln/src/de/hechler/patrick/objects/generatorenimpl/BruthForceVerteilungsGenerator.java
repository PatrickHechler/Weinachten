package de.hechler.patrick.objects.generatorenimpl;

import de.hechler.patrick.objects.DiffrentFirstVerteilungsGenerator;
import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.Verteilung;

public class BruthForceVerteilungsGenerator extends DiffrentFirstVerteilungsGenerator {
	
	private int highPos;
	
	
	
	public BruthForceVerteilungsGenerator(Klasse klasse) {
		super(klasse, true);
		highPos = klasse.size();
	}
	
	
	
	@Override
	public Verteilung first() {
		return verteilung;
	}
	
	@Override
	public boolean hasNext() {
		return !verteilung.isReversed();
	}
	
	@Override
	public Verteilung next() {
		int neu;
		int old;
		highPos = ( (highPos >= klasse.size() - 1) ? highPos : klasse.size()) - 1;
		neu = old = verteilung.get(highPos);
		while (true) {
			if (neu >= klasse.size()) {
				verteilung.set(highPos, old);
				highPos -- ;
				neu = old = verteilung.get(highPos);
			}
			neu ++ ;
			verteilung.set(highPos, neu);
			for (int runde = highPos + 1; runde <= klasse.size(); runde ++ ) {
				if (verteilung.get(runde) == neu) {
					verteilung.set(runde, old);
					verteilung.sortFrom(highPos + 1);
					return verteilung;
				}
			}
		}
	}
	/*
	public static void main(String[] args) {
		System.out.println("lade die Klasse");
		Klasse klasse = Klasse.lade(System.in);
		System.out.println("Fertig geladen");
		VerteilungsGenerator generator = new BruthForceVerteilungsGenerator(klasse);
		System.out.println("Suche nun nach der besten Möglichkeit");
		Verteilung verteilung = generator.besteVerbleibende();
		System.out.println("BEWERTUNG:" + klasse.bewerte(verteilung));
		System.out.println("Hier ist sie: (BFVG)");
		verteilung.print();
	}
	*/
}
