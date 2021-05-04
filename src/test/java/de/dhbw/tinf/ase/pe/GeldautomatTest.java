package de.dhbw.tinf.ase.pe;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;
import de.dhbw.tinf.ase.pe.state.Bereit;
import de.dhbw.tinf.ase.pe.state.KarteDrin;
import de.dhbw.tinf.ase.pe.state.KeinGeld;
import de.dhbw.tinf.ase.pe.state.PinKorrekt;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Klasse mit den Unit-Tests für die Geldautomat-Klasse (inklusive Zuständen)
 * @author André Bautz, Tim-Loris Deinert, Jan Stippe
 */
public class GeldautomatTest {

	@Test
	public void testEinschiebenKarteIstNull() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(null);
		assertEquals("Wert von Karte muss bei null bleiben", null, geldautomat.getKarte());
	}
	
	@Test
	public void testEinschiebenZweiteKarte() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		Karte karte1 = new Karte("1111");
		Karte karte2 = new Karte("2222");
		geldautomat.einschieben(karte1);
		geldautomat.einschieben(karte2);
		assertEquals("Beim Einschieben einer zweiten Karte, soll die erste gespeichert bleiben", karte1, geldautomat.getKarte());
		assertEquals("Beim Einschieben eine zweiten Karte soll der Zustand bei KarteDrin bleiben", KarteDrin.class, geldautomat.getZustand().getClass());
	}
	
	@Test
	public void testEinschiebenKeinBargeld() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.einschieben(new Karte("1111"));
		assertEquals("Beim Einschieben einer Karte ohne Bargeld soll diese wieder ausgeworfen werden", null, geldautomat.getKarte());
		assertEquals("Beim Einschieben einer Karte ohne Bargeld soll der Zustand bei KeinGeld bleiben", KeinGeld.class, geldautomat.getZustand().getClass());
	}
	
	@Test
	public void testAuszahlenKorrektePin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(500);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		int summe = geldautomat.auszahlen(100);
		assertEquals("Bei korrekter PIN muss Summe zurückgegeben werden", 100, summe);
		assertEquals("Bei korrekter PIN muss Bargeld um ausgegebene Summe erniedrigt werden", 400, geldautomat.getBargeld());
		assertEquals("Bei korrekter PIN bleibt der Zustand bei PinKorrekt", PinKorrekt.class, geldautomat.getZustand().getClass());
	}
	
	@Test
	public void testAuszahlenFalschePin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("2222");
		int summe = geldautomat.auszahlen(500);
		assertEquals("Bei falscher PIN muss -1 zurückgegeben werden!", -1, summe);
		assertEquals("Bei falscher PIN soll der Zustand bei KarteDrin bleiben", KarteDrin.class, geldautomat.getZustand().getClass());
		assertEquals("Bei falscher PIN darf der Füllstand des Automaten nicht reduziert werden", 1000, geldautomat.getBargeld());
	}
	
	@Test
	public void testAuszahlenOhnePin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		int summe = geldautomat.auszahlen(500);
		assertEquals("Bei falscher PIN muss -1 zurückgegeben werden!", -1, summe);
		assertEquals("Beim Auszahlen ohne Pin soll der Zustand KarteDrin sein", KarteDrin.class, geldautomat.getZustand().getClass());
	}
	
	@Test
	public void testAuszahlenZuKleineSumme() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		geldautomat.auszahlen(4);
		assertEquals("Beim Auszahlen einer zu kleinen Summe soll der Zustand bei PinKorrekt bleiben", PinKorrekt.class, geldautomat.getZustand().getClass());
		assertEquals("Bei einer zu kleinen Summe darf der Füllstand des Automaten nicht reduziert werden", 1000, geldautomat.getBargeld());
	}
	
	@Test
	public void testAuszahlenZuGroßeSumme() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		geldautomat.auszahlen(501);
		assertEquals("Beim Auszahlen einer zu großen Summe soll der Automat bei PinKorrekt bleiben", PinKorrekt.class, geldautomat.getZustand().getClass());
		assertEquals("Bei einer zu großen Summe darf der Füllstand des Automaten nicht reduziert werden", 1000, geldautomat.getBargeld());
	}
	
	@Test
	public void testAuszahlenSummeNichtDurch5Teilbar() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		geldautomat.auszahlen(333);
		assertEquals("Beim Auszahlen einer Summe, die nicht durch 5 teilbar ist, soll der Zustand bei PinKorrekt bleiben", PinKorrekt.class, geldautomat.getZustand().getClass());
		assertEquals("Bei einer nicht durch 5 teilbaren Summe darf der Füllstand des Automaten nicht reduziert werden", 1000, geldautomat.getBargeld());
	}
	
	@Test
	public void testAuszahlenZuWenigGeldImAutomat() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(100);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		int summe = geldautomat.auszahlen(300);
		assertEquals("Bei zu wenig Geld muss der Rest (100) zurückgegeben werden!", 100, summe);
		assertEquals("Bargeld muss jetzt 0 sein!", 0, geldautomat.getBargeld());
		assertEquals("Automat soll zu Zustand KeinGeld wechseln", KeinGeld.class, geldautomat.getZustand().getClass());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEingebenPinMitBuchstaben() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("12a4");
		fail("Expected IllegalArgumentException");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEingebenPinZuKurz() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("123");
		fail("Expected IllegalArgumentException");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEingebenPinZuLang() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("12345");
		fail("Expected IllegalArgumentException");
	}
	
	@Test
	public void testEingebenPin3MalFalsch() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1234");
		geldautomat.eingeben("1234");
		geldautomat.eingeben("1234");
		assertEquals("Nach drei falschen Eingaben soll die Karte ausgegeben werden", null, geldautomat.getKarte());
		assertEquals("Nach drei falschen Eingaben soll in den Zustand Bereit gewechselt werden", Bereit.class, geldautomat.getZustand().getClass());
	}

	@Test
	public void testBestücken() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(100);
		assertEquals("Bestand muss übereinstimmen!", 100, geldautomat.getBargeld());
		assertEquals("Nach dem Bestücken soll in den Zustand Bereit gewechselt werden", Bereit.class, geldautomat.getZustand().getClass());
	}
	
	@Test
	public void testBestückenVollerAutomat() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(100);
		geldautomat.bestücken(100);
		assertEquals("Bestand muss erhöht sein!", 200, geldautomat.getBargeld());
		assertEquals("Nach erneutem Bestücken soll im Zustand Bereit geblieben werden", Bereit.class, geldautomat.getZustand().getClass());
	}
	
	@Test
	public void testBestückenMitKarte() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.bestücken(100);
		assertEquals("Karte muss ausgeworfen werden", null, geldautomat.getKarte());
		assertEquals("Das Bargeld des Automaten soll erhöht werden", 100, geldautomat.getBargeld());
		assertEquals("Zustand ändert sich zu Bereit", Bereit.class, geldautomat.getZustand().getClass());
	}
	
	@Test
	public void testInfoKeinBargeld() {
		Geldautomat geldautomat = new Geldautomat();
		String info = geldautomat.info();
		String sollString = "Der Automat enthält " + geldautomat.getBargeld() + " Taler.";
		assertEquals("Wenn der Automat kein Bargeld hat soll dies als Info ausgegeben werden", sollString, info);
	}
	
	@Test
	public void testInfoMehrAls500BargeldOhneKarte() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(600);
		String info = geldautomat.info();
		String sollString = "Alles OK - bitte Karte eingeben";
		assertEquals("Wenn der Automat mehr als 500 Taler Bargeld hat und keine Karte im Automat ist soll dies als Info ausgegeben werden.", sollString, info);
	}
	
	@Test
	public void testInfoMehrAls500BargeldMitKarteFalschePin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(600);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("2222");
		String info = geldautomat.info();
		String sollString = "Falsche PIN oder PIN nicht eingegeben - Abhebung nicht möglich!";
		assertEquals("Wenn der Automat mehr als 500 Taler Bargeld hat und eine Karte mit falsch eingegebener Pin im Automat ist soll dies als Info ausgegeben werden.", sollString, info);
	}
	
	@Test
	public void testInfoMehrAls500BargeldMitKarteRichtigePin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(600);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		String info = geldautomat.info();
		String sollString = "Maximalbetrag kann abgehoben werden";
		assertEquals("Wenn der Automat mehr als 500 Taler Bargeld hat und eine Karte mit korrekt eingegebener Pin im Automat ist soll dies als Info ausgegeben werden.", sollString, info);
	}
	
	@Test
	public void testInfoWenigerAls500BargeldOhneKarte() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(400);
		String info = geldautomat.info();
		String sollString = "Abhebung bis zu " + geldautomat.getBargeld() + " Geld ist möglich - bitte Karte eingeben";
		assertEquals("Wenn der Automat weniger als 500 Taler Bargeld hat und keine Karte im Automat ist soll dies als Info ausgegeben werden.", sollString, info);
	}
	
	@Test
	public void testInfoWenigerAls500BargeldMitKarteFalschePin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(400);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("2222");
		String info = geldautomat.info();
		String sollString = "Falsche PIN oder PIN nicht eingegeben - Abhebung nicht möglich!";
		assertEquals("Wenn der Automat weniger als 500 Taler Bargeld hat und eine Karte mit falsch eingegebener Pin im Automat ist soll dies als Info ausgegeben werden.", sollString, info);
	}
	
	@Test
	public void testInfoWenigerAls500BargeldMitKarteRichtigePin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(400);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		String info = geldautomat.info();
		String sollString = "Abhebung bis zu " + geldautomat.getBargeld() + " Talern ist möglich";
		assertEquals("Wenn der Automat weniger als 500 Taler Bargeld hat und eine Karte mit korrekt eingegebener Pin im Automat ist soll dies als Info ausgegeben werden.", sollString, info);
	}
	
}
