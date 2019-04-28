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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class GroupContainer extends VBox {
	private GUI_Controller controller;
	private VBox startContent;
	private VBox groupCreationContent;
	private VBox groupInspectionContent;
	private ObservableList<String> comboBoxItems;
	private UserGroup newUserGroup;
	private UserGroup selectedUserGroup;
	private List<UserGroup> userGroups;
	private final Insets MARGIN = new Insets(5, 5, 5, 5);
	
	public GroupContainer(GUI_Controller controller) {
		this.setAlignment(Pos.CENTER);
		this.controller = controller;
		createStartContent();
		createGroupCreationContent();
		createGroupInspectionContent();
		displayStartContent();
	}
	
	private void createStartContent() {
		startContent = new VBox();
		startContent.setAlignment(Pos.CENTER);
		Button inspectGroupBtn = new Button("View group");
		inspectGroupBtn.setDisable(true);
		userGroups = controller.getUserGroups();
		/*
		Label infoLbl = new Label("You don't have any groups");
		if (userGroups.size() > 0) {
			infoLbl.setVisible(false);
			infoLbl.setManaged(false);
		}*/
		comboBoxItems = FXCollections.observableArrayList();
		for (UserGroup group : userGroups) {
			comboBoxItems.add(group.getGroupName());
		}
		final ComboBox<String> comboBox = new ComboBox<>(comboBoxItems);
		comboBox.setPromptText("Select group");
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				for (UserGroup group : userGroups) {
					if (newValue.equals(group.getGroupName())) {
						selectedUserGroup = group;
						break;
					}
				}
				if (selectedUserGroup == null) {
					inspectGroupBtn.setDisable(true);
				} else {
					inspectGroupBtn.setDisable(false);
				}
			}
		});
		Button createNewGroupBtn = new Button("Create new group");
		createNewGroupBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				newUserGroup = new UserGroup(controller.getUserId());
				displayGroupCreationContent();
			}
		});
		inspectGroupBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				displayGroupInspectionContent();
			}
		});
		startContent.getChildren().addAll(/*infoLbl,*/ comboBox, createNewGroupBtn, inspectGroupBtn);
	}
	
	private void createGroupCreationContent() {
		groupCreationContent = new VBox();
		groupCreationContent.setAlignment(Pos.CENTER);
		Label infoLbl = new Label("");
		groupCreationContent.getChildren().add(infoLbl);
		Button confirmBtn = new Button("Create");
		//Button is disabled while there are no group members
		confirmBtn.setDisable(true);
		TextField nameTextField = new TextField();
		nameTextField.setPromptText("Group name");
		TextField userIdTextField = new TextField();
		userIdTextField.setPromptText("User Id");
		Button addBtn = new Button("Add user");
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String userIdStr = userIdTextField.getText();
				if (controller.addUserToGroup(newUserGroup, userIdStr)) {
					infoLbl.setText("User added");
					groupCreationContent.getChildren().add(1, createUserTab(userIdStr));
					confirmBtn.setDisable(false);
					userIdTextField.clear();
				} else {
					infoLbl.setText("The Id you entered is not valid");
				}
			}
		});
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				newUserGroup.setGroupName(nameTextField.getText());
				if (controller.saveUserGroup(newUserGroup)) {
					displayStartContent();
				} else {
					infoLbl.setText("Failed to create a new group, check that there is atleast one other member and that the name is unique");
				}
			}
		});
		groupCreationContent.getChildren().addAll(nameTextField, userIdTextField, addBtn, confirmBtn);
		for (Node node : groupCreationContent.getChildren()) {
			VBox.setMargin(node, MARGIN);
		}
	}
	
	private HBox createUserTab(String userIdStr) {
		HBox userTab = new HBox();
		Label idLbl = new Label(userIdStr);
		Button removeBtn = new Button("Remove");
		removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (controller.removeUserFromGroup(newUserGroup, userIdStr)) {
					groupCreationContent.getChildren().remove(userTab);
				}
			}
		});
		userTab.getChildren().addAll(idLbl, removeBtn);
		return userTab;
	}
	
	
	private void createGroupInspectionContent() {
		groupInspectionContent = new VBox();
		Label infoLbl = new Label("");
		Button removeGroupBtn = new Button("Remove group");
		removeGroupBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (controller.removeUserGroup(selectedUserGroup)) {
					infoLbl.setText("");
					displayStartContent();
				} else {
					infoLbl.setText("Failed to remove group");
				}
			}
		});
		Button statsBtn = new Button("Show group history");
		statsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				groupInspectionContent.getChildren().setAll(new HistoryContainer(controller, selectedUserGroup));
			}
		});
		groupInspectionContent.getChildren().addAll(removeGroupBtn, statsBtn);
	}
	
	private void displayStartContent() {
		newUserGroup = null;
		selectedUserGroup = null;
		comboBoxItems.clear();
		userGroups = controller.getUserGroups();
		for (UserGroup group : userGroups) {
			comboBoxItems.add(group.getGroupName());
		}
		this.getChildren().setAll(startContent);
	}
	
	private void displayGroupCreationContent() {
		this.getChildren().setAll(groupCreationContent);
	}
	
	private void displayGroupInspectionContent() {
		this.getChildren().setAll(groupInspectionContent);
	}
	
	public void refresh() {
		displayStartContent();
	}
}
