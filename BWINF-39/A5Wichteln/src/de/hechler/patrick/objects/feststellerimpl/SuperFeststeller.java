package de.hechler.patrick.objects.feststellerimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.hechler.patrick.objects.Feststeller;
import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.Teilnehmer;
import de.hechler.patrick.objects.Verteilung;

public class SuperFeststeller implements Feststeller {
	
	public SuperFeststeller() {
	}
	
	
	
	@Override
	public void stelleFest(Verteilung feststellen, Klasse klasse) {
		Set <Integer> alleErstWunsch = klasse.erstWunsch();
		einzelnErst(klasse, feststellen);
		entferneUnerreichbareZweitUDrittWunsch(klasse, alleErstWunsch);
		Map <Integer, Teilnehmer> xMinusMinus = suchexNichtsNichtsErstWunsch(feststellen, klasse);
		entferneUnerreichbareErstWunsch(klasse, xMinusMinus.keySet());
	}
	
	private Map <Integer, Teilnehmer> suchexNichtsNichtsErstWunsch(Verteilung feststellen, Klasse klasse) {
		Map <Integer, Teilnehmer> xMinusMinus = new HashMap <Integer, Teilnehmer>();
		for (Teilnehmer dieser : klasse) {
			if (dieser.zweitWunsch() == 0 && dieser.drittWunsch() == 0) {
				xMinusMinus.put(dieser.erstWunsch(), dieser);
			}
		}
		klasse.removeAll(xMinusMinus.values());
		for (Teilnehmer dieser : xMinusMinus.values()) {
			feststellen.set(dieser.nummer(), dieser.erstWunsch());
		}
		return xMinusMinus;
	}
	
	private void entferneUnerreichbareErstWunsch(Klasse klasse, Set <Integer> entfernendeErstWunsch) {
		for (Teilnehmer dieser : klasse) {
			if (entfernendeErstWunsch.contains(dieser.erstWunsch())) {
				dieser.deleteErstWunsch();
			}
		}
	}
	
	private void entferneUnerreichbareZweitUDrittWunsch(Klasse klasse, Set <Integer> alleErstWunsch) {
		for (Teilnehmer dieser : klasse) {
			if (alleErstWunsch.contains(dieser.zweitWunsch())) {
				dieser.deleteZweitWunsch();
			}
			if (alleErstWunsch.contains(dieser.drittWunsch())) {
				dieser.deleteDrittWunsch();
			}
		}
	}
	
	@Override
	public Verteilung stelleFest(Klasse klasse) {
		Verteilung erg = new Verteilung(klasse.size(), false);
		stelleFest(erg, klasse);
		return erg;
	}
	
	private void einzelnErst(Klasse klasse, Verteilung verteilung) {
		Set <Teilnehmer> einzelnErst = klasse.einzelnErstWunsch();
		for (Teilnehmer einduetig : einzelnErst) {
			verteilung.set(einduetig.nummer(), einduetig.erstWunsch());
		}
		klasse.removeAll(einzelnErst);
	}
	
}
