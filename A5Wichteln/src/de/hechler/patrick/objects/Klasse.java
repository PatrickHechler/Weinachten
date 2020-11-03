package de.hechler.patrick.objects;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Klasse {
	
	private Teilnehmer[] teilnehmer;
	
	
	
	private Klasse(int size) {
		teilnehmer = new Teilnehmer[size];
	}
	
	public static Klasse lade(InputStream in) {
		return lade(new Scanner(in));
	}
	
	private static Klasse lade(Scanner eingang) {
		Klasse ergebnis;
		int zwischenA;
		int zwischenB;
		int zwischenC;
		zwischenA = eingang.nextInt();
		ergebnis = new Klasse(zwischenA);
		for (int runde = 0; runde < ergebnis.teilnehmer.length; runde ++ ) {
			zwischenA = eingang.nextInt();
			zwischenB = eingang.nextInt();
			zwischenC = eingang.nextInt();
			ergebnis.teilnehmer[runde] = new Teilnehmer(runde + 1, zwischenA, zwischenB, zwischenC);
		}
		return ergebnis;
	}
	
	
	
	public int size() {
		return teilnehmer.length;
	}
	
	public Teilnehmer teilnehmer(int teilnehmerNummer) {
		return teilnehmer[teilnehmerNummer - 1];
	}
	
	public Set <Integer> einzelnErstWunsch() {
		Set <Integer> ergebnis;
		ergebnis = new HashSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if (ergebnis.contains(teste.erstWunsch())) {
				ergebnis.remove(teste.erstWunsch());
			} else {
				ergebnis.add(teste.erstWunsch());
			}
		}
		return ergebnis;
	}
	
	public Set <Integer> einzelnZweitWunsch() {
		Set <Integer> ergebnis;
		ergebnis = new HashSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if (ergebnis.contains(teste.zweitWunsch())) {
				ergebnis.remove(teste.zweitWunsch());
			} else {
				ergebnis.add(teste.zweitWunsch());
			}
		}
		return ergebnis;
	}
	
	public Set <Integer> einzelnZweitWunsch(Set <Teilnehmer> ignore) {
		Set <Integer> ergebnis;
		ergebnis = new HashSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !ignore.contains(teste)) {
				if (ergebnis.contains(teste.zweitWunsch())) {
					ergebnis.remove(teste.zweitWunsch());
				} else {
					ergebnis.add(teste.zweitWunsch());
				}
			}
		}
		return ergebnis;
	}
	
	public Set <Integer> einzelnDrittWunsch() {
		Set <Integer> ergebnis;
		ergebnis = new HashSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if (ergebnis.contains(teste.drittWunsch())) {
				ergebnis.remove(teste.drittWunsch());
			} else {
				ergebnis.add(teste.drittWunsch());
			}
		}
		return ergebnis;
	}
	
	public Set <Integer> einzelnDrittWunsch(Set <Teilnehmer> ignore1, Set <Teilnehmer> ignore2) {
		Set <Integer> ergebnis;
		ergebnis = new HashSet <Integer>();
		for (Teilnehmer teste : teilnehmer) {
			if ( !ignore1.contains(teste) && !ignore2.contains(teste)) {
				if (ergebnis.contains(teste.drittWunsch())) {
					ergebnis.remove(teste.drittWunsch());
				} else {
					ergebnis.add(teste.drittWunsch());
				}
			}
		}
		return ergebnis;
	}
	
	public List <Teilnehmer> habenErstWunsch(int gegenstand) {
		List <Teilnehmer> ergebnis;
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : ergebnis) {
			if (gegenstand == teste.erstWunsch()) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
		
	}
	
	public List <Teilnehmer> habenZweitWunsch(int gegenstand) {
		List <Teilnehmer> ergebnis;
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : ergebnis) {
			if (gegenstand == teste.zweitWunsch()) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
		
	}
	
	public List <Teilnehmer> habenDrittWunsch(int gegenstand) {
		List <Teilnehmer> ergebnis;
		ergebnis = new ArrayList <Teilnehmer>();
		for (Teilnehmer teste : ergebnis) {
			if (gegenstand == teste.drittWunsch()) {
				ergebnis.add(teste);
			}
		}
		return ergebnis;
		
	}
	
	public Bewertung bewerte(Verteilung verteilung) {
		Bewertung ergebnis;
		ergebnis = new Bewertung();
		for (Teilnehmer bewerten : teilnehmer) {
			bewerten.bewerte(verteilung.get(bewerten.nummer()), ergebnis);
		}
		return ergebnis;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(Integer.toString(size())).append("\n");
		for (int i=1; i<=size(); i++) {
			Teilnehmer t = teilnehmer(i);
			result.append(Integer.toString(t.erstWunsch())).append(" ");
			result.append(Integer.toString(t.zweitWunsch())).append(" ");
			result.append(Integer.toString(t.drittWunsch())).append("\n");
		}
		return result.toString();
	}
	
}
