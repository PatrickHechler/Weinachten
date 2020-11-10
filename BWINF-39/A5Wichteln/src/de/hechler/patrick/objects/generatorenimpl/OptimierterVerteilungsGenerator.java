package de.hechler.patrick.objects.generatorenimpl;

import java.util.NavigableSet;
import java.util.Set;

import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.LoadableVerteilungsGenerator;
import de.hechler.patrick.objects.Teilnehmer;
import de.hechler.patrick.objects.Verteilung;

/**
 * Ist an die Klasse {@link BruthForceVerteilungsGenerator} und optimiert diese indem sie nicht alle Möglichkeiten ausprobiert.
 * 
 * @author Patrick
 *
 */
public class OptimierterVerteilungsGenerator extends LoadableVerteilungsGenerator {
	
	private int highPos;
	private int deep;
	private boolean puffer;
	private NavigableSet <Teilnehmer> erstT;
	private NavigableSet <Teilnehmer> zweitT;
	private NavigableSet <Teilnehmer> drittT;
	private NavigableSet <Integer> erstG;
	private NavigableSet <Integer> zweitG;
	private NavigableSet <Integer> drittG;
	
	
	
	protected OptimierterVerteilungsGenerator(Klasse klasse) {
		super(klasse, false);
	}
	
	
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean hasNext() {
		return this.puffer = next() != null;
	}
	
	@Override
	public Verteilung next() {
		int neu;
		int old;
		NavigableSet <Integer> wunsch;
		Set <Teilnehmer> teil;
		switch (deep) {
		case 1:
			wunsch = erstG;
			teil = erstT;
			break;
		case 2:
			wunsch = zweitG;
			teil = zweitT;
			break;
		case 3:
			wunsch = drittG;
			teil = drittT;
			break;
		default:
			throw new RuntimeException("unknown deep!");
		}
//		highPos = ( (highPos >= klasse.size() - 1) ? highPos : klasse.size()) - 1;
		highPos =  wunsch.lower((highPos >= wunsch.last() - 1) ? highPos : wunsch.last());
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
