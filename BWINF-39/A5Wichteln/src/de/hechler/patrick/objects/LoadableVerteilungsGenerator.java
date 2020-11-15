package de.hechler.patrick.objects;

public abstract class LoadableVerteilungsGenerator extends VerteilungsGenerator {
	
	protected LoadableVerteilungsGenerator(Klasse klasse, boolean richtigeStartVerteilung) {
		super(klasse, richtigeStartVerteilung);
	}
	
	
	
	/**
	 * lädt den {@link LoadableVerteilungsGenerator}, damit dieser {@link Verteilung}en generieren kann.
	 */
	public abstract void load();
	
	@Override
	public Verteilung besteVerbleibende() {
		load();
		return super.besteVerbleibende();
	}
	
}
