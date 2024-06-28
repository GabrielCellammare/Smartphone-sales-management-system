package azienda.Smartphone;

/**
 * Interfaccia che viene implementata soltanto da Smartphone Base e Avanzato
 * Permette a queste due tipologie di smartphone di essere acquistati
 * @author Gabriel Cellammare
 *
 */
public interface SmartphoneAcquistabile {
	public double getPrezzo();
	public String getCodiceImei();
	public boolean isDisponibile();
	public void setDisponibile(boolean disponibile);
	public String toString();
}
