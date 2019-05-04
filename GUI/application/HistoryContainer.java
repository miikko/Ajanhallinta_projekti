package application;

import java.time.LocalDate;
import java.util.Set;

import controllers.GUI_Controller;
import controllers.LanguageUtil;
import database.Sitting;
import database.UserGroup;
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

class HistoryContainer extends VBox {
	private CalendarPair calendarPair;
	private Button confirmBtn;
	private GUI_Controller controller;
	private BorderPane chartContainer;
	private UserGroup userGroup;

	public HistoryContainer(GUI_Controller controller) {
		this.controller = controller;
		this.setAlignment(Pos.CENTER);
		create();
	}
	
	public HistoryContainer(GUI_Controller controller, UserGroup userGroup) {
		this.controller = controller;
		this.userGroup = userGroup;
		this.setAlignment(Pos.CENTER);
		create();
	}

	private void create() {
		calendarPair = new CalendarPair();
		calendarPair.setId("calPair");
		confirmBtn = new Button(LanguageUtil.translate("Confirm"));
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
		Set<Sitting> sittings = null;
		if (userGroup == null) {
			sittings = controller.getSittings(startDate, endDate);
		} else {
			sittings = controller.getGroupSittings(startDate, endDate, userGroup);
		}
		StackPane pieChart = PieChartFactory.getInstance().createChart(sittings, startDate.toString(),
				endDate.toString());
		StackPane barChart = BarChartFactory.getInstance().createChart(sittings, startDate.toString(),
				endDate.toString());
		chartTypes.add(LanguageUtil.translate("Pie chart"));
		chartTypes.add(LanguageUtil.translate("Bar chart"));
		final ComboBox<String> comboBox = new ComboBox<>(chartTypes);
		comboBox.setPromptText(LanguageUtil.translate("Select chart type"));
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals(LanguageUtil.translate("Pie chart"))) {
					chartContainer.setCenter(pieChart);
				} else if (newValue.equals(LanguageUtil.translate("Bar chart"))) {
					chartContainer.setCenter(barChart);
				}
			}
		});
		comboBox.setValue(LanguageUtil.translate("Pie chart"));
		Button returnBtn = new Button(LanguageUtil.translate("Return"));
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
