package azienda.Ruoli;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import azienda.Ruoli.Eccezioni.EccezioneRuoli;

/**
 * La classe Venditore rappresenta ogni venditore e le sue caratteristiche, ereditando anche gli attributi e i metodi della classe AbstractPersona
 * @author Gabriel Cellammare
 *
 */
public class Venditore extends AbstractPersona {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3837913290284208180L;

	/**
	 * 
	 */
	private HashMap<String, Integer> DataNoleggi = new HashMap<String, Integer>(); //Elenco per controllare il numero di smartphone noleggiati al giorno

	/**
	 * Hashmap che conterra' come chiave principale la data d'inizio noleggio, e come valore il numero di noleggi effettuati in quel giorno
	 * Il valore di noleggi massimo effettuabili in un giorno per un cliente e' di 10
	 */
	
	/**
	 * Numero massimo di noleggi effettuabili in un giorno
	 */
	private static final int MaxNoleggi = 10;

	public Venditore(String nome, String cognome, String DataDiNascita, String CodiceFiscale, String LuogoDiNascita,
			String LuogoDiResidenza, Genere sesso, String codice_venditore) throws EccezioneRuoli {

		super(nome, cognome, DataDiNascita, CodiceFiscale, LuogoDiNascita, LuogoDiResidenza, sesso, 
				codice_venditore);

	}

	@Override
	public void setDataNoleggi(String data) throws EccezioneRuoli{

		if(DataNoleggi.containsKey(data)) {

			if(DataNoleggi.get(data)>=1) {

				DataNoleggi.computeIfPresent(data, (k, v) -> v-1);
			}

			else {
				throw new EccezioneRuoli("Limite giornaliero di noleggi per i venditori raggiunto");
			}

		}

		else {
			DataNoleggi.put(data, MaxNoleggi-1);
		}


	}




	/**
	 * Metodo getter dell'Hashmap contenente tutte le date dei noleggi e il numero di noleggi effettuati in quella data
	 * @return DataNoleggi [Variabile di tipo Hashmap]
	 */
	public HashMap<String, Integer> getDataNoleggi() {
		return DataNoleggi;
	}


	@Override
	public int hashCode() {
		return Objects.hash(super.codice_venditore);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractPersona other = (AbstractPersona) obj;
		return Objects.equals(super.codice_venditore, other.codice_venditore);
	}


	/**
	 * Metodo getter del codice venditore
	 * @return codice_venditore [Variabile String di lunghezza 10 che rappresenta il codice venditore]
	 */
	public String getCodice_venditore() {
		return this.codice_venditore;
	}

	/**
	 * Metodo che permette di eliminare un codice venditore dal set di codici venditore statico contenuto nella classe AbstractPersona
	 * @param v [Oggetto di tipo Venditore]
	 */
	public static void setCodiciVenditori(Venditore v) { 
		Iterator<String> it = CodiciVenditori.iterator();

		while(it.hasNext()) {
			if(it.next().compareTo(v.getCodice_venditore())==0)
				it.remove();
		}

	}


	@Override
	public String toString() {
		return "\nNome = " + nome + "\nCognome = " + cognome + "\nData di nascita = " + DataDiNascita
				+ "\nCodiceFiscale = " + CodiceFiscale + "\nLuogo di nascita = " + LuogoDiNascita + "\nLuogo di residenza = " 
				+ LuogoDiResidenza + "\nSesso = " + sesso + "\nEta = " + eta + "\nCodice venditore  = " + codice_venditore + "";
	}






}
