package de.hechler.patrick.objects.generatorenimpl;

import de.hechler.patrick.objects.DiffrentFirstVerteilungsGenerator;
import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.Verteilung;

/**
 * iterates to all validate {@link Verteilung} (a {@link Verteilung} is valid, when {@link Verteilung#isValid()} returns <code>true</code>)
 * 
 * @author Patrick
 *
 */
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
	
}
