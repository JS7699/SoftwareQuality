package de.dhbw.tinf.ase.pe;

import de.dhbw.tinf.ase.pe.state.KeinGeld;
import de.dhbw.tinf.ase.pe.state.Zustand;

/**
 * Klasse zur Erzeugung eines Geldautomaten
 * @author André Bautz, Tim-Loris Deinert, Jan Stippe
 */
public class Geldautomat {

	private Zustand zustand;
	private Karte karte;
	private int bargeld = 0;
	private int pinFalsch = 0;
	
	public Geldautomat() {
		zustand = new KeinGeld(this);
	}
	
	public Zustand getZustand() {
		return zustand;
	}
	
	public void setZustand(Zustand zustand) {
		this.zustand = zustand;
	}
	
	public int getBargeld() {
		return bargeld;
	}

	public void setBargeld(int bargeld) {
		this.bargeld = bargeld;
	}
	
	public Karte getKarte() {
		return karte;
	}

	public void setKarte(Karte karte) {
		this.karte = karte;
	}

	public int getPinFalsch() {
		return pinFalsch;
	}

	public void setPinFalsch(int pinFalsch) {
		this.pinFalsch = pinFalsch;
	}

	public void bestücken(int bargeld) {
		zustand.bestücken(bargeld);
	}
	
	public void einschieben(Karte karte) {
		zustand.einschieben(karte);
	}

	public void ausgeben() {
		zustand.ausgeben();
	}

	public void eingeben(String pin) {
		zustand.eingeben(pin);
	}

	public int auszahlen(int summe) {
		return zustand.auszahlen(summe);
	}

	public String info() {
		return zustand.info();
	}
	
}
