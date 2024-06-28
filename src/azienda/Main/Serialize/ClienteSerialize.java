package azienda.Main.Serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import azienda.Ruoli.Cliente;

/**
 * Classe che si occupera' della serializzazione e deserializzazione del Set formato da oggetti di tipo "Cliente".
 * @author Gabriel Cellammare
 *
 */
public class ClienteSerialize{
	
	/**
	 * Metodo che serializzera' nella cartella "\\src\\azienda\\Ruoli\\DataInputCliente" tutti i dati di ogni cliente, contenuti nel Set formato da oggetti di tipo Cliente, chiamato clienti.
	 * Sono state serializzate in maniera distinta anche due variabili statiche, poiche' sono essenziali per il funzionamento del Software.
	 * Variabili statiche serializzate della classe Cliente:
	 * ATTENZIONE! Il codice Fiscale e' univoco anche per i venditori, ma il Set contenente tutti i codici fiscali, viene serializzato soltanto una volta, poichè tutte e 2 le classi fanno riferimento ad una Classe Madre "AbstractPersona", quindi la variabile statica e' comune a tutte e due le sottoclassi
	 * Last code: Codice generato automaticamente dal sistema, assegnato in maniera univoca ad ogni cliente
	 * CodiciFiscali: Set di oggetti di tipo String, che conterra' tutti i codici fiscali dei clienti
	 * Queste due variabili sono ottenibili attraverso un metodo Getter statico
	 * 
	 * @param clienti [Set che andra' serializzato di oggetti di tipo "Cliente"]
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void SerializeDataCliente (Set<Cliente> clienti) throws IOException, InterruptedException {
		

		
		File currentDir = new File("");

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\Ruoli\\DataInputCliente";
		
		System.out.println("L'esportazione dei dati dei clienti e' in corso...");
		System.out.println("La cartella di destinazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
		
		FileOutputStream outFileSet= new FileOutputStream(path+"\\Clienti.ser");
		FileOutputStream outFileLastCode= new FileOutputStream(path+"\\LastCode.ser");
		FileOutputStream outFileCodiciFiscali = new FileOutputStream(path+"\\CodiciFiscali.ser");

		ObjectOutputStream outSet = new ObjectOutputStream(outFileSet);
		outSet.writeObject(clienti);
		
		outSet.close();
		outFileSet.close();
		
		ObjectOutputStream outLastCode = new ObjectOutputStream(outFileLastCode);
		outLastCode.writeObject(Cliente.getLast_code());
		
		outLastCode.close();
		outFileLastCode.close();

		ObjectOutputStream outCodiciFiscali = new ObjectOutputStream(outFileCodiciFiscali);
		outCodiciFiscali.writeObject(Cliente.getCodiciFiscali());
		
		outCodiciFiscali.close();
		outFileCodiciFiscali.close();
		
	}
	
	
	
	/**
	 * Funzione che deserializzera' nella cartella "\\src\\azienda\\Ruoli\\DataInputCliente" tutti i dati di ogni cliente, ritornando un Set formato da oggetti di tipo Cliente, chiamato clienti.
	 * Sono state deserializzate in maniera distinta anche due variabili statiche, poiche' sono essenziali per il funzionamento del Software.
	 * Variabili statiche deserializzate della classe Cliente:
	 * Last code: Codice generato automaticamente dal sistema, assegnato in maniera univoca ad ogni cliente
	 * CodiciFiscali: Set di oggetti di tipo String, che conterra' tutti i codici fiscali dei clienti
	 * Queste due variabili sono inizializzabili attraverso un metodo Setter statico
	 * Se la cartella di importazione non sara' presente, verra' creata opportunamente
	 * @return clienti [Set di oggetti di tipo "Cliente"]
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	
	
	@SuppressWarnings("unchecked")
	public static Set<Cliente> DeserializeDataCliente() throws IOException, ClassNotFoundException, InterruptedException {

		File currentDir = new File("");
		
		Set<Cliente> clienti = new LinkedHashSet<Cliente>();

		String path = currentDir.getAbsolutePath()+"\\src\\azienda\\Ruoli\\DataInputCliente";
		
		if(checkFile(path)) {
			
		System.out.println("L'importazione dei dati dei clienti e' in corso...");
		System.out.println("La cartella d'importazione dei file e' la seguente: " + path);
		Thread.sleep(1000);
			
		FileInputStream fileSet = new FileInputStream(path+"\\Clienti.ser");
		ObjectInputStream inSet = new ObjectInputStream(fileSet);

		FileInputStream fileLastCode = new FileInputStream(path+"\\LastCode.ser");
		ObjectInputStream inLastCode = new ObjectInputStream(fileLastCode);

		FileInputStream fileCodiciFiscali = new FileInputStream(path+"\\CodiciFiscali.ser");
		ObjectInputStream inCodiciFiscali = new ObjectInputStream(fileCodiciFiscali);

		
		

		clienti = (LinkedHashSet<Cliente>) inSet.readObject();
		
		inSet.close(); 
		fileSet.close();
		
		
		Cliente.setLast_code((int) inLastCode.readObject());
		inLastCode.close(); 
		fileLastCode.close();
		
		Cliente.setCodiciFiscaliSerialize((Set<String>) inCodiciFiscali.readObject());
		inCodiciFiscali.close();
		fileCodiciFiscali.close();
		
		
		return clienti;
		}
		
		else {
			
			System.out.println("L'importazione dei dati dei clienti e' in corso...");
			System.out.println("E' stata creata la cartella 'DataInputCliente' poiche' non era presente\n");
			System.out.println("Il percorso specificato e' il seguente: " + path);
			Thread.sleep(1000);
			return clienti;
		}

	}
	
	
	private static boolean checkFile(String path) {
		
		File directoryCliente = new File(path);
		
		boolean file;

		boolean trovato=false;
		
		if(directoryCliente.mkdir()) {
			trovato = false;
		}
		
		
		else {
			
			file=new File(path+"\\Clienti.ser").isFile();
			file=new File(path+"\\LastCode.ser").isFile();
			file=new File(path+"\\CodiciFiscali.ser").isFile();
			
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
