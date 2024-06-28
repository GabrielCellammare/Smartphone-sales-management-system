package azienda.Main.Serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import azienda.VenditeNoleggi.Vendita;
/**
 * Classe che si occupera' della serializzazione e deserializzazione del Set formato da oggetti di tipo "Vendita".
 * @author Gabriel Cellammare
 *
 *
 */



public class VenditaSerialize{
	
	/**
	 * Metodo che serializzera' nella cartella "\\src\\azienda\\VenditeNoleggi\\DataVendita" tutti i dati di ogni vendita, contenuto nel Set formato da oggetti di tipo Vendita, chiamato vendite.
	 * Sono state serializzate in maniera distinta anche due variabili statiche, poiche' sono essenziali per il funzionamento del Software.
	 * Variabili statiche serializzate della classe Vendita:
	 * Last code: Codice generato automaticamente dal sistema, assegnato in maniera univoca ad ogni vendita
	 * Incasso: Variabile di tipo double, che conterra' l'incasso aggiornato di tutti le vendite effettuate
	 * Queste due variabili sono ottenibili attraverso un metodo Getter statico
	 * @param vendite [Set che andra' serializzato di oggetti di tipo "Vendita"]
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void SerializeDataVendita (Set<Vendita> vendite) throws IOException, InterruptedException {
		

		
		File currentDir = new File("");

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\VenditeNoleggi\\DataVendita";
		
		System.out.println("L'esportazione dei dati delle vendite e' in corso...");
		System.out.println("La cartella di destinazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
		
		FileOutputStream outFileSet= new FileOutputStream(path+"\\Vendite.ser");
		FileOutputStream outFileIncasso= new FileOutputStream(path+"\\Incasso.ser");
		FileOutputStream outFileLastCode = new FileOutputStream(path+"\\LastCode.ser");

		ObjectOutputStream outSet = new ObjectOutputStream(outFileSet);
		outSet.writeObject(vendite);
		
		outSet.close();
		outFileSet.close();
		
		ObjectOutputStream outIncasso = new ObjectOutputStream(outFileIncasso);
		outIncasso.writeObject(Vendita.getIncasso());
		
		outIncasso.close();
		outFileIncasso.close();

		ObjectOutputStream outLastCode = new ObjectOutputStream(outFileLastCode);
		outLastCode.writeObject(Vendita.getLast_code());
		
		outLastCode.close();
		outFileLastCode.close();
		
	}
	
	
	
	/**
	 * Funzione che deserializzera' nella cartella "\\src\\azienda\\VenditeNoleggi\\DataVendita" tutti i dati di ogni vendita, ritornando un Set formato da oggetti di tipo Vendita, chiamato vendite.
	 * Sono state deserializzate in maniera distinta anche due variabili statiche, poiche' sono essenziali per il funzionamento del Software.
	 * Variabili statiche deserializzate della classe Noleggio:
	 * Last code: Codice generato automaticamente dal sistema, assegnato in maniera univoca ad ogni vendita
	 * Incasso: Variabile di tipo double, che conterra' l'incasso aggiornato di tutti le vendite effettuate
	 * Queste due variabili sono ottenibili attraverso un metodo Getter statico
	 * Se la cartella di importazione non sara' presente, verra' creata opportunamente
	 * @return vendite [Set di oggetti di tipo "Vendita"]
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public static Set<Vendita> DeserializeDataVendita() throws IOException, ClassNotFoundException, InterruptedException {

		File currentDir = new File("");
		
		Set<Vendita> vendite = new LinkedHashSet<Vendita>();

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\VenditeNoleggi\\DataVendita";
		
		if(checkFile(path)) {
			
		System.out.println("L'importazione dei dati delle vendite e' in corso...");
		System.out.println("La cartella d'importazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
			
		FileInputStream fileSet = new FileInputStream(path+"\\Vendite.ser");
		ObjectInputStream inSet = new ObjectInputStream(fileSet);

		FileInputStream fileIncasso = new FileInputStream(path+"\\Incasso.ser");
		ObjectInputStream inIncasso = new ObjectInputStream(fileIncasso);

		FileInputStream fileLastCode = new FileInputStream(path+"\\LastCode.ser");
		ObjectInputStream inLastCode = new ObjectInputStream(fileLastCode);

		
		

		vendite = (LinkedHashSet<Vendita>) inSet.readObject();
		
		inSet.close(); 
		fileSet.close();
		
		
		Vendita.setIncasso((double) inIncasso.readObject());
		fileIncasso.close(); 
		inIncasso.close();
		
		Vendita.setLast_code((int) inLastCode.readObject());
		inLastCode.close();
		fileLastCode.close();
		
		
		return vendite;
		}
		
		else {
			
			System.out.println("L'importazione dei dati delle vendite e' in corso...");
			System.out.println("E' stata creata la cartella 'DataVendita' poiche' non era presente\n");
			System.out.println("Il percorso specificato e' il seguente: " + path);
			Thread.sleep(1000);
			return vendite;
		}

	}
	
	
	private static boolean checkFile(String path) {
		
		File directoryVendita= new File(path);
		
		boolean file;

		boolean trovato=false;
		
		if(directoryVendita.mkdir()) {
			trovato = false;
		}
		
		
		else {
			
			file=new File(path+"\\Vendite.ser").isFile();
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
