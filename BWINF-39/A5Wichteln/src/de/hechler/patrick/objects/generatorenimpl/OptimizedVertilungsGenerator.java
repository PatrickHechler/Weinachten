package de.hechler.patrick.objects.generatorenimpl;

import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.LoadableVerteilungsGenerator;
import de.hechler.patrick.objects.Verteilung;
import de.hechler.patrick.objects.VerteilungsGenerator;


public class OptimizedVertilungsGenerator extends LoadableVerteilungsGenerator {
	
	private int deep;
	private int cnt;
	private int index;
	private Verteilung fest;
	private Set <Integer> erst;
	private Set <Integer> zweit;
	private Set <Integer> dritt;
	private Set <Integer> frei;
	private boolean puffer;
	
	
	
	protected OptimizedVertilungsGenerator(Klasse klasse) {
		super(klasse, false);
		this.fest = new Verteilung(klasse.size(), false);
		this.cnt = -1;
	}
	
	
	
	@Override
	public void load() {
		erst = new HashSet <Integer>();
		zweit = new HashSet <Integer>();
		dritt = new HashSet <Integer>();
		Set <Integer> einzelnErstWunsch = klasse.einzelnErstWunsch();
		frei = new HashSet <Integer>();
		for (int i = 1; i <= klasse.size(); i ++ ) {
			frei.add(i);
		}
		for (Integer festlegen : einzelnErstWunsch) {
			verteilung.set(festlegen, klasse.teilnehmer(festlegen).erstWunsch());
			fest.set(festlegen, klasse.teilnehmer(festlegen).erstWunsch());
		}
		for (int gegenstandsNummer = 1; gegenstandsNummer < klasse.size(); gegenstandsNummer ++ ) {
			if (klasse.habenErstWunsch(gegenstandsNummer).size() > 0) {
				frei.remove(gegenstandsNummer);
				erst.add(gegenstandsNummer);
			}
		}
		for (int gegenstandsNummer = 1; gegenstandsNummer < klasse.size(); gegenstandsNummer ++ ) {
			if ( !erst.contains(gegenstandsNummer) && klasse.habenZweitWunsch(gegenstandsNummer).size() > 0) {
				frei.remove(gegenstandsNummer);
				zweit.add(gegenstandsNummer);
			}
		}
		for (int gegenstandsNummer = 1; gegenstandsNummer < klasse.size(); gegenstandsNummer ++ ) {
			if ( !erst.contains(gegenstandsNummer) && !zweit.contains(gegenstandsNummer) && klasse.habenZweitWunsch(gegenstandsNummer).size() > 0) {
				frei.remove(gegenstandsNummer);
				dritt.add(gegenstandsNummer);
			}
		}
		verteilung.fillRest();
		deep = 3;
	}
	
	@Override
	public boolean hasNext() {
		return puffer = (next() != null);
	}
	
	@Override
	public Verteilung next() {
		if (puffer) {
			puffer = false;
			return verteilung;
		}
		int alteNummer;
		int nummer;
		switch (deep) {
		case 1:
			while (true) {
				int cnt = 0;
				this.cnt ++ ;
				for (nummer = 1; nummer <= klasse.size(); nummer ++ ) {
					if (klasse.habenErstWunsch(nummer).size() > 1) {
						cnt ++ ;
						if (cnt == this.cnt) {
							break;
						}
					}
				}
				if (cnt == this.cnt) break;
				else if (cnt > this.cnt) {
					return null;// gibt keine weitere Verteilung mehr
				}
			}
			alteNummer = verteilung.get(nummer);
			verteilung.set(nummer, alteNummer + klasse.habenErstWunsch(nummer).get(index).nummer());
			rebuild(nummer, alteNummer);
			break;
		case 2:
			while (true) {
				int cnt = 0;
				this.cnt ++ ;
				for (nummer = 1; nummer <= klasse.size(); nummer ++ ) {
					if (klasse.habenZweitWunsch(nummer, erst).size() > 1) {
						cnt ++ ;
						if (cnt == this.cnt) {
							break;
						}
					}
				}
				if (cnt == this.cnt) break;
				else if (cnt > this.cnt) {
					deep = 1;
					return next();
				}
			}
			alteNummer = verteilung.get(nummer);
			verteilung.set(nummer, alteNummer + klasse.habenZweitWunsch(nummer, erst).get(index).nummer());
			rebuild(nummer, alteNummer);
			break;
		case 3:
			while (true) {
				int cnt = 0;
				this.cnt ++ ;
				for (nummer = 1; nummer <= klasse.size(); nummer ++ ) {
					if (klasse.habenDrittWunsch(nummer, erst, zweit).size() > 1) {
						cnt ++ ;
						if (cnt == this.cnt) {
							break;
						}
					}
				}
				if (cnt == this.cnt) break;
				else if (cnt > this.cnt) {
					deep = 2;
					return next();
				}
			}
			alteNummer = verteilung.get(nummer);
			verteilung.set(nummer, alteNummer + klasse.habenDrittWunsch(nummer, erst, zweit).get(index).nummer());
			rebuild(nummer, alteNummer);
			break;
		default:
			throw new RuntimeException("Unbekannte Tiefe (Vieleicht wurde vorher nicht geladen)!");
		}
		return verteilung;
	}
	
	protected void rebuild(int startNummer, int alteWunschNummer) {
		while (true) {
			NavigableSet <Integer> benutzbar = new TreeSet <Integer>();
			Set <Integer> tester;
			switch (deep) {
			case 1:
				tester = erst;
				break;
			case 2:
				tester = zweit;
				break;
			case 3:
				tester = dritt;
				break;
			default:
				throw new RuntimeException("Unbekannte Tiefe");
			}
			benutzbar.add(alteWunschNummer);
			for (int i = startNummer + 1; i <= klasse.size(); i ++ ) {
				if (tester.contains(verteilung.get(i))) {
					benutzbar.add(i);
				}
			}
			for (int i = startNummer + 1, use = -1; i <= klasse.size(); i ++ ) {
				if (tester.contains(verteilung.get(i))) {
					use = benutzbar.higher(use);
					verteilung.set(i, use);
				}
			}
			if (deep > 1) {
				deep -- ;
			} else return;
		}
	}
	
	public static void main(String[] args) {
		System.out.println("lade die Klasse");
		Klasse klasse = Klasse.lade(System.in);
		System.out.println("Fertig geladen");
		VerteilungsGenerator generator = new OptimizedVertilungsGenerator(klasse);
		System.out.println("Suche nun nach der besten Möglichkeit");
		Verteilung verteilung = generator.besteVerbleibende();
		System.out.println("BEWERTUNG:" + klasse.bewerte(verteilung));
		System.out.println("Hier ist sie: (BFVG)");
		verteilung.print();
	}
	
}
