package azienda.VenditeNoleggi;
import java.io.Serializable;
import java.util.Objects;

import azienda.Ruoli.Cliente;
import azienda.Ruoli.Venditore;
import azienda.Ruoli.Eccezioni.EccezioneRuoli;
import azienda.Smartphone.SmartphoneAcquistabile;
import azienda.VenditeNoleggi.Eccezioni.VenditeNoleggiEccezione;

/**
 * Classe che si occupera' della creazione di un oggetto di tipo Vendita, che conterra' tutte le informazioni necessarie per la vendita di uno smartphone
 * Attenzione, la vendita e' consentita soltanto a smartphone di tipo Base e Avanzato
 * @author Gabriel Cellammare
 * 
 */
public class Vendita implements Cloneable,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4272670607071301622L;

	private int codice;
	private String data;
	private double prezzo_DiVendita;
	private Cliente c;
	private Venditore v;
	private SmartphoneAcquistabile s;


	private static double incasso=0;
	private static int last_code=0;
	
	/**
	 * 
	 * Regex che impone il formato data GG/MM/AAAA
	 */
	
	private static final String DataF = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$"; //GG/MM/AAAA;

	public Vendita(Cliente c, Venditore v, String data, double prezzo_DiVendita, SmartphoneAcquistabile s) throws VenditeNoleggiEccezione, EccezioneRuoli {

		if(c==null) 
			throw new VenditeNoleggiEccezione("Cliente non valido");

		if(v==null)
			throw new VenditeNoleggiEccezione("Venditore non valido");

		if(s==null)
			throw new VenditeNoleggiEccezione("Smartphone non valido");

		if(!data.matches(DataF))
			throw new VenditeNoleggiEccezione("Data non valida");

		if(s.isDisponibile()==false) {
			throw new VenditeNoleggiEccezione("Smartphone non disponibile attualmente");
		}

		this.codice=last_code++;
		this.prezzo_DiVendita=prezzo_DiVendita;
		this.data=data;
		this.c=c;
		this.c.addSmartphoneAcquistato(s);
		this.v=v;
		this.s=s;


		this.s.setDisponibile(false);

		Vendita.incasso+=prezzo_DiVendita;


		CatalogoVendite.addVendita(this);
	}
	
	/**
	 * Funzione getter del codice univoco assegnato ad ogni vendita
	 * @return codice [Variabile di tipo Int che identifica il codice noleggio]
	 */
	public int getCodice() {
		return codice;
	}

	/**
	 * Metodo getter del giorno in cui e' stata effettuata la vendita
	 * @return data [Variabile di tipo String che rappresenta la data d'acquisto]
	 */
	public String getData() {
		return data;
	}

	/**
	 * Metodo che restituisce il prezzo di vendita
	 * @return prezzo_DiVendita [Variabile di tipo double che rappresenta il prezzo di vendita]
	 */
	public double getPrezzoDiVendita() {
		return prezzo_DiVendita;
	}

	/**
	 * Metodo getter del cliente che ha effettuato la vendita
	 * @return c [Variabile di tipo Cliente]
	 */
	public Cliente getC() {
		return c;
	}

	/**
	 * Metodo getter del venditore che ha effettuato la vendita
	 * @return v [Variabile di tipo Venditore]
	 */
	public Venditore getV() {
		return v;
	}

	/**
	 * Metodo getter dello Smartphone venduto
	 * @return s [Variabile di tipo SmartphoneAcquistabile che rappresenta lo smartphone venduto]
	 */
	public SmartphoneAcquistabile getS() {
		return s;
	}

	/**
	 * Metodo getter statico che restituira' l'incasso totale prodotto da tutte le vendite
	 * @return incasso [Variabile di tipo double che rappresenta l'incasso prodotto da tutte le vendite]
	 */
	public static double getIncasso() {
		return incasso;
	}


	/**
	 * Metodo setter statico che settera' l'incasso totale prodotto da tutte le vendite
	 * @param incasso [Variabile di tipo double che rappresenta l'incasso prodotto da tutte le vendite]
	 */
	public static void setIncasso(double incasso) {
		Vendita.incasso = incasso;
	}


	/**
	 * Metodo getter che si occupera' di restituire la variabile last_code
	 * @return last_code [Variabile di tipo Int utilizzata per generare ad ogni vendita, un numero univoco]
	 */
	public static int getLast_code() {
		return last_code;
	}

	/**
	 * Metodo setter che si occupera' di settare la variabile last_code
	 * @param last_code [Variabile di tipo Int utilizzata per generare ad ogni vendita, un numero univoco]
	 */
	public static void setLast_code(int last_code) {
		Vendita.last_code = last_code;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendita other = (Vendita) obj;
		return codice == other.codice;
	}


	@Override
	public Object clone(){
		// TODO Auto-generated method stub
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String toString() {
		return "\nCodice vendita = " + codice + "\nData di acquisto = " + data + "\nPrezzo di vendita = " + 
				prezzo_DiVendita + "\nCliente (Codice fiscale) = " + c.getCodiceFiscale() + "\nVenditore (Codice venditore) = " + v.getCodice_venditore() + 
				"\nSmartphone (Codice IMEI) = "
				+ s.getCodiceImei() + "";
	}




}
