package application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import database.Sitting;
import database.WindowTime;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Factory class for creating bar charts that can be used to show the elapsed
 * screen time of a user.<br>
 * Uses the Singleton pattern.
 * 
 * @author miikk & MrJoXuX
 */
class BarChartFactory implements ChartFactory {
	
	private final String[] WEEKDAYS = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

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
		Set<XYChart.Series<String, Number>> data = organizeData(sittings);
		if (data.size() == 0) {
			Label infoLbl = new Label("No data to show");
			barChart.getChildren().add(infoLbl);
			return barChart;
		}
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bChart = new BarChart<String, Number>(xAxis, yAxis);
		bChart.setTitle("Used time on different applications from " + startDateStr + " to " + endDateStr);
		xAxis.setLabel("Day of the week");
		yAxis.setLabel("Hours");
		bChart.getData().addAll(data);
		barChart.getChildren().addAll(bChart);
		return barChart;
	}
	
	private Set<XYChart.Series<String, Number>> organizeData(Set<Sitting> sittings) {
		Set<XYChart.Series<String, Number>> programs = new HashSet<>();
		// Group WindowTimes together based on program name
		HashMap<String, Set<WindowTime>> nameGroupedWts = new HashMap<>();
		HashMap<String, Set<WindowTime>> dayGroupedWts = new HashMap<>();
		for (String weekDay : WEEKDAYS) {
			dayGroupedWts.put(weekDay, new HashSet<WindowTime>());
		}
		for (Sitting sitting : sittings) {
			Set<WindowTime> wts = sitting.getWindowTimes();
			LocalDate startDate = LocalDate.parse(sitting.getStart_date(), DATE_FORMATTER);
			String weekDay = WEEKDAYS[startDate.getDayOfWeek().getValue() - 1];
			dayGroupedWts.get(weekDay).addAll(wts);
			for (WindowTime wt : wts) {
				String progName = wt.getProgramName();
				if (!nameGroupedWts.containsKey(progName)) {
					nameGroupedWts.put(progName, new HashSet<WindowTime>());
				}
				nameGroupedWts.get(progName).add(wt);
			}
		}
		//Create a XYChart.Series for each key
		for (String progName : nameGroupedWts.keySet()) {
			Set<WindowTime> namedWts = nameGroupedWts.get(progName);
			XYChart.Series<String, Number> thisProg = new XYChart.Series<>();
			thisProg.setName(progName);
			for (String weekday : WEEKDAYS) {
				double hours = 0;
				Set<WindowTime> wtsForThisWeekday = dayGroupedWts.get(weekday);
				for (WindowTime wt : wtsForThisWeekday) {
					if (namedWts.contains(wt)) {
						hours += wt.getHours() + ((double) wt.getMinutes() / 60) + ((double) wt.getSeconds() / 3600);
					}
				}
				thisProg.getData().add(new XYChart.Data<String, Number>(weekday, round(hours, 1)));
				
			}
			programs.add(thisProg);
		}
		return programs;
	}

	private double round(double value, int decimals) {
		if (decimals < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd.setScale(decimals, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
