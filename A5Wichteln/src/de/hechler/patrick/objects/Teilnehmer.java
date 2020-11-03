package de.hechler.patrick.objects;

public class Teilnehmer {
	
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
		if (eigeneNummer < 1 || erstWunsch < 1 || zweitWunsch < 1 || drittWunsch < 1) {
			throw new RuntimeException("Die folgenden vars müssen min. 1 sein: eigenNummer = " + eigeneNummer + ", erstWunsch = " + erstWunsch + ", zweitWunsch = " + zweitWunsch
					+ ", drittWunsch = " + drittWunsch + "!");
		}
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
	
	/**
	 * Gibt einen String zurück, welcher diesen {@link Teilnehmer} darstellt. <br>
	 * 
	 * @implNote Gibt das gleiche zurück, wie: <br>
	 *           <code>T[#" + {@link #nummer} + ":" + {@link #erstWunsch()} + "," + {@link #zweitWunsch()} + "," + {@link #drittWunsch()} + "]"</code>
	 *           @return Diesen {@link Teilnehmer} als String
	 */
	@Override
	public String toString() {
		return "T[#" + nummer + ":" + wunsch[0] + "," + wunsch[1] + "," + wunsch[2] + "]";
	}
	
}
