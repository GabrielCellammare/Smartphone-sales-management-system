package azienda.VenditeNoleggi;

import java.io.IOException;
import java.util.HashSet;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import azienda.Main.Serialize.VenditaSerialize;
import azienda.Ruoli.Venditore;
import azienda.VenditeNoleggi.Eccezioni.VenditeNoleggiEccezione;

/**
 * Classe che si occupera' della serializzazione e deserializzazione del Set formato da oggetti di tipo Vendita
 * @author Gabriel Cellammare
 *
 */
public final class CatalogoVendite {

	private int codice_catalogo;

	private static Set<Vendita> vendite = new HashSet<Vendita>();

	private CatalogoVendite () { //Costruttore privato
		this.codice_catalogo=1;
	}


	/**
	 * Metodo che aggiungera' ogni vendita al Set statico vendite 
	 * @param v [Variabile di tipo Vendita]
	 * @throws VenditeNoleggiEccezione
	 */
	protected static void addVendita(Vendita v) throws VenditeNoleggiEccezione {
		if(!vendite.add(v)) {
			throw new VenditeNoleggiEccezione("Vendita  gia' inserita nel catalogo");
		}
	}
	
	/**
	 * Metodo che si occupera' di clonare il set statico vendite in un TreeSet che ha come metodo di comparazione la data di vendita e il prezzo di ogni vendita
	 * clonando soltanto le vendite effettuate da un determinato venditore
	 * 
	 * @param v [Oggetto di tipo Venditore]
	 * @return clone [Variabile di tipo LinkedHashSet che conterra' tutti le vendite ordinate per data di vendita e prezzo di ogni vendita]
	 */
	public static Set<Vendita> cloneVenditaDataPrezzo(Venditore v){

		Set<Vendita> completo = new TreeSet<Vendita>(new ComparatoreVenditaDataPrezzo());

		completo.addAll(vendite);
		
		Set<Vendita> clone = new LinkedHashSet<Vendita>();

		for (Vendita vendita : completo) {

			if(vendita.getV().equals(v)) {
				clone.add((Vendita) vendita.clone());
			}

		}

		return clone;
	}


	/**
	 * Metodo che si occupera' di clonare il set statico vendite in un TreeSet che ha come metodo di comparazione la data di vendita e il prezzo di ogni vendita
	 * 
	 * @return clone [Variabile di tipo LinkedHashSet che conterra' tutti le vendite ordinate per data di vendita e prezzo di ogni vendita]
	 */
	public static Set<Vendita> cloneVenditaDataPrezzo(){

		Set<Vendita> completo = new TreeSet<Vendita>(new ComparatoreVenditaDataPrezzo());

		completo.addAll(vendite);

		Set<Vendita> clone = new LinkedHashSet<Vendita>();

		for (Vendita vendita : completo) {

			clone.add((Vendita) vendita.clone());


		}

		return clone;
	}

	public int getCodice_catalogo() {
		return codice_catalogo;
	}

	/**
	 * Metodo che richiamera' la classe VenditaSerialize per serializzare il set statico composto da tutte le vendite
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void VenditaSerialize() throws IOException, InterruptedException {
		
		VenditaSerialize.SerializeDataVendita(vendite);
		System.out.println("\n");
		
	}
	
	/**
	 * Metodo che richiamera' la classe VenditaSerialize per deeserializzare il set statico composto da tutte le vendite
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void VenditaDeserialize() throws ClassNotFoundException, IOException, InterruptedException {
	
		CatalogoVendite.vendite=VenditaSerialize.DeserializeDataVendita();
		
	}


}
