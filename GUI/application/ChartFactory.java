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

	public StackPane createChart(Set<Sitting> sittings);
	
}
