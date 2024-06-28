package azienda.Smartphone;

import azienda.Smartphone.Eccezioni.EccezioneSmartphone;

/**
 * Classe Smartphone Base (Acquistabile e non noleggiabile) che estende la classe AbstractSmartphone
 * @author Gabriel Cellammare
 *
 */
public class SmartphoneBase extends AbstractSmartphone implements SmartphoneAcquistabile{

	/**
	 * 
	 */
	private static final long serialVersionUID = 903328251479636379L;

	public SmartphoneBase(String codiceImei, SistemaOperativo sistemaOperativo, String modello,
			int peso, double pollici, int mAh, int megapixelFrontale, int megapixelPrincipale, int ram,
			Colore colore, String processore, double prezzo) throws EccezioneSmartphone {
		super(codiceImei, sistemaOperativo,modello, peso, pollici, mAh, megapixelFrontale, megapixelPrincipale, ram,
				colore, processore, prezzo);
	}

}
