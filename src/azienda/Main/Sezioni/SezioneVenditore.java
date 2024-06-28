package azienda.Main.Sezioni;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import azienda.Main.Eccezione.EccezioneMain;
import azienda.Ruoli.Genere;
import azienda.Ruoli.Venditore;
import azienda.Ruoli.Eccezioni.EccezioneRuoli;
import azienda.VenditeNoleggi.CatalogoNoleggi;
import azienda.VenditeNoleggi.CatalogoVendite;
import azienda.VenditeNoleggi.Noleggio;
import azienda.VenditeNoleggi.Vendita;
/**
 * Sezione del Software che si occupera' della registrazione, cancellazione, visualizzazione di ogni venditore
 * @author Gabriel Cellammare
 *
 */
public final class SezioneVenditore {

	/**
	 * Menu' utilizzato per scopi grafici; descrive tutte le possibili azioni che possono essere ricondotte alla gestione del singolo venditore
	 */
	public static void MenuVenditore() {

		System.out.println("\nBenvenuto nel menu' dedicato ai venditori!\n");
		System.out.println("1) Aggiungi nuovo venditore");
		System.out.println("2) Ricerca venditore tramite codice fiscale");
		System.out.println("3) Ricerca venditore tramite codice venditore");
		System.out.println("4) Rimuovi un venditore");
		System.out.println("5) Visualizza i venditori");
		System.out.println("6) Torna al menu principale");
		System.out.print("\nFai la tua scelta: ");

	}

	/**
	 * Funzione che si occupa della registrazione di un Venditore, controllando opportunamente tutti i suoi dati anagrafici
	 * Il venditore deve essere maggiorenne, il codice fiscale inserito deve essere UNIVOCO, e la sintassi di ogni parametro deve essere corretta, pena il sollevamento di un'eccezione
	 * Alla fine della registrazione, verra' effettuata la generazione di un codice venditore univoco (ABCDEF/1234), ovvero una stringa alfanumerica di 10 caratteri, che sara' assegnata al venditore
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @return venditori [Set di oggetti di tipo Venditore che conterra' ogni venditore registrato]
	 * @throws EccezioneRuoli
	 * @throws EccezioneMain
	 */
	public static Venditore addVenditore(Scanner s) throws EccezioneRuoli, EccezioneMain{

		System.out.println("\n");

		String nome,cognome,dataDiNascita,CodiceFiscale,LuogoDiNascita,LuogoDiResidenza,Codice_Venditore;
		int temp;
		Genere g = null;

		s.nextLine();

		System.out.print("Inserisci il nome del venditore: ");

		nome=s.nextLine();

		System.out.print("Inserisci il cognome del venditore: ");

		cognome=s.nextLine();


		System.out.print("Inserisci la data di nascita del venditore (GG/MM/AAAA) [Il venditore deve essere maggiorenne]: ");
		dataDiNascita=s.next();
		System.out.print("Inserisci il codice fiscale (Formato normale e maiuscolo): ");
		CodiceFiscale=s.next();

		s.nextLine();

		System.out.print("Inserisci il luogo di nascita del venditore: ");
		LuogoDiNascita=s.nextLine();
		System.out.print("Inserisci il luogo di residenza del venditore: ");
		LuogoDiResidenza=s.nextLine();

		System.out.println("\nScegli il genere del venditore: ");
		System.out.println("0) " + Genere.Maschio.toString());
		System.out.println("1) " + Genere.Femmina.toString());
		System.out.println("2) " + Genere.Non_specificato.toString());
		System.out.print("\n\nScelta: ");
		temp=s.nextInt();

		switch(temp) {
		case 0:
			g=Genere.Maschio;
			break;
		case 1:
			g=Genere.Femmina;
			break;
		case 2:
			g=Genere.Non_specificato;
			break;
		default:
			throw new EccezioneMain("Scelta non valida");
		}

		Codice_Venditore = GenerazioneCodiceVenditore();

		Venditore v = new Venditore(nome, cognome, dataDiNascita, CodiceFiscale, LuogoDiNascita, LuogoDiResidenza,g,Codice_Venditore);

		System.out.println("\nAl venditore con codice fiscale: " + CodiceFiscale + " e' stato assegnato il codice venditore: " + Codice_Venditore + "\n" );

		System.out.println("Venditore registrato correttamente\n");

		return v;

	}

	private static String GenerazioneCodiceVenditore() {

		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numbers = "0123456789";

		StringBuilder sb = new StringBuilder();

		Random random = new Random();

		final int length = 10;

		for(int i = 0; i < length; i++) {

			if(i<6) {
				int index = random.nextInt(upperAlphabet.length());
				char randomChar = upperAlphabet.charAt(index);
				sb.append(randomChar);
			}

			else {
				int index = random.nextInt(numbers.length());
				char randomChar = numbers.charAt(index);
				sb.append(randomChar);
			}


		}

		return sb.toString();

	}


	/**
	 * Questo metodo permette, attraverso il corretto inserimento di un codice fiscale (rispettando il formato), di ricercare un venditore registrato
	 * Se la ricerca non va a buon fine, il venditore sicuramente non sara' registrato, se invece viene inserito un codice fiscale con un formato nullo, il programma sollevera' un'eccezione
	 * Una volta trovato il venditore in questione, e' possibile modificare il luogo di residenza, visualizzare tutti i dettagli del venditore, visualizzare gli smartphone venduti e noleggiati,
	 * visualizzare il numero di noleggi rimanenti per un dato giorno (Il numero massimo di noleggi in un giorno, per un venditore, e' 10).
	 * Se un venditore o un smartphone venuduto/noleggiato viene eliminato, tutte le informazioni rimarranno comunque consultabili (ATTENZIONE! non sarà possibile gestire direttamente gli oggetti eliminati, ma soltanto visualizzarli)
	 * @param venditori [Set di oggetti di tipo Venditore che conterra' ogni venditore registrato]
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @throws EccezioneMain
	 * @throws EccezioneRuoli
	 */
	public static void RicercaVenditoreCodiceFiscale(Set<Venditore> venditori, Scanner s) throws EccezioneMain, EccezioneRuoli {

		final String CodiceFiscaleF = "[A-Z]{6}[0-9]{2}[ABCDEHLMPRST]{1}[0-9]{2}([A-Z]{1}[0-9]{3})[A-Z]{1}$";
		String cf=null;
		boolean trovato=false;
		int scelta;
		String residenza;
		Venditore v = null;
		String d;
		final String Data = "^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d$"; //GG/MM/AAAA;

		if(venditori.isEmpty())
			System.out.println("Nessun venditore ancora registrato");

		else {


			System.out.print("\nInserisci il codice fiscale del venditore da ricercare (Maiuscolo): ");
			cf=s.next();

			if(!cf.matches(CodiceFiscaleF)) 
				throw new EccezioneMain("Errore, formato del codice fiscale errato");


			for (Venditore venditore : venditori) {
				if(venditore.getCodiceFiscale().compareTo(cf)==0) {
					v=venditore;
					trovato=true;
				}



			}

			if(!trovato)
				System.out.println("\nNessun venditore trovato\n");

			else {
				System.out.println("\nIl venditore " + v.getCodiceFiscale() + " e' stato trovato correttamente\n");
				System.out.println("1) Modifica luogo di residenza");	
				System.out.println("2) Visualizza tutti i dettagli venditore ");
				System.out.println("3) Visualizzza gli smartphone venduti");
				System.out.println("4) Visualizzza gli smartphone noleggiati");
				System.out.println("5) Visualizzi i noleggi ancora disponibili per un dato giorno");
				System.out.print("\nFai la tua scelta: ");

				scelta = s.nextInt();

				switch(scelta) {
				case 1: System.out.print("Inserisci il nuovo luogo di residenza: ");
				residenza = s.next();
				try {
					v.setLuogoDiResidenza(residenza);
				} catch (EccezioneRuoli e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Luogo di residenza aggiornato correttamente");
				break;

				case 2:
					System.out.println(v.toString());
					break;
				case 3:
					Set<Vendita> vendite = new LinkedHashSet<Vendita>();

					vendite = CatalogoVendite.cloneVenditaDataPrezzo(v);

					if(vendite.isEmpty())
						System.out.println("Nessuno smartphone venduto dal venditore con codice: " + v.getCodice_venditore());
					else
						StampaFormattata.Stampa(vendite);


					break;
				case 4:
					Set<Noleggio> noleggi = new LinkedHashSet<Noleggio>();

					noleggi = CatalogoNoleggi.cloneNoleggioCognome(v);

					if(noleggi.isEmpty())
						System.out.println("Nessuno smartphone noleggiato dal venditore con codice: " + v.getCodice_venditore());
					else
						StampaFormattata.Stampa(noleggi);

					break;		

				case 5:
					System.out.print("\nInserisci la data da controllare (GG/MM/AA): ");
					d = s.next();

					if(!d.matches(Data))
						throw new EccezioneMain("Formato data errato");

					if(v.getDataNoleggi().containsKey(d)) {
						System.out.println("In data " + d  + " e' possibile effettuare ancora " + v.getDataNoleggi().get(d)+ " noleggi");
					}

					else{	
						System.out.println("In data " + d  + " non e' stato effettuato nessun noleggio. \nI noleggi totali effettuabili in un giorno sono 10 ");
					}
					break;

				default:
					throw new EccezioneMain("Scelta non consentita");

				}

			}
		}
	}


	/**
	 * Il codice venditore viene generato in maniera univoca per ogni venditore
	 * Questo metodo permette, attraverso il corretto inserimento di un codice venditore (rispettando il formato, ovvero una stringa di 10 caratteri alfanumerici), di ricercare un venditore registrato
	 * Se la ricerca non va a buon fine, il venditore sicuramente non sara' registrato
	 * Una volta trovato il venditore in questione, e' possibile modificare il luogo di residenza, visualizzare tutti i dettagli del venditore, visualizzare gli smartphone venduti e noleggiati,
	 * visualizzare il numero di noleggi rimanenti per un dato giorno (Il numero massimo di noleggi in un giorno, per un venditore, e' 10).
	 * Se un venditoree o un smartphone venuduto/noleggiato viene eliminato, tutte le informazioni rimarranno comunque consultabili (ATTENZIONE! non sarà possibile gestire direttamente gli oggetti eliminati, ma soltanto visualizzarli)
	 * @param venditori [Set di oggetti di tipo Venditore che conterra' ogni venditore registrato]
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @throws EccezioneMain
	 * @throws EccezioneRuoli
	 */
	public static void RicercaCodiceVenditore(Set<Venditore> venditori, Scanner s) throws EccezioneMain, EccezioneRuoli {

		if(venditori.isEmpty())
			System.out.println("Nessun venditore ancora registrato");
		else {


			System.out.print("\nInserisci codice venditore per la ricerca (10 caratteri alfanumerici): ");
			boolean trovato=false;
			String codice = s.next();

			if(codice.length()!=10)
				throw new EccezioneMain("La lunghezza del codice venditore per la ricerca deve essere di 10 caratteri");


			int scelta;
			String residenza;
			String d;
			final String Data = "^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d$"; //GG/MM/AAAA;
			Venditore v = null;

			for (Venditore venditore : venditori) {
				if(venditore.getCodice_venditore().compareTo(codice)==0) {
					v=venditore;
					trovato=true;
				}

			}

			if(!trovato)
				System.out.println("\nNessun venditore trovato\n");

			else {
				System.out.println("\nIl venditore " + v.getCodice_venditore() + " e' stato trovato correttamente\n");
				System.out.println("1) Modifica luogo di residenza");	
				System.out.println("2) Visualizza tutti i dettagli venditore ");
				System.out.println("3) Visualizzza gli smartphone venduti");
				System.out.println("4) Visualizzza gli smartphone noleggiati");
				System.out.println("5) Visualizzi i noleggi ancora disponibili per un dato giorno ");
				System.out.print("\nFai la tua scelta: ");

				scelta = s.nextInt();

				switch(scelta) {
				case 1: System.out.print("Inserisci il nuovo luogo di residenza: ");
				residenza = s.next();
				try {
					v.setLuogoDiResidenza(residenza);
				} catch (EccezioneRuoli e) {
					e.printStackTrace();
				}
				System.out.println("Luogo di residenza aggiornato correttamente");
				break;

				case 2:
					System.out.println(v.toString());
					break;
				case 3:
					Set<Vendita> vendite = new LinkedHashSet<Vendita>();

					vendite=(CatalogoVendite.cloneVenditaDataPrezzo(v));

					if(vendite.isEmpty())
						System.out.println("Nessuno smartphone venduto dal venditore con codice: " + v.getCodice_venditore());
					
					else
						StampaFormattata.Stampa(vendite);
					break;
				case 4:
					Set<Noleggio> noleggi = new LinkedHashSet<Noleggio>();

					noleggi = CatalogoNoleggi.cloneNoleggioCognome(v);

					if(noleggi.isEmpty())
						System.out.println("Nessuno smartphone noleggiato dal venditore con codice: " + v.getCodice_venditore());
					else
						StampaFormattata.Stampa(noleggi);

					break;	
				case 5:
					System.out.print("\nInserisci la data da controllare (GG/MM/AA): ");
					d = s.next();

					if(!d.matches(Data))
						throw new EccezioneMain("Formato data errato");

					if(v.getDataNoleggi().containsKey(d)) {
						System.out.println("In data " + d  + " e' possibile effettuare ancora " + v.getDataNoleggi().get(d)+ " noleggi");
					}

					else{	
						System.out.println("In data " + d  + " non e' stato effettuato nessun noleggio. \nI noleggi totali effettuabili sono 10 ");
					}

					break;

				default:
					throw new EccezioneMain("Scelta non consentita");

				}

			}
		}
	}

	/**
	 * Metodo che permette, attraveso l'inserimento di un codice venditore valido, l'eliminazione da quest'ultimo dal Set di venditori registrati
	 * Attenzione, verra' rimosso anche il suo Codice Fiscale all'interno di un Set statico presente nella classe Venditore (AbstractPersona), per prevenire eventuali errori e permettere in un futuro
	 * la reiscrizione di quel preciso venditore 
	 * @param venditori [Set di oggetti di tipo Venditore che conterra' ogni venditore registrato]
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @throws EccezioneMain
	 */
	public static void RimuoviVenditore(Set<Venditore> venditori, Scanner s) throws EccezioneMain {

		String scelta = null;
		Venditore v = null;
		boolean t = false;

		if(venditori.isEmpty())
			System.out.println("Nessun venditore registrato");

		else {
			System.out.println("\n");
			System.err.println("ATTENZIONE! L'ELIMINAZIONE SARA' DEFINITIVA");
			System.out.println("\n");
			StampaFormattata.Stampa(venditori);

			System.out.print("\nScegli il codice venditore: ");
			scelta = s.next();

			for (Venditore venditore : venditori) {
				if(venditore.getCodice_venditore().compareTo(scelta)==0){
					t=true;
					v=venditore;
				}

			}

			if(!t)
				System.out.println("Nessun venditore eliminato");

			else {
				Iterator<Venditore> it = venditori.iterator();

				while(it.hasNext()) {
					if(it.next().getCodice_venditore().compareTo(scelta)==0) {
						it.remove();	
						System.out.println("\nVenditore eliminato correttamente");
					}

				}

				Venditore.setCodiciVenditori(v);
				Venditore.setCodiciFiscali(v);
			}
		}
	}

	/**
	 * Metodo che permette la stampa di tutti i venditori registrati nel Set
	 * 
	 * @param venditori [Set di oggetti di tipo Venditore che conterra' ogni venditore registrato]
	 */
	public static void VisualizzaVenditori(Set<Venditore> venditori) {

		if(venditori.isEmpty())
			System.out.println("\nNessun venditore registrato\n\n");

		else {
			StampaFormattata.Stampa(venditori);
		}

	}

}
