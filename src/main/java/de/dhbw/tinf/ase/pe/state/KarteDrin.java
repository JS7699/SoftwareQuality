package de.dhbw.tinf.ase.pe.state;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

/**
 * Klasse mit den Methoden des Geldautomaten im Zustand "Karte Drin"
 * @author André Bautz, Tim-Loris Deinert, Jan Stippe
 */
public class KarteDrin implements Zustand {

	private Geldautomat geldautomat;
	
	public KarteDrin(Geldautomat geldautomat) {
		this.geldautomat = geldautomat;
	}

	@Override
	public void bestücken(int bargeld) {
		System.out.println("Automat darf nicht während einer Transaktion bestückt werden!");		
	}

	@Override
	public void eingeben(String pin) {
		try {
			Integer.parseInt(pin);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("PIN muss aus Zahlen bestehen!", e);
		}

		if (pin.length() != 4) {
			throw new IllegalArgumentException("PIN muss vierstellig sein1");
		}
		
		if (geldautomat.getKarte().istKorrekt(pin)) {
			System.out.println("PIN ist korrekt!");
			geldautomat.setZustand(new PinKorrekt(geldautomat));
		} else {
			System.out.println("PIN ist falsch! Versuchen Sie es erneut!");
			geldautomat.setPinFalsch(geldautomat.getPinFalsch() + 1);
		}
		
		if (geldautomat.getPinFalsch() > 2) {
			ausgeben();
			System.out.println("PIN zu oft falsch eingegeben! Bitte Karte entnehmen!");
		}
	}

	@Override
	public void einschieben(Karte karte) {
		System.out.println("Es befindet sich bereits eine Karte im Automat!");
	}

	@Override
	public int auszahlen(int summe) {
		System.out.println("Bitte geben Sie zuerst die richtige PIN ein!");
		return -1;		
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
		return "Falsche PIN oder PIN nicht eingegeben - Abhebung nicht möglich!";
	}
	
}
