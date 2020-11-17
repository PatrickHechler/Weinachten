package de.hechler.patrick.objects;

/**
 * Puffert die nächste Verteilung, zur optimierten hasNext() Abfrage. Die Intelligenz der Vorverarbeitung wird hier noch nicht implementiert.
 * 
 * @author Patrick
 *
 */
public abstract class VorzubereitenderGepufferterVerteilungsGenerator extends VerteilungsGenerator {
	
	/**
	 * speichert, ob bereits eine neue {@link Verteilung} generiert wurde oder ob eine neue generiert werden muss.
	 */
	private boolean puffer;
	
	
	
	protected VorzubereitenderGepufferterVerteilungsGenerator(Klasse klasse, boolean richtigeStartVerteilung) {
		super(klasse, richtigeStartVerteilung);
	}
	
	
	
	@Override
	public final boolean hasNext() {
		if (puffer) {
			return true;
		}
		generiereVerteilung();
		puffer = verteilung != null;
		return puffer;
	}
	
	@Override
	public final Verteilung next() {
		if (puffer) {
			puffer = false;
		} else {
			generiereVerteilung();
		}
		return verteilung;
	}
	
	/**
	 * Generiert eine neue {@link Verteilung} und speichert diese in {@link VerteilungsGenerator#verteilung}. <br>
	 * Wenn keine weitere {@link Verteilung} mehr generiert werden kann wird {@link VerteilungsGenerator#verteilung} auf <code>null</code> gesetzt.
	 */
	protected abstract void generiereVerteilung();
	
	/**
	 * Bereitet den {@link VorzubereitenderGepufferterVerteilungsGenerator} darauf vor {@link Verteilung}en zu generieren. <br>
	 * Ziel ist es die Anzahl Verteilungen möglichst zu reduzieren.
	 */
	public abstract void vorbeitung();
	
	@Override
	public Verteilung besteVerbleibende() {
		vorbeitung();
		return super.besteVerbleibende();
	}
	
}
