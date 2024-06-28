package azienda.Main.Serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import azienda.Smartphone.SmartphoneMedio;
/**
 * Classe che si occupera' della serializzazione e deserializzazione del Set formato da oggetti di tipo "SmartphoneMedio".
 * @author Gabriel Cellammare
 *
 */
public class SmartphoneMedioSerialize{
	
	/**
	 * Metodo che serializzera' nella cartella "\\src\\azienda\\Smartphone\\DataInputSmartphoneMedio" tutti i dati di ogni Smartphone Medio, contenuto nel Set formato da oggetti di tipo SmartphoneMedio, chiamato smartphonemedio.
	 * @param smartphonemedio [Set che andra' serializzato di oggetti di tipo "SmartphoneMedio"]
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void SerializeDataSmartphoneMedio (Set<SmartphoneMedio> smartphonemedio) throws IOException, InterruptedException {
		

		
		File currentDir = new File("");

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\Smartphone\\DataInputSmartphoneMedio";
		
		System.out.println("L'esportazione dei dati degli smartphone medii e' in corso...");
		System.out.println("La cartella di destinazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
		
		FileOutputStream outFileSet= new FileOutputStream(path+"\\SmartphoneMedio.ser");
		
		
		ObjectOutputStream outSet = new ObjectOutputStream(outFileSet);
		outSet.writeObject(smartphonemedio);
		
		outSet.close();
		outFileSet.close();
		
	}
	
	
	
	/**
	 * Funzione che deserializzera' nella cartella "\\src\\azienda\\Smartphone\\DataInputSmartphoneMedio" tutti i dati di ogni Smartphone Medio, ritornando un Set formato da oggetti di tipo SmartphoneMedio, chiamato smartphonemedio.
	 * Se la cartella di importazione non sara' presente, verra' creata opportunamente
	 * @return smartphonemedio [Set di oggetti di tipo "SmartphoneMedio"]
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public static Set<SmartphoneMedio> DeserializeDataSmartphoneMedio() throws IOException, ClassNotFoundException, InterruptedException {

		File currentDir = new File("");
		
		Set<SmartphoneMedio> smartphonemedio = new LinkedHashSet<SmartphoneMedio>();

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\Smartphone\\DataInputSmartphoneMedio";
		
		if(checkFile(path)) {
			
		System.out.println("L'importazione dei dati degli smartphone medii e' in corso...");
		System.out.println("La cartella d'importazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
			
		FileInputStream fileSet = new FileInputStream(path+"\\SmartphoneMedio.ser");
		ObjectInputStream inSet = new ObjectInputStream(fileSet);
				

		smartphonemedio = (LinkedHashSet<SmartphoneMedio>) inSet.readObject();
		
		inSet.close(); 
		fileSet.close();
		
		return smartphonemedio;
		}
		
		else {
			
			System.out.println("L'importazione dei dati degli smartphone medii e' in corso...");
			System.out.println("E' stata creata la cartella 'DataInputSmartphoneMedio' poiche' non era presente\n");
			System.out.println("Il percorso specificato e' il seguente: " + path);
			Thread.sleep(1000);
			return smartphonemedio;
		}

	}
	
	
	private static boolean checkFile(String path) {
		
		File directorySmartphoneMedio = new File(path);
		
		boolean file;

		boolean trovato=false;
		
		if(directorySmartphoneMedio.mkdir()) {
			trovato = false;
		}
		
		
		else {
			
			file=new File(path+"\\SmartphoneMedio.ser").isFile();
			
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
