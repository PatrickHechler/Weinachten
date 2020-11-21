package de.hechler.patrick.objects.generatorenimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import de.hechler.patrick.objects.BigIter;
import de.hechler.patrick.objects.Klasse;
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
	/**
	 * Speichert die erst-Wünsche der {@link Teilnehmer} in {@link #einzelnErst}.
	 */
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
	/**
	 * Speichert die erst-Wünsche der {@link Teilnehmer} in {@link #aktuelleErstTeilnehmer}
	 */
	private Set <Integer> aktuelleErstWunschNummern;
	/**
	 * Speichert die zweit-Wünsche der {@link Teilnehmer} in {@link #aktuelleZweitTeilnehmer}
	 */
	private Set <Integer> aktuelleZweitWunschNummern;
	/**
	 * Speichert die dritt-Wünsche der {@link Teilnehmer} in {@link #aktuelleDrittTeilnehmer}
	 */
	private Set <Integer> aktuelleDrittWunschNummern;
	private Boolean vorbereitet;
	
	
	
	public OptimierterVerteilungsGenerator(Klasse klasse) {
		super(klasse, false);
	}
	
	
	
	@Override
	public void vorbeitung() {
		einzelnErst = klasse.einzelnErstWunsch();
		einzelnErstNummern = new HashSet <Integer>();
		for (Teilnehmer eindeutigerErstWunsch : einzelnErst) {
			einzelnErstNummern.add(eindeutigerErstWunsch.erstWunsch());
			verteilung.set(eindeutigerErstWunsch.nummer(), eindeutigerErstWunsch.erstWunsch());
		}
		klasse.removeAll(einzelnErst);
		vorbereitet = false;
		if (verteilung.isValid()) {
			vorbereitet = true;
			return;
		}
		buildErstIter();
	}
	
	@Override
	protected void generiereVerteilung() {
		if (vorbereitet == null) {
			verteilung = null;
			return;
		}
		if (vorbereitet) {
			vorbereitet =  null;
			return;
		}
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
		for (int i = 1; i < klasse.size(); i ++ ) {
			if ( ! (einzelnErstNummern.contains(i) || aktuelleErstWunschNummern.contains(i) || aktuelleZweitWunschNummern.contains(i))) {
				List <Teilnehmer> wunschVonI = klasse.drittWunschVon(i, aktuelleErstTeilnehmer, aktuelleZweitTeilnehmer);
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
		for (int i = 1; i <= klasse.size(); i ++ ) {
			nummern.add(i);
		}
		einzelnErst.forEach(t -> nummern.remove(t.erstWunsch()));
		nummern.removeAll(aktuelleErstWunschNummern);
		nummern.removeAll(aktuelleZweitWunschNummern);
		nummern.removeAll(aktuelleDrittWunschNummern);
		Iterator <Integer> iter = nummern.iterator();
		for (Teilnehmer teilnehmer : klasse) {
			if ( ! (aktuelleErstTeilnehmer.contains(teilnehmer) || aktuelleZweitTeilnehmer.contains(teilnehmer) || aktuelleDrittTeilnehmer.contains(teilnehmer))) {
				verteilung.set(teilnehmer.nummer(), iter.next());
			}
		}
	}
	
	private void buildErstIter() {
		List <List <Teilnehmer>> build = new ArrayList <List <Teilnehmer>>();
		for (int i = 1; i <= klasse.size(); i ++ ) {
			List <Teilnehmer> wunschVonI = klasse.erstWunschVon(i);
			if ( !wunschVonI.isEmpty()) {
				build.add(wunschVonI);
			}
		}
		erstWunschIter = BigIter.create(build);
	}
	
	private void buildZweitIter() {
		List <List <Teilnehmer>> build = new ArrayList <List <Teilnehmer>>();
		for (int i = 1; i <= klasse.size(); i ++ ) {
			if ( !einzelnErstNummern.contains(i) && !aktuelleErstWunschNummern.contains(i)) {
				List <Teilnehmer> wunschVonI = klasse.zweitWunschVon(i, aktuelleErstTeilnehmer);
				if ( !wunschVonI.isEmpty()) {
					build.add(wunschVonI);
				}
			}
		}
		zweitWunschIter = BigIter.create(build);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Klasse orig;
		String pathname = "./beispieldaten/wichteln3.txt";
		orig = Klasse.lade(new FileInputStream(new File(pathname)));
		System.out.println("SIZE:" + orig.size());
		System.out.println("OPT:");
		Klasse optKl;
		optKl = Klasse.lade(new FileInputStream(new File(pathname)));
		// kl = ModifilableKlasse.lade(new FileInputStream(new File("./beispieldaten/simple1.txt")));
		OptimierterVerteilungsGenerator opt = new OptimierterVerteilungsGenerator(optKl);
		Verteilung optVer = opt.besteVerbleibende();
		optVer.print();
		System.out.println();
		System.out.println("BRU:");
		Klasse bruKl;
		bruKl = Klasse.lade(new FileInputStream(new File(pathname)));
		BruthForceVerteilungsGenerator bru = new BruthForceVerteilungsGenerator(bruKl);
		Verteilung bruVer = bru.besteVerbleibende();
		bruVer.print();
		System.out.println(Objects.equals(optVer, bruVer));
		System.out.println("OPT:" + orig.bewerte(optVer));
		System.out.println("BRU:" + orig.bewerte(bruVer));
	}
	
}
