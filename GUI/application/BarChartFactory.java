package application;

import java.util.Set;

import database.Sitting;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

/**
 * Factory class for creating bar charts that can be used to show the elapsed
 * screen time of a user.<br>
 * Uses the Singleton pattern.
 * 
 * @author miikk & MrJoXuX
 */
class BarChartFactory implements ChartFactory {

	private BarChartFactory() {

	}

	public static BarChartFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static class SingletonHolder {
		private static final BarChartFactory INSTANCE = new BarChartFactory();
	}

	@Override
	public StackPane createChart(Set<Sitting> sittings, String startDateStr, String endDateStr) {
		StackPane barChart = new StackPane();

		final String monday = "Monday";
		final String tuesday = "Tuesday";
		final String wednesday = "Wednesday";
		final String thursday = "Thursday";
		final String friday = "Friday";
		final String saturday = "Saturday";
		final String sunday = "Sunday";

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bChart = new BarChart<String, Number>(xAxis, yAxis);
		bChart.setTitle("Used time on different applications");
		xAxis.setLabel("Day of the week");
		yAxis.setLabel("Hours");

		XYChart.Series<String, Number> platform1 = new XYChart.Series<>();
		platform1.setName("Netflix");
		platform1.getData().add(new XYChart.Data<>(monday, 7.2));
		platform1.getData().add(new XYChart.Data<>(tuesday, 8.7));
		platform1.getData().add(new XYChart.Data<>(wednesday, 3.0));
		platform1.getData().add(new XYChart.Data<>(thursday, 4.4));
		platform1.getData().add(new XYChart.Data<>(friday, 5.2));
		platform1.getData().add(new XYChart.Data<>(saturday, 1.1));
		platform1.getData().add(new XYChart.Data<>(sunday, 1.1));

		XYChart.Series<String, Number> platform2 = new XYChart.Series<>();
		platform2.setName("Twitch");
		platform2.getData().add(new XYChart.Data<>(monday, 2.2));
		platform2.getData().add(new XYChart.Data<>(tuesday, 3.2));
		platform2.getData().add(new XYChart.Data<>(wednesday, 4.8));
		platform2.getData().add(new XYChart.Data<>(thursday, 1.1));
		platform2.getData().add(new XYChart.Data<>(friday, 0.9));
		platform2.getData().add(new XYChart.Data<>(saturday, 7.2));
		platform2.getData().add(new XYChart.Data<>(sunday, 2.2));
		XYChart.Series<String, Number> platform3 = new XYChart.Series<>();
		platform3.setName("YouTube");
		platform3.getData().add(new XYChart.Data<>(monday, 4.4));
		platform3.getData().add(new XYChart.Data<>(tuesday, 3.3));
		platform3.getData().add(new XYChart.Data<>(wednesday, 7.1));
		platform3.getData().add(new XYChart.Data<>(thursday, 4.9));
		platform3.getData().add(new XYChart.Data<>(friday, 9.2));
		platform3.getData().add(new XYChart.Data<>(saturday, 6.8));
		platform3.getData().add(new XYChart.Data<>(sunday, 4.4));

		bChart.getData().addAll(platform1, platform2, platform3);

		barChart.getChildren().addAll(bChart);
		return barChart;
	}

}
