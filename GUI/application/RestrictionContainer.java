package application;

import controllers.DateUtil;
import controllers.GUI_Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

class RestrictionContainer extends HBox {
	private HBox startContent;
	private BorderPane additionContent;
	private BorderPane editingContent;
	private GUI_Controller controller;
	private final Insets MARGIN = new Insets(5, 5, 5, 5);
	
	public RestrictionContainer(GUI_Controller controller) {
		this.controller = controller;
		this.setAlignment(Pos.CENTER);
		createStartContent();
		createAdditionContent();
		createEditingContent();
		displayContent(startContent);
	}
	
	private void createStartContent() {
		startContent = new HBox();
		startContent.setAlignment(Pos.CENTER);
		final ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setPromptText("Select restricted program");
		Button addButton = new Button("Add new");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				displayContent(additionContent);
			}
			
		});
		Button editButton = new Button("Edit");
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				displayContent(editingContent);
			}
			
		});
		VBox buttonContainer = new VBox();
		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.getChildren().addAll(addButton, editButton);
		for (Node child : buttonContainer.getChildren()) {
			VBox.setMargin(child, MARGIN);
		}
		startContent.getChildren().addAll(comboBox, buttonContainer);
		
	}
	
	private void createAdditionContent() {
		additionContent = new BorderPane();
		final ComboBox<String> progNameComboBox = new ComboBox<>();
		progNameComboBox.setPromptText("Choose program");
		progNameComboBox.setEditable(true);
		final ComboBox<String> weekDayComboBox = createWeekDayComboBox();
		VBox tfContainer = new VBox();
		tfContainer.setAlignment(Pos.CENTER);
		TextField hourTF = new TextField();
		hourTF.setPromptText("Hours");
		TextField minuteTF = new TextField();
		minuteTF.setPromptText("Minutes");
		tfContainer.getChildren().addAll(hourTF, minuteTF);
		for (Node child : tfContainer.getChildren()) {
			VBox.setMargin(child, MARGIN);
		}
		HBox btnContainer = new HBox();
		btnContainer.setAlignment(Pos.CENTER);
		Button addBtn = new Button("Add");
		Button confirmBtn = new Button("Finish");
		btnContainer.getChildren().addAll(addBtn, confirmBtn);
		additionContent.setLeft(progNameComboBox);
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
		hourTF.setPromptText("Hours");
		TextField minuteTF = new TextField();
		minuteTF.setPromptText("Minutes");
		tfContainer.getChildren().addAll(hourTF, minuteTF);
		for (Node child : tfContainer.getChildren()) {
			VBox.setMargin(child, MARGIN);
		}
		Button saveBtn = new Button("Save");
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
}
