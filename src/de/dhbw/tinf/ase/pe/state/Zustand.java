package de.dhbw.tinf.ase.pe.state;

import de.dhbw.tinf.ase.pe.Karte;

/**
 * Klasse zur Anpassung der Funktionen des Geldautomaten je nach Zustand
 * @author André Bautz, Tim-Loris Deinert, Jan Stippe
 */
public interface Zustand {

	public void bestücken(int bargeld);
	public void eingeben(String pin);
	public void einschieben(Karte karte);
	public int auszahlen(int summe);
	public void ausgeben();
	public String info();
	
}
