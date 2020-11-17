package de.hechler.patrick.objects.generatorenimpl;

import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import de.hechler.patrick.objects.BigIter;
import de.hechler.patrick.objects.ModifilableKlasse;
import de.hechler.patrick.objects.VorzubereitenderGepufferterVerteilungsGenerator;
import de.hechler.patrick.objects.Teilnehmer;
import de.hechler.patrick.objects.Verteilung;

/**
 * Die Optimierung reduziert die Anzahl Möglichkeiten durch zwei Iteratoren. Einen für die Erstwünsche und einen für die Zweitwünsche.
 * 
 * @author Patrick
 *
 */
public class OptimierterVerteilungsGeneratorBACKUP extends VorzubereitenderGepufferterVerteilungsGenerator {
	
	private BigIter <Teilnehmer> erstWunschIter;
	private BigIter <Teilnehmer> zweitWunschIter;
	private Verteilung fest;
	private Set <Teilnehmer> aktuelleErstTeilnehmer;
	private Set <Teilnehmer> einzelnErstTeilnehmer;
	
	
	
	public OptimierterVerteilungsGeneratorBACKUP(ModifilableKlasse modifilableKlasse) {
		super(modifilableKlasse, false);
	}
	
	
	
	@Override
	public void vorbeitung() {
		einzelnErstTeilnehmer = modifilableKlasse.einzelnErstWunsch();
		einzelnErstTeilnehmer.forEach((Teilnehmer teilnehmer) -> {
			fest.set(teilnehmer.nummer(), teilnehmer.erstWunsch());
		});
		buildErstIter();
		buildZweitIter();
	}
	
	@Override
	protected void generiereVerteilung() {
		if (zweitWunschIter.hasNext()) {
			Set <Integer> tester = nextSmallIteration();
			NavigableSet <Teilnehmer> benutzen = new TreeSet <>();
			for (int i = 1; i < modifilableKlasse.size(); i ++ ) {
				if (fest.get(i) == 0 && !tester.contains(verteilung.get(i)) && !aktuelleErstTeilnehmer.contains(modifilableKlasse.teilnehmer(i))) {
					benutzen.add(modifilableKlasse.teilnehmer(i));
				}
			}
			
			
			
		} else if (erstWunschIter.hasNext()) {
			// TODO machen
			
			
			erstWunschIter.next();
			buildZweitIter();
		} else {
			// Keine weitere Verteilung möglich
			verteilung = null;
		}
	}
	
	private Set <Integer> nextSmallIteration() {
		List <Teilnehmer> diese = zweitWunschIter.next();
		Set <Integer> tester = new HashSet <>();
		diese.forEach((Teilnehmer add) -> tester.add(add.nummer()));
		diese.forEach((Teilnehmer setze) -> verteilung.set(setze.nummer(), setze.zweitWunsch()));
		return tester;
	}
	
	private void buildErstIter() {
		// TODO Auto-generated method stub
		
	}
	
	private void buildZweitIter() {
		// TODO Auto-generated method stub
		
	}
	
}
