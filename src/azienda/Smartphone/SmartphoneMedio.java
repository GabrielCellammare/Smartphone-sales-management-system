package azienda.Smartphone;


import azienda.Smartphone.Eccezioni.EccezioneSmartphone;

/**
 * Classe Smartphone Medio (Non Acquistabile e non  noleggiabile) che, possiede tutti gli attributi di uno smartphone base (Estende la classe AbstractSmartphone)
 * e possiede inoltre, una doppia fotocamera
 * @author Gabriel Cellammare
 *
 */
public class SmartphoneMedio extends AbstractSmartphone{

	/**
	 * 
	 */

	private static final long serialVersionUID = 8245904826393372624L;
	private int DoppiaFotocamera=0;


	public SmartphoneMedio(String codiceImei, SistemaOperativo sistemaOperativo, String modello,
			int peso, double pollici, int mAh, int megapixelFrontale, int megapixelPrincipaleF1, int megapixelPrincipaleF2, int ram,
			Colore colore, String processore, double prezzo) throws EccezioneSmartphone {

		super(codiceImei, sistemaOperativo, modello, peso, pollici, mAh, megapixelFrontale, megapixelPrincipaleF1, ram,
				colore, processore, prezzo);

		if(megapixelPrincipaleF2 < 10 || megapixelPrincipaleF2 >50)
			throw new EccezioneSmartphone("Errore, il valore in megaPixel della seconda fotocamera non valido");

		this.DoppiaFotocamera=megapixelPrincipaleF2;



	}

	/**
	 * Metodo che ritorna i MegaPixel della fotocamera aggiuntiva
	 * @return DoppiaFotocamera[Variabile di tipo Int che ritorna il valore della fotocamera espresso in MegaPixel]
	 */
	public int getDoppiaFotocamera() {
		return DoppiaFotocamera;
	}



	@Override
	public String toString() {
		return "\n\nSmartphoneMedio \nDoppia Fotocamera (MegaPixel) = " + DoppiaFotocamera + "\nCaratteristiche standard dello smartphone: \n\n\n" + super.toString();
	}







}
