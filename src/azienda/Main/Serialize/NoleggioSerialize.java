package azienda.Main.Serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import azienda.VenditeNoleggi.Noleggio;
/**
 * Classe che si occupera' della serializzazione e deserializzazione del Set formato da oggetti di tipo Noleggio
 * @author Gabriel Cellammare
 *
 */
public class NoleggioSerialize{
	
	/**
	 * Metodo che serializzera' nella cartella "\\src\\azienda\\VenditeNoleggi\\DataNoleggio" tutti i dati di ogni noleggio, contenuti nel Set formato da oggetti di tipo Noleggio, chiamato noleggi.
	 * Sono state serializzate in maniera distinta anche due variabili statiche, poiche' sono essenziali per il funzionamento del Software.
	 * Variabili statiche serializzate della classe Noleggio:
	 * Last code: Codice generato automaticamente dal sistema, assegnato in maniera univoca ad ogni noleggio
	 * Incasso: Variabile di tipo double, che conterra' l'incasso aggiornato di tutti i noleggi effettuati
	 * Queste due variabili sono ottenibili attraverso un metodo Getter statico
	 * 
	 * @param noleggi [Set che andra' serializzato di oggetti di tipo "Noleggi"]
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void SerializeDataNoleggio(Set<Noleggio> noleggi) throws IOException, InterruptedException {
		
		
		File currentDir = new File("");

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\VenditeNoleggi\\DataNoleggio";
		
		System.out.println("L'esportazione dei dati dei noleggi e' in corso...");
		System.out.println("La cartella di destinazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
		
		FileOutputStream outFileSet= new FileOutputStream(path+"\\Noleggi.ser");
		FileOutputStream outFileIncasso= new FileOutputStream(path+"\\Incasso.ser");
		FileOutputStream outFileLastCode = new FileOutputStream(path+"\\LastCode.ser");

		ObjectOutputStream outSet = new ObjectOutputStream(outFileSet);
		outSet.writeObject(noleggi);
		
		outSet.close();
		outFileSet.close();
		
		ObjectOutputStream outIncasso = new ObjectOutputStream(outFileIncasso);
		outIncasso.writeObject(Noleggio.getIncassi());
		
		outIncasso.close();
		outFileIncasso.close();

		ObjectOutputStream outLastCode = new ObjectOutputStream(outFileLastCode);
		outLastCode.writeObject(Noleggio.getLast_code());
		
		outLastCode.close();
		outFileLastCode.close();
		
	}
	
	
	
	/**
	 * Metodo che deserializzera' nella cartella "\\src\\azienda\\VenditeNoleggi\\DataNoleggio" tutti i dati di ogni noleggio, ritornando un Set formato da oggetti di tipo Noleggio, chiamato noleggi.
	 * Sono state deserializzate in maniera distinta anche due variabili statiche, poiche' sono essenziali per il funzionamento del Software.
	 * Variabili statiche deserializzate della classe Noleggio:
	 * Last code: Codice generato automaticamente dal sistema, assegnato in maniera univoca ad ogni noleggio
	 * Incasso: Variabile di tipo double, che conterra' l'incasso aggiornato di tutti i noleggi effettuati
	 * Queste due variabili sono inizalizzabili attraverso un metodo Setter statico
	 * Se la cartella di importazione non sara' presente, verra' creata opportunamente
	 * @return noleggi [Set di oggetti di tipo "Noleggi"]
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public static Set<Noleggio> DeserializeDataNoleggio() throws IOException, ClassNotFoundException, InterruptedException {

		File currentDir = new File("");
		
		Set<Noleggio> noleggi = new LinkedHashSet<Noleggio>();

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\VenditeNoleggi\\DataNoleggio";
		
		if(checkFile(path)) {
			
		System.out.println("L'importazione dei dati dei noleggi e' in corso...");
		System.out.println("La cartella d'importazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
			
		FileInputStream fileSet = new FileInputStream(path+"\\Noleggi.ser");
		ObjectInputStream inSet = new ObjectInputStream(fileSet);

		FileInputStream fileIncasso = new FileInputStream(path+"\\Incasso.ser");
		ObjectInputStream inIncasso = new ObjectInputStream(fileIncasso);

		FileInputStream fileLastCode = new FileInputStream(path+"\\LastCode.ser");
		ObjectInputStream inLastCode = new ObjectInputStream(fileLastCode);

		
		

		noleggi = (LinkedHashSet<Noleggio>) inSet.readObject();
		
		inSet.close(); 
		fileSet.close();
		
		
		Noleggio.setIncassi((double) inIncasso.readObject());
		fileIncasso.close(); 
		inIncasso.close();
		
		Noleggio.setLast_code((int) inLastCode.readObject());
		inLastCode.close();
		fileLastCode.close();
		
		
		return noleggi;
		}
		
		else {
			
			System.out.println("L'importazione dei dati dei noleggi e' in corso...");
			System.out.println("E' stata creata la cartella 'DataNoleggio' poiche' non era presente\n");
			System.out.println("Il percorso specificato e' il seguente: " + path);
			Thread.sleep(1000);
			return noleggi;
		}

	}
	
	
	private static boolean checkFile(String path) {
		
		File directoryNoleggio= new File(path);
		
		boolean file;

		boolean trovato=false;
		
		if(directoryNoleggio.mkdir()) {
			trovato = false;
		}
		
		
		else {
			
			file=new File(path+"\\Noleggi.ser").isFile();
			file=new File(path+"\\Incasso.ser").isFile();
			file=new File(path+"\\LastCode.ser").isFile();
			
			if(!file) {
				trovato = false;
			}
			
			else {
				trovato = true;
			}
				
			
			
	
		}
		
		
		if(!trovato) {
			return false;
		}
		
		else {
			return true;
		}


		
		
	}


}
