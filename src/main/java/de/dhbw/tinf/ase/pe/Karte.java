package de.dhbw.tinf.ase.pe;

/**
 * Klasse zur Erzeugung einer Karte
 * @author André Bautz, Tim-Loris Deinert, Jan Stippe
 */
public class Karte {

	private String pin;

	public Karte(String pin) {
		try {
			Integer.parseInt(pin);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("PIN muss aus Zahlen bestehen!", e);
		}
		
		if (pin.length() != 4) {
			throw new IllegalArgumentException("PIN muss vierstellig sein!");
		}
		
		this.pin = pin;
	}

	public boolean istKorrekt(String pin) {
		return this.pin.equalsIgnoreCase(pin);
	}

	public static String erzeugePin() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			double zufall = Math.random();
			double ziffer = zufall;
			ziffer *= 10;
			sb.append((int) ziffer);
		}
		return sb.toString();
	}
	
}
