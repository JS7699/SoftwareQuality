package de.dhbw.tinf.ase.pe;

import de.dhbw.tinf.ase.pe.state.Bereit;
import de.dhbw.tinf.ase.pe.state.KarteDrin;
import de.dhbw.tinf.ase.pe.state.KeinGeld;
import de.dhbw.tinf.ase.pe.state.PinKorrekt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Klasse zur Steuerung des Geldautomaten
 * @author André Bautz, Tim-Loris Deinert, Jan Stippe
 */
public class Anwendung {

	private static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		Geldautomat geldautomat = new Geldautomat();

		System.out.println("Willkommen beim DHBW Geldautomat!");

		System.out.println(geldautomat.info());

		while (true) {

			hauptmenueAusgabe();

			String input = cin.readLine();

			int aktion = 0;

			try {
				aktion = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Unzulässige Eingabe!");
				continue;
			}

			if (aktion == 1) {
				System.out.println(geldautomat.info());
			} else if (aktion == 2) {
				geldautomatBestücken(geldautomat);
			} else if (aktion == 3) {
				karteEinschieben(geldautomat);
			} else if (aktion == 4) {
				pinEingeben(geldautomat);
			} else if (aktion == 5) {
				geldAuszahlen(geldautomat);
			} else if (aktion == 6) {
				geldautomat.ausgeben();
			} else if (aktion == 7) {
				System.out.println("Der Automat enthält " + geldautomat.getBargeld() + " Taler");
			} else if (aktion == 8) {
				break;
			}

		}

		System.out.println("Danke dass du den DHBW Geldautomat benutzt hast :-)");

		cin.close();

	}

	private static void geldAuszahlen(Geldautomat geldautomat) {
		if (geldautomat.getZustand().getClass() == PinKorrekt.class) {
			System.out.print("Bitte gib den gewünschten Betrag ein: ");
			try {
				String input = cin.readLine();
				int abheben = Integer.parseInt(input);
				int summe = geldautomat.auszahlen(abheben);
				
				if (summe == -1) {
					System.out.println("Keine Karte oder falsche PIN - bitte noch einmal versuchen!");
				}
				
				if (summe == abheben) {
					System.out.println(input + " Taler ausgegeben - viel Spaß damit");
				}
			} catch (IOException | NumberFormatException e) {
				geldAuszahlen(geldautomat);
			} 
		} else {
			geldautomat.auszahlen(0);
		}
	}

	private static void pinEingeben(Geldautomat geldautomat) {
		if (geldautomat.getZustand().getClass() == KarteDrin.class) {
			System.out.print("Bitte gib jetzt deine PIN ein: ");
			try {
				String pin = cin.readLine();
				geldautomat.eingeben(pin);
			} catch (IOException e) {
				pinEingeben(geldautomat);
			} catch (IllegalStateException e) {
				System.out.println(e.getMessage());
			}
		} else {
			geldautomat.eingeben("");
		}
	}

	private static void karteEinschieben(Geldautomat geldautomat) {
		if (geldautomat.getZustand().getClass() == Bereit.class || geldautomat.getZustand().getClass() == KeinGeld.class) {
			String pin = Karte.erzeugePin();
			System.out.println("Die Pin für deine Karte ist " + pin);
			Karte karte = new Karte(pin);
			geldautomat.einschieben(karte);
		} else {
			geldautomat.einschieben(null);
		}
	}

	private static void geldautomatBestücken(Geldautomat geldautomat) {
		if (geldautomat.getZustand().getClass() == Bereit.class || geldautomat.getZustand().getClass() == KeinGeld.class) {
			System.out.print("Bitte gib die Summe ein: ");
			try {
				String input = cin.readLine();
				int summe = Integer.parseInt(input);
				geldautomat.bestücken(summe);
			} catch (NumberFormatException | IOException e) {
				geldautomatBestücken(geldautomat);
			}
		} else {
			geldautomat.bestücken(0);
		}
	}

	private static void hauptmenueAusgabe() {
		System.out.println("Was willst du tun?");
		System.out.println("[1] - Info ausgeben");
		System.out.println("[2] - Geldautomat bestücken");
		System.out.println("[3] - Karte einschieben");
		System.out.println("[4] - PIN eingeben");
		System.out.println("[5] - Geld auszahlen");
		System.out.println("[6] - Karte entnehmen");
		System.out.println("[7] - Füllstand anzeigen");
		System.out.println("[8] - Programm beenden");

	}

}
