package azienda.Main.Sezioni;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import azienda.Ruoli.AbstractPersona;
import azienda.Ruoli.Cliente;
import azienda.Ruoli.Venditore;
import azienda.Smartphone.AbstractSmartphone;
import azienda.Smartphone.SmartphoneAcquistabile;
import azienda.Smartphone.SmartphoneAvanzato;
import azienda.Smartphone.SmartphoneBase;
import azienda.Smartphone.SmartphoneMedio;
import azienda.VenditeNoleggi.Noleggio;
import azienda.VenditeNoleggi.Vendita;

/**
 * Classe utilizzata a scopi grafici, che permette una stampa ordinata e formattata 
 * @author Gabriel Cellammare
 *
 */
public class StampaFormattata {
	
	private static final String HORIZONTAL_SEP = "-";
	private String verticalSep;
	private String joinSep;
	private String[] headers;
	private List<String[]> rows = new ArrayList<>();
	private boolean rightAlign;

	private StampaFormattata() {
		setShowVerticalLines(false);
	}

	@SuppressWarnings("unused")
	private void setRightAlign(boolean rightAlign) {
		this.rightAlign = rightAlign;
	}

	private void setShowVerticalLines(boolean showVerticalLines) {
		verticalSep = showVerticalLines ? "|" : "";
		joinSep = showVerticalLines ? "+" : " ";
	}

	private void setHeaders(String... headers) {
		this.headers = headers;
	}

	private void addRow(String... cells) {
		rows.add(cells);
	}

	private void print() {
		int[] maxWidths = headers != null ?
				Arrays.stream(headers).mapToInt(String::length).toArray() : null;

		for (String[] cells : rows) {
			if (maxWidths == null) {
				maxWidths = new int[cells.length];
			}
			if (cells.length != maxWidths.length) {
				throw new IllegalArgumentException("Number of row-cells and headers should be consistent");
			}
			for (int i = 0; i < cells.length; i++) {
				maxWidths[i] = Math.max(maxWidths[i], cells[i].length());
			}
		}

		if (headers != null) {
			printLine(maxWidths);
			printRow(headers, maxWidths);
			printLine(maxWidths);
		}
		for (String[] cells : rows) {
			printRow(cells, maxWidths);
		}
		if (headers != null) {
			printLine(maxWidths);
		}
	}

	private void printLine(int[] columnWidths) {
		for (int i = 0; i < columnWidths.length; i++) {
			String line = String.join("", Collections.nCopies(columnWidths[i] +
					verticalSep.length() + 1, HORIZONTAL_SEP));
			System.out.print(joinSep + line + (i == columnWidths.length - 1 ? joinSep : ""));
		}
		System.out.println();
	}

	private void printRow(String[] cells, int[] maxWidths) {
		for (int i = 0; i < cells.length; i++) {
			String s = cells[i];
			String verStrTemp = i == cells.length - 1 ? verticalSep : "";
			if (rightAlign) {
				System.out.printf("%s %" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
			} else {
				System.out.printf("%s %-" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
			}
		}
		System.out.println();
	}

	/**
	 * La stampa viene formattata in base al tipo di set che il Metodo Generic Stampa riceve
	 * Quindi, ogni stampa viene gestita in maniera diversa
	 * @param <T>
	 * @param c
	 */
	public static <T> void Stampa(Set<T> c) {

		StampaFormattata st = new StampaFormattata();

		Iterator<T> it = c.iterator();
		Object o = null;

		while(it.hasNext()) {
			o = it.next().getClass();
		}

		if(o.equals(Venditore.class)){

			st.setShowVerticalLines(true);
			st.setHeaders("Codice Venditore", "Cognome", "Nome");
			for (T venditore : c) {
				st.addRow(((Venditore) venditore).getCodice_venditore(), ((AbstractPersona) venditore).getCognome(), ((AbstractPersona) venditore).getNome());
			}
			st.print();

		}

		if(o.equals(Cliente.class)) {

			st.setShowVerticalLines(true);
			st.setHeaders("Codice cliente", "Cognome", "Nome");
			for (T cliente : c) {
				st.addRow( String.valueOf(((Cliente) cliente).getCodice()),((AbstractPersona) cliente).getCognome(),((AbstractPersona) cliente).getNome());
			}
			st.print();

		}

		if(o.equals(SmartphoneBase.class)) {
			st.setShowVerticalLines(true);
			st.setHeaders("Codice Imei", "Modello", "Prezzo","Disponibilita'");
			for (T smartphone : c) {
				st.addRow(((AbstractSmartphone) smartphone).getCodiceImei(),((AbstractSmartphone) smartphone).getModello(),(String.valueOf(((AbstractSmartphone) smartphone).getPrezzo())),(String.valueOf(((AbstractSmartphone) smartphone).isDisponibile())));
			}
			st.print();

		}

		if(o.equals(SmartphoneMedio.class)) {
			st.setShowVerticalLines(true);
			st.setHeaders("Codice Imei", "Modello", "Prezzo","Disponibilita'","Doppia fotocamera");
			for (T smartphone : c) {
				st.addRow(((AbstractSmartphone) smartphone).getCodiceImei(),((AbstractSmartphone) smartphone).getModello(),(String.valueOf(((AbstractSmartphone) smartphone).getPrezzo())),(String.valueOf(((AbstractSmartphone) smartphone).isDisponibile())),String.valueOf(((SmartphoneMedio) smartphone).getDoppiaFotocamera() + " MegaPixel"));
			}
			st.print();

		}

		if(o.equals(SmartphoneAvanzato.class)) {
			st.setShowVerticalLines(true);
			st.setHeaders("Codice Imei", "Modello", "Prezzo","Disponibilita'","Riconoscimento tramite impronta/facciale");
			for (T smartphone : c) {
				st.addRow(((AbstractSmartphone) smartphone).getCodiceImei(),((AbstractSmartphone) smartphone).getModello(),(String.valueOf(((AbstractSmartphone) smartphone).getPrezzo())),(String.valueOf(((AbstractSmartphone) smartphone).isDisponibile())),String.valueOf(((SmartphoneAvanzato) smartphone).isRiconoscimentoFacciale()));
			}
			st.print();

		}

		if(o.equals(SmartphoneAcquistabile.class)) {
			st.setShowVerticalLines(true);
			st.setHeaders("Codice Imei", "Modello", "Prezzo","Disponibilita'","Riconoscimento tramite impronta/facciale");
			for (T smartphone : c) {
				st.addRow(((AbstractSmartphone) smartphone).getCodiceImei(),((AbstractSmartphone) smartphone).getModello(),(String.valueOf(((AbstractSmartphone) smartphone).getPrezzo())),(String.valueOf(((AbstractSmartphone) smartphone).isDisponibile())),String.valueOf(((SmartphoneAvanzato) smartphone).isRiconoscimentoFacciale()));
			}
			st.print();

		}

		if(o.equals(Vendita.class)) {
			st.setShowVerticalLines(true);
			st.setHeaders("Codice venditore", "Codice Fiscale cliente", "Prezzo di vendita","Data di vendita","Smartphone venduto");
			for (T vendita : c) {
				st.addRow( ((Vendita)vendita).getV().getCodice_venditore(),((Vendita)vendita).getC().getCodiceFiscale(),String.valueOf(((Vendita)vendita).getPrezzoDiVendita()),((Vendita)vendita).getData(),((Vendita)vendita).getS().getCodiceImei());
			}
			st.print();

		}

		if(o.equals(Noleggio.class)) {
			DecimalFormat df = new DecimalFormat("0.00");
			st.setShowVerticalLines(true);
			st.setHeaders("Cognome venditore","Nome venditore","Codice Fiscale cliente", "Prezzo di noleggio","Data d'inizio noleggio","Data di fine noleggio","Smartphone noleggiato(IMEI)","Modello");
			for (T noleggio : c) {
				st.addRow( ((Noleggio)noleggio).getV().getCognome(),((Noleggio)noleggio).getV().getNome(),((Noleggio)noleggio).getC().getCodiceFiscale(),"€"+String.valueOf(df.format(((Noleggio)noleggio).getPrezzoDiNoleggio())),((Noleggio)noleggio).getData_i(),((Noleggio)noleggio).getData_f(),((Noleggio)noleggio).getS().getCodiceImei(),
						((Noleggio)noleggio).getS().getModello());
			}
			st.print();

		}
		

	}
	

}


