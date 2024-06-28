package azienda.Smartphone.Eccezioni;

/**
 * Classe EccezioneSmartphone utilizzata per sollevare eccezioni specifiche delle classi che rappresentano gli smartphone
 * @author Gabriel Cellammare
 *
 */
public class EccezioneSmartphone extends Exception {

	private String msg;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EccezioneSmartphone(String msg) {
		super();
		this.msg = msg;
	}
	@Override
	
	/**
	 * Stampa del messaggio d'eccezione
	 */
	public String toString() {
		return "EccezioneSmartphone [msg = " + msg + "]\n[Attenzione, a causa dell'eccezione, nessuna modifica e' stata salvata]";
	}
	
	
	
	
	

}
