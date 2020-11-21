package de.hechler.patrick.objects;

import java.util.Objects;

public class Teilnehmer implements Comparable <Teilnehmer> {
	
	/**
	 * speichert die nummer des {@link Teilnehmer}s.
	 */
	private final int nummer;
	/**
	 * speichert die Wünsche des {@link Teilnehmer}s.
	 */
	private int[] wunsch;
	
	
	
	/**
	 * Erstellt einen neuen {@link Teilnehmer} mit gegebener {@link #nummer} und den gegebenen erst-/zweit-/dritt{@link #wunsch}
	 * 
	 * @param eigeneNummer
	 *            die {@link #nummer} des {@link Teilnehmer}s
	 * @param erstWunsch
	 *            der erstWunsch des {@link Teilnehmer}s
	 * @param zweitWunsch
	 *            der zweitWunsch des {@link Teilnehmer}s
	 * @param drittWunsch
	 *            der drittWunsch des {@link Teilnehmer}s
	 */
	public Teilnehmer(int eigeneNummer, int erstWunsch, int zweitWunsch, int drittWunsch) {
		nummer = eigeneNummer;
		wunsch = new int[] {erstWunsch, zweitWunsch, drittWunsch };
	}
	
	
	
	/**
	 * bewertet mit der {@link Bewertung} {@code bew}, welcher Wunsch bei diesem {@link Teilnehmer} erfüllt wird (wenn kein Wunsch erfüllt wird, bleibt die Bewertung unverändert).
	 * 
	 * @param zugeteilt
	 *            Die Nummer des Geschenks, welches dem {@link Teilnehmer} zugeteilt wurde.
	 * @param bew
	 *            Die {@link Bewertung}, welche ja nach Wunscherfüllung des {@link Teilnehmer}s verändert wird.
	 */
	public void bewerte(int zugeteilt, Bewertung bew) {
		if (zugeteilt == 0) {
			return;
		}
		if (zugeteilt == wunsch[0]) {
			bew.incRichtigeErste();
		}
		if (zugeteilt == wunsch[1]) {
			bew.incRichtigeZweite();
		}
		if (zugeteilt == wunsch[2]) {
			bew.incRichtigeDritte();
		}
	}
	
	/**
	 * Gibt den erstWunsch zurück
	 * 
	 * @return Der erstWunsch des {@link Teilnehmer}s
	 */
	public int erstWunsch() {
		return wunsch[0];
	};
	
	/**
	 * Gibt den zweitWunsch zurück
	 * 
	 * @return Der zweitWunsch des {@link Teilnehmer}s
	 */
	public int zweitWunsch() {
		return wunsch[1];
	};
	
	/**
	 * Gibt den drittWunsch zurück
	 * 
	 * @return Der drittWunsch des {@link Teilnehmer}s
	 */
	public int drittWunsch() {
		return wunsch[2];
	};
	
	/**
	 * Gibt die {@link #nummer} des {@link Teilnehmer}s zurück
	 * 
	 * @return Die {@link #nummer} des {@link Teilnehmer}s
	 */
	public int nummer() {
		return nummer;
	}
	
	@Override
	public int hashCode() {
		return nummer;
	}
	
	public void deleteErstWunsch() {
		this.wunsch[0] = 0;
	}
	
	public void deleteZweitWunsch() {
		this.wunsch[1] = 0;
	}
	
	public void deleteDrittWunsch() {
		this.wunsch[2] = 0;
	}
	
	public boolean keinWunsch() {
		return wunsch[0] == 0 && wunsch[1] == 0 && wunsch[2] == 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( ! (obj instanceof Teilnehmer)) {
			return false;
		}
		Teilnehmer t = (Teilnehmer) obj;
		if (t.nummer != nummer) {
			return false;
		}
		return Objects.deepEquals(t.wunsch, wunsch);
	}
	
	/**
	 * Gibt einen String zurück, welcher diesen {@link Teilnehmer} darstellt. <br>
	 * 
	 * @implNote Gibt das gleiche zurück, wie: <br>
	 *           <code>T[#" + {@link #nummer} + ":" + {@link #erstWunsch()} + "," + {@link #zweitWunsch()} + "," + {@link #drittWunsch()} + "]"</code>
	 * @return Diesen {@link Teilnehmer} als String
	 */
	@Override
	public String toString() {
		return "T[#" + nummer + ":" + wunsch[0] + "," + wunsch[1] + "," + wunsch[2] + "]";
	}
	
	@Override
	public int compareTo(Teilnehmer other) {
		return Integer.compare(nummer, other.nummer);
	}
	
}
