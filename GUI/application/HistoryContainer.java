package application;

import java.time.LocalDate;
import java.util.Set;

import controllers.GUI_Controller;
import database.Sitting;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HistoryContainer extends VBox {
	private CalendarPair calendarPair;
	private Button confirmBtn;
	private GUI_Controller controller;
	private BorderPane chartContainer;

	public HistoryContainer(GUI_Controller controller) {
		this.controller = controller;
		this.setAlignment(Pos.CENTER);
		create();
	}

	private void create() {
		calendarPair = new CalendarPair();
		confirmBtn = new Button("Confirm");
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				createCharts(calendarPair.getStartDate(), calendarPair.getEndDate());
				displayCharts();
			}
		});
		this.getChildren().addAll(calendarPair, confirmBtn);
	}

	private void displayCharts() {
		this.getChildren().setAll(chartContainer);
	}

	private void displayCalendars() {
		this.getChildren().setAll(calendarPair, confirmBtn);
	}

	/**
	 * Calls the chart creation methods and puts the created charts in a BorderPane
	 * container.<br>
	 * The container also contains a dropdown menu for selecting charts and a return
	 * button.
	 * 
	 * @param sittings
	 */
	private void createCharts(LocalDate startDate, LocalDate endDate) {
		ObservableList<String> chartTypes = FXCollections.observableArrayList();
		chartContainer = new BorderPane();
		Set<Sitting> sittings = controller.getSittings(startDate, endDate);
		StackPane pieChart = PieChartFactory.getInstance().createChart(sittings, startDate.toString(),
				endDate.toString());
		StackPane barChart = BarChartFactory.getInstance().createChart(sittings, startDate.toString(),
				endDate.toString());
		chartTypes.add("Pie chart");
		chartTypes.add("Bar chart");
		final ComboBox<String> comboBox = new ComboBox<>(chartTypes);
		comboBox.setPromptText("Select chart type");
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("Pie chart")) {
					chartContainer.setCenter(pieChart);
				} else if (newValue.equals("Bar chart")) {
					chartContainer.setCenter(barChart);
				}
			}
		});
		comboBox.setValue("Pie chart");
		Button returnBtn = new Button("Return");
		returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				displayCalendars();
			}
		});
		chartContainer.setTop(comboBox);
		chartContainer.setBottom(returnBtn);
	}
}
