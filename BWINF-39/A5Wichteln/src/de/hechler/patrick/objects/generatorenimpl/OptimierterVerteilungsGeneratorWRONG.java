package de.hechler.patrick.objects.generatorenimpl;

import java.util.NavigableSet;
import java.util.TreeSet;

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
public class OptimierterVerteilungsGeneratorWRONG extends LoadableVerteilungsGenerator {
	
	private int highPos;
	private int deep;
	private boolean puffer;
	private NavigableSet <Teilnehmer> erstT;
	private NavigableSet <Teilnehmer> zweitT;
	private NavigableSet <Teilnehmer> drittT;
	private NavigableSet <Integer> erstN;
	private NavigableSet <Integer> zweitN;
	private NavigableSet <Integer> drittN;
	
	
	
	protected OptimierterVerteilungsGeneratorWRONG(Klasse klasse) {
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
		if (puffer) {
			puffer = false;
			return verteilung;
		}
		int neu;
		int old;
		NavigableSet <Integer> nummer;
		NavigableSet <Teilnehmer> teil;
		switch (deep) {
		case 1:
			nummer = erstN;
			teil = erstT;
			break;
		case 2:
			nummer = zweitN;
			teil = zweitT;
			break;
		case 3:
			nummer = drittN;
			teil = drittT;
			break;
		default:
			throw new RuntimeException("unknown deep!");
		}
		// highPos = ( (highPos >= klasse.size() - 1) ? highPos : klasse.size()) - 1;
		highPos = nummer.lower( (highPos >= nummer.last() - 1) ? highPos : nummer.last());
		neu = old = verteilung.get(highPos);
		while (true) {
			if (neu >= teil.last().nummer()) {
				verteilung.set(highPos, old);
				highPos = nummer.lower(highPos);
				neu = old = verteilung.get(highPos);
			}
			nummer.higher(neu);
			verteilung.set(highPos, neu);
			for (int runde = nummer.higher(highPos); runde <= nummer.last(); runde = nummer.higher(runde)) {
				if (verteilung.get(runde) == neu) {
					verteilung.set(runde, old);
					NavigableSet <Teilnehmer> benutzen = new TreeSet <Teilnehmer>();
					nummer.subSet(highPos, false, nummer.last(), true).forEach((Integer add) -> benutzen.add(klasse.teilnehmer(add)));
					switch (deep) {
					case 1:
						for (Teilnehmer sort = benutzen.first(); sort != null; sort = benutzen.higher(sort)) {
							verteilung.set(sort.nummer(), sort.erstWunsch());
						}
						break;
					case 2:
						for (Teilnehmer sort = benutzen.first(); sort != null; sort = benutzen.higher(sort)) {
							verteilung.set(sort.nummer(), sort.zweitWunsch());
						}
						break;
					case 3:
						for (Teilnehmer sort = benutzen.first(); sort != null; sort = benutzen.higher(sort)) {
							verteilung.set(sort.nummer(), sort.drittWunsch());
						}
						break;
					default:
						throw new RuntimeException("unknown deep!");
					}
					return verteilung;
				}
			}
		}
	}
	
}
