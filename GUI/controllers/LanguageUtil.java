package controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * LanguageUtil contains all the visible words of the GUI in different languages
 * and handles translations with the translate method.
 * 
 * @author Arttuhal
 * @since 04/05/2019
 */

public class LanguageUtil {

	private static final String[] GUI_WORDS_EN = new String[] { "Time Manager", "Username", "Password", "Login",
			"Register", "Invalid username and/or password.",
			"Selected username is already taken. Please choose another one.", "Options", "Account", "Logout", "Quit",
			"Your Id: ", "New username", "New password", "Type same password", "Change", "Change", "Main menu",
			"History", "Groups", "Restrictions", "Username was changed successfully!", "Failed to change username.",
			"Password was changed successfully!", "Failed to change password.", "Welcome ", "Confirm",
			"Select chart type", "Pie chart", "Bar chart", "Return", "No data to show",
			"Used time on different applications from ", " to ", "Day of the week", "Hours", "Select starting date",
			"Select end date", "Remove group", "Group history", "Create new group", "Create", "Group name", "User Id",
			"Add user", "User added", "The Id you entered is not valid",
			"Failed to create a new group, check that there is atleast one other member and that the name is unique",
			"Remove", "Select restricted program", "Edit", "Choose program", "Minutes", "Add", "Add new", "Finish",
			"Save", "Select weekday", "Please select a weekday", "Invalid time values", "Restriction updated!",
			"Failed to save restrictions", "Main", "Start", "Stop", "Warning!", "You have ",
			" minute(s) left to use the program ", "Select group", "Add", "Monday", "Tuesday", "Wednesday", "Thursday",
			"Friday", "Saturday", "Sunday" };

	private static final String[] GUI_WORDS_FI = new String[] { "Ajanhallintapalvelu", "K\u00E4ytt\u00E4j\u00E4", "Salasana",
			"Kirjaudu", "Rekister\u00f6idy", "Virheellinen k\u00E4ytt\u00E4j\u00E4nimi ja/tai salasana.",
			"Valitsemasi k\u00E4ytt\u00E4j\u00E4nimi on varattu, valitse toinen nimi", "Asetukset", "K\u00E4ytt\u00E4j\u00E4tiedot", "Kirjaudu ulos",
			"Lopeta", "K\u00E4ytt\u00E4j\u00E4si Id: ", "Uusi k\u00E4ytt\u00E4j\u00E4nimi", "Uusi salasana", "Uusi salasana uudelleen", "Vaihda",
			"Vaihda", "P\u00E4\u00E4valikko", "Historia", "Ryhm\u00E4t", "Rajoitukset", "K\u00E4ytt\u00E4j\u00E4nimen vaihto onnistui!",
			"K\u00E4ytt\u00E4j\u00E4nimen vaihto ep\u00E4onnistui.", "Salasanan vaihto onnistui!", "Salasanan vaihto ep\u00E4onnistui.",
			"Tervetuloa ", "Hyv\u00E4ksy", "Valitse kaaviotyyppi", "Ympyr\u00E4kaavio", "Pylv\u00E4skaavio", "Takaisin",
			"Ei n\u00E4ytett\u00E4vi\u00E4 tilastoja", "Eri ohjelmien parissa k\u00E4ytetty aika p\u00E4iv\u00E4st\u00E4 ", " p\u00E4iv\u00E4\u00E4n ", "Viikonp\u00E4iv\u00E4",
			"Tunnit", "Valitse aloitusp\u00E4iv\u00E4", "Valitse lopetusp\u00E4iv\u00E4", "Poista ryhm\u00E4", "Ryhm\u00E4n historia",
			"Luo uusi ryhm\u00E4", "Luo", "Ryhm\u00E4n nimi", "K\u00E4ytt\u00E4j\u00E4n Id", "Lis\u00E4\u00E4 k\u00E4ytt\u00E4j\u00E4", "K\u00E4ytt\u00E4j\u00E4 lis\u00E4tty",
			"Antamaasi Id:t\u00E4 ei l\u00f6ydy.",
			"Ryhm\u00E4n luominen ep\u00E4onnistui. Tarkista, ett\u00E4 ryhm\u00E4ss\u00E4 on v\u00E4hint\u00E4\u00E4n yksi j\u00E4sen lis\u00E4ksesi ja ett\u00E4 ryhm\u00E4n nimi on k\u00E4ytett\u00E4viss\u00E4",
			"Poista", "Valitse rajoitettava sovellus", "Muokkaa", "Valitse sovellus", "Minuutit", "Lis\u00E4\u00E4", "Lis\u00E4\u00E4 uusi",
			"Valmis", "Tallenna", "Valitse viikonp\u00E4iv\u00E4", "Ole hyv\u00E4 ja valitse viikonp\u00E4iv\u00E4", "V\u00E4\u00E4r\u00E4t aika arvot",
			"Rajoitus p\u00E4ivitetty!", "Rajoituksien tallentaminen ep\u00E4onnistui", "P\u00E4\u00E4valikko", "Aloita", "Lopeta",
			"Varoitus!", "Sinulla on ", " minuutti(a) aikaa k\u00E4ytt\u00E4\u00E4 ohjelmaa ", "Valitse ryhm\u00E4", "Lis\u00E4\u00E4", "Maanantai",
			"Tiistai", "Keskiviikko", "Torstai", "Perjantai", "Lauantai", "Sunnuntai" };

	private static List<String> wordList_en = Arrays.asList(GUI_WORDS_EN);
	private static List<String> wordList_fi = Arrays.asList(GUI_WORDS_FI);

	private static String manualLanguage = null;

	/**
	 * This method initially sets the program to the current default language of the
	 * host environment through the JVM. If the string manualLanguage is set, the
	 * method will use the manually set language.
	 * 
	 * @param originalString This is the string in the GUI that will be searched
	 *                       from the word list and translated accordingly.
	 * @return The correct language version of the string given to the method.
	 */
	public static String translate(String originalString) {

		int wordPointer = wordList_en.indexOf(originalString);
		if (wordPointer < 0) {
			return originalString;
		}
		String langString = Locale.getDefault().getLanguage();

		if (manualLanguage != null) {
			langString = manualLanguage;
		}

		switch (langString) {
		case "en":
			return wordList_en.get(wordPointer);
		case "fi":
			return wordList_fi.get(wordPointer);
		default:
			return originalString;
		}
	}

	public String getManualLanguage() {
		return manualLanguage;
	}

	public void setManualLanguage(String manualLanguage) {
		this.manualLanguage = manualLanguage;
	}

}
