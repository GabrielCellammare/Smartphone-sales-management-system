package azienda.Main.Sezioni;

import java.util.Scanner;
import java.util.Set;

import azienda.Main.Eccezione.EccezioneMain;
import azienda.Ruoli.Cliente;
import azienda.Ruoli.Venditore;
import azienda.Ruoli.Eccezioni.EccezioneRuoli;
import azienda.Smartphone.SmartphoneAcquistabile;
import azienda.Smartphone.SmartphoneAvanzato;
import azienda.Smartphone.SmartphoneBase;
import azienda.Smartphone.Eccezioni.EccezioneSmartphone;
import azienda.VenditeNoleggi.CatalogoNoleggi;
import azienda.VenditeNoleggi.CatalogoVendite;
import azienda.VenditeNoleggi.Noleggio;
import azienda.VenditeNoleggi.Vendita;
import azienda.VenditeNoleggi.Eccezioni.VenditeNoleggiEccezione;
/**
 * Sezione del Software che si occupera' della registrazione, cancellazione, visualizzazione di ogni vendita
 * @author Gabriel Cellammare
 *
 */
public class SezioneVendita {

	/**
	 * Menu' utilizzato per scopi grafici; descrive tutte le possibili azioni che possono essere ricondotte alla gestione della singola vendita
	 */
	public static void MenuVendita() {

		System.out.println("\nBenvenuto nel menu' dedicato alle vendite!\n");
		System.out.println("1) Effettua una vendita");
		System.out.println("2) Ricerca vendita tramite codice identificativo vendita");
		System.out.println("3) Visualizza tutte le vendite");
		System.out.println("4) Visualizza l'incasso totale delle vendite");
		System.out.println("5) Torna al menu' principale");
		System.out.print("\nFai la tua scelta: ");

	}

	/**
	 * Metodo che si occupa della registrazione di una vendita, che andra' inserito nel Set statico contenuto nella classe CatalogoVendite
	 * Il metodo controlla oppurtanamente che ci sia un cliente, un venditore e uno smartphone acquistabile (Base o avanzato) registrato, se così non fosse non sara' possibile ultimare la vendita
	 * Dopodiche', verra stampata la lista di tutti i clienti che possono effettuare la vendita, richiedendo in input il codice cliente di quest'ultimo. Se non presente nella lista, la vendita non verra' effettuata
	 * In seguito, verra' stamapata la lista di tutti i venditori, richiedendo in input il codice del venditore che dovra' effettuare quest'ultima. Se non presente, la vendita non verra' completata
	 * Successivamente, verra' richiesto il Codice IMEI dello smartphone che si intende acquistare, se non presente nella lista, la vendita non verra' effettuata
	 * Durante la scelta dello smartphone, viene controllato se quest'ultimo e' stato noleggiato, e se così fosse, il Software permettera' di acquistare quest'ultimo SOLO e SOLTANTO dopo la conclusione del noleggio precedente
	 * E' stato implementato questo controllo, poichè il Software permette di registrare acquisti anche antecedenti alla data odierna. Quindi l'acquisto, e' consentito soltanto dopo l'ultimo periodo di noleggio, in modo
	 * da non causare problemi temporali.
	 * Esempio: Telefono acquistato durante un periodo di noleggio
	 * Se in questo caso, la data d'acquisto inserita e' antecedente la data di termine dell'ultimo noleggio, la vendita non verra' completata
	 * Se il telefono non presenta nessun noleggio, e' possibile acquistarlo direttamente, inserendo una qualsiasi data, anche antecedente o conseguente (accordo privato Cliente - Venditore) alla data odierna
	 * Nel momento in cui viene acquistato, la disponibilita' dello smartphone viene settata su "False".
	 * Ad ogni vendita, verra' assegnato un codice univoco che servira' per ricercare alcuni dettagli di quest'ultima
	 * Dopo la creazione dell'istanza Vendita, quest'ultima sara' inserita nel set statico della classe "CatalogoVendite", che conterra' tutte le vendite effettuate
	 * 
	 * @param s [Oggetto di tipo Scanner che servira' per l'inserimento da tastiera]
	 * @param smartphoneavanzato [Oggetto di tipo "SmartphoneAvanzato" e "SmartphoneAcquistabile" che servira' per la vendita]
	 * @param smartphonebase [Oggetto di tipo "SmartphoneBase" e "SmartphoneAcquistabile" che servira' per la vendita]
	 * @param clienti [Oggetto di tipo "Cliente" che servira' per la vendita]
	 * @param venditori [Oggetto di tipo "Venditore" che servira' per la vendita]
	 * @throws EccezioneSmartphone
	 * @throws EccezioneMain
	 * @throws VenditeNoleggiEccezione
	 * @throws EccezioneRuoli
	 * @throws InterruptedException
	 */
	public static void addVendita(Scanner s, Set<SmartphoneAvanzato> smartphoneavanzato, Set<SmartphoneBase> smartphonebase,Set<Cliente> clienti, Set<Venditore> venditori) throws EccezioneSmartphone, EccezioneMain, VenditeNoleggiEccezione, EccezioneRuoli, InterruptedException{

		String cv,imei,data;
		int cc;
		boolean flagC=false;
		boolean flagS=false;
		boolean flagfinale=false;
		boolean trovato=false;
		boolean sanoleggio=false;
		String dataMax="01/01/1900";
		Cliente c=null;
		Venditore v=null;
		SmartphoneAcquistabile sa=null;
		
		
		if(smartphoneavanzato.isEmpty())
			flagC=true;

		if(smartphonebase.isEmpty())
			flagS=true;

		if(flagC && flagS)
			System.out.println("Impossibile effettuare una vendita, aggiungere prima degli smartphone acquistabili (Base o Avanzato)");

		else{

			flagC=false;

			if(clienti.isEmpty())
				flagC=true;

			if(venditori.isEmpty())
				flagC=true;

			if(flagC)
				System.out.println("Impossibile effettuare una vendita, aggiungere almeno un cliente e un venditore");
			else
				flagfinale=true;

		}

		if(flagfinale) {


			System.out.println("\n");
			System.out.println("Lista di tutti i clienti che possono effettuare l'acquisto");
			SezioneCliente.VisualizzaClienti(clienti);

			System.out.print("Inserisci il codice cliente del cliente che effettuera' l'acquisto: ");
			cc=s.nextInt();

			for (Cliente cliente : clienti) {

				if(cliente.getCodice()==cc) {
					trovato=true;
					c=cliente;
				}

			}

			if(!trovato)
				System.out.println("Errore, il codice cliente inserito non ha nessuna corrispondenza");

			else{
				trovato=false;
				System.out.println("\n");
				System.out.println("Lista di tutti i venditori che possono effettuare la vendita");
				SezioneVenditore.VisualizzaVenditori(venditori);

				System.out.print("Inserisci il codice venditore del venditore che effettuera' la vendita (10 caratteri alfanumerici): ");
				cv=s.next();

				if(cv.length()!=10)
					throw new EccezioneRuoli("Errore, il codice venditore ha una lunghezza di 10 caratteri");

				for (Venditore venditore : venditori) {

					if(venditore.getCodice_venditore().compareTo(cv)==0) {
						trovato=true;
						v=venditore;
					}
				}

				if(!trovato)
					System.out.println("Errore, il codice venditore inserito non ha nessuna corrispondenza");

				else {
					trovato = false;

					final String IMEI = ("[0-9]{15}/[0-9]{2}");
					System.out.println("\n\n\nATTENZIONE! E' possibile acquistare soltanto Smartphone base o avanzati");

					System.out.println("\nLista smartphone base\n");
					SezioneSmartphoneBase.VisualizzaSmartphone(smartphonebase);
					
					Thread.sleep(1000);
					
					System.out.println("\n\nLista smartphone avanzati\n\n");
					SezioneSmartphoneAvanzato.VisualizzaSmartphone(smartphoneavanzato);

					System.out.println("\n\nAttenzione, selezionando degli smartphone non disponibili, il programma sollevera' un'eccezione");
					System.out.print("\n\nScegliere lo smartphone da acquistare inserendo il codice IMEI: ");
					imei=s.next();

					if(!imei.matches(IMEI))
						throw new EccezioneSmartphone("Errore, formato del codice IMEI non valido");

					for (SmartphoneBase smartphone : smartphonebase) {
						if(smartphone.getCodiceImei().compareTo(imei)==0) {
							trovato=true;
							sa=smartphone;
						}
					}

					if(!trovato){
						for (SmartphoneAvanzato smartphone : smartphoneavanzato) {
							if(smartphone.getCodiceImei().compareTo(imei)==0) {
								trovato=true;
								sa=smartphone;
							}
						}
					}

					if(!trovato)
						System.out.println("Errore, il codice IMEI inserito non ha nessuna corrispondenza");

					else {

						for (Noleggio noleggio : CatalogoNoleggi.cloneNoleggioCognome()) {

							if(noleggio.getS().equals(sa)) {

								if(SezioneNoleggio.CalcoloGiorni(noleggio.getData_f(), dataMax)<0) {
									dataMax=noleggio.getData_f();
									sanoleggio=true;
								}



							}
						}

						if(!sanoleggio) {

							System.out.print("Inserisci la data dell'acquisto (GG/MM/AAAA): ");
							data=s.next();

							Vendita V = new Vendita(c, v, data, sa.getPrezzo(),sa);

							System.out.println("\n\nCongratulazioni, vendita effettuata correttamente\n\n");

							System.out.println("Questa vendita effettuata da " + v.getCodice_venditore() + " avra' un codice identificativo n: " + V.getCodice() + " ");
						}

						else {
							System.out.println("\n\nLo smartphone " + sa.getCodiceImei() + " e' stato noleggiato in precedenza\n\n");
							System.out.println("I periodi di noleggio dello smartphone " + sa.getCodiceImei() + " sono i seguenti: ");
							StampaFormattata.Stampa(CatalogoNoleggi.cloneNoleggioCognomeS(sa));


							System.out.println("Attenzione, la data di vendita deve essere effettuata dopo la scadenza dell'ultimo noleggio.");
							System.out.println("L'ultimo noleggio e' terminato in data " + dataMax + " ");
							System.out.print("Inserisci la data dell'acquisto (GG/MM/AAAA): ");
							data=s.next();
							
							if(SezioneNoleggio.CalcoloGiorni(dataMax,data)>0) {
								
								Vendita V = new Vendita(c, v, data, sa.getPrezzo(),sa);

								System.out.println("\n\nCongratulazioni, vendita effettuata correttamente\n\n");

								System.out.println("Questa vendita effettuata da " + v.getCodice_venditore() + " avra' un codice identificativo n: " + V.getCodice() + " ");
								
							}
							
							else {
								
								System.out.println("\nLa data inserita non e' valida, poiche' in quel periodo lo smartphone era noleggiato\n");
								
							}
						}
					}





				}

			}

		}

	}

	/**
	 * Metodo che permette la stampa dell'incasso totale generato da ogni vendita
	 * Viene richiamato il metodo getter statico, Vendita.getincasso()
	 */
	public static void VisualizzaIncassoTotale() {

		System.out.println("Le vendite hanno prodotto un fatturato di €" + Vendita.getIncasso() + " ");
	}

	/**
	 * Metodo che permette attraverso il corretto inserimento di un codice vendita esistente, di leggere tutte le informazioni di quest'ultima
	 * 
	 * @param s [Oggetto di tipo Scanner che servira' per l'inserimento da tastiera]
	 * @throws EccezioneMain
	 * @throws VenditeNoleggiEccezione
	 */
	public static void RicercaCodiceVendita(Scanner s) throws EccezioneMain, VenditeNoleggiEccezione {

		if(CatalogoVendite.cloneVenditaDataPrezzo().isEmpty())
			System.out.println("Nessuna vendita ancora registrata");

		else {
			System.out.print("\nInserisci codice vendita per la ricerca: ");
			boolean trovato=false;
			int codice = s.nextInt();
			Vendita v = null;

			for (Vendita vendita : CatalogoVendite.cloneVenditaDataPrezzo()) {
				if(vendita.getCodice()==codice) {
					v=vendita;
					trovato=true;
				}

			}

			if(!trovato)
				System.out.println("\nNessuna vendita trovata\n");

			else {
				System.out.println("\nLa vendita " + v.getCodice() + " e' stata trovata correttamente\n");
				System.out.println("Informazioni della vendita selezionata");
				System.out.println(v.toString());
			}



		}

	}
	
	/**
	 * Metodo che permette di stampare attraverso la classe StampaFormattata, tutte le vendite 
	 */
	public static void VisualizzaVendite() {

		if(CatalogoVendite.cloneVenditaDataPrezzo().isEmpty())
			System.out.println("\nNessuna vendita registrata\n\n");

		else {


			StampaFormattata.Stampa(CatalogoVendite.cloneVenditaDataPrezzo());

		}
	}

}
