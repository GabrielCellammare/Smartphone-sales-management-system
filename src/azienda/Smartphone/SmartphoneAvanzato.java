package azienda.Smartphone;

import azienda.Smartphone.Eccezioni.EccezioneSmartphone;

/**
 * Classe Smartphone Avanzato (Acquistabile e noleggiabile) che, possiede tutti gli attributi di uno smartphone base (Estende la classe AbstractSmartphone)
 * E inoltre possiede il riconoscimento facciale e tramite impronta
 * @author Gabriel Cellammare
 *
 */
public class SmartphoneAvanzato extends AbstractSmartphone implements SmartphoneAcquistabile{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5792380771653189579L;
	
	private boolean RiconoscimentoFacciale = false;
	private boolean RiconoscimentoImpronta = false;
	
	
	public SmartphoneAvanzato(String codiceImei, SistemaOperativo sistemaOperativo, String modello,
			int peso, double pollici, int mAh, int megapixelFrontale, int megapixelPrincipale, int ram,
			Colore colore, String processore, double prezzo) throws EccezioneSmartphone {
		super(codiceImei, sistemaOperativo, modello, peso, pollici, mAh, megapixelFrontale, megapixelPrincipale, ram,
				colore, processore, prezzo);
		
		this.RiconoscimentoFacciale=true;
		this.RiconoscimentoImpronta=true;
	}

	/**
	 * Metodo getter della variabile RiconoscimentoFacciale 
	 * @return RiconoscimentoFacciale [Variabile di tipo boolean che permette di conoscere la presenza o meno del riconoscimento facciale]
	 */
	public boolean isRiconoscimentoFacciale() {
		return RiconoscimentoFacciale;
	}
	
	/**
	 * Metodo getter della variabile RiconoscimentoImpronta 
	 * @return RiconoscimentoImpronta [Variabile di tipo boolean che permette di conoscere la presenza o meno del riconoscimento tramite impronta]
	 */
	public boolean isRiconoscimentoImpronta() {
		return RiconoscimentoImpronta;
	}

	@Override
	public String toString() {
		return "\n\nSmartphone avanzato \nRiconoscimento Facciale = " + RiconoscimentoFacciale + "\nImpronta digitale = "
				+ RiconoscimentoImpronta + "\nCaratteristiche standard dello smartphone: \n" + super.toString();
	}
	
	

}
