package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import database.Sitting;
import database.WindowTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Factory class that creates PieCharts. PieCharts are used to display the
 * relative amount of time the user has spent on each program.<br>
 * Uses the Singleton pattern.
 * 
 * @author miikk & MrJoXuX
 */
class PieChartFactory implements ChartFactory {

	private PieChartFactory() {

	}

	public static PieChartFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static class SingletonHolder {
		private static final PieChartFactory INSTANCE = new PieChartFactory();
	}

	//TODO: Piechart transforms needlessly when application window is resized, Chart title doesn't resize itself. 
	@Override
	public StackPane createChart(Set<Sitting> sittings, String startDateStr, String endDateStr) {
		StackPane pieChart = new StackPane();
		Set<PieChart.Data> slices = formSlices(sittings);
		if (slices.size() == 0) {
			Label infoLabel = new Label("No data to show");
			pieChart.getChildren().add(infoLabel);
			return pieChart;
		}
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(slices);
		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Used time on different applications from " + startDateStr + " to " + endDateStr);
		final Label caption = new Label("");
		caption.setTextFill(Color.BLACK);
		caption.setStyle("-fx-font: 20 arial;");
		for (final PieChart.Data data : chart.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					Bounds pieChartBoundsInScene = pieChart.localToScene(pieChart.getBoundsInLocal());
					double pieChartCenterX = pieChartBoundsInScene.getMaxX() - (pieChart.getWidth() / 2);
					double pieChartCenterY = pieChartBoundsInScene.getMaxY() - (pieChart.getHeight() / 2);
					caption.setTranslateX(e.getSceneX() - pieChartCenterX);
					caption.setTranslateY(e.getSceneY() - pieChartCenterY);
					caption.setText(data.getPieValue() + "%");
				}
			});
		}
		chart.setLabelLineLength(10);
		chart.setLegendSide(Side.LEFT);
		pieChart.getChildren().addAll(chart, caption);
		return pieChart;
	}

	// TODO: Come up with a clever way to round slice shares
	/**
	 * Extracts and groups WindowTimes with the same name from the given
	 * sittings.<br>
	 * Then it calculates the percentage value for each group comparing the total
	 * time in the group to the total time in all of the extracted WindowTimes.<br>
	 * Finally, creates a PieChart.Data-object out of each group which contain the
	 * program name and the calculated value. These are then added to the to be
	 * returned Set.
	 * 
	 * @param sittings
	 * @return a Set of PieChart.Data with each item belonging to a unique program.
	 */
	private Set<PieChart.Data> formSlices(Set<Sitting> sittings) {
		HashMap<String, Long> secsPerProgram = new HashMap<>();
		Set<PieChart.Data> slices = new HashSet<>();
		long totalTimeInSecs = 0;
		for (Sitting sitting : sittings) {
			Set<WindowTime> wts = sitting.getWindowTimes();
			for (WindowTime wt : wts) {
				String pName = wt.getProgramName();
				long secsInThisWindow = wt.getHours() * 3600 + wt.getMinutes() * 60 + wt.getSeconds();
				totalTimeInSecs += secsInThisWindow;
				if (!secsPerProgram.containsKey(pName)) {
					secsPerProgram.put(pName, 0L);
				}
				secsPerProgram.put(pName, secsPerProgram.get(pName) + secsInThisWindow);
			}
		}
		if (totalTimeInSecs > 0) {
			for (String pName : secsPerProgram.keySet()) {
				double share = Math.round(((double) secsPerProgram.get(pName) / totalTimeInSecs) * 100);
				slices.add(new PieChart.Data(pName, share));
			}
		}
		return slices;
	}
}
