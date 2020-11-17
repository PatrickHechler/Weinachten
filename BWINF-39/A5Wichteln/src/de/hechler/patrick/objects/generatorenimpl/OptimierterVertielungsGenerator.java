package de.hechler.patrick.objects.generatorenimpl;

import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import de.hechler.patrick.objects.BigIter;
import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.VorzubereitenderGepufferterVerteilungsGenerator;
import de.hechler.patrick.objects.Teilnehmer;
import de.hechler.patrick.objects.Verteilung;

/**
 * Die Optimierung reduziert die Anzahl Möglichkeiten durch zwei Iteratoren. Einen für die Erstwünsche und einen für die Zweitwünsche.
 * 
 * @author Patrick
 *
 */
public class OptimierterVertielungsGenerator extends VorzubereitenderGepufferterVerteilungsGenerator {
	
	private BigIter <Teilnehmer> erstWunschIter;
	private BigIter <Teilnehmer> zweitWunschIter;
	private Verteilung fest;
	private Set <Integer> erstTeilnehmerNummern;
	private Set <Integer> einzelnErstTeilnehmerNummern;
	
	
	
	public OptimierterVertielungsGenerator(Klasse klasse) {
		super(klasse, false);
	}
	
	
	
	@Override
	public void vorbeitung() {
		erstTeilnehmerNummern = klasse.erstWunsch();
		einzelnErstTeilnehmerNummern = klasse.einzelnErstWunsch();
		einzelnErstTeilnehmerNummern.forEach((Integer nummer) -> {
			fest.set(nummer, klasse.teilnehmer(nummer).erstWunsch());
		});
		
		buildErstIter();
		buildZweitIter();
	}
	
	@Override
	protected void generiereVerteilung() {
		if (zweitWunschIter.hasNext()) {
			Set <Integer> tester = nextSmallIteration();
			NavigableSet <Teilnehmer> benutzen = new TreeSet <>();
			for (int i = 1; i < klasse.size(); i ++ ) {
				if (fest.get(i) == 0 && !tester.contains(verteilung.get(i)) && !erstTeilnehmerNummern.contains(i)) {
					benutzen.add(klasse.teilnehmer(i));
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
