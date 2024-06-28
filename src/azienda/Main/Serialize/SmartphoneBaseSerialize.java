package azienda.Main.Serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import azienda.Smartphone.SmartphoneBase;
/**
 * Classe che si occupera' della serializzazione e deserializzazione del Set formato da oggetti di tipo "SmartphoneBase".
 * @author Gabriel Cellammare
 *
 */
public class SmartphoneBaseSerialize{
	
	/**
	 * Metodo che serializzera' nella cartella "\\src\\azienda\\Smartphone\\DataInputSmartphoneBase" tutti i dati di ogni SmartphoneBase, contenuto nel Set formato da oggetti di tipo SmartphoneBase, chiamato smartphonebase.
	 * Sono state serializzate in maniera distinta anche due variabili statiche, poiche' sono essenziali per il funzionamento del Software.
	 * Variabili statiche serializzate della classe SmartphoneBase:
	 * ATTENZIONE! Il CodiceImei essendo univoco anche per SmartphoneMedi e SmartphoneAvanzati, viene serializzato soltanto una volta, poichè tutte e 3 le classi fanno riferimento ad una Classe Madre "AbstractSmartphone", quindi la variabile statica e' comune a tutte e tre le sottoclassi
	 * ATTENZIONE! Last code viene assegnato in maniera univoca anche a SmartphoneMedi e SmartphoneAvanzati, ma viene serializzato soltanto una volta, poichè tutte e 3 le classi fanno riferimento ad una Classe Madre "AbstractSmartphone", quindi la variabile statica e' comune a tutte e tre le sottoclassi
	 * Last code: Codice generato automaticamente dal sistema, assegnato in maniera univoca ad ogni smartphone
	 * CodiciImei: Set di oggetti di tipo String, che conterra' tutti i codici IMEI di ogni Smartphone
	 * Queste due variabili sono ottenibili attraverso un metodo Getter statico
	 * 
	 * @param smartphonebase [Set che andra' serializzato di oggetti di tipo "SmartphoneBase"]
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void SerializeDataSmartphoneBase (Set<SmartphoneBase> smartphonebase) throws IOException, InterruptedException {
		

		
		File currentDir = new File("");

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\Smartphone\\DataInputSmartphoneBase";
		
		System.out.println("L'esportazione dei dati degli smartphone base e' in corso...");
		System.out.println("La cartella di destinazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
		
		FileOutputStream outFileSet= new FileOutputStream(path+"\\SmartphoneBase.ser");
		FileOutputStream outFileCodiciImei= new FileOutputStream(path+"\\CodiciImei.ser");
		FileOutputStream outFileLastCode= new FileOutputStream(path+"\\LastCode.ser");
		
		
		ObjectOutputStream outSet = new ObjectOutputStream(outFileSet);
		outSet.writeObject(smartphonebase);
		
		outSet.close();
		outFileSet.close();
		
		ObjectOutputStream outCodiciImei = new ObjectOutputStream(outFileCodiciImei);
		outCodiciImei.writeObject(SmartphoneBase.getCodiciImei());
		
		outCodiciImei.close();
		outFileCodiciImei.close();

		ObjectOutputStream outLastCode = new ObjectOutputStream(outFileLastCode);
		outLastCode.writeObject(SmartphoneBase.getLast_code());
		
		outLastCode.close();
		outFileLastCode.close();
		
	}
	
	
	
	/**
	 * Funzione che deserializzera' nella cartella "\\src\\azienda\\Smartphone\\DataInputSmartphoneBase" tutti i dati di ogni SmartphoneBase, ritornando un Set formato da oggetti di tipo SmartphoneBase, chiamato smartphonebase.
	 * Sono state deserializzate in maniera distinta anche due variabili statiche, poiche' sono essenziali per il funzionamento del Software.
	 * Variabili statiche deserializzate della classe SmartphoneBase:
	 * ATTENZIONE! Last code viene assegnato in maniera univoca anche a SmartphoneMedi e SmartphoneAvanzati, ma viene serializzato soltanto una volta, poichè tutte e 3 le classi fanno riferimento ad una Classe Madre "AbstractSmartphone", quindi la variabile statica e' comune a tutte e tre le sottoclassi
	 * ATTENZIONE! Il CodiceImei essendo univoco anche per SmartphoneMedi e SmartphoneAvanzati, viene serializzato soltanto una volta, poichè tutte e 3 le classi fanno riferimento ad una Classe Madre "AbstractSmartphone", quindi la variabile statica e' comune a tutte e tre le sottoclassi
	 * Last code: Codice generato automaticamente dal sistema, assegnato in maniera univoca ad ogni smartphone
	 * CodiciImei: Set di oggetti di tipo String, che conterra' tutti i codici IMEI di ogni Smartphone
	 * Queste due variabili sono inizializzabili attraverso un metodo Setter statico
	 * Se la cartella di importazione non sara' presente, verra' creata opportunamente
	 * @return smartphonebase [Set di oggetti di tipo "SmartphoneBase"]
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public static Set<SmartphoneBase> DeserializeDataSmartphoneBase() throws IOException, ClassNotFoundException, InterruptedException {

		File currentDir = new File("");
		
		Set<SmartphoneBase> smartphonebase = new LinkedHashSet<SmartphoneBase>();

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\Smartphone\\DataInputSmartphoneBase";
		
		if(checkFile(path)) {
			
		System.out.println("L'importazione dei dati degli smartphone base e' in corso...");
		System.out.println("La cartella d'importazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
			
		FileInputStream fileSet = new FileInputStream(path+"\\SmartphoneBase.ser");
		ObjectInputStream inSet = new ObjectInputStream(fileSet);

		FileInputStream fileCodiciImei = new FileInputStream(path+"\\CodiciImei.ser");
		ObjectInputStream inCodiciImei = new ObjectInputStream(fileCodiciImei);
		
		FileInputStream fileLastCode = new FileInputStream(path+"\\LastCode.ser");
		ObjectInputStream inLastCode = new ObjectInputStream(fileLastCode);

		
		

		smartphonebase = (LinkedHashSet<SmartphoneBase>) inSet.readObject();
		
		inSet.close(); 
		fileSet.close();
		
		
		SmartphoneBase.setCodiciImeiSerialize((Set<String>) inCodiciImei.readObject());
		inCodiciImei.close(); 
		fileCodiciImei.close();
		

		SmartphoneBase.setLast_code((int) inLastCode.readObject());
		inLastCode.close();
		fileLastCode.close();
		
		return smartphonebase;
		}
		
		else {
			
			System.out.println("L'importazione dei dati degli smartphone base e' in corso...");
			System.out.println("E' stata creata la cartella 'DataInputSmartphoneBase' poiche' non era presente\n");
			System.out.println("Il percorso specificato e' il seguente: " + path);
			Thread.sleep(1000);
			return smartphonebase;
		}

	}
	
	
	private static boolean checkFile(String path) {
		
		File directorySmartphoneBase = new File(path);
		
		boolean file;

		boolean trovato=false;
		
		if(directorySmartphoneBase.mkdir()) {
			trovato = false;
		}
		
		
		else {
			
			file=new File(path+"\\SmartphoneBase.ser").isFile();
			file=new File(path+"\\CodiciImei.ser").isFile();
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
