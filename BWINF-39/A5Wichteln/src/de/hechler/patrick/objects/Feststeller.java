package de.hechler.patrick.objects;


public interface Feststeller {
	
	/**
	 * setzt alle ungesetzten stellen der {@link Verteilung} <code>feststellen</code>, welche fest stehen.
	 * 
	 * @param feststellen
	 *            die zu verändernde {@link Verteilung}
	 * @param klasseInterface
	 *            die zu der {@link Verteilung} <code>feststellen</code> gehörende Klasse.
	 */
	void stelleFest(Verteilung feststellen, Klasse klasse);
	
	/**
	 * erstellt eine neue {@link Verteilung} und setzt dort alle bei der {@link KlasseInterface} <code>klasse</code> feststehenden Zuordnungen und gibt dann diese {@link Verteilung} zurück.
	 * 
	 * @param klasseInterface
	 *            Die {@link KlasseInterface} zu der die feststehenden Zuordnungen gesucht werden.
	 * @return Die erstellte {@link Verteilung} mit den festen Zuordnungen.
	 */
	Verteilung stelleFest(Klasse klasse);
	
}
