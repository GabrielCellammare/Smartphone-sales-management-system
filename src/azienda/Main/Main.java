package azienda.Main;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.Set;

import azienda.Main.Eccezione.EccezioneMain;
import azienda.Main.Serialize.ClienteSerialize;
import azienda.Main.Serialize.SmartphoneAvanzatoSerialize;
import azienda.Main.Serialize.SmartphoneBaseSerialize;
import azienda.Main.Serialize.SmartphoneMedioSerialize;
import azienda.Main.Serialize.VenditoreSerialize;
import azienda.Main.Sezioni.*;
import azienda.Ruoli.Cliente;
import azienda.Ruoli.Venditore;
import azienda.Ruoli.Eccezioni.EccezioneRuoli;
import azienda.Smartphone.SmartphoneAvanzato;
import azienda.Smartphone.SmartphoneBase;
import azienda.Smartphone.SmartphoneMedio;
import azienda.Smartphone.Eccezioni.EccezioneSmartphone;
import azienda.VenditeNoleggi.CatalogoNoleggi;
import azienda.VenditeNoleggi.CatalogoVendite;
import azienda.VenditeNoleggi.Eccezioni.VenditeNoleggiEccezione;

/**
 * Classe Main principale
 * @author Gabriel Cellammare
 *
 */
public final class Main implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -720559467921224733L;


	/**
	 * Metodo main principale, si occupa di deserializzare tutti i set salvati nelle opportune cartelle,
	 * e di permettere all'utente di scegliere, attraverso uno switch, quale sezione del Software richiamare.
	 * 
	 * @param args
	 * @throws EccezioneRuoli
	 * @throws EccezioneMain
	 * @throws EccezioneSmartphone
	 * @throws VenditeNoleggiEccezione
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * 
	 * @author Gabriel Cellammare
	 */
	
	public static void main(String[] args) throws EccezioneRuoli, EccezioneMain, EccezioneSmartphone, VenditeNoleggiEccezione, IOException, ClassNotFoundException, InterruptedException{

		int scelta;

		boolean temp=false;

		Set<Cliente> clienti = ClienteSerialize.DeserializeDataCliente();
		
		System.out.println("\n");

		Set<Venditore> venditori = VenditoreSerialize.DeserializeDataVenditore();
		
		System.out.println("\n");

		Set<SmartphoneBase> smartphonebase = SmartphoneBaseSerialize.DeserializeDataSmartphoneBase();
		
		System.out.println("\n");

		Set<SmartphoneMedio> smartphonemedio = SmartphoneMedioSerialize.DeserializeDataSmartphoneMedio();
		
		System.out.println("\n");

		Set<SmartphoneAvanzato> smartphoneavanzato = SmartphoneAvanzatoSerialize.DeserializeDataSmartphoneAvanzato();
		
		System.out.println("\n");
		
		CatalogoVendite.VenditaDeserialize();
		
		System.out.println("\n");
		
		CatalogoNoleggi.NoleggioDeserialize();
		
		System.out.println("\n\n\n");
		
		Scanner s = new Scanner(System.in);

		do {
			menuIniziale();

			scelta = s.nextInt();

			switch(scelta) {
			case 1:
				GestioneClienti(s,clienti);
				break;
			case 2:
				GestioneVenditori(s,venditori);
				break;
			case 3: 
				GestioneSmartphoneBase(s,smartphonebase);
				break;
			case 4:
				GestioneSmartphoneMedio(s,smartphonemedio);
				break;
			case 5:
				GestioneSmartphoneAvanzato(s,smartphoneavanzato);
				break;
			case 6:
				GestioneVendita(s,smartphoneavanzato,smartphonebase, clienti, venditori);
				break;
			case 7:
				GestioneNoleggio(s,smartphoneavanzato,clienti,venditori);
				break;
			case 8:
				temp=true;
				break;

			default:
				s.close();
				throw new EccezioneMain("Scelta errata");
			}

			if(!temp) {
				System.out.println("\nVuoi uscire dal programma?");
				System.out.println("1) Torna al menu' principale");
				System.out.println("2) Termina il programma");
				System.out.print("Scegli un numero: ");
				scelta=s.nextInt();

				if(scelta<1 || scelta>2)
					throw new EccezioneMain("Errore, scelta non consentita");
			}
			else
				scelta=0;

			
			System.out.println("\n\n");
		}while(scelta==1 && temp==false);


		s.close();

		System.out.println("Salvataggio dei dati in corso...");
		System.out.println("\n\n");
		
		ClienteSerialize.SerializeDataCliente(clienti);
		System.out.println("\n");
		
		VenditoreSerialize.SerializeDataVenditore(venditori);
		System.out.println("\n");
		
		SmartphoneBaseSerialize.SerializeDataSmartphoneBase(smartphonebase);
		System.out.println("\n");
		
		SmartphoneMedioSerialize.SerializeDataSmartphoneMedio(smartphonemedio);
		System.out.println("\n");
		
		SmartphoneAvanzatoSerialize.SerializeDataSmartphoneAvanzato(smartphoneavanzato);
		System.out.println("\n");
		
		CatalogoVendite.VenditaSerialize();
		
		CatalogoNoleggi.NoleggioSerialize();
		System.out.println("\n");
		
		
		System.out.println("\n\nGrazie per aver utilizzato il software!");
		System.out.println("Autore: Gabriel Cellammare");
		System.out.println("Matricola: 735118");
		System.out.println("Caso di studio 2021/2022 [Programmazione II]");
		System.out.println("Bari, Universita' degli studi 'Aldo Moro'");
		 
	}
	
	/**
	 * Metodo che genera il menu' iniziale del software
	 */
	private static void menuIniziale() {

		System.out.println("Benvenuto nel menu' aziendale!");
		System.out.println("1) Gestione clienti");
		System.out.println("2) Gestione venditori");
		System.out.println("3) Gestione smartphone base");
		System.out.println("4) Gestione smartphone medio");
		System.out.println("5) Gestione smartphone avanzato");
		System.out.println("6) Gestione vendite");
		System.out.println("7) Gestione noleggi");
		System.out.println("8) Esci e salva tutti i dati");
		System.out.print("\nFai la tua scelta: ");

	}


	private static void GestioneClienti(Scanner s, Set<Cliente> clienti) throws EccezioneRuoli, EccezioneMain {

		int scelta;
		boolean temp=false;

		do{

			SezioneCliente.MenuCliente();
			scelta = s.nextInt();

			switch(scelta) {

			case 1:
				clienti.add(SezioneCliente.addCliente(s));
				break;
			case 2:
				SezioneCliente.RicercaClienteCodiceFiscale(clienti,s);
				break;
			case 3:
				SezioneCliente.RicercaCodiceCliente(clienti,s);
				break;
			case 4:
				SezioneCliente.RimuoviCliente(clienti,s);
				break;
			case 5:
				SezioneCliente.VisualizzaClienti(clienti);
				break;
			case 6:
				temp=true;
				break;

			default:
				throw new EccezioneMain("Scelta non consentita");
			}

			if(!temp) {
				System.out.println("\nRitornare al menu principale o al menu clienti?");
				System.out.println("1) Menu' principale");
				System.out.println("2) Menu' clienti");
				System.out.print("Scegli un numero: ");
				scelta=s.nextInt();

				if(scelta<1 || scelta>2)
					throw new EccezioneMain("Errore, scelta non consentita");
			}
			else
				scelta=0;

		}while(scelta==2 && temp==false);

	}


	private static void GestioneVenditori(Scanner s, Set<Venditore> venditori) throws EccezioneRuoli, EccezioneMain {
		int scelta;
		boolean temp=false;

		do{

			SezioneVenditore.MenuVenditore();
			scelta = s.nextInt();

			switch(scelta) {

			case 1:
				venditori.add(SezioneVenditore.addVenditore(s));
				break;
			case 2:
				SezioneVenditore.RicercaVenditoreCodiceFiscale(venditori,s);
				break;
			case 3:
				SezioneVenditore.RicercaCodiceVenditore(venditori,s);
				break;
			case 4:
				SezioneVenditore.RimuoviVenditore(venditori,s);
				break;
			case 5:
				SezioneVenditore.VisualizzaVenditori(venditori);
				break;
			case 6:
				temp=true;
				break;

			default:
				throw new EccezioneMain("Scelta non consentita");
			}

			if(!temp) {
				System.out.println("\nRitornare al menu principale o al menu venditori? ");
				System.out.println("1) Menu' principale");
				System.out.println("2) Menu' venditori");
				System.out.print("Scegli un numero: ");

				scelta=s.nextInt();

				if(scelta<1 || scelta>2)
					throw new EccezioneMain("Errore, scelta non consentita");
			}

			else
				scelta=0;

		}while(scelta==2 && temp==false);


	}


	private static void GestioneSmartphoneBase(Scanner s, Set<SmartphoneBase> smartphonebase) throws EccezioneSmartphone, EccezioneMain {

		int scelta;
		boolean temp=false;

		do{

			SezioneSmartphoneBase.MenuSmartphoneBase();
			scelta = s.nextInt();

			switch(scelta) {

			case 1:
				smartphonebase.add(SezioneSmartphoneBase.addSmartphoneBase(s));
				break;
			case 2:
				SezioneSmartphoneBase.RicercaSmartphoneBaseImei(smartphonebase, s);
				break;
			case 3:
				SezioneSmartphoneBase.RicercaSmartphoneBaseCodice(smartphonebase, s);
				break;
			case 4:
				SezioneSmartphoneBase.RimuoviSmartphoneBase(smartphonebase, s);
				break;
			case 5:
				SezioneSmartphoneBase.VisualizzaSmartphone(smartphonebase);
				break;
			case 6:
				temp=true;
				break;

			default:
				throw new EccezioneMain("Scelta non consentita");
			}

			if(!temp) {
				System.out.println("\nRitornare al menu principale o al menu smartphone base?");
				System.out.println("1) Menu' principale");
				System.out.println("2) Menu' smartphone base");
				System.out.print("Scegli un numero: ");
				scelta=s.nextInt();

				if(scelta<1 || scelta>2)
					throw new EccezioneMain("Errore, scelta non consentita");
			}
			else
				scelta=0;

		}while(scelta==2 && temp==false);


	}


	private static void GestioneSmartphoneMedio(Scanner s, Set<SmartphoneMedio> smartphonemedio) throws EccezioneSmartphone, EccezioneMain {

		int scelta;
		boolean temp=false;

		System.out.println("\n\nAttenzione, si ricorda che gli smartphone medii possiedono una doppia fotocamera principale\n\n");

		do{

			SezioneSmartphoneMedio.MenuSmartphoneMedio();
			scelta = s.nextInt();

			switch(scelta) {

			case 1:
				smartphonemedio.add(SezioneSmartphoneMedio.addSmartphoneMedio(s));
				break;
			case 2:
				SezioneSmartphoneMedio.RicercaSmartphoneMedioImei(smartphonemedio, s);
				break;
			case 3:
				SezioneSmartphoneMedio.RicercaSmartphoneMedioCodice(smartphonemedio, s);
				break;
			case 4:
				SezioneSmartphoneMedio.RimuoviSmartphoneMedio(smartphonemedio, s);
				break;
			case 5:
				SezioneSmartphoneMedio.VisualizzaSmartphone(smartphonemedio);
				break;
			case 6:
				temp=true;
				break;

			default:
				throw new EccezioneMain("Scelta non consentita");
			}

			if(!temp) {
				System.out.println("\nRitornare al menu principale o al menu smartphone medio?");
				System.out.println("1) Menu' principale");
				System.out.println("2) Menu' smartphone medio");
				System.out.print("Scegli un numero: ");
				scelta=s.nextInt();

				if(scelta<1 || scelta>2)
					throw new EccezioneMain("Errore, scelta non consentita");
			}
			else
				scelta=0;

		}while(scelta==2 && temp==false);


	}

	private static void GestioneSmartphoneAvanzato(Scanner s, Set<SmartphoneAvanzato> smartphoneavanzato) throws EccezioneSmartphone, EccezioneMain {

		int scelta;
		boolean temp=false;

		System.out.println("\n\nAttenzione, si ricorda che gli smartphone avanzati possiedono un riconoscimento mediante impronta e riconoscimento facciale\n\n");

		do{

			SezioneSmartphoneAvanzato.MenuSmartphoneAvanzato();
			scelta = s.nextInt();

			switch(scelta) {

			case 1:
				smartphoneavanzato.add(SezioneSmartphoneAvanzato.addSmartphoneAvanzato(s));
				break;
			case 2:
				SezioneSmartphoneAvanzato.RicercaSmartphoneAvanzatoImei(smartphoneavanzato, s);
				break;
			case 3:
				SezioneSmartphoneAvanzato.RicercaSmartphoneAvanzatoCodice(smartphoneavanzato, s);
				break;
			case 4:
				SezioneSmartphoneAvanzato.RimuoviSmartphoneAvanzato(smartphoneavanzato, s);
				break;
			case 5:
				SezioneSmartphoneAvanzato.VisualizzaSmartphone(smartphoneavanzato);
				break;
			case 6:
				temp=true;
				break;

			default:
				throw new EccezioneMain("Scelta non consentita");
			}

			if(!temp) {
				System.out.println("\nRitornare al menu principale o al menu smartphone avanzato?");
				System.out.println("1) Menu' principale");
				System.out.println("2) Menu' smartphone avanzato");
				System.out.print("Scegli un numero: ");
				scelta=s.nextInt();

				if(scelta<1 || scelta>2)
					throw new EccezioneMain("Errore, scelta non consentita");
			}
			else
				scelta=0;

		}while(scelta==2 && temp==false);


	}

	private static void GestioneVendita(Scanner s, Set<SmartphoneAvanzato> smartphoneavanzato, Set<SmartphoneBase> smartphonebase,Set<Cliente> clienti, Set<Venditore> venditori) throws EccezioneSmartphone, EccezioneMain, VenditeNoleggiEccezione, EccezioneRuoli, InterruptedException {

		int scelta;
		boolean temp=false;


		do{

			SezioneVendita.MenuVendita();
			scelta = s.nextInt();

			switch(scelta) {

			case 1:
				SezioneVendita.addVendita(s, smartphoneavanzato, smartphonebase, clienti, venditori);
				break;
			case 2:
				SezioneVendita.RicercaCodiceVendita(s);
				break;
			case 3:
				SezioneVendita.VisualizzaVendite();
				break;
			case 4:
				SezioneVendita.VisualizzaIncassoTotale();
				break;
			case 5:
				temp=true;
				break;

			default:
				throw new EccezioneMain("Scelta non consentita");
			}

			if(!temp) {
				System.out.println("\nRitornare al menu principale o al menu vendita?");
				System.out.println("1) Menu' principale");
				System.out.println("2) Menu' vendita");
				System.out.print("Scegli un numero: ");
				scelta=s.nextInt();

				if(scelta<1 || scelta>2)
					throw new EccezioneMain("Errore, scelta non consentita");
			}
			else
				scelta=0;

		}while(scelta==2 && temp==false);


	}


	private static void GestioneNoleggio(Scanner s, Set<SmartphoneAvanzato> smartphoneavanzato, Set<Cliente> clienti,
			Set<Venditore> venditori) throws EccezioneSmartphone, EccezioneMain, VenditeNoleggiEccezione, EccezioneRuoli, IOException, InterruptedException {

		int scelta;
		boolean temp=false;

		System.out.println("\n\n\n\n\nControllo dei noleggi in scadenza...");
		Thread.sleep(1000);
		SezioneNoleggio.ControllaDisponibilita(smartphoneavanzato);
		System.out.println("\n\n\n");
		do{

			SezioneNoleggio.MenuNoleggio();
			scelta = s.nextInt();

			switch(scelta) {

			case 1:
				SezioneNoleggio.addNoleggio(s, smartphoneavanzato, clienti, venditori);
				break;
			case 2:
				SezioneNoleggio.RicercaCodiceNoleggio(s);
				break;
			case 3:
				SezioneNoleggio.VisualizzaNoleggi();
				break;
			case 4:
				SezioneNoleggio.VisualizzaIncassoTotale();
				break;
			case 5:
				SezioneNoleggio.InfoNoleggio(s);
				break;
			case 6:
				SezioneNoleggio.StampaFile();
				break;
			case 7:
				temp=true;
				break;

			default:
				throw new EccezioneMain("Scelta non consentita");
			}

			System.out.println("\n\n\n\n\nControllo dei noleggi in scadenza...");
			Thread.sleep(2000);
			SezioneNoleggio.ControllaDisponibilita(smartphoneavanzato);

			if(!temp) {
				System.out.println("\nRitornare al menu principale o al menu noleggio?");
				System.out.println("1) Menu' principale");
				System.out.println("2) Menu' noleggi");
				System.out.print("Scegli un numero: ");
				scelta=s.nextInt();

				if(scelta<1 || scelta>2)
					throw new EccezioneMain("Errore, scelta non consentita");
			}
			else
				scelta=0;

		}while(scelta==2 && temp==false);

	}






}
