package de.hechler.patrick.objects;

import java.util.Objects;

public class Bewertung implements Comparable <Bewertung> {
	
	/**
	 * speichert die anzahl an erfüllten Erst-/Zweit-Drittwünschen. <br>
	 * Erstwünsche: {@code [0]} | Zweitwünsche: {@code [1]} | Drittwünsche: {@code [2]}
	 */
	private int[] richtigePlatzierungen;
	
	
	
	/**
	 * erstellt eine neue {@link Bewertung}, bei welcher noch kein Wunsch erfüllt ist.
	 */
	public Bewertung() {
		richtigePlatzierungen = new int[3];
	}
	
	
	
	/**
	 * erhöht die Anzahl der erfüllten Erstwünsche um 1
	 */
	public void incRichtigeErste() {
		richtigePlatzierungen[0] ++ ;
	}
	
	/**
	 * erhöht die Anzahl der erfüllten Zweitwünsche um 1
	 */
	public void incRichtigeZweite() {
		richtigePlatzierungen[1] ++ ;
	}
	
	/**
	 * erhöht die Anzahl der erfüllten Drittwünsche um 1
	 */
	public void incRichtigeDritte() {
		richtigePlatzierungen[2] ++ ;
	}
	
	@Override
	public int compareTo(Bewertung other) {
		if (other == null) {
			return 1;
		}
		if (richtigePlatzierungen[0] == other.richtigePlatzierungen[0]) {
			if (richtigePlatzierungen[1] == other.richtigePlatzierungen[1]) {
				if (richtigePlatzierungen[2] == other.richtigePlatzierungen[2]) {
					return 0;
				}
				return richtigePlatzierungen[2] > other.richtigePlatzierungen[2] ? 1 : -1;
			}
			return richtigePlatzierungen[1] > other.richtigePlatzierungen[1] ? 1 : -1;
		}
		return richtigePlatzierungen[0] > other.richtigePlatzierungen[0] ? 1 : -1;
	}
	
	@Override
	public String toString() {
		return richtigePlatzierungen[0] + "." + richtigePlatzierungen[1] + "." + richtigePlatzierungen[2];
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Bewertung) {
			return Objects.deepEquals(richtigePlatzierungen, ((Bewertung) obj).richtigePlatzierungen);
		}
		return false;
	}



	public long getRichtigeErste() {
		return richtigePlatzierungen[0];
	}
	public long getRichtigeZweite() {
		return richtigePlatzierungen[1];
	}
	public long getRichtigeDritte() {
		return richtigePlatzierungen[2];
	}
	
}
