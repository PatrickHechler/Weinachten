package de.hechler.patrick.objects.generatorenimpl;

import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.LoadableVerteilungsGenerator;
import de.hechler.patrick.objects.Teilnehmer;
import de.hechler.patrick.objects.Verteilung;
import de.hechler.patrick.objects.VerteilungsGenerator;


/**
 * Optimizes the {@link BruthForceVerteilungsGenerator} and iterates only to the {@link Verteilung}, which can be the best
 * 
 * @author Patrick
 *
 */
public class OptimizedVertilungsGeneratorOLD extends LoadableVerteilungsGenerator {
	
	private int deep;
	private int highPos;
	private Verteilung fest;
	private NavigableMap <Integer, Teilnehmer> erst;	//TODO: erst, zweit und dritt komplett neu machen
	private NavigableMap <Integer, Teilnehmer> zweit;
	private NavigableMap <Integer, Teilnehmer> dritt;
	private Set <Integer> frei;
	private boolean puffer;
	
	
	
	protected OptimizedVertilungsGeneratorOLD(Klasse klasse) {
		super(klasse, false);
		this.fest = new Verteilung(klasse.size(), false);
	}
	
	
	
	@Override
	public void load() {
		erst = new TreeMap <Integer, Teilnehmer>();
		zweit = new TreeMap <Integer, Teilnehmer>();
		dritt = new TreeMap <Integer, Teilnehmer>();
		Set <Integer> einzelnErstWunsch = klasse.einzelnErstWunsch();
		frei = new HashSet <Integer>();
		for (int i = 1; i <= klasse.size(); i ++ ) {
			frei.add(i);
		}
		for (Integer festlegen : einzelnErstWunsch) {
			verteilung.set(festlegen, klasse.teilnehmer(festlegen).erstWunsch());
			fest.set(festlegen, klasse.teilnehmer(festlegen).erstWunsch());
		}
		for (int gegenstandsNummer = 1; gegenstandsNummer <= klasse.size(); gegenstandsNummer ++ ) {
			Map <Integer, Teilnehmer> erst = klasse.erstWunschVon(gegenstandsNummer);
			if (erst.size() > 0) {
				frei.remove(gegenstandsNummer);
				erst.forEach((Integer num, Teilnehmer add) -> this.erst.put(add.nummer(), add));
			}
		}
		for (int gegenstandsNummer = 1; gegenstandsNummer <= klasse.size(); gegenstandsNummer ++ ) {
			Map <Integer, Teilnehmer> zweit = klasse.zweitWunschVon(gegenstandsNummer, new TreeSet <Teilnehmer>(erst.values()));
			if (zweit.size() > 0) {
				frei.remove(gegenstandsNummer);
				zweit.forEach((Integer num, Teilnehmer add) -> this.zweit.put(add.nummer(), add));
			}
		}
		for (int gegenstandsNummer = 1; gegenstandsNummer <= klasse.size(); gegenstandsNummer ++ ) {
			Map <Integer, Teilnehmer> dritt = klasse.drittWunschVon(gegenstandsNummer, erst, zweit);
			if (dritt.size() > 0) {
				frei.remove(gegenstandsNummer);
				dritt.forEach((Integer num, Teilnehmer add) -> this.dritt.put(add.nummer(), add));
			}
		}
		deep = 3;
		for (int i = klasse.size() - 1; i > 0; i -- ) {
			if (dritt.containsKey(i)) {
				highPos = i;
				break;
			}
		}
		if (highPos == 0) {
			for (int i = klasse.size() - 1; i > 0; i -- ) {
				if (zweit.containsKey(i)) {
					highPos = i;
				}
			}
		}
		if (highPos == 0) {
			for (int i = klasse.size() - 1; i > 0; i -- ) {
				if (erst.containsKey(i)) {
					highPos = i;
					break;
				}
			}
		}
		puffer = fest.isValid();
		verteilung.fillRest();
	}
	
	@Override
	public boolean hasNext() {
		return puffer = (next() != null);
	}
	
	
	@Override
	public Verteilung next() {
		if (checkPuffer()) {
			return verteilung;
		}
		Integer zw;
		NavigableSet <Integer> allowedPos;
		NavigableSet <Integer> allowedNumber;
		int neu;
		int alt;
		{
			NavigableMap <Integer, Teilnehmer> zwischen;
			switch (deep) {
			case 1:
				zwischen = erst;
				break;
			case 2:
				zwischen = zweit;
				break;
			case 3:
				zwischen = dritt;
				break;
			default:
				throw new RuntimeException("unknown deep! (pherhaps the load method has not been called or evry Verteilung has been read)");
			}
			allowedNumber = new TreeSet <Integer>();
			allowedPos = new TreeSet <Integer>();
			for (int nummer = 1; nummer <= highPos; nummer ++ ) {
				if (zwischen.containsKey(verteilung.get(nummer)) && fest.get(nummer) == 0) {
					allowedPos.add(nummer);
				}
			}
			for (int nummer = highPos + 1; nummer <= klasse.size(); nummer ++ ) {
				if (zwischen.containsKey(verteilung.get(nummer)) && fest.get(nummer) == 0) {
					allowedPos.add(nummer);
					allowedNumber.add(verteilung.get(nummer));
				}
			}
		}
		// ( (highPos >= klasse.size() - 1) ? highPos : klasse.size()) - 1;
		// low highPos or if not possible set higPos to the highest position
		zw = allowedPos.lower(highPos);
		if (zw == null) {
			zw = allowedPos.last();
		}
		highPos = zw;
		neu = alt = verteilung.get(highPos);
		while (true) {
			try {
				zw = allowedNumber.last();
			} catch (NoSuchElementException noMoreElements) {
				deep -- ;
				if (deep == 0) {
					return null;
				}
				return next();
			}
			if (neu >= zw) {
				verteilung.set(highPos, alt);
				highPos = allowedPos.lower(highPos); // low highPos
				allowedNumber.add(alt);
				neu = alt = verteilung.get(highPos);
			}
			neu = allowedNumber.higher(neu);// high neu
			verteilung.set(highPos, neu);
			// rebuild from highPos+1
			int pos = highPos;
			while (true) {
				pos = allowedPos.higher(pos);
				if (verteilung.get(pos) == neu) {
					verteilung.set(pos, alt);
					break;
				}
			}
			pos = allowedPos.higher(highPos);
			int nummer = allowedNumber.first();
			while (true) {
				verteilung.set(pos, nummer);
				zw = allowedPos.higher(pos);
				if (zw == null) {
					return verteilung;
				}
				pos = zw;
				nummer = allowedNumber.higher(nummer);
			}
		}
	}
	
	private boolean checkPuffer() {
		if (puffer) {
			puffer = fest.isValid();
			return true;
		}
		return false;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("lade die Klasse");
		Klasse klasse = Klasse.lade(System.in);
		System.out.println("Fertig geladen");
		VerteilungsGenerator generator = new OptimizedVertilungsGeneratorOLD(klasse);
		System.out.println("Suche nun nach der besten Möglichkeit");
		Verteilung verteilung = generator.besteVerbleibende();
		System.out.println("BEWERTUNG:" + klasse.bewerte(verteilung));
		System.out.println("Hier ist sie: (BFVG)");
		verteilung.print();
	}
	
}
