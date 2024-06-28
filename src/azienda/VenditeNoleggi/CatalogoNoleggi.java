package azienda.VenditeNoleggi;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import azienda.Main.Serialize.NoleggioSerialize;
import azienda.Main.Sezioni.SezioneNoleggio;
import azienda.Ruoli.Cliente;
import azienda.Ruoli.Venditore;
import azienda.Smartphone.SmartphoneAcquistabile;
import azienda.VenditeNoleggi.Eccezioni.VenditeNoleggiEccezione;

/**
 * Classe CatalogoNoleggi che conterra' tutti i noleggi effettuati all'interno dell'azienda
 * @author Gabriel Cellammare
 *
 */
public final class CatalogoNoleggi {

	private int codice_catalogo;

	private static Set<Noleggio> noleggi = new HashSet<Noleggio>();

	private CatalogoNoleggi() { //Costruttore privato
		this.codice_catalogo=1;
	}

	/**
	 * Metodo che aggiungera' ogni noleggio al Set statico noleggi 
	 * @param n [Variabile di tipo Noleggio]
	 * @throws VenditeNoleggiEccezione
	 */
	protected static void addNoleggio(Noleggio n) throws VenditeNoleggiEccezione {
		if(!noleggi.add(n)) {
			throw new VenditeNoleggiEccezione("Noleggio gia' inserito nel catalogo");
		}
	}

	/**
	 * Metodo che si occupera' di clonare il set statico Noleggi in un TreeSet che ha come metodo di comparazione il cognome di ogni venditore
	 * 
	 * @return clone [Variabile di tipo LinkedHashSet che conterra' tutti i noleggi ordinati per cognome del dipendente che li ha effettuati]
	 */
	public static Set<Noleggio> cloneNoleggioCognome(){

		Set<Noleggio> completo = new TreeSet<Noleggio>(new ComparatoreNoleggioCognome());

		completo.addAll(noleggi);

		Set<Noleggio> clone = new LinkedHashSet<Noleggio>();

		for (Noleggio noleggio : completo) {

			clone.add((Noleggio) noleggio.clone());

		}

		return clone;
	}

	/**
	 * Metodo che si occupera' di clonare il set statico Noleggi in un TreeSet che ha come metodo di comparazione il cognome di ogni venditore
	 * clonando soltanto quelli che hanno come data di termine noleggio, un data uguale o antecedente a quella odierna
	 * 
	 * @return clone [Variabile di tipo LinkedHashSet che conterra' tutti i noleggi ordinati per cognome del dipendente che li ha effettuati]
	 */
	public static Set<Noleggio> cloneNoleggioCognomeDataOdierna(){

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");  
		LocalDateTime now = LocalDateTime.now(); 

		Set<Noleggio> completo = new TreeSet<Noleggio>(new ComparatoreNoleggioCognome());

		completo.addAll(noleggi);

		Set<Noleggio> clone = new LinkedHashSet<Noleggio>();

		for (Noleggio noleggio : completo) {

			if(SezioneNoleggio.CalcoloGiorni(noleggio.getData_f(), dtf.format(now))>=0) {
				clone.add((Noleggio) noleggio.clone());
			}


		}

		return clone;
	}

	/**
	 * Metodo che si occupera' di clonare il set statico noleggi in un TreeSet che ha come metodo di comparazione il cognome di ogni venditore
	 * clonando soltanto i noleggi effettuati da un determinato venditore
	 * 
	 * @param v [Oggetto di tipo Venditore]
	 * @return clone [Variabile di tipo LinkedHashSet che conterra' tutti i noleggi ordinati per cognome del dipendente che li ha effettuati]
	 */
	public static Set<Noleggio> cloneNoleggioCognome(Venditore v){

		Set<Noleggio> completo = new TreeSet<Noleggio>(new ComparatoreNoleggioCognome());

		completo.addAll(noleggi);

		Set<Noleggio> clone = new LinkedHashSet<Noleggio>();

		for (Noleggio noleggio : completo) {

			if(noleggio.getV().equals(v)) {
				clone.add((Noleggio) noleggio.clone());
			}


		}

		return clone;
	}

	/**
	 * Metodo che si occupera' di clonare il set statico noleggi in un TreeSet che ha come metodo di comparazione il cognome di ogni venditore
	 * clonando soltanto i noleggi effettuati da un determinato cliente
	 * 
	 * @param c [Oggetto di tipo Cliente]
	 * @return clone [Variabile di tipo LinkedHashSet che conterra' tutti i noleggi ordinati per cognome del dipendente che li ha effettuati]
	 */
	public static Set<Noleggio> cloneNoleggioCognome(Cliente c){

		Set<Noleggio> completo = new TreeSet<Noleggio>(new ComparatoreNoleggioCognome());

		completo.addAll(noleggi);

		Set<Noleggio> clone = new LinkedHashSet<Noleggio>();

		for (Noleggio noleggio : completo) {

			if(noleggio.getC().equals(c)) {
				clone.add((Noleggio) noleggio.clone());
			}


		}

		return clone;
	}

	/**
	 * Metodo che si occupera' di clonare il set statico noleggi in un TreeSet che ha come metodo di comparazione il cognome di ogni venditore
	 * clonando soltanto i noleggi che riguardano un determinato smartphone
	 * 
	 * @param sa [Oggetto di tipo SmartphoneAcquistabile]
	 * @return clone [Variabile di tipo LinkedHashSet che conterra' tutti i noleggi ordinati per cognome del dipendente che li ha effettuati]
	 */
	public static Set<Noleggio> cloneNoleggioCognomeS(SmartphoneAcquistabile sa){

		Set<Noleggio> completo = new TreeSet<Noleggio>(new ComparatoreNoleggioCognome());

		completo.addAll(noleggi);

		Set<Noleggio> clone = new LinkedHashSet<Noleggio>();

		for (Noleggio noleggio : completo) {

			if(noleggio.getS().equals(sa)) {
				clone.add((Noleggio) noleggio.clone());
			}


		}

		return clone;
	}

	public int getCodice_catalogo() {
		return codice_catalogo;
	}

	/**
	 * Metodo che richiamera' la classe NoleggioSerialize per serializzare il set statico composto da tutti i noleggi
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void NoleggioSerialize() throws IOException, InterruptedException {

		NoleggioSerialize.SerializeDataNoleggio(noleggi);

	}

	/**
	 * Metodo che richiamera' la classe NoleggioSerialize per deserializzare il set statico composto da tutti i noleggi
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void NoleggioDeserialize() throws ClassNotFoundException, IOException, InterruptedException {

		CatalogoNoleggi.noleggi=NoleggioSerialize.DeserializeDataNoleggio();

	}



}
