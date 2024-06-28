package azienda.Ruoli;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import azienda.Ruoli.Eccezioni.EccezioneRuoli;

/**
 * La classe AbstractPersona raggruppa le caratteristiche comuni (Persona) di ogni cliente e venditore
 * @author Gabriel Cellammare
 *
 */
public abstract class AbstractPersona implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6088469612847190006L;

	protected String nome,cognome,DataDiNascita, CodiceFiscale, LuogoDiNascita, LuogoDiResidenza;
	protected Genere sesso;
	protected int eta,codice_cliente;
	protected String codice_venditore;

	/**
	 * HashSet che conterra' tutti i codici fiscali, sia di clienti che di venditori
	 */
	protected static Set<String> CodiciFiscali = new HashSet<String>();

	/**
	 * 	HashSet che conterra' tutti i codici venditore
	 */
	protected static Set<String> CodiciVenditori = new HashSet<String>();
	private static int last_code=0;


	/**
	 * Regular expression che controlla che il codice fiscale rispetti il formato classico [MAIUSCOLO]
	 */
	private static final String CodiceFiscaleF = "[A-Z]{6}[0-9]{2}[ABCDEHLMPRST]{1}[0-9]{2}([A-Z]{1}[0-9]{3})[A-Z]{1}$";

	/**
	 * Regular expression che controlla che ogni data rispetti il formato GG/MM/AAAA
	 */
	private static final String Data = "^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d$"; 


	/**
	 * Costruttore della classe AbstractPersona utilizzato per istanziare un oggetto di tipo Cliente, che
	 * controllera' tutti i parametri in ingresso
	 * 
	 * @param nome [Variabile String che rappresenta il nome del cliente]
	 * @param cognome [Variabile String che rappresenta il cognome del cliente]
	 * @param DataDiNascita [Variabile String nel formato GG/MM/AAAA che rappresenta la data di nascita del cliente; attenzione, il cliente deve esseree maggiorenne]
	 * @param CodiceFiscale [Variabile String che rappresenta il codice fiscale del cliente, controllando opportunamente che sia nel formato corretto]
	 * @param LuogoDiNascita [Variabile String che rappresenta il luogo di nascita del cliente]
	 * @param LuogoDiResidenza [Variabile String che rappresenta il luogo di residenza del cliente]
	 * @param sesso [Variabile di tipo Genere che rappresenta il sesso del cliente]
	 * @throws EccezioneRuoli
	 */
	public AbstractPersona(String nome, String cognome, String DataDiNascita,String CodiceFiscale, String LuogoDiNascita,
			String LuogoDiResidenza, Genere sesso) throws EccezioneRuoli {

		if(nome==null)
			throw new EccezioneRuoli("Nome nullo");

		if(cognome==null) 
			throw new EccezioneRuoli("Cognome nullo");

		if(ControlloStringa(nome)) {
			throw new EccezioneRuoli("Il nome non puo' contenere numeri");
		}

		if(ControlloStringa(cognome)) {
			throw new EccezioneRuoli("Il cognome non puo' contenere numeri");
		}

		if(!DataDiNascita.matches(Data)) 
			throw new EccezioneRuoli("Formato della data di nascita non valido");

		if(CalcolaEta(DataDiNascita)<18)
			throw new EccezioneRuoli("Eta' non consentita (Solo maggiorenni)");

		if(!CodiceFiscale.matches(CodiceFiscaleF)) 
			throw new EccezioneRuoli("Formato del codice fiscale non valido");


		if(!CodiciFiscali.add(CodiceFiscale)) 
			throw new EccezioneRuoli("Codice fiscale gia' presente");


		if(LuogoDiNascita==null) 
			throw new EccezioneRuoli("Luogo di nascita nullo");


		if(LuogoDiResidenza==null) 
			throw new EccezioneRuoli("Luogo di residenza nullo");

		if(ControlloStringa(LuogoDiNascita)) {
			throw new EccezioneRuoli("Il luogo di nascita non puo' contenere numeri");
		}

		if(ControlloStringa(LuogoDiResidenza)) {
			throw new EccezioneRuoli("Il luogo di residenza non puo' contenere numeri");
		}

		if(sesso==null)
			throw new EccezioneRuoli("Genere nullo");


		this.codice_cliente=last_code++;
		this.eta=this.CalcolaEta(DataDiNascita);
		this.nome=nome;
		this.cognome=cognome;
		this.DataDiNascita=DataDiNascita;
		this.CodiceFiscale=CodiceFiscale;
		this.LuogoDiNascita=LuogoDiNascita;
		this.LuogoDiResidenza=LuogoDiResidenza;
		this.sesso=sesso;
		this.codice_venditore=null;

	}

	/**
	 * 
	 * Costruttore della classe AbstractPersona utilizzato per istanziare un oggetto di tipo Venditore, che
	 * controllera' tutti i parametri in ingresso
	 * 
	 * @param nome [Variabile String che rappresenta il nome del venditore]
	 * @param cognome [Variabile String che rappresenta il cognome del venditore]
	 * @param DataDiNascita [Variabile String nel formato GG/MM/AAAA che rappresenta la data di nascita del venditore; attenzione, il venditore deve esseree maggiorenne]
	 * @param CodiceFiscale [Variabile String che rappresenta il codice fiscale del venditore, controllando opportunamente che sia nel formato corretto]
	 * @param LuogoDiNascita [Variabile String che rappresenta il luogo di nascita del venditore]
	 * @param LuogoDiResidenza [Variabile String che rappresenta il luogo di residenza del venditore]
	 * @param sesso [Variabile di tipo Genere che rappresenta il sesso del venditore]
	 * @param codice_venditore [Variabile String che rappresenta il codice venditore del venditoree, di lunghezza 10, generato automaticamente dal sistema]
	 * @throws EccezioneRuoli
	 */
	public AbstractPersona(String nome, String cognome, String DataDiNascita,String CodiceFiscale, String LuogoDiNascita,
			String LuogoDiResidenza, Genere sesso, String codice_venditore) throws EccezioneRuoli {

		if(nome==null) 
			throw new EccezioneRuoli("Nome nullo");

		if(cognome==null) 
			throw new EccezioneRuoli("Cognome nullo");

		if(ControlloStringa(nome)) {
			throw new EccezioneRuoli("Il nome non puo' contenere numeri");
		}

		if(ControlloStringa(cognome)) {
			throw new EccezioneRuoli("Il cognome non puo' contenere numeri");
		}

		if(!DataDiNascita.matches(Data)) 
			throw new EccezioneRuoli("Formato della data di nascita non valido");

		if(CalcolaEta(DataDiNascita)<18)
			throw new EccezioneRuoli("Eta' non consentita (Solo maggiorenni)");

		if(!CodiceFiscale.matches(CodiceFiscaleF)) 
			throw new EccezioneRuoli("Formato del codice fiscale non valido");


		if(!CodiciFiscali.add(CodiceFiscale)) 
			throw new EccezioneRuoli("Codice fiscale gia' presente");


		if(LuogoDiNascita==null) 
			throw new EccezioneRuoli("Luogo di nascita nullo");


		if(LuogoDiResidenza==null) 
			throw new EccezioneRuoli("Luogo di residenza nullo");

		if(ControlloStringa(LuogoDiNascita)) {
			throw new EccezioneRuoli("Il luogo di nascita non puo' contenere numeri");
		}

		if(ControlloStringa(LuogoDiResidenza)) {
			throw new EccezioneRuoli("Il luogo di residenza non puo' contenere numeri");
		}

		if(sesso==null)
			throw new EccezioneRuoli("Genere nullo");

		if(!CodiciVenditori.add(codice_venditore))
			throw new EccezioneRuoli("Errore di generazione, il codice venditore e' gia presente");

		this.eta=this.CalcolaEta(DataDiNascita);
		this.nome=nome;
		this.cognome=cognome;
		this.DataDiNascita=DataDiNascita;
		this.CodiceFiscale=CodiceFiscale;
		this.LuogoDiNascita=LuogoDiNascita;
		this.LuogoDiResidenza=LuogoDiResidenza;
		this.sesso=sesso;
		this.codice_venditore=codice_venditore;
	}


	private int CalcolaEta(String DataDiNascita) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		//String to date
		LocalDate localDate = LocalDate.parse(DataDiNascita, dateTimeFormatter);

		Period p = Period.between(localDate, LocalDate.now());


		//System.out.println(p.getYears());

		return p.getYears();
	}
	/**
	 * Metodo astratto che permette di settare la variabile hashmap utile a gestire tutte le date di noleggio
	 * @param data [Variabile di tipo String che rappresenta l'inizio del noleggio]
	 * @throws EccezioneRuoli
	 */
	public abstract void setDataNoleggi(String data) throws EccezioneRuoli;

	public String getLuogoDiResidenza() {
		return LuogoDiResidenza;
	}

	/**
	 * Metodo che permeette di settare il nuovo luogo di residenza della persona
	 * Viene controllato se quest'ultimo e' uguale a quello precedente o nullo, in entrambi i casi il Software sollevera' un'eccezione
	 * @param luogoDiResidenza [Variabile di tipo String che rappresenta il nuovo luogo di Residenza della presona]
	 * @throws EccezioneRuoli
	 */
	public void setLuogoDiResidenza(String luogoDiResidenza) throws EccezioneRuoli {

		if(luogoDiResidenza==null)
			throw new EccezioneRuoli("Il luogo di residenza e' nullo");

		this.LuogoDiResidenza = luogoDiResidenza;

	}
	/**
	 * Metodo getter del nome della presona
	 * @return nome [Variabile String che conterra' il nome della persona]
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Metodo getter del cognome della persona
	 * @return cognome [Variabile String che conterra' il cognome della persona]
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * Metodo getter della data di nascita della persona
	 * @return DataDiNascita[Variabile String che conterra' la data di nascita della persona]
	 */
	public String getDataDiNascita() {
		return DataDiNascita;
	}

	/**
	 * Metodo getter del Codice Fiscale della persona
	 * @return CodiceFiscale [Variabile String che conterra' il codice fiscale della persona]
	 */
	public String getCodiceFiscale() {
		return CodiceFiscale;
	}

	/**
	 * Metodo getter del luogo di nascita
	 * @return LuogoDiNascita[Variabile String che conterra' il Luogo di nascita della persona]
	 */
	public String getLuogoDiNascita() {
		return LuogoDiNascita;
	}

	/**
	 * Metodo getter del sesso della persona
	 * @return sesso [Variabile Genere che conterra' il sesso della persona]
	 */
	public Genere getSesso() {
		return sesso;
	}

	/**
	 * Metodo getter dell'eta' della persona
	 * @return eta[Variabile Int che conterra' l'eta' della persona]
	 */
	public int getEta() {
		return eta;
	}

	/**
	 * Metodo che permette di eliminare un codice fiscale dal set di codici fiscali statico contenuto nella classe AbstractPersona
	 * @param a [Oggetto di tipo AbstractPersona]
	 */
	public static void setCodiciFiscali(AbstractPersona a) { 
		Iterator<String> it = CodiciFiscali.iterator();

		while(it.hasNext()) {
			if(it.next().compareTo(a.getCodiceFiscale())==0)
				it.remove();
		}

	}


	/**
	 * Metodo getter del Set che contiene tutti i codici fiscali della classe AbstractPersona (Venditori e cliente)
	 * @return CodiciFisali [Set di oggetti di tipo String che contiene tutti i codici fiscali]
	 */
	public static Set<String> getCodiciFiscali() {
		return CodiciFiscali;
	}

	/**
	 * Metodo setter del Set che contiene tutti i codici fiscali della classe AbstractPersona (Venditori e cliente) utlizzato per la serializzazione
	 * @param codiciFiscali [Set di oggetti di tipo String che conterra' tutti i codici fiscali]
	 */
	public static void setCodiciFiscaliSerialize(Set<String> codiciFiscali) {
		CodiciFiscali = codiciFiscali;
	}

	/**
	 * Metodo getter del Set che conterra' tutti i codici venditore
	 * 
	 * @return CodiciVenditori [set di oggetti di tipo String che conterra' tutti i codici venditore]
	 * 
	 */
	public static Set<String> getCodiciVenditori() {
		return CodiciVenditori;
	}

	/**
	 * Metodo setter del Set che contiene tutti i codici venditore della classe Venditore utlizzato per la serializzazione
	 * @param codiciVenditori [set di oggetti di tipo String che conterra' tutti i codici venditore]
	 */
	public static void setCodiciVenditoriSerialize(Set<String> codiciVenditori) {
		CodiciVenditori = codiciVenditori;
	}


	/**
	 * Metodo getter last_code
	 * @return last_code [Variabile di tipo int che serve per inizializzare il codice di ogni persona]
	 */
	public static int getLast_code() {
		return last_code;
	}

	/**
	 * Metodo setter last_code 
	 * @param last_code [Variabile di tipo int che serve per inizializzare il codice di ogni persona]
	 */
	public static void setLast_code(int last_code) {
		AbstractPersona.last_code = last_code;
	}

	private boolean ControlloStringa(String s) {

		boolean risultato = false;
		char[] c = s.toCharArray();

		for (int i = 0; i < c.length; i++) {

			if(Character.isDigit(c[i]))
				risultato=true;

		}
		return risultato;
	}

	@Override
	public int hashCode() {
		return Objects.hash(CodiceFiscale);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractPersona other = (AbstractPersona) obj;
		return Objects.equals(CodiceFiscale, other.CodiceFiscale);
	}

	@Override
	public String toString() {
		return "\nNome = " + nome + "\nCognome = " + cognome + "\nData di nascita = " + DataDiNascita
				+ "\nCodiceFiscale = " + CodiceFiscale + "\nLuogo di nascita = " + LuogoDiNascita + "\nLuogo di residenza = " 
				+ LuogoDiResidenza + "\nSesso = " + sesso + "\nEta = " + eta + "\nCodice = " + codice_cliente + "";
	}




}
