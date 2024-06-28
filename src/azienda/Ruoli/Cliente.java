package azienda.Ruoli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import azienda.Ruoli.Eccezioni.EccezioneRuoli;
import azienda.Smartphone.SmartphoneAvanzato;
import azienda.Smartphone.SmartphoneAcquistabile;

/**
 * La classe Cliente rappresenta ogni cliente e le sue caratteristiche, ereditando anche gli attributi e i metodi della classe AbstractPersona
 * @author Gabriel Cellammare
 *
 */
public class Cliente extends AbstractPersona {

	
	private static final long serialVersionUID = 5070207064264021549L;
	
	/**
	 * Set che conterra' tutti gli smartphone acquistati dal cliente
	 */
	private Set<SmartphoneAcquistabile> SmartphoneAcquistati = new HashSet<SmartphoneAcquistabile>(); //Set per memorizzare tutti gli smartphone acquistati.

	/**
	 * ArrayList che conterra' tutti gli smartphone noleggiati da quel dato cliente, la scelta dell'array list e' data dal fatto che un cliente possa noleggiare lo stesso smartphone piu' volte
	 */
	private ArrayList<SmartphoneAvanzato> SmartphoneNoleggiati= new ArrayList<SmartphoneAvanzato>(); //Elenco Smartphone noleggiati
	
	/**
	 * Hashmap che conterra' come chiave principale la data d'inizio noleggio, e come valore il numero di noleggi effettuati in quel giorno
	 * Il valore di noleggi massimo effettuabili in un giorno per un cliente e' di 3
	 */
	private HashMap<String, Integer> DataNoleggi = new HashMap<String, Integer>(); //Elenco per controllare il numero di smartphone noleggiati al giorno

	/**
	 * Numero massimo di noleggi effettuabili in un giorno
	 */
	private static final int MaxNoleggi = 3; //Un cliente può noleggiare al massimo 3 Smartphone

	public Cliente(String nome, String cognome, String DataDiNascita, String CodiceFiscale, String LuogoDiNascita,
			String LuogoDiResidenza, Genere sesso) throws EccezioneRuoli {
		super(nome, cognome, DataDiNascita, CodiceFiscale, LuogoDiNascita, LuogoDiResidenza, sesso);
	}
	

	@Override
	public void setDataNoleggi(String data) throws EccezioneRuoli{

		if(this.DataNoleggi.containsKey(data)) {

			if(this.DataNoleggi.get(data)>=1) {

				this.DataNoleggi.computeIfPresent(data, (k, v) -> v-1);
			}

			else {
				throw new EccezioneRuoli("Limite giornaliero di noleggi raggiunto");
			}

		}

		else {
			this.DataNoleggi.put(data, MaxNoleggi-1);
		}


	}

	/**
	 * Metodo che permette di aggiungere al Set di smartphone acquistati di ogni cliente, l'ultimo smartphone acquistato
	 * @param s [Oggetto di tipo SmartphoneAcquistabile (Base o Avanzato)]
	 * @throws EccezioneRuoli
	 */
	public void addSmartphoneAcquistato(SmartphoneAcquistabile s) throws EccezioneRuoli {
		
		if(!SmartphoneAcquistati.add(s))
			throw new EccezioneRuoli("Smartphone gia' acquistato");
		
	}
	
	/**
	 * Metodo che permette di aggiungere al Set di smartphone noleggiati di ogni cliente, l'ultimo smartphone noleggiato
	 * @param s [Oggetto di tipo SmartphoneAvanzato]
	 * @throws EccezioneRuoli
	 */
	public void addSmartphoneNoleggiato(SmartphoneAvanzato s) throws EccezioneRuoli {
		
		SmartphoneNoleggiati.add(s);
		
	}
	
	/**
	 * Metodo getter del set contenente tutti gli smartphone acquistati
	 * @return SmartphoneAcquistati [Set di oggetti di tipo SmartphoneAcquistabile]
	 */
	public Set<SmartphoneAcquistabile> getSmartphoneAcquistati() {
		return SmartphoneAcquistati;
	}

	/**
	 * Metodo getter del set contenente tutti gli smartphone noleggiati
	 * @return SmartphoneNoleggiati [Set di oggetti di tipo SmartphoneAvanzato]
	 */
	public ArrayList<SmartphoneAvanzato> getSmartphoneNoleggiati() {
		return SmartphoneNoleggiati;
	}

	/**
	 * Metodo getter dell'Hashmap contenente tutte le date dei noleggi e il numero di noleggi effettuati in quella data
	 * @return DataNoleggi [Variabile di tipo Hashmap]
	 */
	public HashMap<String, Integer> getDataNoleggi() {
		return DataNoleggi;
	}
	
	/**
	 * Metodo getter del codice cliente
	 * @return codice_cliente [Variabile di tipo Int che rappresenta il codice cliente]
	 */
	public int getCodice() {
		return codice_cliente;
	}



}
