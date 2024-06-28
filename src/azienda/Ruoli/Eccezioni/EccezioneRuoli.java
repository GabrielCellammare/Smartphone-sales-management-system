package azienda.Ruoli.Eccezioni;

/**
 * 
 * Classe EccezioneRuoli utilizzata per sollevare eccezioni specifiche delle classi che rappresentano i ruoli
 * 
 * @author Gabriel Cellammare
 *
 */

public class EccezioneRuoli extends Exception {
	
	private String msg;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public EccezioneRuoli(String msg) {
		super();
		this.msg = msg;
	}


	@Override
	/**
	 * Stampa del messaggio d'eccezione
	 */
	public String toString() {
		return "EccezioneRuoli [msg = " + msg + "]\n[Attenzione, a causa dell'eccezione, nessuna modifica e' stata salvata]";
	}
	
	

}
