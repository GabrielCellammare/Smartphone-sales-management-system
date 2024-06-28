package azienda.Main.Sezioni;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;

import azienda.Main.Eccezione.EccezioneMain;
import azienda.Ruoli.Cliente;
import azienda.Ruoli.Venditore;
import azienda.Ruoli.Eccezioni.EccezioneRuoli;
import azienda.Smartphone.SmartphoneAvanzato;
import azienda.Smartphone.Eccezioni.EccezioneSmartphone;
import azienda.VenditeNoleggi.CatalogoNoleggi;
import azienda.VenditeNoleggi.CatalogoVendite;
import azienda.VenditeNoleggi.Noleggio;
import azienda.VenditeNoleggi.Vendita;
import azienda.VenditeNoleggi.Eccezioni.VenditeNoleggiEccezione;
/**
 * Sezione del Software che si occupera' della registrazione, cancellazione, visualizzazione di ogni noleggio
 * @author Gabriel Cellammare
 *
 */
public class SezioneNoleggio{

	/**
	 * Metodo che permette, di controllare la scadenza del noleggio di ogni smartphone, confrontando la data di fine noleggio con quella odierna
	 * Tutti i noleggi sono registrati nel Set statico della classe CatalogoNoleggio, che all'interno di questo metodo, viene letto completamente
	 * Se il confronto tra la data di noleggio, e la data odierna produce come risultato 0, allora andra' controllata la disponibilita' dello smartphone
	 * Se è falsa, allora la disponibilita' andra' settata nuovamente al valore "True", controllando opportunamente che il telefono in questione non risulti venduto
	 * Se nessuno smartphone risulta in scadenza, l'utentee verra' avvisato
	 * 
	 * ESEMPIO:
	 * Lo smartphone x e' stato noleggiato dal 15/02/2022 al 20/02/2022.
	 * Lo smartphone x viene acquistato il giorno 25/02/2022
	 * Dopo aver richiamato questo metodo il giorno 27/02/2022, senza il controllo sul Set delle Vendite, il Software renderebbe lo smartphone nuovamente disponibile, data la scadenza del noleggio
	 * senza conoscere, l'effettivo acquisto
	 * 
	 * Il metodo verra' richiamato ogni qualvolta si tornera' alla SezioneNoleggio
	 * @param smartphoneavanzato [Set di oggetti di tipo "SmarpthoneAvanzato" noleggiabili]
	 * @throws EccezioneRuoli
	 */
	public static void ControllaDisponibilita(Set<SmartphoneAvanzato> smartphoneavanzato) throws EccezioneRuoli {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");  
		LocalDateTime now = LocalDateTime.now();  

		SmartphoneAvanzato s = null;

		boolean venduto=false;
		boolean entrato=false;
		boolean trovato = false;



		if(CatalogoNoleggi.cloneNoleggioCognome().isEmpty()) {
			System.out.println("\n\nAttualmente nessuno smartphone avanzato ha terminato il suo noleggio, poiche' non e' stato effettuato nessun noleggio\n");

		}

		else {


			for(Noleggio noleggio : CatalogoNoleggi.cloneNoleggioCognome()) {

				entrato=false;
				venduto = false;

				if(SezioneNoleggio.CalcoloGiorni(noleggio.getData_f(), dtf.format(now))>=0) {


					if(!noleggio.getS().isDisponibile()){

						s=noleggio.getS();
						entrato=true;

						for (Vendita vendita : CatalogoVendite.cloneVenditaDataPrezzo()) {

							if(vendita.getS().equals(s)) {
								venduto = true;
							}
						}


						if(entrato) {


							if(!venduto){


								noleggio.getS().setDisponibile(true);


								System.out.println("\nLo smartphone avanzato con codice IMEI: " + noleggio.getS().getCodiceImei() + " e' di nuovo disponibile, poiche' il noleggio e' terminato\n");
								trovato = true;

							}
						}
					}
				}
			}





		}



		if(!trovato) {
			System.out.println("Nessun noleggio in scadenza nella data odierna: " + dtf.format(now) + " "); 
		}
	}

	/**
	 * Menu' utilizzato per scopi grafici; descrive tutte le possibili azioni che possono essere ricondotte alla gestione del singolo noleggio
	 */
	public static void MenuNoleggio() {

		System.out.println("\nBenvenuto nel menu' dedicato ai noleggi!\n");
		System.out.println("1) Effettua un noleggio");
		System.out.println("2) Ricerca noleggio tramite codice identificativo noleggio");
		System.out.println("3) Visualizza tutti i noleggi");
		System.out.println("4) Visualizza l'incasso totale dei noleggi");
		System.out.println("5) Visualizza la formula per il calcolo del prezzo di noleggio");
		System.out.println("6) Stampa su file l'elenco degli smartphone noleggiati");
		System.out.println("7) Torna al menu' principale");

		System.out.print("\nFai la tua scelta: ");

	}

	/**

	 * Metodo che si occupa della registrazione di un noleggio, che andra' inserito nel Set statico contenuto nella classe CatalogoNoleggi
	 * Il metodo controlla oppurtanamente che ci sia un cliente, un venditore e uno smartphone noleggiabile (Avanzato) registrato, se così non fosse non sara' possibile ultimare il noleggio
	 * Dopodiche', verra stampata la lista di tutti i clienti che possono effettuare il noleggio, richiedendo in input il codice cliente di quest'ultimo. Se non presente nella lista, il noleggio non verra' effettuato
	 * In seguito, verra' stamapata la lista di tutti i venditori, richiedendo in input il codice del venditore che dovra' effettuare quest'ultima. Se non presente, il noleggio non verra' completato
	 * Successivamente, verra' richiesto il Codice IMEI dello smartphone che si intende noleggiare, se non presente nella lista, il noleggio non verra' effettuato
	 * Durante la scelta dello smartphone, viene controllato se quest'ultimo e' stato noleggiato, e se così fosse, il Software permettera' di noleggiare quest'ultimo SOLO e SOLTANTO dopo la conclusione del noleggio precedente
	 * E' stato implementato questo controllo, poichè il Software permette di registrare noleggi anche antecedenti alla data odierna. Quindi il noleggio, e' consentito soltanto dopo l'ultimo periodo di noleggio, in modo
	 * da non causare problemi temporali.
	 * Esempio: Telefono noleggiato durante un altro periodo di noleggio
	 * Se in questo caso, la data di inizio noleggio inserita e' antecedente la data di termine dell'ultimo noleggio, il noleggio non verra' completato
	 * Se il telefono non presenta nessun noleggio, e' possibile noleggiarlo direttamente, inserendo una qualsiasi data, anche antecedente o conseguente (accordo privato Cliente - Venditore) alla data odierna
	 * Nel momento in cui viene noleggiato, la disponibilita' dello smartphone viene settata su "False".
	 * Ogni volta che si ritornera' al menu principale della gestione Noleggio, verra' controllata la disponibilita' dello Smartphone(Se la data di terminazione del noleggio corrisponde a quella odierna, lo smartphone ritorna disponibile)
	 * Ad ogni noleggio, verra' assegnato un codice univoco che servira' per ricercare alcuni dettagli di quest ultimo
	 * Dopo la creazione dell'istanza Noleggio, quest'ultima sara' inserita nel set statico della classe "CatalogoNoleggi", che conterra' tutti i noleggi effettuati
	 *
	 * @param s
	 * @param smartphoneavanzato
	 * @param clienti
	 * @param venditori
	 * @throws EccezioneSmartphone
	 * @throws EccezioneMain
	 * @throws VenditeNoleggiEccezione
	 * @throws EccezioneRuoli
	 */

	public static void addNoleggio(Scanner s, Set<SmartphoneAvanzato> smartphoneavanzato, Set<Cliente> clienti, Set<Venditore> venditori) throws EccezioneSmartphone, EccezioneMain, VenditeNoleggiEccezione, EccezioneRuoli{

		String cv,imei,datai,dataf;
		int cc;
		boolean flagC=false;
		boolean flagfinale=false;
		boolean  trovato=false;
		Cliente c=null;
		Venditore v=null;
		int valorec=0;
		int valorev=0;
		SmartphoneAvanzato sa=null;
		double prezzonoleggio=0;
		boolean sanoleggio=false;
		String dataMax="01/01/1900";

		DecimalFormat df = new DecimalFormat("0.00");


		if(smartphoneavanzato.isEmpty())
			flagC=true;


		if(flagC)
			System.out.println("Impossibile effettuare un noleggio, aggiungere prima degli smartphone noleggiabili (Tipo: Avanzato)");

		else{

			flagC=false;

			if(clienti.isEmpty())
				flagC=true;

			if(venditori.isEmpty())
				flagC=true;

			if(flagC)
				System.out.println("Impossibile effettuare un noleggio, aggiungere almeno un cliente e un venditore");
			else
				flagfinale=true;

		}

		if(flagfinale) {

			System.out.println("\n");
			System.out.println("Lista di tutti i clienti che possono effettuare il noleggio");
			SezioneCliente.VisualizzaClienti(clienti);

			System.out.print("Inserisci il codice cliente del cliente che effettuera' il noleggio: ");
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
				System.out.println("Lista di tutti i venditori che possono effettuare il noleggio");
				SezioneVenditore.VisualizzaVenditori(venditori);

				System.out.print("Inserisci il codice venditore del venditore che effettuera' il noleggio (10 caratteri alfanumerici): ");
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
					System.err.println("\n\nE' possibile noleggiare soltanto Smartphone avanzati");


					System.out.println("\n\nLista smartphone avanzati\n\n");
					SezioneSmartphoneAvanzato.VisualizzaSmartphone(smartphoneavanzato);
					System.out.print("Scegliere lo smartphone da noleggiare inserendo il codice IMEI: ");
					imei=s.next();

					if(!imei.matches(IMEI))
						throw new EccezioneSmartphone("Errore, formato del codice IMEI non valido");

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

						if(sa.isDisponibile()) {
							trovato = false;
							for (Noleggio noleggio : CatalogoNoleggi.cloneNoleggioCognome()) {

								if(noleggio.getS().equals(sa)) {

									if(SezioneNoleggio.CalcoloGiorni(noleggio.getData_f(), dataMax)<0) {
										dataMax=noleggio.getData_f();
										sanoleggio=true;
									}



								}
							}


							if(!sanoleggio) {

								System.out.println("\n\nAttenzione, il noleggio deve essere minimo di 1 giorno\n\n");
								System.out.print("\nInserisci la data d'inizio del noleggio (GG/MM/AAAA): ");

								datai=s.next();


								if(c.getDataNoleggi().containsKey(datai)) {

									trovato = true;
									valorec = c.getDataNoleggi().get(datai);

								}


								if(trovato) {

									if(valorec>0) {

										System.out.println("\nIl cliente " + c.getCodiceFiscale() + " in data " + datai + " puo' effettuare ancora " + valorec + " noleggi (MAX NOLEGGI IN UN GIORNO = 3)");

									}

									if(valorec==0) {
										System.err.println("\nAttenzione, il cliente ha raggiunto il limite massimo di noleggi in un giorno (MAX NOLEGGI = 3)");
									}

								}

								else {
									System.out.println("\nIl cliente " + c.getCodiceFiscale() + " in data " + datai + " non ha ancora effettuato un noleggio (MAX NOLEGGI IN UN GIORNO = 3)");
									valorec=1;
								}


								trovato=false;



								if(v.getDataNoleggi().containsKey(datai)) {

									trovato = true;
									valorev = v.getDataNoleggi().get(datai);

								}

								if(trovato) {

									if(valorev>0) {

										System.out.println("\nIl venditore " + v.getCodice_venditore() + " in data " + datai + " puo' effettuare ancora " + valorev + " noleggi (MAX NOLEGGI IN UN GIORNO = 10)");

									}

									if(valorev==0) {
										System.err.println("\nAttenzione, il venditore ha raggiunto il limite massimo di noleggi in un giorno (MAX NOLEGGI = 10)");
									}



								}

								else {
									System.out.println("\nIl venditore " + v.getCodice_venditore() + " in data " + datai + " non ha ancora effettuato un noleggio (MAX NOLEGGI IN UN GIORNO = 10) ");
									valorev=1;
								}

								if(valorec>0 && valorev>0) {


									System.out.print("\nInserisci la data di fine del noleggio (GG/MM/AAAA): ");
									dataf=s.next();

									prezzonoleggio = CalcoloPrezzoNoleggio(sa.getPrezzo(),datai,dataf);

									Noleggio n = new Noleggio(c,v,sa,prezzonoleggio,datai,dataf);

									System.out.println("Il prezzo di noleggio complessivo equivale a €" + df.format(prezzonoleggio) + ", calcolato attraverso la formula consultabile dal menu\n");
									System.out.println("Il costo giornaliero del noleggio equivale a €" + df.format(prezzonoleggio/(SezioneNoleggio.CalcoloGiorni(datai,dataf))) + " al giorno, per un totale di " + (SezioneNoleggio.CalcoloGiorni(datai,dataf)) + " giorni\n");


									System.out.println("Questo noleggio effettuato da " + v.getCodice_venditore() + " avra' un codice identificativo n: " + n.getCodice() + " ");

								}

								else {
									System.out.println("\nNon e' stato possibile effettuare il noleggio, a causa dei limiti imposti dal sistema.\n");
									System.err.println("\nUn venditore puo' effettuare 10 noleggi al giorno\nUn cliente puo' effettuare 3 noleggi al giorno\n");
								}

							}


							else {

								System.out.println("\n\nLo smartphone " + sa.getCodiceImei() + " e' stato noleggiato in precedenza\n\n");
								System.out.println("I periodi di noleggio dello smartphone " + sa.getCodiceImei() + " sono i seguenti: ");
								StampaFormattata.Stampa(CatalogoNoleggi.cloneNoleggioCognomeS(sa));


								System.out.println("Attenzione, la data d'inizio noleggio deve essere effettuata dopo la scadenza dell'ultimo noleggio.");
								System.out.println("L'ultimo noleggio e' terminato in data " + dataMax + " ");
								System.out.print("Inserisci la data d'inizio noleggio (GG/MM/AAAA): ");
								datai=s.next();

								if(SezioneNoleggio.CalcoloGiorni(dataMax,datai)>0) {


									if(c.getDataNoleggi().containsKey(datai)) {

										trovato = true;
										valorec = c.getDataNoleggi().get(datai);

									}


									if(trovato) {

										if(valorec>0) {

											System.out.println("\nIl cliente " + c.getCodiceFiscale() + " in data " + datai + " puo' effettuare ancora " + valorec + " noleggi (MAX NOLEGGI IN UN GIORNO = 3)");

										}

										if(valorec==0) {
											System.err.println("\nAttenzione, il cliente ha raggiunto il limite massimo di noleggi in un giorno (MAX NOLEGGI = 3)");
										}

									}

									else {
										System.out.println("\nIl cliente " + c.getCodiceFiscale() + " in data " + datai + " non ha ancora effettuato un noleggio (MAX NOLEGGI IN UN GIORNO = 3)");
										valorec=1;
									}


									trovato=false;



									if(v.getDataNoleggi().containsKey(datai)) {

										trovato = true;
										valorev = v.getDataNoleggi().get(datai);

									}

									if(trovato) {

										if(valorev>0) {

											System.out.println("\nIl venditore " + v.getCodice_venditore() + " in data " + datai + " puo' effettuare ancora " + valorev + " noleggi (MAX NOLEGGI IN UN GIORNO = 10)");

										}

										if(valorev==0) {
											System.err.println("\nAttenzione, il venditore ha raggiunto il limite massimo di noleggi in un giorno (MAX NOLEGGI = 10)");
										}



									}

									else {
										System.out.println("\nIl venditore " + v.getCodice_venditore() + " in data " + datai + " non ha ancora effettuato un noleggio (MAX NOLEGGI IN UN GIORNO = 10) ");
										valorev=1;
									}

									if(valorec>0 && valorev>0) {


										System.out.print("\nInserisci la data di fine del noleggio (GG/MM/AAAA): ");
										dataf=s.next();

										prezzonoleggio = CalcoloPrezzoNoleggio(sa.getPrezzo(),datai,dataf);

										Noleggio n = new Noleggio(c,v,sa,prezzonoleggio,datai,dataf);

										System.out.println("Il prezzo di noleggio complessivo equivale a €" + df.format(prezzonoleggio) + ", calcolato attraverso la formula consultabile dal menu\n");
										System.out.println("Il costo giornaliero del noleggio equivale a €" + df.format(prezzonoleggio/(SezioneNoleggio.CalcoloGiorni(datai,dataf))) + " al giorno, per un totale di " + (SezioneNoleggio.CalcoloGiorni(datai,dataf)) + " giorni\n");


										System.out.println("Questo noleggio effettuato da " + v.getCodice_venditore() + " avra' un codice identificativo n: " + n.getCodice() + " ");

									}

									else {
										System.out.println("\nNon e' stato possibile effettuare il noleggio, a causa dei limiti imposti dal sistema.\n");
										System.err.println("\nUn venditore puo' effettuare 10 noleggi al giorno\nUn cliente puo' effettuare 3 noleggi al giorno\n");
									}


								}

								else {

									System.out.println("\nLa data inserita non e' valida, poiche' in quel periodo lo smartphone era noleggiato\n");

								}
							}

						}

						else {
							System.out.println("\n\nSmartphone non disponibile attualmente per il noleggio\n");
						}


					}






				}

			}

		}

	}

	/**
	 * Metodo che permette la stampa dell'incasso totale generato da ogni noleggio
	 * Viene richiamato il metodo getter statico, Noleggio.getincassi()
	 */
	public static void VisualizzaIncassoTotale() {

		DecimalFormat df = new DecimalFormat("0.00");

		System.out.println("I noleggi hanno prodotto un fatturato di €" + df.format(Noleggio.getIncassi()) + " ");
	}

	/**
	 * Metodo che permette attraverso il corretto inserimento di un codice noleggio esistente, di leggere tutte le informazioni di quest ultimo
	 * 
	 * @param s [Oggetto di tipo Scanner che servira' per l'inserimento da tastiera]
	 * @throws EccezioneMain
	 * @throws VenditeNoleggiEccezione
	 */
	public static void RicercaCodiceNoleggio(Scanner s) throws EccezioneMain, VenditeNoleggiEccezione {

		if(CatalogoNoleggi.cloneNoleggioCognome().isEmpty())
			System.out.println("Nessun noleggio registrato");

		else {
			System.out.print("\nInserisci codice noleggio per la ricerca: ");
			boolean trovato=false;
			int codice = s.nextInt();
			Noleggio n = null;

			for (Noleggio noleggio : CatalogoNoleggi.cloneNoleggioCognome()) {
				if(noleggio.getCodice()==codice) {
					n=noleggio;
					trovato=true;
				}

			}

			if(!trovato)
				System.out.println("\nNessun noleggio trovato\n");

			else {
				System.out.println("\nIl noleggio " + n.getCodice() + " e' stato trovato correttamente\n");
				System.out.println("Informazioni del noleggio selezionato");
				System.out.println(n.toString());
			}



		}

	}

	/**
	 *  Metodo che permette di stampare attraverso la classe StampaFormattata, tutti i noleggi 
	 */
	public static void VisualizzaNoleggi() {

		if(CatalogoNoleggi.cloneNoleggioCognome().isEmpty())
			System.out.println("\nNessun noleggio registrato\n\n");

		else {


			StampaFormattata.Stampa(CatalogoNoleggi.cloneNoleggioCognome());

		}
	}

	private static void VisualizzaNoleggiGiornoCorrente() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");  
		LocalDateTime now = LocalDateTime.now();  


		if(CatalogoNoleggi.cloneNoleggioCognomeDataOdierna().isEmpty())
			System.out.println("Nessun noleggio terminato fino alla data corrente: " + dtf.format(now));

		else {


			StampaFormattata.Stampa(CatalogoNoleggi.cloneNoleggioCognomeDataOdierna());

		}
	}




	private static double CalcoloPrezzoNoleggio(double prezzo_acquisto,String data_i,String data_f) {


		final double Assicurazione = 15;
		double AssicurazioneGiorno = 0;
		int giorni=0;
		int mesi=0;
		int anni=0;
		double percentuale = 0;
		double prezzogiornaliero=0;
		Period p = Noleggio.calcoloDate(data_i, data_f);

		anni=p.getYears();
		mesi=(anni*12)+p.getMonths();
		giorni=(mesi*30)+p.getDays();


		if(giorni<=30) 
			percentuale = 0.15;

		if(giorni>30 && giorni<360)
			percentuale = 0.5;

		if(giorni==360)
			percentuale = 0.25;

		if(giorni>360)
			percentuale = -1;


		if(percentuale>0)
			prezzogiornaliero = (prezzo_acquisto/365) + ((prezzo_acquisto/365)*percentuale); //aumento

		if(percentuale<0) {
			percentuale = 0.5; //sconto del 5%
			prezzogiornaliero = (prezzo_acquisto/365) - ((prezzo_acquisto/365)*percentuale); //sconto del 5%;
		}

		AssicurazioneGiorno = Assicurazione/giorni;

		prezzogiornaliero+=AssicurazioneGiorno;


		return prezzogiornaliero*giorni;

	}

	/**
	 * Funzione che restituisce il numero di giorni che intercorrono tra una certa data x d'inizio, e una data y di fine
	 * @param data_i [Variabile String che rappresenta la data d'inizio]
	 * @param data_f [Variabile String che rappresenta la data di fine]
	 * @return giorni [Variabile Int che rappresenta il numero di giorni]
	 */
	public static int CalcoloGiorni(String data_i,String data_f) {

		Period p = Noleggio.calcoloDate(data_i, data_f);

		int anni=p.getYears();
		int mesi=(anni*12)+p.getMonths();

		int giorni = (mesi*30)+p.getDays();


		return giorni;

	}

	/**
	 * Metodo che permette all'utente di simulare un noleggio, inserendo il prezzo di un ipotetico smartphone, la data di inizio noleggio e la data di fine
	 * L'algoritmo utilizzato per calcolare il prezzo di noleggio, e' assolutamente auto-sviluppato e soggettivo
	 * Per conoscere l'algoritmo utilizzato, e' necessario richiamare la funzione
	 * 
	 * @param s [Oggetto di tipo Scanner che servira' per l'inserimento da tastiera]
	 * @throws EccezioneMain
	 */
	public static void InfoNoleggio(Scanner s) throws EccezioneMain {

		String datai,dataf = null;
		double prezzo;

		int scelta;

		DecimalFormat df = new DecimalFormat("0.00");

		System.out.println("\n\n");
		System.out.println("\nBenvenuto nella simulazione di un noleggio!\n");

		System.out.println("Il prezzo di noleggio e' calcolato con una forumula che prevede: ");

		System.out.println("\n15€ = Assicurazione (Costo fisso)\nCosto giornaliero = (Prezzo di acquisto / 365) * Giorni");
		System.out.println("\nLa durata del noleggio determina una percentuale di aumento o di sconto sul costo giornaliero\n");
		System.out.println("\n1)Durata del noleggio uguale o inferiore ad 1 mese: Aumento del 15/% del costo giornaliero");
		System.out.println("\n2)Durata del noleggio superiore ad 1 mese e inferiore a 12 mesi: Aumento del 5/%  del costo giornaliero");
		System.out.println("\n3)Durata del noleggio uguale a 12 mesi: Aumento dello 2,5/% del costo giornaliero ");
		System.out.println("\n4)Durata del noleggio superiore di 12 mesi: Sconto del 5% del costo giornaliero");


		System.out.println("\n\n");

		System.out.println("\nVuoi effettuare una simulazione?");
		System.out.println("1) Si");
		System.out.println("2) No");
		System.out.print("Fai una scelta: ");
		scelta=s.nextInt();

		if(scelta==1){

			System.out.print("\nInserisci il prezzo d'acquisto dello smartphone: ");
			prezzo=s.nextDouble();

			System.out.println("\nAttenzione, rispettare il formato della data\n\n");

			System.out.print("\nInserisci la data d'inizio noleggio (GG/MM/AAAA): ");
			datai=s.next();

			System.out.print("\nInserisci la data di fine noleggio (GG/MM/AAAA): ");
			dataf=s.next();


			System.out.println("\n\nIl prezzo di noleggio complessivo equivale a €" + df.format(SezioneNoleggio.CalcoloPrezzoNoleggio(prezzo, datai, dataf)) + "\n");
			System.out.println("Il costo giornaliero del noleggio equivale a €" + df.format(SezioneNoleggio.CalcoloPrezzoNoleggio(prezzo, datai, dataf)/(SezioneNoleggio.CalcoloGiorni(datai,dataf))) + " al giorno, per un totale di " + (SezioneNoleggio.CalcoloGiorni(datai,dataf)) + " giorni\n");
		}


		if(scelta==2){
			System.out.println("\n\n");
		}

		if(scelta<1 || scelta>2)
			throw new EccezioneMain("Scelta non consentita");



	}

	/**
	 * Metodo che risolve il caso d'uso [L’applicazione permette di ottenere la stampa su file dell’elenco degli smartphone noleggiati fino al
	 * giorno corrente.
	 * Tale elenco deve essere ordinato per cognome del dipendente che ha effettuato il noleggio; l’elenco riporterà per
	 * ogni noleggio il cognome del dipendente e tutte le informazioni dello smartphone noleggiato.]
	 * 
	 * La stampa avviene su un file chiamato "Noleggi.txt" salvato nella cartella "735118_Cellammare_cs2022\735118_Cellammare_cs2022\src\azienda\VenditeNoleggi\Noleggi".
	 * Ovviamente se la cartella esiste, il file viene creato o sovrascritto; se così non fosse, la cartella verra' creata correttamente
	 * Sul file verra' stampato esattamente quello che comparira' sul prompt, in modo tale da rispettare la stampa formattata
	 * Attenzione, non sono state stampate tutte le informazioni dello smartphone puramente per una questione grafica
	 * @throws IOException
	 */
	public static void StampaFile() throws IOException {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");  
		LocalDateTime now = LocalDateTime.now(); 

		if(CatalogoNoleggi.cloneNoleggioCognome().isEmpty()) {
			System.out.println("Stampa su file non completata, nessun noleggio presente nel programma");
		}

		else {


			File currentDir = new File("");

			String path = currentDir.getAbsolutePath()+"\\src\\azienda\\VenditeNoleggi\\Noleggi";

			File directoryNoleggi = new File(path);

			if(directoryNoleggi.mkdir()) {
				System.out.println("\nCartella 'Noleggi' creata correttamente\n");
			}
			else {
				System.out.println("\nCartella 'Noleggi' gia' presente\n");
			}


			PrintStream o = new PrintStream(new File(path)+"\\Noleggi.txt");


			PrintStream console = System.out;


			System.setOut(o);
			SezioneNoleggio.VisualizzaNoleggiGiornoCorrente();

			System.setOut(console);
			System.out.println("L'elenco dei noleggi limitato al giorno corrente, ovvero: " + dtf.format(now) +" e'stato copiato sul file 'Noleggi.txt', \nnella cartella: " + path);

		}
	}


}

