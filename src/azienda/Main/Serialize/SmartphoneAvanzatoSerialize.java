package azienda.Main.Serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import azienda.Smartphone.SmartphoneAvanzato;
/**
 * Classe che si occupera' della serializzazione e deserializzazione del Set formato da oggetti di tipo "SmartphoneAvanzato".
 * @author Gabriel Cellammare
 *
 */
public class SmartphoneAvanzatoSerialize{
	
	/**
	 * Metodo che serializzera' nella cartella "\\src\\azienda\\Smartphone\\DataInputSmartphoneAvanzato" tutti i dati di ogni Smartphone Avanzato, contenuto nel Set formato da oggetti di tipo SmartphoneAvanzato, chiamato smartphoneavanzato.
	 * 
	 * @param smartphoneavanzato [Set che andra' serializzato di oggetti di tipo "SmartphoneAvanzato"]
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void SerializeDataSmartphoneAvanzato (Set<SmartphoneAvanzato> smartphoneavanzato) throws IOException, InterruptedException {
		

		
		File currentDir = new File("");

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\Smartphone\\DataInputSmartphoneAvanzato";
		
		System.out.println("L'esportazione dei dati degli smartphone avanzati e' in corso...");
		System.out.println("La cartella di destinazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
		
		FileOutputStream outFileSet= new FileOutputStream(path+"\\SmartphoneAvanzato.ser");
		
		
		ObjectOutputStream outSet = new ObjectOutputStream(outFileSet);
		outSet.writeObject(smartphoneavanzato);
		
		outSet.close();
		outFileSet.close();
		
	}
	
	
	
	/**
	 * Metodo che deserializzera' nella cartella "\\src\\azienda\\Smartphone\\DataInputSmartphoneAvanzato" tutti i dati di ogni Smartphone Avanzato, ritornando un Set formato da oggetti di tipo SmartphoneAvanzato, chiamato smartphoneavanzato.
	 * Se la cartella di importazione non sara' presente, verra' creata opportunamente
	 * @return smartphoneavanzato [Set di oggetti di tipo "SmartphoneAvanzato"]
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public static Set<SmartphoneAvanzato> DeserializeDataSmartphoneAvanzato() throws IOException, ClassNotFoundException, InterruptedException {

		File currentDir = new File("");
		
		Set<SmartphoneAvanzato> smartphoneavanzato = new LinkedHashSet<SmartphoneAvanzato>();

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\Smartphone\\DataInputSmartphoneAvanzato";
		
		if(checkFile(path)) {
			
		System.out.println("L'importazione dei dati degli smartphone avanzati e' in corso...");
		System.out.println("La cartella d'importazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
			
		FileInputStream fileSet = new FileInputStream(path+"\\SmartphoneAvanzato.ser");
		ObjectInputStream inSet = new ObjectInputStream(fileSet);
				

		smartphoneavanzato = (LinkedHashSet<SmartphoneAvanzato>) inSet.readObject();
		
		inSet.close(); 
		fileSet.close();
		
		return smartphoneavanzato;
		}
		
		else {
			
			System.out.println("L'importazione dei dati degli smartphone avanzati e' in corso...");
			System.out.println("E' stata creata la cartella 'DataInputSmartphoneAvanzato' poiche' non era presente\n");
			System.out.println("Il percorso specificato e' il seguente: " + path);
			Thread.sleep(1000);
			return smartphoneavanzato;
		}

	}
	
	
	private static boolean checkFile(String path) {
		
		File directorySmartphoneAvanzato = new File(path);
		
		boolean file;

		boolean trovato=false;
		
		if(directorySmartphoneAvanzato.mkdir()) {
			trovato = false;
		}
		
		
		else {
			
			file=new File(path+"\\SmartphoneAvanzato.ser").isFile();
			
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
