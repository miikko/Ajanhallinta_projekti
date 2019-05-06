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

	private static final String[] GUI_WORDS_FI = new String[] { "Ajanhallintapalvelu", "K‰ytt‰j‰", "Salasana",
			"Kirjaudu", "Rekisterˆidy", "Virheellinen k‰ytt‰j‰nimi ja/tai salasana.",
			"Valitsemasi k‰ytt‰j‰nimi on varattu, valitse toinen nimi", "Asetukset", "K‰ytt‰j‰tiedot", "Kirjaudu ulos",
			"Lopeta", "K‰ytt‰j‰si Id: ", "Uusi k‰ytt‰j‰nimi", "Uusi salasana", "Uusi salasana uudelleen", "Vaihda",
			"Vaihda", "P‰‰valikko", "Historia", "Ryhm‰t", "Rajoitukset", "K‰ytt‰j‰nimen vaihto onnistui!",
			"K‰ytt‰j‰nimen vaihto ep‰onnistui.", "Salasanan vaihto onnistui!", "Salasanan vaihto ep‰onnistui.",
			"Tervetuloa ", "Hyv‰ksy", "Valitse kaaviotyyppi", "Ympyr‰kaavio", "Pylv‰skaavio", "Takaisin",
			"Ei n‰ytett‰vi‰ tilastoja", "Eri ohjelmien parissa k‰ytetty aika p‰iv‰st‰ ", " p‰iv‰‰n ", "Viikonp‰iv‰",
			"Tunnit", "Valitse aloitusp‰iv‰", "Valitse lopetusp‰iv‰", "Poista ryhm‰", "Ryhm‰n historia",
			"Luo uusi ryhm‰", "Luo", "Ryhm‰n nimi", "K‰ytt‰j‰n Id", "Lis‰‰ k‰ytt‰j‰", "K‰ytt‰j‰ lis‰tty",
			"Antamaasi Id:t‰ ei lˆydy.",
			"Ryhm‰n luominen ep‰onnistui. Tarkista, ett‰ ryhm‰ss‰ on v‰hint‰‰n yksi j‰sen lis‰ksesi ja ett‰ ryhm‰n nimi on k‰ytett‰viss‰",
			"Poista", "Valitse rajoitettava sovellus", "Muokkaa", "Valitse sovellus", "Minuutit", "Lis‰‰", "Lis‰‰ uusi",
			"Valmis", "Tallenna", "Valitse viikonp‰iv‰", "Ole hyv‰ ja valitse viikonp‰iv‰", "V‰‰r‰t aika arvot",
			"Rajoitus p‰ivitetty!", "Rajoituksien tallentaminen ep‰onnistui", "P‰‰valikko", "Aloita", "Lopeta",
			"Varoitus!", "Sinulla on ", " minuutti(a) aikaa k‰ytt‰‰ ohjelmaa ", "Valitse ryhm‰", "Lis‰‰", "Maanantai",
			"Tiistai", "Keskiviikko", "Torstai", "Perjantai", "Lauantai", "Sunnuntai" };

	private static List<String> wordList_en = Arrays.asList(GUI_WORDS_EN);
	private static List<String> wordList_fi = Arrays.asList(GUI_WORDS_FI);

	private static String manualLanguage = "fi";

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
