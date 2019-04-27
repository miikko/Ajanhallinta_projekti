package application;

import java.util.List;

import controllers.GUI_Controller;
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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GroupContainer extends VBox {
	private GUI_Controller controller;
	private VBox startContent;
	private VBox groupCreationContent;
	
	public GroupContainer(GUI_Controller controller) {
		this.setAlignment(Pos.CENTER);
		this.controller = controller;
		createStartContent();
		createGroupCreationContent();
		displayStartContent();
	}
	
	private void createStartContent() {
		startContent = new VBox();
		startContent.setAlignment(Pos.CENTER);
		List<UserGroup> userGroups = controller.getUserGroups();
		Label infoLbl = new Label("You don't have any groups");
		if (userGroups.size() > 0) {
			infoLbl.setVisible(false);
			infoLbl.setManaged(false);
		}
		ObservableList<String> comboBoxItems = FXCollections.observableArrayList();
		final ComboBox<String> comboBox = new ComboBox<>(comboBoxItems);
		comboBox.setPromptText("Select group");
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
			}
		});
		Button createNewGroupBtn = new Button("Create new group");
		createNewGroupBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				displayGroupCreationContent();
			}
		});
		startContent.getChildren().addAll(infoLbl, comboBox, createNewGroupBtn);
	}
	
	private void createGroupCreationContent() {
		groupCreationContent = new VBox();
	}
	
	private void displayStartContent() {
		this.getChildren().setAll(startContent);
	}
	
	private void displayGroupCreationContent() {
		this.getChildren().setAll(groupCreationContent);
	}
}
