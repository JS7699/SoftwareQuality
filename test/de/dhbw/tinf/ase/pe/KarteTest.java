package de.dhbw.tinf.ase.pe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Klasse mit den Unit-Tests für die Klasse Karte
 * @author André Bautz, Tim-Loris Deinert, Jan Stippe
 */
public class KarteTest {

	@Test (expected = IllegalArgumentException.class)
	public void testKarteErzeugenPinMitBuchstaben() {
		new Karte("abcd");
		fail("Expected IllegalArgumentException");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testKarteErzeugenPinZuKurz() {
		new Karte("123");
		fail("Expected IllegalArgumentException");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testKarteErzeugenPinZuLang() {
		new Karte("12345");
		fail("Expected IllegalArgumentException");
	}
	
	@Test
	public void testPinIstKorrekt() {
		Karte karte = new Karte("1234");
		boolean ergebnis = karte.istKorrekt("1234");
		assertEquals("Bei korrekter PIN muss true zurückgegeben werden", true, ergebnis);
	}
	
	@Test
	public void testPinIstFalsch() {
		Karte karte = new Karte("1234");
		boolean ergebnis = karte.istKorrekt("4321");
		assertEquals("Bei falscher PIN muss false zurückgegeben werden", false, ergebnis);
	}
	
	@Test
	public void testErzeugePin() {
		String pin = Karte.erzeugePin();
		assertEquals("Pin muss 4 Zeichen lang sein", 4, pin.length());
	}
	
}
