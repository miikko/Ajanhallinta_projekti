package application;

import java.util.Set;

import database.Sitting;
import javafx.scene.layout.StackPane;

/**
 * Abstract Factory for creating chartsa
 * 
 * @author miikk
 *
 */
interface ChartFactory {

	public abstract StackPane createChart(Set<Sitting> sittings);
}
