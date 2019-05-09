package application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import controllers.DateUtil;
import controllers.LanguageUtil;
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
	
	private final String[] WEEKDAYS = DateUtil.getWeekdays();

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
		Set<XYChart.Series<String, Number>> data = formBars(sittings);
		if (data.size() == 0) {
			Label infoLbl = new Label(LanguageUtil.translate("No data to show"));
			barChart.getChildren().add(infoLbl);
			return barChart;
		}
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bChart = new BarChart<String, Number>(xAxis, yAxis);
		bChart.setTitle(LanguageUtil.translate("Used time on different applications from ") + startDateStr + LanguageUtil.translate(" to ") + endDateStr);
		xAxis.setLabel(LanguageUtil.translate("Day of the week"));
		yAxis.setLabel(LanguageUtil.translate("Hours"));
		bChart.getData().addAll(data);
		barChart.getChildren().addAll(bChart);
		return barChart;
	}
	
	/**
	 * Creates bar charts based on the information recorded into the database.
	 * 
	 * @param sittings
	 * @return the information about the program times in charts
	 */
	
	private Set<XYChart.Series<String, Number>> formBars(Set<Sitting> sittings) {
		Set<XYChart.Series<String, Number>> programs = new HashSet<>();
		HashMap<String, Set<WindowTime>> nameGroupedWts = groupWtsByProgName(sittings);
		HashMap<String, Set<WindowTime>> dayGroupedWts = groupWtsByWeekDay(sittings);
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
	
	/**
	 * Sets the bar charts program data to the data found in the database. <br>
	 * Inserts different program names to the label portion
	 * 
	 * @param sittings
	 * @return names of the programs used
	 */
	
	private HashMap<String, Set<WindowTime>> groupWtsByProgName(Set<Sitting> sittings) {
		HashMap<String, Set<WindowTime>> nameGroupedWts = new HashMap<>();
		for (Sitting sitting : sittings) {
			Set<WindowTime> wts = sitting.getWindowTimes();
			for (WindowTime wt : wts) {
				String progName = wt.getProgramName();
				if (!nameGroupedWts.containsKey(progName)) {
					nameGroupedWts.put(progName, new HashSet<WindowTime>());
				}
				nameGroupedWts.get(progName).add(wt);
			}
		}
		return nameGroupedWts;
	}
	
	/**
	 * Sets the window times to certain weekdays as recorded in the database.
	 * 
	 * @param sittings
	 * @return  window times on different weekdays
	 */
	
	private HashMap<String, Set<WindowTime>> groupWtsByWeekDay(Set<Sitting> sittings) {
		HashMap<String, Set<WindowTime>> dayGroupedWts = new HashMap<>();
		for (String weekDay : WEEKDAYS) {
			dayGroupedWts.put(weekDay, new HashSet<WindowTime>());
		}
		for (Sitting sitting : sittings) {
			Set<WindowTime> wts = sitting.getWindowTimes();
			LocalDateTime startDate = DateUtil.stringToDateTime(sitting.getStart_date());
			String weekDay = DateUtil.weekdayUtil(startDate);
			dayGroupedWts.get(weekDay).addAll(wts);
		}
		return dayGroupedWts;
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
