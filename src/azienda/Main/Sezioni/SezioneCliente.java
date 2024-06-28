package azienda.Main.Sezioni;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import azienda.Main.Eccezione.EccezioneMain;
import azienda.Ruoli.Cliente;
import azienda.Ruoli.Genere;
import azienda.Ruoli.Eccezioni.EccezioneRuoli;
import azienda.VenditeNoleggi.CatalogoNoleggi;
import azienda.VenditeNoleggi.Noleggio;
/**
 * Sezione del Software che si occupera' della registrazione, cancellazione, visualizzazione di ogni cliente
 * @author Gabriel Cellammare
 *
 */
public final class SezioneCliente {
	/**
	 * Menu' utilizzato per scopi grafici; descrive tutte le possibili azioni che possono essere ricondotte alla gestione del singolo cliente
	 */
	public static void MenuCliente() {

		System.out.println("\nBenvenuto nel menu' dedicato alla clientela!\n");
		System.out.println("1) Aggiungi nuovo cliente");
		System.out.println("2) Ricerca cliente tramite codice fiscale");
		System.out.println("3) Ricerca cliente tramite codice cliente");
		System.out.println("4) Rimuovi un cliente");
		System.out.println("5) Visualizza i clienti");
		System.out.println("6) Torna al menu' principale");
		System.out.print("\nFai la tua scelta: ");

	}

	/**
	 * Funzione che si occupa della registrazione di un Cliente, controllando opportunamente tutti i suoi dati anagrafici
	 * Il cliente deve essere maggiorenne, il codice fiscale inserito deve essere UNIVOCO, e la sintassi di ogni parametro deve essere corretta, pena il sollevamento di un'eccezione
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @return cliente [Oggetto di tipo "Cliente" che andra' aggiunto al suo set dedicato]
	 * @throws EccezioneRuoli
	 * @throws EccezioneMain
	 */
	public static Cliente addCliente(Scanner s) throws EccezioneRuoli, EccezioneMain{

		System.out.println("\n");

		String nome,cognome,dataDiNascita,CodiceFiscale,LuogoDiNascita,LuogoDiResidenza;
		int temp;
		Genere g = null;

		s.nextLine();

		System.out.print("Inserisci il nome del cliente: ");
		nome=s.nextLine();

		System.out.print("Inserisci il cognome del cliente: ");

		cognome=s.nextLine();

		System.out.print("Inserisci la data di nascita del cliente (GG/MM/AAAA) [Il cliente deve essere maggiorenne]: ");
		dataDiNascita=s.next();

		System.out.print("Inserisci il codice fiscale (Formato normale e maiuscolo): ");
		CodiceFiscale=s.next();

		s.nextLine();

		System.out.print("Inserisci il luogo di nascita del cliente: ");
		LuogoDiNascita=s.nextLine();
		System.out.print("Inserisci il luogo di residenza del cliente: ");
		LuogoDiResidenza=s.nextLine();

		System.out.println("\nScegli il genere del cliente: ");
		System.out.println("0) " + Genere.Maschio.toString());
		System.out.println("1) " + Genere.Femmina.toString());
		System.out.println("2) " + Genere.Non_specificato.toString());
		System.out.print("\n\nScelta (Scegli un numero): ");
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

		Cliente c = new Cliente(nome, cognome, dataDiNascita, CodiceFiscale, LuogoDiNascita, LuogoDiResidenza,g);

		System.out.println("Cliente registrato correttamente\n");
		System.out.println("Al cliente " + c.getNome() + " " + c.getCognome() + " e' stato assegnato il codice: " + c.getCodice());

		return c;




	}
	/**
	 * Questo metodo permette, attraverso il corretto inserimento di un codice fiscale (rispettando il formato), di ricercare un cliente registrato
	 * Se la ricerca non va a buon fine, il cliente sicuramente non sara' registrato, se invece viene inserito un codice fiscale con un formato nullo, il programma sollevera' un'eccezione
	 * Una volta trovato il cliente in questione, e' possibile modificare il luogo di residenza, visualizzare tutti i dettagli del cliente, visualizzare gli smartphone acquistati e noleggiati,
	 * visualizzare il numero di noleggi rimanenti per un dato giorno (Il numero massimo di noleggi in un giorno, per un cliente, e' 3).
	 * Se un cliente o un smartphone venuduto/noleggiato viene eliminato, tutte le informazioni rimarranno comunque consultabili (ATTENZIONE! non sarà possibile gestire direttamente gli oggetti eliminati, ma soltanto visualizzarli)
	 * @param clienti [Set di oggetti di tipo CLiente che conterra' ogni cliente registrato]
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @throws EccezioneMain
	 * @throws EccezioneRuoli
	 */
	public static void RicercaClienteCodiceFiscale(Set<Cliente> clienti, Scanner s) throws EccezioneMain, EccezioneRuoli {

		final String CodiceFiscaleF = "[A-Z]{6}[0-9]{2}[ABCDEHLMPRST]{1}[0-9]{2}([A-Z]{1}[0-9]{3})[A-Z]{1}$";
		String cf=null;
		boolean trovato=false;
		int scelta;
		String residenza;
		Cliente c = null;
		String d;
		final String Data = "^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d$"; //GG/MM/AAAA;

		if(clienti.isEmpty())
			System.out.println("Nessun cliente ancora registrato");

		else {


			System.out.print("\nInserisci il codice fiscale del cliente da ricercare (Maiuscolo): ");
			cf=s.next();

			if(!cf.matches(CodiceFiscaleF)) 
				throw new EccezioneMain("Errore, formato del codice fiscale errato");

			for (Cliente cliente : clienti) {
				if(cliente.getCodiceFiscale().compareTo(cf)==0) {
					c=cliente;
					trovato=true;
				}



			}

			if(!trovato)
				System.out.println("\nNessun cliente trovato\n");

			else {
				System.out.println("\nIl cliente " + c.getCodiceFiscale() + " e' stato trovato correttamente\n");
				System.out.println("1) Modifica luogo di residenza");	
				System.out.println("2) Visualizza tutte le informazioni personali del cliente ");
				System.out.println("3) Visualizzza gli smartphone acquistati");
				System.out.println("4) Visualizzza gli smartphone noleggiati ");
				System.out.println("5) Visualizzi i noleggi ancora disponibili per un dato giorno");
				System.out.print("\nFai la tua scelta (Scegli un numero):  ");

				scelta = s.nextInt();

				switch(scelta) {
				case 1: System.out.print("Inserisci il nuovo luogo di residenza: ");
				residenza = s.next();
				try {
					c.setLuogoDiResidenza(residenza);
				} catch (EccezioneRuoli e) {
					e.printStackTrace();
				}
				System.out.println("Luogo di residenza aggiornato correttamente");
				break;

				case 2:
					System.out.println(c.toString());
					break;
				case 3:
					System.out.println("\nLista degli Smartphone acquistati dal cliente: " + c.getCodiceFiscale() + " ");

					if(c.getSmartphoneAcquistati().isEmpty())
						System.out.println("\nNessuno smartphone acquistato dal cliente\n");

					else
						StampaFormattata.Stampa(c.getSmartphoneAcquistati());

					break;
				case 4:
					Set<Noleggio> noleggi = new LinkedHashSet<Noleggio>();

					noleggi = CatalogoNoleggi.cloneNoleggioCognome(c);

					if(noleggi.isEmpty())
						System.out.println("Nessuno smartphone noleggiato dal cliente\n");
					else
						StampaFormattata.Stampa(noleggi);

					break;	
				case 5:
					System.out.print("\nInserisci la data da controllare (GG/MM/AA): ");
					d = s.next();

					if(!d.matches(Data))
						throw new EccezioneMain("Formato data errato");

					if(c.getDataNoleggi().containsKey(d)) {
						System.out.println("In data " + d  + " e' possibile effettuare ancora " + c.getDataNoleggi().get(d)+ " noleggi");
					}

					else{	
						System.out.println("In data " + d  + " non e' stato effettuato nessun noleggio. \nI noleggi totali effettuabili in un giorno sono 3 ");
					}

					break;

				default:
					throw new EccezioneMain("Scelta non consentita");

				}

			}
		}
	}
	
	/**
	 * Il codice cliente viene assegnato in maniera univoca ad ogni cliente
	 * Il riferimento ad ogni cliente avverra' sempre tramite il Codice fiscale, infatti il codice cliente e' stato implementato per una questione puramente User-Friendly
	 * Quindi, invece di utilizzare il codice fiscale per ricercare un cliente, e' possibile ricercarlo anche attraverso il suo codice cliente, formato soltanto da un numero
	 * Ovviamente, e' possibile effettuare la ricerca in entrambi i modi
	 * Questo metodo permette, attraverso il corretto inserimento di un codice cliente (rispettando il formato), di ricercare un cliente registrato
	 * Se la ricerca non va a buon fine, il cliente sicuramente non sara' registrato
	 * Una volta trovato il cliente in questione, e' possibile modificare il luogo di residenza, visualizzare tutti i dettagli del cliente, visualizzare gli smartphone acquistati e noleggiati,
	 * visualizzare il numero di noleggi rimanenti per un dato giorno (Il numero massimo di noleggi in un giorno, per un cliente, e' 3).
	 * Se un cliente o un smartphone venuduto/noleggiato viene eliminato, tutte le informazioni rimarranno comunque consultabili (ATTENZIONE! non sarà possibile gestire direttamente gli oggetti eliminati, ma soltanto visualizzarli)
	 * @param clienti [Set di oggetti di tipo CLiente che conterra' ogni cliente registrato]
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @throws EccezioneMain
	 * @throws EccezioneRuoli
	 */
	public static void RicercaCodiceCliente(Set<Cliente> clienti, Scanner s) throws EccezioneMain, EccezioneRuoli {

		if(clienti.isEmpty())
			System.out.println("Nessun cliente ancora registrato");
		else {


			System.out.print("\nInserisci codice cliente per la ricerca: ");
			boolean trovato=false;
			int codice = s.nextInt();
			int scelta;
			String residenza;
			String d;
			final String Data = "^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d$"; //GG/MM/AAAA;
			Cliente c = null;

			for (Cliente cliente : clienti) {
				if(cliente.getCodice()==codice) {
					c=cliente;
					trovato=true;
				}

			}

			if(!trovato)
				System.out.println("\nNessun cliente trovato\n");

			else {
				System.out.println("\nIl cliente " + c.getCodice() + " e' stato trovato correttamente\n");
				System.out.println("1) Modifica luogo di residenza");	
				System.out.println("2) Visualizza tutti i dettagli cliente ");
				System.out.println("3) Visualizzza gli smartphone acquistati");
				System.out.println("4) Visualizzza gli smartphone noleggiati");
				System.out.println("5) Visualizzi i noleggi ancora disponibili per un dato giorno ");
				System.out.print("\nFai la tua scelta: ");

				scelta = s.nextInt();

				switch(scelta) {
				case 1: System.out.print("Inserisci il nuovo luogo di residenza: ");
				residenza = s.next();
				try {
					c.setLuogoDiResidenza(residenza);
				} catch (EccezioneRuoli e) {
					
					e.printStackTrace();
				}
				System.out.println("Luogo di residenza aggiornato correttamente");
				break;

				case 2:
					System.out.println(c.toString());
					break;
				case 3:
					System.out.println("\nLista degli Smartphone acquistati dal cliente: " + c.getCodiceFiscale() + " ");

					if(c.getSmartphoneAcquistati().isEmpty())
						System.out.println("\nNessuno smartphone acquistato dal cliente\n");

					else
						StampaFormattata.Stampa(c.getSmartphoneAcquistati());

					break;

				case 4:
					
					Set<Noleggio> noleggi = new LinkedHashSet<Noleggio>();

					noleggi = CatalogoNoleggi.cloneNoleggioCognome(c);

					if(noleggi.isEmpty())
						System.out.println("Nessuno smartphone noleggiato dal cliente\n");
					else
						StampaFormattata.Stampa(noleggi);

					break;	
				case 5:
					System.out.print("\nInserisci la data da controllare (GG/MM/AA): ");
					d = s.next();

					if(!d.matches(Data))
						throw new EccezioneMain("Formato data errato");

					if(c.getDataNoleggi().containsKey(d)) {
						System.out.println("In data " + d  + " e' possibile effettuare ancora " + c.getDataNoleggi().get(d)+ " noleggi");
					}

					else{	
						System.out.println("In data " + d  + " non e' stato effettuato nessun noleggio. \nI noleggi totali effettuabili in un giorno sono 3 ");
					}
					break;

				default:
					throw new EccezioneMain("Scelta non consentita");

				}

			}
		}
	}


	/**
	 * Metodo che permette, attraveso l'inserimento di un codice cliente valido, l'eliminazione da quest'ultimo dal Set di clienti registrati
	 * Attenzione, verra' rimosso anche il suo Codice Fiscale all'interno di un Set statico presente nella classe Cliente (AbstractPersona), per prevenire eventuali errori e permettere in un futuro
	 * la reiscrizione di quel preciso cliente 
	 * @param clienti [Set di oggetti di tipo CLiente che conterra' ogni cliente registrato]
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @throws EccezioneMain
	 */
	public static void RimuoviCliente(Set<Cliente> clienti, Scanner s) throws EccezioneMain {

		int scelta = 0;
		boolean t=false;
		Cliente c = null;

		if(clienti.isEmpty())
			System.out.println("Nessun cliente registrato");

		else {
			System.out.println("\n");
			System.err.println("ATTENZIONE! L'ELIMINAZIONE SARA' DEFINITIVA");
			System.out.println("\n");

			StampaFormattata.Stampa(clienti);

			System.out.print("\nScegli il codice cliente: ");
			scelta = s.nextInt();

			for (Cliente cliente : clienti) {
				if(cliente.getCodice()==scelta) {
					t=true;
					c=cliente;
				}

			}

			if(t) {
				Iterator<Cliente> it = clienti.iterator();

				while(it.hasNext()) {
					if(it.next().getCodice()==scelta) {
						it.remove();	
						System.out.println("\nCliente eliminato correttamente.");
					}

				}

				Cliente.setCodiciFiscali(c);
			}


			else
				System.out.println("Nessun cliente eliminato");
		}
	}

	/**
	 * Metodo che permette la stampa di tutti i clienti registrati nel Set
	 * 
	 * @param clienti [Set di oggetti di tipo CLiente che conterra' ogni cliente registrato]
	 */
	public static void VisualizzaClienti(Set<Cliente> clienti) {

		if(clienti.isEmpty())
			System.out.println("\nNessun cliente registrato\n\n");

		else {


			StampaFormattata.Stampa(clienti);

		}
	}

}

