package azienda.VenditeNoleggi.Eccezioni;

/**
 * Classe VenditeNoleggiEccezione utilizzata per sollevare eccezioni specifiche delle classi che rappresentano le vendite i noleggi
 * @author Gabriel Cellammare
 *
 */
public class VenditeNoleggiEccezione extends Exception {
	
	private String msg;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VenditeNoleggiEccezione(String msg) {
		super();
		this.msg = msg;
	}

	@Override
	/**
	 * Stampa del messaggio d'eccezione
	 */
	public String toString() {
		return "VenditeNoleggiEccezione [msg = " + msg + "]\n[Attenzione, a causa dell'eccezione, nessuna modifica e' stata salvata]";
	}
	
	

}
