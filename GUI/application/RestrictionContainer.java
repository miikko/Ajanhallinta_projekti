package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controllers.DateUtil;
import controllers.GUI_Controller;
import controllers.LanguageUtil;
import database.Restriction;
import database.Sitting;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

class RestrictionContainer extends HBox {
	private HBox startContent;
	private BorderPane progSelectionContent;
	private BorderPane additionContent;
	private BorderPane editingContent;
	private GUI_Controller controller;
	private List<Restriction> restrictions;
	private Set<String> restrictedProgNames;
	private ObservableList<String> obsRestrictedProgNames;
	private ObservableList<String> obsAllProgNames;
	private String editProgName;
	private String newProgName;
	private HashMap<String, String> newRestrictionDaySettings;
	private final Insets MARGIN = new Insets(5, 5, 5, 5);

	public RestrictionContainer(GUI_Controller controller) {
		this.controller = controller;
		this.setAlignment(Pos.CENTER);
		createStartContent();
		createAdditionContent();
		createProgSelectionContent();
		createEditingContent();
		displayContent(startContent);
	}

	private void createStartContent() {
		startContent = new HBox();
		startContent.setAlignment(Pos.CENTER);
		Button editBtn = new Button(LanguageUtil.translate("Edit"));
		editBtn.setDisable(true);
		editBtn.setOnAction((ActionEvent event) -> {
			displayContent(editingContent);
		});
		Button addBtn = new Button(LanguageUtil.translate("Add new"));
		addBtn.setOnAction((ActionEvent event) -> {
			displayContent(progSelectionContent);
		});
		VBox buttonContainer = new VBox();
		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.getChildren().addAll(addBtn, editBtn);
		restrictions = controller.getRestrictions();
		restrictedProgNames = new HashSet<String>();
		for (Restriction r : restrictions) {
			restrictedProgNames.add(r.getProgName());
		}
		obsRestrictedProgNames = FXCollections.observableArrayList(restrictedProgNames);
		final ComboBox<String> progNameComboBox = new ComboBox<>(obsRestrictedProgNames);
		progNameComboBox.setPromptText(LanguageUtil.translate("Select restricted program"));
		progNameComboBox.valueProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					editProgName = newValue;
					if (editProgName == null) {
						editBtn.setDisable(true);
					} else {
						editBtn.setDisable(false);
					}
				});
		for (Node child : buttonContainer.getChildren()) {
			VBox.setMargin(child, MARGIN);
		}
		startContent.getChildren().addAll(progNameComboBox, buttonContainer);
	}

	private void createProgSelectionContent() {
		progSelectionContent = new BorderPane();
		Button confirmBtn = new Button(LanguageUtil.translate("Confirm"));
		confirmBtn.setDisable(true);
		confirmBtn.setOnAction((ActionEvent event) -> {
			newRestrictionDaySettings.clear();
			displayContent(additionContent);
		});
		obsAllProgNames = FXCollections.observableArrayList(controller.getAllProgramNames());
		final ComboBox<String> progNameComboBox = new ComboBox<>(obsAllProgNames);
		progNameComboBox.setPromptText(LanguageUtil.translate("Choose program"));
		progNameComboBox.valueProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					newProgName = newValue;
					if (newProgName == null) {
						confirmBtn.setDisable(true);
					} else {
						confirmBtn.setDisable(false);
					}
				});
		// progNameComboBox.setEditable(true);
		progSelectionContent.setCenter(progNameComboBox);
		progSelectionContent.setBottom(confirmBtn);
		for (Node child : progSelectionContent.getChildren()) {
			BorderPane.setAlignment(child, Pos.CENTER);
			BorderPane.setMargin(child, MARGIN);
		}
	}

	private void createAdditionContent() {
		additionContent = new BorderPane();
		Label infoLbl = new Label();
		newRestrictionDaySettings = new HashMap<String, String>();
		final ComboBox<String> weekDayComboBox = createWeekDayComboBox();
		VBox tfContainer = new VBox();
		tfContainer.setAlignment(Pos.CENTER);
		TextField hourTF = new TextField();
		hourTF.setPromptText(LanguageUtil.translate("Hours"));
		TextField minuteTF = new TextField();
		minuteTF.setPromptText(LanguageUtil.translate("Minutes"));
		tfContainer.getChildren().addAll(hourTF, minuteTF);
		for (Node child : tfContainer.getChildren()) {
			VBox.setMargin(child, MARGIN);
		}
		HBox btnContainer = new HBox();
		btnContainer.setAlignment(Pos.CENTER);
		Button addBtn = new Button("Add");
		addBtn.setOnAction((ActionEvent event) -> {
			try {
				int hours = Integer.parseInt(hourTF.getText());
				int minutes = Integer.parseInt(minuteTF.getText());
				if (hours > 23 || hours < 0 || minutes > 59 || minutes < 0) {
					infoLbl.setText(LanguageUtil.translate("Invalid time values"));
				} else {
					String weekDay = weekDayComboBox.getValue();
					if (weekDay == null) {
						infoLbl.setText(LanguageUtil.translate("Please select a weekday"));
					} else {
						newRestrictionDaySettings.put(weekDayComboBox.getValue(), hours + ":" + minutes);
					}
				}
			} catch (NumberFormatException e) {
				infoLbl.setText(LanguageUtil.translate("Invalid time values"));
			}

		});
		Button confirmBtn = new Button(LanguageUtil.translate("Finish"));
		btnContainer.getChildren().addAll(addBtn, confirmBtn);
		additionContent.setTop(infoLbl);
		additionContent.setCenter(weekDayComboBox);
		additionContent.setRight(tfContainer);
		additionContent.setBottom(btnContainer);
		for (Node child : additionContent.getChildren()) {
			BorderPane.setAlignment(child, Pos.CENTER);
		}
	}

	private void createEditingContent() {
		editingContent = new BorderPane();
		final ComboBox<String> weekDayComboBox = createWeekDayComboBox();
		VBox tfContainer = new VBox();
		tfContainer.setAlignment(Pos.CENTER);
		TextField hourTF = new TextField();
		hourTF.setPromptText(LanguageUtil.translate("Hours"));
		TextField minuteTF = new TextField();
		minuteTF.setPromptText(LanguageUtil.translate("Minutes"));
		tfContainer.getChildren().addAll(hourTF, minuteTF);
		for (Node child : tfContainer.getChildren()) {
			VBox.setMargin(child, MARGIN);
		}
		Button saveBtn = new Button(LanguageUtil.translate("Save"));
		editingContent.setCenter(weekDayComboBox);
		editingContent.setRight(tfContainer);
		editingContent.setBottom(saveBtn);
		for (Node child : editingContent.getChildren()) {
			BorderPane.setAlignment(child, Pos.CENTER);
		}
	}

	private ComboBox<String> createWeekDayComboBox() {
		ComboBox<String> weekDayComboBox = new ComboBox<>();
		weekDayComboBox.setPromptText("Select weekday");
		weekDayComboBox.getItems().addAll(DateUtil.getWeekdays());
		return weekDayComboBox;
	}

	private void displayContent(Pane content) {
		this.getChildren().setAll(content);
	}

	public void refresh() {
		newProgName = null;
		editProgName = null;
		newRestrictionDaySettings.clear();
		restrictions = controller.getRestrictions();
		restrictedProgNames.clear();
		for (Restriction r : restrictions) {
			restrictedProgNames.add(r.getProgName());
		}
		obsRestrictedProgNames.setAll(restrictedProgNames);
		obsAllProgNames.setAll(controller.getAllProgramNames());
		displayContent(startContent);
	}
}
