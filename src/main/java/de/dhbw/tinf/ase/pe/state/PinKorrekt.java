package de.dhbw.tinf.ase.pe.state;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

/**
 * Klasse mit den Methoden des Geldautomaten im Zustand "Pin Korrekt"
 * @author André Bautz, Tim-Loris Deinert, Jan Stippe
 */
public class PinKorrekt implements Zustand {

	private Geldautomat geldautomat;
	
	public PinKorrekt(Geldautomat geldautomat) {
		this.geldautomat = geldautomat;
	}

	@Override
	public void bestücken(int bargeld) {
		System.out.println("Automat darf nicht während einer Transaktion bestückt werden!");		
	}

	@Override
	public void eingeben(String pin) {
		System.out.println("Pin wurde bereits richtig eingegeben!");
	}

	@Override
	public void einschieben(Karte karte) {
		System.out.println("Es befindet sich bereits eine Karte im Automat!");	
	}

	@Override
	public int auszahlen(int summe) {
		int ausbezahlt = 0;
		if (summe < 5 || summe > 500) {
			System.out.println("Betrag muss zwischen 5 und 500 Geld liegen!");
			return -1;
		}
		
		if (summe % 5 != 0) {
			System.out.println("Betrag muss durch 5 teilbar sein");
			return -1;
		}

		int bargeld = geldautomat.getBargeld();
		if (bargeld < summe) {
			ausbezahlt = bargeld;
			geldautomat.setKarte(null);
			geldautomat.setPinFalsch(0);
			System.out.println(ausbezahlt + " Taler ausgegeben");
			System.out.println("Automat ist leer! Bitte Karte entnehmen!");
			geldautomat.setZustand(new KeinGeld(geldautomat));
		} else {
			ausbezahlt = summe;
		}

		geldautomat.setBargeld(bargeld - ausbezahlt);
		return ausbezahlt;
	}

	@Override
	public void ausgeben() {
		geldautomat.setKarte(null);
		geldautomat.setPinFalsch(0);
		System.out.println("Deine Karte wurde wieder ausgeworfen");
		geldautomat.setZustand(new Bereit(geldautomat));
	}

	@Override
	public String info() {
		String info = "";
		if (geldautomat.getBargeld() > 500) {
			info = "Maximalbetrag kann abgehoben werden";
		} else if (geldautomat.getBargeld() > 0) {
			info = "Abhebung bis zu " + geldautomat.getBargeld() + " Talern ist möglich";
		}
		return info;
	}
	
}
