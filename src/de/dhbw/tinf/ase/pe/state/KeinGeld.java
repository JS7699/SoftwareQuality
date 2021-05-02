package de.dhbw.tinf.ase.pe.state;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

/**
 * Klasse mit den Methoden des Geldautomaten im Zustand "Kein Geld"
 * @author André Bautz, Tim-Loris Deinert, Jan Stippe
 */
public class KeinGeld implements Zustand {

	private Geldautomat geldautomat;
	
	public KeinGeld(Geldautomat geldautomat) {
		this.geldautomat = geldautomat;
	}
	
	@Override
	public void bestücken(int bargeld) {
		geldautomat.setBargeld(geldautomat.getBargeld() + bargeld);
		geldautomat.setZustand(new Bereit(geldautomat));
	}

	@Override
	public void eingeben(String pin) {
		System.out.println("Der Automat ist leer und enthält keine Karte. Bitte warten Sie bis der Automat wieder bestückt wurde!");
	}

	@Override
	public void einschieben(Karte karte) {
		System.out.println("Der Automat hat aktuell kein Bargeld! Bitte Karte entnehmen!");
	}

	@Override
	public int auszahlen(int summe) {
		System.out.println("Der Automat ist aktuell leer und es können keine Auszahlungen getätigt werden!");
		return -1;
	}

	@Override
	public void ausgeben() {
		geldautomat.setKarte(null);
		geldautomat.setPinFalsch(0);
		System.out.println("Es befindet sich aktuell keine Karte im Automat!");
	}

	@Override
	public String info() {
		return "Der Automat enthält " + geldautomat.getBargeld() + " Taler.";
	}
	
}
