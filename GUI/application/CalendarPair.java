package application;

import java.time.LocalDate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class CalendarPair extends HBox {
	private VBox sCalendar;
	private VBox eCalendar;
	private DatePicker sDatePicker;
	private DatePicker eDatePicker;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate currDate;
	private final Insets MARGIN = new Insets(5, 5, 5, 5);
	
	public CalendarPair() {
		this.setAlignment(Pos.CENTER);
		currDate = LocalDate.now();
		createStartCalendar();
		createEndCalendar();
		this.getChildren().addAll(sCalendar, eCalendar);
	}
	
	private void createStartCalendar() {
		sCalendar = new VBox(10);
		sCalendar.setAlignment(Pos.CENTER);
		Label startInfoLbl = new Label("Select starting date");
		sDatePicker = new DatePicker();
		sDatePicker.setValue(currDate);
		startDate = currDate;
		// Prevents users from clicking dates that are in the future. Does not prevent
		// users from typing invalid dates.
		sDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isAfter(currDate) || (endDate != null && item.isAfter(endDate))) {
							setDisable(true);
						}
					}
				};
			}
		});
		sDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldDate, LocalDate newDate) {
				// Correct selection if user types an invalid date
				if (endDate != null && newDate.isAfter(endDate)) {
					sDatePicker.setValue(endDate);
				} else if (newDate.isAfter(currDate)) {
					sDatePicker.setValue(currDate);
				} else {
					startDate = newDate;
					updateEndCalendar();
				}
			}
		});
		sCalendar.getChildren().addAll(startInfoLbl, sDatePicker);
		HBox.setMargin(sCalendar, MARGIN);
	}
	
	private void createEndCalendar() {
		eCalendar = new VBox(10);
		eCalendar.setAlignment(Pos.CENTER);
		Label endInfoLbl = new Label("Select end date");
		eDatePicker = new DatePicker();
		eDatePicker.setValue(currDate);
		endDate = currDate;
		eDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isAfter(currDate) || (startDate != null && item.isBefore(startDate))) {
							setDisable(true);
						}

					}
				};
			}
		});
		eDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldDate, LocalDate newDate) {
				// Correct selection if user types an invalid date
				if (newDate.isAfter(currDate) || (startDate != null && newDate.isBefore(startDate))) {
					eDatePicker.setValue(currDate);
				} else {
					endDate = newDate;
					updateStartCalendar();
				}
			}
		});
		eCalendar.getChildren().addAll(endInfoLbl, eDatePicker);
		HBox.setMargin(eCalendar, MARGIN);
	}

	private void updateStartCalendar() {
		sDatePicker.getDayCellFactory().call(sDatePicker);
	}

	private void updateEndCalendar() {
		eDatePicker.getDayCellFactory().call(eDatePicker);
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
}
