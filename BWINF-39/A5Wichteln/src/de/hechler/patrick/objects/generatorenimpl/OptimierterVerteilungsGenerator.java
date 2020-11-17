package de.hechler.patrick.objects.generatorenimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.hechler.patrick.objects.BigIter;
import de.hechler.patrick.objects.ModifilableKlasse;
import de.hechler.patrick.objects.Teilnehmer;
import de.hechler.patrick.objects.Verteilung;
import de.hechler.patrick.objects.VorzubereitenderGepufferterVerteilungsGenerator;

/**
 * Die Optimierung reduziert die Anzahl Möglichkeiten durch zwei Iteratoren. Einen für die Erstwünsche und einen für die Zweitwünsche.
 * 
 * @author Patrick
 *
 */
public class OptimierterVerteilungsGenerator extends VorzubereitenderGepufferterVerteilungsGenerator {
	
	/**
	 * Iteriert durch alle erst-Wunsch-Kombinationen durch
	 */
	private BigIter <Teilnehmer> erstWunschIter;
	/**
	 * Iteriert durch alle zweitWunsch-Kombinationen der derzeitigen erst-Wunsch-Kombination durch
	 */
	private BigIter <Teilnehmer> zweitWunschIter;
	/**
	 * Speichert die {@link Teilnehmer}, welche einen eindeutigen erst-Wunsch haben.
	 */
	private Set <Teilnehmer> einzelnErst;
	private Set <Integer> einzelnErstNummern;
	/**
	 * Speichert die aktuellen erst-Wunsch-Teilnehmer der aktuellen Kombination. Die letzte Rückgabe von {@link #erstWunschIter}.
	 */
	private Set <Teilnehmer> aktuelleErstTeilnehmer;
	/**
	 * Speichert die aktuellen zweit-Wunsch-Teilnehmer der aktuellen Kombination. Die letzte Rückgabe von {@link #zweitWunschIter}.
	 */
	private Set <Teilnehmer> aktuelleZweitTeilnehmer;
	/**
	 * Speichert die aktuellen dritt-Wunsch-Teilnehmer der aktuellen Kombination.
	 */
	private HashSet <Teilnehmer> aktuelleDrittTeilnehmer;
	private Set <Integer> aktuelleErstWunschNummern;
	private Set <Integer> aktuelleZweitWunschNummern;
	private Set <Integer> aktuelleDrittWunschNummern;
	
	
	
	public OptimierterVerteilungsGenerator(ModifilableKlasse modifilableKlasse) {
		super(modifilableKlasse, false);
	}
	
	
	
	@Override
	public void vorbeitung() {
		einzelnErst = modifilableKlasse.einzelnErstWunsch();
		einzelnErstNummern = new HashSet <Integer>();
		for (Teilnehmer eindeutigerErstWunsch : einzelnErst) {
			einzelnErstNummern.add(eindeutigerErstWunsch.erstWunsch());
			verteilung.set(eindeutigerErstWunsch.nummer(), eindeutigerErstWunsch.erstWunsch());
		}
		modifilableKlasse.removeAll(einzelnErst);
		
		buildErstIter();
	}
	
	@Override
	protected void generiereVerteilung() {
		if (zweitWunschIter != null && zweitWunschIter.hasNext()) {
			zweit();
		} else if (erstWunschIter.hasNext()) {
			aktuelleErstTeilnehmer = new HashSet <Teilnehmer>(erstWunschIter.next());
			aktuelleErstWunschNummern = new HashSet <Integer>();
			for (Teilnehmer aktuell : aktuelleErstTeilnehmer) {
				aktuelleErstWunschNummern.add(aktuell.erstWunsch());
				verteilung.set(aktuell.nummer(), aktuell.erstWunsch());
			}
			buildZweitIter();
			if (zweitWunschIter.hasNext()) {
				zweit();
			} else {
				aktuelleZweitTeilnehmer = Collections.emptySet();
				aktuelleZweitWunschNummern = Collections.emptySet();
				dritt();
			}
		} else {
			// Keine weitere Verteilung möglich
			verteilung = null;
		}
	}
	
	private void zweit() {
		aktuelleZweitTeilnehmer = new HashSet <Teilnehmer>(zweitWunschIter.next());
		aktuelleZweitWunschNummern = new HashSet <Integer>();
		for (Teilnehmer aktuell : aktuelleZweitTeilnehmer) {
			aktuelleZweitWunschNummern.add(aktuell.zweitWunsch());
			verteilung.set(aktuell.nummer(), aktuell.zweitWunsch());
		}
		dritt();
	}
	
	private void dritt() {
		aktuelleDrittTeilnehmer = new HashSet <Teilnehmer>();
		aktuelleDrittWunschNummern = new HashSet <Integer>();
		for (int i = 1; i < modifilableKlasse.size(); i ++ ) {
			if ( ! (einzelnErstNummern.contains(i) || aktuelleErstWunschNummern.contains(i) || aktuelleZweitWunschNummern.contains(i))) {
				List <Teilnehmer> wunschVonI = modifilableKlasse.drittWunschVon(i, aktuelleErstTeilnehmer, aktuelleZweitTeilnehmer);
				if ( !wunschVonI.isEmpty()) {
					Teilnehmer dieser = wunschVonI.get(0);
					aktuelleDrittTeilnehmer.add(dieser);
					aktuelleDrittWunschNummern.add(dieser.drittWunsch());
					verteilung.set(dieser.nummer(), dieser.drittWunsch());
				}
			}
		}
		pech();
	}
	
	private void pech() {
		Set <Integer> nummern = new HashSet <Integer>();
		for (int i = 1; i <= modifilableKlasse.size(); i ++ ) {
			nummern.add(i);
		}
		einzelnErst.forEach(t -> nummern.remove(t.erstWunsch()));
		nummern.removeAll(aktuelleErstWunschNummern);
		nummern.removeAll(aktuelleZweitWunschNummern);
		nummern.removeAll(aktuelleDrittWunschNummern);
		Iterator <Integer> iter = nummern.iterator();
		for (Teilnehmer teilnehmer : modifilableKlasse) {
			if ( ! (aktuelleErstTeilnehmer.contains(teilnehmer) || aktuelleZweitTeilnehmer.contains(teilnehmer) || aktuelleDrittTeilnehmer.contains(teilnehmer))) {
				verteilung.set(teilnehmer.nummer(), iter.next());
			}
		}
	}
	
	private void buildErstIter() {
		List <List <Teilnehmer>> build = new ArrayList <List <Teilnehmer>>();
		for (int i = 1; i <= modifilableKlasse.size(); i ++ ) {
			List <Teilnehmer> wunschVonI = modifilableKlasse.erstWunschVon(i);
			if ( !wunschVonI.isEmpty()) {
				build.add(wunschVonI);
			}
		}
		erstWunschIter = BigIter.create(build);
	}
	
	private void buildZweitIter() {
		List <List <Teilnehmer>> build = new ArrayList <List <Teilnehmer>>();
		for (int i = 1; i <= modifilableKlasse.size(); i ++ ) {
			if ( !einzelnErstNummern.contains(i) && !aktuelleErstWunschNummern.contains(i)) {
				List <Teilnehmer> wunschVonI = modifilableKlasse.zweitWunschVon(i, aktuelleErstTeilnehmer);
				if ( !wunschVonI.isEmpty()) {
					build.add(wunschVonI);
				}
			}
		}
		zweitWunschIter = BigIter.create(build);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		ModifilableKlasse kl;
		kl = ModifilableKlasse.lade(new FileInputStream(new File("./beispieldaten/wichteln1.txt")));
		// kl = ModifilableKlasse.lade(new FileInputStream(new File("./beispieldaten/simple1.txt")));
		OptimierterVerteilungsGenerator opvg = new OptimierterVerteilungsGenerator(kl);
		Verteilung bv = opvg.besteVerbleibende();
		bv.print();
	}
	
}
