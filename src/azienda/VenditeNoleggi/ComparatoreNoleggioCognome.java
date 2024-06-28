package azienda.VenditeNoleggi;

import java.util.Comparator;
/**
 * Classe comparator, che servira' ad ordinare in maniera corretta ogni noleggio in base al cognome e al nome
 * @author Gabriel Cellammare
 *
 */
public class ComparatoreNoleggioCognome implements Comparator<Noleggio> {

	@Override
	public int compare(Noleggio o1, Noleggio o2) {
		int risultato = o1.getV().getCognome().compareTo(o2.getV().getCognome());
		
		if(risultato==0) {
			risultato = o1.getV().getNome().compareTo(o2.getV().getNome());
			
			if(risultato==0) {
				return 1;
			}
		}
		
		
		return risultato;
	}

}
