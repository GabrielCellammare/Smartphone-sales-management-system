package azienda.Main.Serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import azienda.Ruoli.Venditore;
/**
 * Classe che si occupera' della serializzazione e deserializzazione del Set formato da oggetti di tipo "Venditore".
 * @author Gabriel Cellammare
 *
 */
public class VenditoreSerialize{
	
	/**
	 * Metodo che serializzera' nella cartella "\\src\\azienda\\Ruoli\\DataInputVenditore" tutti i dati di ogni venditore, contenuto nel Set formato da oggetti di tipo Venditore, chiamato venditori.
	 * E' stata serializzata in maniera distinta anche una variabilee statica, poiche' essenziale per il funzionamento del Software.
	 * CodiciVenditori: Set di oggetti di tipo String, che conterra' tutti i codici venditori dei venditori
	 * Queste due variabili sono ottenibili attraverso un metodo Getter statico
	 * 
	 * @param venditori [Set che andra' serializzato di oggetti di tipo "Venditore"]
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void SerializeDataVenditore (Set<Venditore> venditori) throws IOException, InterruptedException {
		

		
		File currentDir = new File("");

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\Ruoli\\DataInputVenditore";
		
		System.out.println("L'esportazione dei dati dei venditori e' in corso...");
		System.out.println("La cartella di destinazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
		
		FileOutputStream outFileSet= new FileOutputStream(path+"\\Venditori.ser");
		FileOutputStream outFileCodiciVenditori= new FileOutputStream(path+"\\CodiciVenditori.ser");
		
		ObjectOutputStream outSet = new ObjectOutputStream(outFileSet);
		outSet.writeObject(venditori);
		outSet.close();
		outFileSet.close();
		
		ObjectOutputStream outCodiciVenditori = new ObjectOutputStream(outFileCodiciVenditori);
		
		outCodiciVenditori.writeObject(Venditore.getCodiciVenditori());
		outCodiciVenditori.close();
		outFileCodiciVenditori.close();

		
	}
	
	
	
	/**
	 * Metodo che deeserializzera' nella cartella "\\src\\azienda\\Ruoli\\DataInputVenditore" tutti i dati di ogni venditore, che ritornera' un Set formato da oggetti di tipo Venditore, chiamato venditori.
	 * E' stata deserializzata in maniera distinta anche una variabilee statica, poiche' essenziale per il funzionamento del Software.
	 * CodiciVenditori: Set di oggetti di tipo String, che conterra' tutti i codici venditori dei venditori
	 * Queste due variabili sono ottenibili attraverso un metodo Setter statico
	 * Se la cartella di importazione non sara' presente, verra' creata opportunamente
	 * @return venditori [Set di oggetti di tipo "Venditore"]
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	
	@SuppressWarnings("unchecked")
	public static Set<Venditore> DeserializeDataVenditore() throws IOException, ClassNotFoundException, InterruptedException {

		File currentDir = new File("");
		
		Set<Venditore> venditori = new LinkedHashSet<Venditore>();

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\Ruoli\\DataInputVenditore";
		
		if(checkFile(path)) {
			
		System.out.println("L'importazione dei dati dei venditori e' in corso...");
		System.out.println("La cartella d'importazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
			
		FileInputStream fileSet = new FileInputStream(path+"\\Venditori.ser");
		ObjectInputStream inSet = new ObjectInputStream(fileSet);

		FileInputStream fileCodiciVenditori = new FileInputStream(path+"\\CodiciVenditori.ser");
		ObjectInputStream inCodiciVenditori = new ObjectInputStream(fileCodiciVenditori);

		
		

		venditori = (LinkedHashSet<Venditore>) inSet.readObject();
		
		inSet.close(); 
		fileSet.close();
		
		
		Venditore.setCodiciVenditoriSerialize((Set<String>) inCodiciVenditori.readObject());
		inCodiciVenditori.close(); 
		fileCodiciVenditori.close();
		

		
		return venditori;
		}
		
		else {
			
			System.out.println("L'importazione dei dati dei venditori e' in corso...");
			System.out.println("E' stata creata la cartella 'DataInputVenditore' poiche' non era presente\n");
			System.out.println("Il percorso specificato e' il seguente: " + path);
			Thread.sleep(1000);
			return venditori;
		}

	}
	
	
	private static boolean checkFile(String path) {
		
		File directoryVenditore = new File(path);
		
		boolean file;

		boolean trovato=false;
		
		if(directoryVenditore.mkdir()) {
			trovato = false;
		}
		
		
		else {
			
			file=new File(path+"\\Venditori.ser").isFile();
			file=new File(path+"\\CodiciVenditori.ser").isFile();
			
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
