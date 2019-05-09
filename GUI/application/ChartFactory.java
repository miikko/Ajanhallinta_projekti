package application;

import java.util.Set;

import database.Sitting;
import javafx.scene.layout.StackPane;

/**
 * Abstract Factory for creating charts
 * 
 * @author miikk
 *
 */
interface ChartFactory {

	/**
	 * Factory method that creates customized chart containers.
	 * 
	 * @param sittings     data-set that is used to form the chart
	 * @param startDateStr The first date at which the data is taken from in String
	 *                     form
	 * @param endDateStr   The last date at which the data is taken from in String
	 *                     form
	 * @return a StackPane which contains the chart
	 */
	public StackPane createChart(Set<Sitting> sittings, String startDateStr, String endDateStr);

}
