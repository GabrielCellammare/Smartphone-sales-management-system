package azienda.Smartphone;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import azienda.Smartphone.Eccezioni.EccezioneSmartphone;

/**
 * La classe AbstractSmartphone e' una classe astratta che racchiude tutti gli attributi comuni degli Smartphone di tipo base, medio e avanzato
 * @author Gabriel Cellammare
 *
 */
public class AbstractSmartphone implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -299461820885372831L;


	boolean disponibile;
	/**
	 * Codice utile al riconoscimento da parte degli utenti
	 */
	protected int codice; 
	protected String codiceImei;
	protected SistemaOperativo sistemaOperativo;
	protected String modello;
	protected int peso; //grammi	
	protected double pollici;
	protected int mAh; //batteria
	protected int megapixelFrontale;
	protected int megapixelPrincipale;
	protected int ram; //giga
	protected String processore;
	protected Colore colore;
	protected double prezzo;

	/**
	 * Set di stringhe utile per gestire tutti i codici IMEI di ogni Smartphone
	 */
	protected static Set<String> codiciImei = new HashSet<String>(); //Set per memorizzare tutti gli IMEI
	private static int last_code=0;

	private static final String IMEI = ("[0-9]{15}/[0-9]{2}");

	protected AbstractSmartphone(String codiceImei, SistemaOperativo sistemaOperativo,
			String modello, int peso, double pollici, int mAh, 
			int megapixelFrontale,int megapixelPrincipale, int ram, Colore colore,
			String processore, double prezzo) throws EccezioneSmartphone {

		if(!codiceImei.matches(IMEI)) {
			throw new EccezioneSmartphone("Errore, formato Imei non valido");
		}

		if(!codiciImei.add(codiceImei)) {
			throw new EccezioneSmartphone("Errore, codice Imei gia' esistente");
		}

		if(sistemaOperativo == null) {
			throw new EccezioneSmartphone("Errore, sistema operativo nullo");
		}

		if(modello==null) {
			throw new EccezioneSmartphone("Errore, modello nullo");
		}

		if(peso<150 || peso>1350) {
			throw new EccezioneSmartphone("Errore, peso non valido");
		}

		if(pollici < 3 || pollici >11) {
			throw new EccezioneSmartphone("Errore, misura in pollici errata");
		}

		if(mAh < 1500 || mAh >6500) {
			throw new EccezioneSmartphone("Errore, valori della batteria in mAh non validi");
		}

		if(megapixelFrontale<2 || megapixelFrontale>60) {
			throw new EccezioneSmartphone("Errore, valori della fotocamera frontale in megaPixel non validi");
		}

		if(megapixelPrincipale<10 || megapixelPrincipale>100) {
			throw new EccezioneSmartphone("Errore, valori della fotocamera principale in megaPixel non validi");
		}

		if(ram<2 || ram>16) {
			throw new EccezioneSmartphone("Errore, valori della ram in gigaByte non validi");
		}

		if(colore==null) {
			throw new EccezioneSmartphone("Errore, colore nullo");
		}

		if(processore==null) {
			throw new EccezioneSmartphone("Errore, nome del processore nullo");
		}

		if(prezzo<=0) {
			throw new EccezioneSmartphone("Errore, prezzo dello smartphone non valido");
		}

		this.disponibile=true;
		this.codice = last_code++;
		this.codiceImei=codiceImei;
		this.sistemaOperativo=sistemaOperativo;
		this.modello=modello;
		this.peso=peso;
		this.pollici=pollici;
		this.mAh=mAh;
		this.megapixelFrontale=megapixelFrontale;
		this.megapixelPrincipale=megapixelPrincipale;
		this.ram=ram;
		this.processore=processore;
		this.colore=colore;
		this.prezzo=prezzo;

	}

	/**
	 * Metodo getter della variabile disponibile, che esprime la disponibilita' per l'acquisto o per il noleggio di uno smartphone
	 * @return disponibile [Variabile di tipo boolean che esprime la disponibilita']
	 */
	public boolean isDisponibile() {
		return disponibile;
	}

	/**
	 * Metodo setter della variabile disponibile descritta in precendeza
	 * @param disponibile [Variabile di tipo boolean che esprime la disponibilita']
	 */
	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}

	/**
	 * Metodo getter della variabile che esprime il prezzo d'acquisto
	 * @return prezzo [Variabile di tipo double che esprime il prezzo d'acquisto dello smartphone]
	 */
	public double getPrezzo() {
		return prezzo;
	}

	/**
	 * Metodo setter della variabile prezzo
	 * @param prezzo [Variabile di tipo double che esprime il prezzo d'acquisto dello smartphone]
	 */
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	/**
	 * Metodo getter del codice univoco di ogni smartphone
	 * Attenzione, lo smartphone esprime la sua univocita' solo e soltanto tramite il codice Imei, il codice univoco e' uno strumento utile all'utente
	 * @return codice [Variabile di tipo Int che esprime il codice univoco]
	 */
	public int getCodice() {
		return codice;
	}

	/**
	 * Metodo getter del codice Imei
	 * @return codiceImei [Variaible String che conterra' il codice IMEI]
	 */
	public String getCodiceImei() {
		return codiceImei;
	}

	
	/**
	 * Metodo getter del sistema operativo
	 * @return sistemaOperativo [Variabile di tipo Sistema Operativo]
	 */
	public SistemaOperativo getSistemaOperativo() {
		return sistemaOperativo;
	}


	/**
	 * Metodo getter del modello 
	 * @return modello [Variabile di tipo String che contiene il modello dello smartphone]
	 */
	public String getModello() {
		return modello;
	}

	/**
	 * Metodo getter del peso
	 * @return peso [Variabile di tipo int che rappresenta il peso dello smartphone]
	 */
	public int getPeso() {
		return peso;
	}

	/**
	 * Metodo getter della dimensione dello schermo
	 * @return pollici [Variabile di tipo int che rappresenta la dimensione dello schermo in pollici]
	 */
	public double getPollici() {
		return pollici;
	}

	/**
	 * Metodo getter della capacita' della batteria
	 * @return mAh [Variabile di tipo Int che rappresenta i mAh della batteria]
	 */
	public int getmAh() {
		return mAh;
	}

	/**
	 * Metodo getter che restituisce i MegaPixel della fotocamera frontale
	 * @return megapixelFrontale [Variabile int che esprime i MegaPixel della fotocamera frontale]
	 * 
	 */
	public int getMegapixelFrontale() {
		return megapixelFrontale;
	}

	/**
	 * Metodo getter che restituisce i MegaPixel della fotocamera principale
	 * @return megapixelPrincipale [Variabile int che esprime i MegaPixel della fotocamera principale]
	 */
	public int getMegapixelPrincipale() {
		return megapixelPrincipale;
	}


	/**
	 * Metodo getter che restituisce la quantita' di Ram presente nello smartphone
	 * @return ram [Variabile int che rappresenta la Ram]
	 */
	public int getRam() {
		return ram;
	}

	/**
	 * Metodo getter del nome del processore 
	 * @return processore [Variabile di tipo String che descrive il nome del processore]
	 */
	public String getProcessore() {
		return processore;
	}

	/**
	 * Metodo getter del colore dello smartphone
	 * @return colore [Variabile di tipo Colore che rappresenta il colore dello smartphone]
	 */
	public Colore getColore() {
		return colore;
	}

	/**
	 * Metodo getter statico della variabile statica last_code, che viene utilizzata per determinare il codice univoco per ogni smartphone
	 * @return last_code [Variabile di tipo int che viene utilizzata per determinare il codice univoco per ogni smartphone]
	 */
	public static int getLast_code() {
		return last_code;
	}
	
	/**
	 * Metodo setter statico della variabile statica last_code, che viene utilizzata per determinare il codice univoco per ogni smartphone
	 * @param last_code [Variabile di tipo int che viene utilizzata per determinare il codice univoco per ogni smartphone]
	 */
	public static void setLast_code(int last_code) {
		AbstractSmartphone.last_code = last_code;
	}

	/**
	 * Metodo getter statico del set codiciImei
	 * @return codiciImei [Set di tipo String che contiene tutti i codice IMEI degli smartphone registrati]
	 */
	public static Set<String> getCodiciImei() {
		return codiciImei;
	}
	
	/**
	 * Metodo setter statico del set codiciImei
	 * @param codiciImei [Set di tipo String che contiene tutti i codice IMEI degli smartphone registrati]
	 */
	public static void setCodiciImeiSerialize(Set<String> codiciImei) {
		AbstractSmartphone.codiciImei = codiciImei;
	}

	/**
	 * Metodo statico che permette di eliminare un codice Imei dal set statico di classe che contiene tutti i codici Imei degli smartphone registrati
	 * @param a [Variabile di tipo AbstractSmartphone]
	 */
	public static void setCodiciImei(AbstractSmartphone a) { 
		Iterator<String> it = codiciImei.iterator();

		while(it.hasNext()) {
			if(it.next().compareTo(a.getCodiceImei())==0)
				it.remove();
		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(codiceImei);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractSmartphone other = (AbstractSmartphone) obj;
		return Objects.equals(codiceImei, other.codiceImei);
	}

	@Override
	public String toString() {
		return "\nCodice Smartphone = " + codice + "\nCodice Imei = " + codiceImei + "\nSistema Operativo = "
				+ sistemaOperativo + "\nModello = "  + modello + "\nPeso = " + peso + "\nPollici = " + pollici + "\nBatteria (mAh) = " + mAh
				+ "\nMegapixel fotocamera frontale = " + megapixelFrontale + "\nMegapixel fotocamera principale = " + megapixelPrincipale + "\nRam = "
				+ ram + "\nProcessore = " + processore + "\nColore = " + colore + "\nPrezzo = "
				+ prezzo;
	}









}
