package azienda.VenditeNoleggi;

import java.util.Comparator;

import azienda.Main.Sezioni.SezioneNoleggio;

/**
 * Classe comparator, che servira' ad ordinare in maniera corretta ogni vendita in base alla data di vendita e al prezzo di vendita
 * @author Gabriel Cellammare
 *
 */
public class ComparatoreVenditaDataPrezzo implements Comparator<Vendita> {

	@Override
	public int compare(Vendita o1, Vendita o2) {
		
		int risultato = SezioneNoleggio.CalcoloGiorni(o2.getData(), o1.getData());
		
		if(risultato==0) {
			risultato = (int) (o1.getPrezzoDiVendita()-o2.getPrezzoDiVendita());
			
			if(risultato==0) {
				return 1;
			}
		}
		return risultato;
	}

}
