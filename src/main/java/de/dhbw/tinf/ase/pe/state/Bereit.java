package de.dhbw.tinf.ase.pe.state;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

/**
 * Klasse mit den Methoden des Geldautomaten im Zustand "Bereit"
 * @author André Bautz, Tim-Loris Deinert, Jan Stippe
 */
public class Bereit implements Zustand {

	private Geldautomat geldautomat;
	
	public Bereit(Geldautomat geldautomat) {
		this.geldautomat = geldautomat;
	}

	@Override
	public void bestücken(int bargeld) {
		if (geldautomat.getKarte() != null) {
			System.out.println("Automat darf nicht während einer Transaktion bestückt werden!");
		} else {
			geldautomat.setBargeld(geldautomat.getBargeld() + bargeld);
		}
	}

	@Override
	public void eingeben(String pin) {
		System.out.println("Es befindet sich aktuell keine Karte im Automat!");		
	}

	@Override
	public void einschieben(Karte karte) {		
		if (karte == null) {
			System.out.println("Es wurde eine ungültige Karte eingegeben!");
		} else {
			geldautomat.setKarte(karte);
			System.out.println("Die Karte ist jetzt im Automat");
			geldautomat.setZustand(new KarteDrin(geldautomat));			
		}
	}

	@Override
	public int auszahlen(int summe) {
		System.out.println("Es befindet sich aktuell keine Karte im Automat!");
		return -1;
	}

	@Override
	public void ausgeben() {
		System.out.println("Es befindet sich aktuell keine Karte im Automat!");
	}

	@Override
	public String info() {
		String info = "";
		if (geldautomat.getBargeld() > 500) {
			info = "Alles OK - bitte Karte eingeben";
		} else if (geldautomat.getBargeld() > 0) {
			info = "Abhebung bis zu " + geldautomat.getBargeld() + " Geld ist möglich - bitte Karte eingeben";
		}
		return info;
	}
	
}
