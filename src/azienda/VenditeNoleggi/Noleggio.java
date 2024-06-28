package azienda.VenditeNoleggi;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import azienda.Ruoli.Cliente;
import azienda.Ruoli.Venditore;
import azienda.Ruoli.Eccezioni.EccezioneRuoli;
import azienda.Smartphone.SmartphoneAvanzato;
import azienda.VenditeNoleggi.Eccezioni.VenditeNoleggiEccezione;

/**
 * Classe che si occupera' della creazione di un oggetto di tipo Noleggio, che conterra' tutte le informazioni necessarie per il Noleggio di uno smartphone
 * Attenzione, il noleggio e' consentito soltanto a smartphone di tipo Avanzato
 * @author Gabriel Cellammare
 *
 */
public class Noleggio implements Cloneable,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6536048146723085607L;

	private int codice;
	private String data_i;
	private String data_f;
	private double prezzoDiNoleggio;
	private Cliente c;
	private Venditore v;
	private SmartphoneAvanzato s;


	private static double incassi=0;
	private static int last_code=0;

	/**
	 * Regex che impone il formato data GG/MM/AAAA
	 */
	private static final String DataF = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$"; //GG/MM/AAAA;

	public Noleggio(Cliente c, Venditore v,SmartphoneAvanzato s, double prezzoDiNoleggio, String data_i, String data_f) throws VenditeNoleggiEccezione{

		if(c==null) 
			throw new VenditeNoleggiEccezione("Cliente non valido");

		if(v==null)
			throw new VenditeNoleggiEccezione("Venditore non valido");

		if(s==null)
			throw new VenditeNoleggiEccezione("Smartphone non valido");

		if(!data_i.matches(DataF))
			throw new VenditeNoleggiEccezione("Data non valida");

		if(!data_f.matches(DataF))
			throw new VenditeNoleggiEccezione("Data non valida");

		if(calcolo_giorni(data_i,data_f)<=0)
			throw new VenditeNoleggiEccezione("Giorni di noleggio non validi");

		if(s.isDisponibile()==false) 
			throw new VenditeNoleggiEccezione("Smartphone non disponibile attualmente");


		this.c=c;
		this.v=v;
		this.s=s;
		this.codice=last_code++;
		this.data_i=data_i;
		this.data_f=data_f;
		this.prezzoDiNoleggio=prezzoDiNoleggio;



		this.s.setDisponibile(false);
		try {
			this.c.addSmartphoneNoleggiato(s);
		} catch (EccezioneRuoli e) {
			e.printStackTrace();
		}
		try {
			v.setDataNoleggi(data_i);
		} catch (EccezioneRuoli e1) {
			
			e1.printStackTrace();
		}
		try {
			c.setDataNoleggi(data_i);
		} catch (EccezioneRuoli e) {

			e.printStackTrace();
		}

		Noleggio.incassi+=prezzoDiNoleggio;


		/**
		 * L'istanza del noleggio appena creata, verra' aggiunta alla classe CatalogoNoleggi che conterra' tutti i noleggi effettuati
		 */
		CatalogoNoleggi.addNoleggio(this);

	}

	/**
	 * Funzione che restituira' il numero dei giorni che intercorrono tra la data d'inizio e la data di fine
	 * @param data_i [Variabile di tipo String che rappresenta la data d'inizio]
	 * @param data_f [Variabile di tipo String che rappresenta la data di fine]
	 * @return Risultato [Variabile di tipo Int, che puo' rappresentare gli anni, i mesi, oppure i giorni di decorrenza]
	 */

	public static int calcolo_giorni(String data_i, String data_f) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		//String to date
		LocalDate DataInizio = LocalDate.parse(data_i, dateTimeFormatter);
		LocalDate DataFine = LocalDate.parse(data_f, dateTimeFormatter);

		Period p = Period.between(DataInizio,DataFine);


		//System.out.println(p.getYears());

		if(p.getDays()==0) {
			if(p.getMonths()==0) {
				return p.getYears();


			}
			else
				return p.getMonths();
		}

		else {
			return p.getDays();
		}


	}


	/**
	 * Funzione che restituira' il numero dei giorni che intercorrono tra la data d'inizio e la data di fine
	 * @param data_i [Variabile di tipo String che rappresenta la data d'inizio]
	 * @param data_f [Variabile di tipo String che rappresenta la data di fine]
	 * @return p [Variabile di tipo Period che ritonera' tutte le informazioni utili per il calcolo di giorni, mesi, anni]
	 */
	public static Period calcoloDate(String data_i, String data_f) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		//String to date
		LocalDate DataInizio = LocalDate.parse(data_i, dateTimeFormatter);
		LocalDate DataFine = LocalDate.parse(data_f, dateTimeFormatter);

		Period p = Period.between(DataInizio,DataFine);

		return p;


	}

	/**
	 * Metodo getter statico che restituira' l'incasso totale prodotto da tutti i noleggi
	 * @return incassi [Variabile di tipo double che rappresenta l'incasso prodotto da tutti i noleggi]
	 */
	public static double getIncassi() {
		return incassi;
	}

	/**
	 * Metodo setter statico che settera' l'incasso totale prodotto da tutti i noleggi
	 * @param incassi [Variabile di tipo double che rappresenta l'incasso prodotto da tutti i noleggi]
	 */
	public static void setIncassi(double incassi) {
		Noleggio.incassi = incassi;
	}

	/**
	 * Metodo getter dello Smartphone noleggiato
	 * @return s [Variabile di tipo SmartphoneAvanzato che rappresenta lo smartphone noleggiato]
	 */
	public SmartphoneAvanzato getS() {
		return s;
	}

	/**
	 * Funzione getter del codice univoco assegnato ad ogni noleggio
	 * @return codice [Variabile di tipo Int che identifica il codice noleggio]
	 */
	public int getCodice() {
		return codice;
	}

	/**
	 * Metodo getter del giorno d'inizio noleggio
	 * @return data_i [Variabile di tipo String che rappresenta la data d'inizio]
	 */
	public String getData_i() {
		return data_i;
	}

	/**
	 * Metodo getter del giorno d'inizio noleggio
	 * @return data_f [Variabile di tipo String che rappresenta la data di fine]
	 */
	public String getData_f() {
		return data_f;
	}

	/**
	 * Metodo che restituisce il prezzo di noleggio 
	 * @return prezzoDiNoleggio [Variabile di tipo double che rappresenta il prezzo del noleggio]
	 */
	public double getPrezzoDiNoleggio() {
		return prezzoDiNoleggio;
	}

	/**
	 * Metodo getter del cliente che ha effettuato il noleggio
	 * @return c [Variabile di tipo Cliente]
	 */
	public Cliente getC() {
		return c;
	}

	/**
	 * Metodo getter del venditore che ha effettuato il noleggio
	 * @return v [Variabile di tipo Venditore]
	 */
	public Venditore getV() {
		return v;
	}

	/**
	 * Metodo getter che si occupera' di restituire la variabile last_code
	 * @return last_code [Variabile di tipo Int utilizzata per generare ad ogni noleggio, un numero univoco]
	 */
	public static int getLast_code() {
		return last_code;
	}
	
	/**
	 * Metodo setter che si occupera' di settare la variabile last_code
	 * @param last_code [Variabile di tipo Int utilizzata per generare ad ogni noleggio, un numero univoco]
	 */
	public static void setLast_code(int last_code) {
		Noleggio.last_code = last_code;
	}


	@Override
	public int hashCode() {
		return Objects.hash(codice);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Noleggio other = (Noleggio) obj;
		return codice == other.codice;
	}



	@Override
	public Object clone(){
		// TODO Auto-generated method stub
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");

		return "\n\nCodice noleggio = " + codice + "\nData inizio noleggio = " + data_i + "\nData fine noleggio = "  + data_f + "\nPrezzo totale di noleggio = €"
		+ df.format(prezzoDiNoleggio) + "\nCliente [Codice Fiscale] = " + c.getCodiceFiscale() + "\nVenditore [Codice venditore] = " + v.getCodice_venditore() + "\nSmartphone [Codice IMEI] = " + s.getCodiceImei();
	}





}
