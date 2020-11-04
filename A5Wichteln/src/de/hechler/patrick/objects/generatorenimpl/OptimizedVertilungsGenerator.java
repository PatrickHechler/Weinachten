package de.hechler.patrick.objects.generatorenimpl;

import java.util.Set;

import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.Verteilung;
import de.hechler.patrick.objects.VerteilungsGenerator;


public class OptimizedVertilungsGenerator extends VerteilungsGenerator {
	
	private int deep;
	private Verteilung fest;
	
	
	protected OptimizedVertilungsGenerator(Klasse klasse) {
		super(klasse, false);
		fest = new Verteilung(klasse.size(), false);
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Verteilung next() {
		switch (deep) {
		case 0:
			Set <Integer> einzelnErstWunsch = klasse.einzelnErstWunsch();
			for (Integer festlegen : einzelnErstWunsch) {
				verteilung.set(festlegen, klasse.teilnehmer(festlegen).erstWunsch());
				fest.set(festlegen, klasse.teilnehmer(festlegen).erstWunsch());
			}
			for (int i = 1; i <= klasse.size(); i ++ ) {
				
			}
			deep ++ ;
			break;
		
		default:
			throw new RuntimeException("Unbekannte tiefe!");
		}
		
		
		return null;
	}
	
}
