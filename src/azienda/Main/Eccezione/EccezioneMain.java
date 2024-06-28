package azienda.Main.Eccezione;

/**
 * Classe EccezioneMain utilizzata per sollevare eccezioni specifiche della classe "Main.java"
 * 
 * @author Gabriel Cellammare
 *
 */

public class EccezioneMain extends Exception {

	private String msg;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EccezioneMain(String msg) {
		super();
		this.msg = msg;
	}
	/**
	 * Stampa del messaggio d'eccezone
	 * @author Gabriel cellammare
	 */
	@Override
	public String toString() {
		return "EccezioneMain [msg = " + msg + "]\n[Attenzione, a causa dell'eccezione, nessuna modifica e' stata salvata]";
		
	}
	
	

}
