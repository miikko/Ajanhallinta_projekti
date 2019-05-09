package application;

import java.util.Set;

import controllers.HistoryController;
import controllers.LanguageUtil;
import database.Sitting;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Container implementation that contains different types of charts and a
 * dropdown menu for choosing between them.
 * 
 * @author miikk
 *
 */
class ChartContainer implements Container {
	private BorderPane content;
	private HistoryController hisController;
	private Set<Sitting> data;
	private ComboBox<String> comboBox;
	private StackPane pieChart;
	private StackPane barChart;
	private String sDateStr;
	private String eDateStr;

	public ChartContainer(HistoryController hisController) {
		this.hisController = hisController;
		data = hisController.getData();
		sDateStr = hisController.getStartDate().toString();
		eDateStr = hisController.getEndDate().toString();
		create();
	}

	/**
	 * Creates the content inside the container.<br>
	 * This method should only be called once, during object initialization.
	 */
	private void create() {
		content = new BorderPane();
		ObservableList<String> chartTypes = FXCollections.observableArrayList();
		pieChart = PieChartFactory.getInstance().createChart(data, sDateStr, eDateStr);
		barChart = BarChartFactory.getInstance().createChart(data, sDateStr, eDateStr);
		chartTypes.add(LanguageUtil.translate("Pie chart"));
		chartTypes.add(LanguageUtil.translate("Bar chart"));
		comboBox = new ComboBox<>(chartTypes);
		comboBox.setPromptText(LanguageUtil.translate("Select chart type"));
		comboBox.valueProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					if (newValue.equals(LanguageUtil.translate("Pie chart"))) {
						content.setCenter(pieChart);
					} else if (newValue.equals(LanguageUtil.translate("Bar chart"))) {
						content.setCenter(barChart);
					}

				});
		comboBox.setValue(LanguageUtil.translate("Pie chart"));
		content.setTop(comboBox);
	}

	@Override
	public void refresh() {
		data = hisController.getData();
		sDateStr = hisController.getStartDate().toString();
		eDateStr = hisController.getEndDate().toString();
		pieChart = PieChartFactory.getInstance().createChart(data, sDateStr, eDateStr);
		barChart = BarChartFactory.getInstance().createChart(data, sDateStr, eDateStr);
		comboBox.setValue(LanguageUtil.translate("Pie chart"));
		content.setCenter(pieChart);
	}

	@Override
	public Node getContent() {
		return content;
	}

}
