package de.hechler.patrick.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RestKlasse extends Klasse {
	
	private RestKlasse(List <Teilnehmer> teilnehmer, int size) {
		super(teilnehmer, size);
	}
	
	
	
	public static RestKlasse create(KlasseInterface von) {
		List <Teilnehmer> erg;
		erg = new ArrayList <Teilnehmer>();
		for (Teilnehmer dieser : von) {
			Teilnehmer add = new RestTeilnehmer(dieser);
			erg.add(add);
		}
		return new RestKlasse(erg, von.size());
	}
	
	
	
	/**
	 * Gibt eine empyList von {@link Collections#emptyList()} zurück, da es in der {@link RestKlasse} keine drittWünsche gibt!
	 * 
	 * @return {@link Collections#emptyList()}
	 */
	@Override
	public List <Teilnehmer> drittWunschVon(int gegenstandsNummer, Set <Teilnehmer> ignore1, Set <Teilnehmer> ignore2) {
		return Collections.emptyList();
	}
	
	/**
	 * Gibt eine empyList von {@link Collections#emptyList()} zurück, da es in der {@link RestKlasse} keine drittWünsche gibt!
	 * 
	 * @return {@link Collections#emptyList()}
	 */
	@Override
	public Set <Integer> drittWunsch() {
		return Collections.emptySet();
	}
	
	/**
	 * Gibt eine empyList von {@link Collections#emptyList()} zurück, da es in der {@link RestKlasse} keine drittWünsche gibt!
	 * 
	 * @return {@link Collections#emptyList()}
	 */
	@Override
	public Set <Integer> drittWunsch(Set <Integer> ignore1, Set <Integer> ignore2) {
		return Collections.emptySet();
	}
	
	/**
	 * Gibt eine empyList von {@link Collections#emptyList()} zurück, da es in der {@link RestKlasse} keine drittWünsche gibt!
	 * 
	 * @return {@link Collections#emptyList()}
	 */
	@Override
	public List <Teilnehmer> drittWunschVon(int gegenstandsNummer) {
		return Collections.emptyList();
	}
	
	
	
	private static class RestTeilnehmer extends Teilnehmer {
		
		public RestTeilnehmer(Teilnehmer ursprung) {
			super(ursprung.nummer(), ursprung.zweitWunsch(), ursprung.drittWunsch(), 0);
		}
		
	}
	
}
