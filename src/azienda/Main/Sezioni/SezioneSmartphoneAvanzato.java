package azienda.Main.Sezioni;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import azienda.Main.Eccezione.EccezioneMain;
import azienda.Smartphone.Colore;
import azienda.Smartphone.SistemaOperativo;
import azienda.Smartphone.SmartphoneAvanzato;
import azienda.Smartphone.Eccezioni.EccezioneSmartphone;
/**
 * Sezione del Software che si occupera' della registrazione, cancellazione, visualizzazione di ogni Smartphone Avanzato
 * @author Gabriel Cellammare
 *
 */
public class SezioneSmartphoneAvanzato {

	/**
	 * Menu' utilizzato per scopi grafici; descrive tutte le possibili azioni che possono essere ricondotte alla gestione del singolo Smartphone avanzato
	 */
	public static void MenuSmartphoneAvanzato() {

		System.out.println("\nBenvenuto nel menu' dedicato agli smartphone avanzati!\n");
		System.out.println("1) Aggiungi nuovo smartphone avanzato");
		System.out.println("2) Ricerca smartphone avanzato tramite codice Imei");
		System.out.println("3) Ricerca smartphone avanzato tramite codice identificativo smartphone");
		System.out.println("4) Rimuovi uno smartphone avanzato");
		System.out.println("5) Visualizza tutti gli smartphone avanzati");
		System.out.println("6) Torna al menu' principale");
		System.out.print("\nFai la tua scelta: ");

	}
	/**
	 * Funzione che si occupa della registrazione di uno Smartphone avanzato, controllando opportunamente che tutte le sue caratteristiche rispettino quelle standard
	 * Il codice IMEI inserito deve essere UNIVOCO (15 cifre / 2 cifre), e la sintassi di ogni parametro deve essere corretta, pena il sollevamento di un'eccezione
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @return sm [Oggetto di tipo "SmartphoneAvanzato" che andra' aggiunto al suo set dedicato]
	 * @throws EccezioneSmartphone
	 * @throws EccezioneMain
	 */
	public static SmartphoneAvanzato addSmartphoneAvanzato(Scanner s) throws EccezioneSmartphone, EccezioneMain{

		System.out.println("\n");

		String CodiceImei,modello;
		SistemaOperativo sistema;
		int peso; 
		double pollici;
		int mAh; 
		int megapixelFrontale;
		int megapixelPrincipale;
		int ram; 
		String processore;
		Colore colore;
		double prezzo;

		int temp;

		s.nextLine();

		System.out.print("Inserisci il modello dello smartphone: ");
		modello=s.nextLine();

		System.out.print("Inserisci il codice Imei dello smartphone (15 cifre / 2 cifre): ");
		CodiceImei=s.next();

		System.out.println("\nScegli il sistema operativo dello smartphone: ");
		System.out.println("0) " + SistemaOperativo.Android.toString());
		System.out.println("1) " + SistemaOperativo.iOS.toString());
		System.out.println("2) " + SistemaOperativo.WindowsPhone.toString());
		System.out.print("\n\nScelta (Scegli un numero): ");
		temp=s.nextInt();

		switch(temp) {
		case 0:
			sistema=SistemaOperativo.Android;
			break;
		case 1:
			sistema=SistemaOperativo.iOS;
			break;
		case 2:
			sistema=SistemaOperativo.WindowsPhone;
			break;
		default:
			throw new EccezioneMain("Scelta non valida");
		}


		System.out.print("Inserisci il peso dello smartphone in grammi (MIN 150, MAX 1350): ");
		peso=s.nextInt();

		System.out.print("Inserisci i pollici dello schermo dello smartphone (MIN 3, MAX 11): ");
		pollici=s.nextDouble();

		System.out.print("Inserisci i mAh della batteria dello smartphone (MIN 1500, MAX 6500): ");
		mAh=s.nextInt();

		System.out.print("Inserisci i megaPixel della fotocamera frontale (MIN 2, MAX 60): ");
		megapixelFrontale=s.nextInt();

		System.out.print("Inserisci i megaPixel della fotocamera principale (MIN 10, MAX 100): ");
		megapixelPrincipale=s.nextInt();

		System.out.print("Inserisci la Ram in gigaByte (MIN 2, MAX 16): ");
		ram=s.nextInt();

		s.nextLine();

		System.out.print("Inserisci il processore dello smartphone: ");
		processore=s.nextLine();

		System.out.println("\nScegli il colore dello smartphone: ");
		System.out.println("0) " + Colore.Silver.toString());
		System.out.println("1) " + Colore.Nero.toString());
		System.out.println("2) " + Colore.Bianco.toString());
		System.out.println("3) " + Colore.Dorato.toString());
		System.out.println("4) " + Colore.Blu.toString());
		System.out.print("\n\nScelta (Scegli un numero): ");
		temp=s.nextInt();

		switch(temp) {
		case 0:
			colore = Colore.Silver;
			break;
		case 1:
			colore = Colore.Nero;
			break;
		case 2:
			colore = Colore.Bianco;
			break;
		case 3:
			colore = Colore.Dorato;
			break;
		case 4:
			colore = Colore.Blu;
			break;
		default:
			throw new EccezioneMain("Scelta non valida");
		}

		System.out.print("Inserisci il prezzo dello smartphone: ");
		prezzo=s.nextDouble();


		SmartphoneAvanzato sm = new SmartphoneAvanzato(CodiceImei, sistema, modello, peso, pollici, mAh, megapixelFrontale, megapixelPrincipale, ram, colore, processore, prezzo);

		System.out.println("Smarthpone avanzato registrato correttamente\n");
		System.out.println("Allo smartphone avanzato modello " + sm.getModello() + " e' stato assegnato il codice identificativo: " + sm.getCodice());

		return sm;

	}
	
	/**
	 * Questo metodo permette, attraverso il corretto inserimento di un codice IMEI (rispettando il formato), di ricercare uno smartphone avanzato registrato
	 * Se la ricerca non va a buon fine, lo smartphone avanzato sicuramente non sara' registrato, se invece viene inserito un codice IMEI con un formato nullo, il programma sollevera' un'eccezione
	 * Una volta trovato lo smartphone avanzato in questione, e' possibile modificare il prezzo di acquisto (SE LO SMARTPHONE RISULTA ATTUALMENTE DISPONIBILE), visualizzare tutti le caratteristiche e visualizzare i dettagli riguardo l'acquisto e il noleggio.
	 * Se un cliente o un smartphone venuduto/noleggiato viene eliminato, tutte le informazioni rimarranno comunque consultabili (ATTENZIONE! non sar� possibile gestire direttamente gli oggetti eliminati, ma soltanto visualizzarli)
	 * @param smartphoneavanzato [Oggetto di tipo "SmartphoneAvanzato" che andra' aggiunto al suo set dedicato]
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @throws EccezioneMain
	 * @throws EccezioneSmartphone
	 */
	public static void RicercaSmartphoneAvanzatoImei(Set<SmartphoneAvanzato> smartphoneavanzato, Scanner s) throws EccezioneMain, EccezioneSmartphone{

		String imei=null;
		boolean trovato=false;
		double prezzo=0;
		int scelta;
		SmartphoneAvanzato sa = null;

		final String IMEI = ("[0-9]{15}/[0-9]{2}");

		if(smartphoneavanzato.isEmpty())
			System.out.println("Nessuno smartphone ancora registrato");

		else {


			System.out.print("\nInserisci il codice Imei dello Smartphone da ricercare (15 cifre / 2 cifre): ");
			imei=s.next();

			if(!imei.matches(IMEI)) 
				throw new EccezioneMain("Errore, formato del codice IMEI errato");


			for (SmartphoneAvanzato smartphone : smartphoneavanzato) {
				if(smartphone.getCodiceImei().compareTo(imei)==0) {
					sa=smartphone;
					trovato=true;
				}



			}

			if(!trovato)
				System.out.println("\nNessuno smartphone trovato\n");

			else {
				System.out.println("\nLo smartphone con codice IMEI " + sa.getCodiceImei() + " e' stato trovato correttamente\n");
				System.out.println("1) Modifica prezzo ");	
				System.out.println("2) Visualizza tutti i dettagli dello smartphone ");
				System.out.println("3) Informazioni riguardo l'acquisto e il noleggio ");
				System.out.print("\nFai la tua scelta: ");

				scelta = s.nextInt();

				switch(scelta) {

				case 1: 
					if(sa.isDisponibile()) {
						System.out.print("Inserisci il nuovo prezzo di acquisto: ");
						prezzo = s.nextDouble();
						sa.setPrezzo(prezzo);
						System.out.println("Prezzo di acquisto aggiornato correttamente");
					}

					else {
						System.out.println("\nImpossibile modificare il prezzo d'acquisto, lo smartphone e' stato venduto o e' attualmente noleggiato\n");
					}

					break;

				case 2:
					System.out.println(sa.toString());
					break;
				case 3:
					System.out.println("Questo smartphone e' un modello avanzato.\nAcquistabile = Si \nNoleggiabile = Si \nDisponibile = " + sa.isDisponibile());
					break;
					
				default:
					throw new EccezioneMain("Scelta non consentita");

				}

			}
		}
	}

	/**
	 * Il codice smartphone viene assegnato in maniera univoca ad ogni smartphone
	 * Il riferimento ad ogni smartphone avverra' sempre tramite il Codice IMEI, infatti il codice smartphone e' stato implementato per una questione puramente User-Friendly
	 * Quindi, invece di utilizzare il codice IMEI per ricercare uno smartphone, e' possibile ricercarlo anche attraverso il suo codice, formato soltanto da un numero
	 * Ovviamente, e' possibile effettuare la ricerca in entrambi i modi
	 * Questo metodo permette, attraverso il corretto inserimento di un codice smartphone, di ricercare uno smartphone avanzato registrato
	 * Se la ricerca non va a buon fine, lo smartphone avanzato sicuramente non sara' registrato, se invece viene inserito un codice con un formato nullo, il programma sollevera' un'eccezione
	 * Una volta trovato lo smartphone avanzato in questione, e' possibile modificare il prezzo di acquisto (SE LO SMARTPHONE RISULTA ATTUALMENTE DISPONIBILE), visualizzare tutti le caratteristiche e visualizzare i dettagli riguardo l'acquisto e il noleggio.
	 * Se un cliente o un smartphone venuduto/noleggiato viene eliminato, tutte le informazioni rimarranno comunque consultabili (ATTENZIONE! non sar� possibile gestire direttamente gli oggetti eliminati, ma soltanto visualizzarli)
	 * @param smartphoneavanzato [Oggetto di tipo "SmartphoneAvanzato" che andra' aggiunto al suo set dedicato]
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @throws EccezioneMain
	 * @throws EccezioneSmartphone
	 */
	public static void RicercaSmartphoneAvanzatoCodice(Set<SmartphoneAvanzato> smartphoneavanzato, Scanner s) throws EccezioneMain, EccezioneSmartphone{

		int codice=0;
		boolean trovato=false;
		double prezzo=0;
		int scelta;
		SmartphoneAvanzato sa = null;

		if(smartphoneavanzato.isEmpty())
			System.out.println("Nessuno smartphone ancora registrato");

		else {


			System.out.print("\nInserisci il codice identificativo dello Smartphone da ricercare: ");
			codice=s.nextInt();

			for (SmartphoneAvanzato smartphone : smartphoneavanzato) {
				if(smartphone.getCodice()==codice) {
					sa=smartphone;
					trovato=true;
				}



			}

			if(!trovato)
				System.out.println("\nNessuno smartphone trovato\n");

			else {
				System.out.println("\nLo smartphone con codice identificativo " + sa.getCodice() + " e' stato trovato correttamente\n");
				System.out.println("1) Modifica prezzo ");	
				System.out.println("2) Visualizza tutti i dettagli dello smartphone ");
				System.out.println("3) Informazioni riguardo l'acquisto e il noleggio ");
				System.out.print("\nFai la tua scelta: ");

				scelta = s.nextInt();

				switch(scelta) {
				case 1:

					if(sa.isDisponibile()) {
						System.out.print("Inserisci il nuovo prezzo di acquisto: ");
						prezzo = s.nextDouble();
						sa.setPrezzo(prezzo);
						System.out.println("Prezzo di acquisto aggiornato correttamente");
					}

					else {
						System.out.println("\nImpossibile modificare il prezzo d'acquisto, lo smartphone e' stato venduto o e' attualmente noleggiato\n");
					}

					break;

				case 2:
					System.out.println(sa.toString());
					break;
				case 3:
					System.out.println("Questo smartphone e' un modello avanzato.\nAcquistabile = Si \nNoleggiabile = Si \nDisponibile = " + sa.isDisponibile());
					break;
				default:
					throw new EccezioneMain("Scelta non consentita");

				}

			}
		}
	}

	/**
	 * Metodo che permette, attraveso l'inserimento di un codice smartphone valido, l'eliminazione da quest'ultimo dal Set di smartphone registrati
	 * Attenzione, verra' rimosso anche il suo Codice IMEI all'interno di un Set statico presente nella classe SmartphoneAvanzato (AbstractSmartphone), per prevenire eventuali errori e permettere in un futuro
	 * il reinserimento di quel preciso smartphone 
	 * @param smartphoneavanzato [Set di oggetti di tipo SmartphoneAvanzato che conterra' ogni smartphone registrato]
	 * @param s [Oggetto di tipo Scanner utilizzato per gli inserimenti da tastiera]
	 * @throws EccezioneMain
	 */

	public static void RimuoviSmartphoneAvanzato(Set<SmartphoneAvanzato> smartphoneavanzato, Scanner s) throws EccezioneMain {

		String scelta = null;
		SmartphoneAvanzato sa = null;
		boolean t = false;

		final String IMEI = ("[0-9]{15}/[0-9]{2}");

		if(smartphoneavanzato.isEmpty())
			System.out.println("Nessuno smartphone avanzato registrato");

		else {
			System.out.println("\n");
			System.err.println("ATTENZIONE! L'ELIMINAZIONE SARA' DEFINITIVA");
			System.out.println("\n");
			StampaFormattata.Stampa(smartphoneavanzato);

			System.out.print("\nScegli il codice Imei dello smartphone da eliminare (15 cifre / 2 cifre): ");
			scelta = s.next();

			if(!scelta.matches(IMEI))
				throw new EccezioneMain("Formato IMEI non corretto");


			for (SmartphoneAvanzato smartphoneavanzatoi: smartphoneavanzato) {
				if(smartphoneavanzatoi.getCodiceImei().compareTo(scelta)==0) {
					t=true;
					sa=smartphoneavanzatoi;	
				}

			}

			if(!t)
				System.out.println("Nessuno smartphone eliminato");

			else {
				Iterator<SmartphoneAvanzato> it = smartphoneavanzato.iterator();

				while(it.hasNext()) {
					if(it.next().getCodiceImei().compareTo(scelta)==0) {
						it.remove();	
						System.out.println("\nSmartphone eliminato correttamente");
					}

				}

				SmartphoneAvanzato.setCodiciImei(sa);
			}
		}
	}
	
	/**
	 * Metodo che permette la stampa di tutti gli smartphone avanzati
	 * 
	 * @param smartphoneavanzato [Set di oggetti di tipo SmartphoneAvanzato che conterra' ogni smartphone registrato]
	 */
	public static void VisualizzaSmartphone(Set<SmartphoneAvanzato> smartphoneavanzato) {

		if(smartphoneavanzato.isEmpty())
			System.out.println("\nNessuno smartphone avanzato registrato\n\n");

		else {


			StampaFormattata.Stampa(smartphoneavanzato);

		}

	}
}
