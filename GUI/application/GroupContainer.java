package application;

import java.util.List;

import controllers.ContainerController;
import controllers.GUI_Controller;
import controllers.HistoryController;
import controllers.LanguageUtil;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * A Container-Class for creating, removing and viewing UserGroups.<br>
 * This class extends the VBox-class.
 * 
 * @author miikk
 *
 */
class GroupContainer implements Container {
	private VBox content;
	private GUI_Controller guiController;
	private ContainerController conController;
	private HistoryController hisController;
	private HBox startContent;
	private VBox groupCreationContent;
	private ObservableList<String> comboBoxItems;
	private UserGroup newUserGroup;
	private UserGroup selectedUserGroup;
	private List<UserGroup> userGroups;
	private final Insets MARGIN = new Insets(5, 5, 5, 5);

	public GroupContainer(GUI_Controller guiController, ContainerController conController,
			HistoryController hisController) {
		this.guiController = guiController;
		this.conController = conController;
		this.hisController = hisController;
		create();
		displayContent(startContent);
	}

	private void create() {
		content = new VBox();
		content.setAlignment(Pos.CENTER);
		createStartContent();
		createGroupCreationContent();
	}

	/**
	 * Creates a HBox that contains buttons for creating groups, removing groups and
	 * for viewing the selected group's history.<br>
	 * Group selection is done by using a dropdown menu which shows names of owned
	 * groups.
	 */
	private void createStartContent() {
		startContent = new HBox();
		startContent.setId("groupStart");
		startContent.setAlignment(Pos.CENTER);
		Button removeGroupBtn = new Button(LanguageUtil.translate("Remove group"));
		removeGroupBtn.setId("removeGroup");
		removeGroupBtn.setDisable(true);
		Button groupStatsBtn = new Button(LanguageUtil.translate("Group history"));
		groupStatsBtn.setId("groupStat");
		groupStatsBtn.setDisable(true);
		Button createNewGroupBtn = new Button(LanguageUtil.translate("Create new group"));
		createNewGroupBtn.setId("newGroup");
		VBox btnContainer = new VBox();
		btnContainer.getChildren().addAll(createNewGroupBtn, removeGroupBtn, groupStatsBtn);
		for (Node child : btnContainer.getChildren()) {
			VBox.setMargin(child, MARGIN);
		}
		userGroups = guiController.getUserGroups();
		comboBoxItems = FXCollections.observableArrayList();
		for (UserGroup group : userGroups) {
			comboBoxItems.add(group.getGroupName());
		}
		final ComboBox<String> comboBox = new ComboBox<>(comboBoxItems);
		comboBox.setPromptText(LanguageUtil.translate("Select group"));
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					for (UserGroup group : userGroups) {
						if (newValue.equals(group.getGroupName())) {
							selectedUserGroup = group;
							break;
						}
					}
				}
				if (selectedUserGroup == null) {
					removeGroupBtn.setDisable(true);
					groupStatsBtn.setDisable(true);
				} else {
					removeGroupBtn.setDisable(false);
					groupStatsBtn.setDisable(false);
				}
			}
		});
		createNewGroupBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				newUserGroup = new UserGroup(guiController.getUserId());
				displayContent(groupCreationContent);
			}
		});
		removeGroupBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				guiController.removeUserGroup(selectedUserGroup);
				refresh();
			}
		});
		groupStatsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				hisController.setDataSource(selectedUserGroup.getUserIds());
				conController.display("Calendars", true);
			}
		});
		startContent.getChildren().addAll(comboBox, btnContainer);
		for (Node child : startContent.getChildren()) {
			HBox.setMargin(child, MARGIN);
		}
	}

	/**
	 * Creates a VBox that contains text fields meant for specifying the group name
	 * and user ids. Also has buttons for adding users to group and creating the
	 * group<br>
	 * 
	 */
	private void createGroupCreationContent() {
		groupCreationContent = new VBox();
		groupCreationContent.setAlignment(Pos.CENTER);
		Label infoLbl = new Label("");
		groupCreationContent.getChildren().add(infoLbl);
		Button confirmBtn = new Button(LanguageUtil.translate("Create"));
		// Button is disabled while there are no group members
		confirmBtn.setDisable(true);
		TextField nameTextField = new TextField();
		nameTextField.setPromptText(LanguageUtil.translate("Group name"));
		TextField userIdTextField = new TextField();
		userIdTextField.setPromptText(LanguageUtil.translate("User Id"));
		Button addBtn = new Button(LanguageUtil.translate("Add user"));
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String userIdStr = userIdTextField.getText();
				if (guiController.addUserToGroup(newUserGroup, userIdStr)) {
					infoLbl.setText(LanguageUtil.translate("User added"));
					groupCreationContent.getChildren().add(1, createUserTab(userIdStr));
					confirmBtn.setDisable(false);
					userIdTextField.clear();
				} else {
					infoLbl.setText(LanguageUtil.translate("The Id you entered is not valid"));
				}
			}
		});
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				newUserGroup.setGroupName(nameTextField.getText());
				if (guiController.saveUserGroup(newUserGroup)) {
					refresh();
				} else {
					infoLbl.setText(LanguageUtil.translate(
							"Failed to create a new group, check that there is atleast one other member and that the name is unique"));
				}
			}
		});
		groupCreationContent.getChildren().addAll(nameTextField, userIdTextField, addBtn, confirmBtn);
		for (Node node : groupCreationContent.getChildren()) {
			VBox.setMargin(node, MARGIN);
		}
	}

	/**
	 * Creates a HBox that contains the specified user id and a remove button.
	 * 
	 * @param userIdStr The users id in String form
	 * @return a HBox with the user id and a button for removing the user from the
	 *         unfinished group
	 */
	private HBox createUserTab(String userIdStr) {
		HBox userTab = new HBox();
		Label idLbl = new Label(userIdStr);
		Button removeBtn = new Button(LanguageUtil.translate("Remove"));
		removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (guiController.removeUserFromGroup(newUserGroup, userIdStr)) {
					groupCreationContent.getChildren().remove(userTab);
				}
			}
		});
		userTab.getChildren().addAll(idLbl, removeBtn);
		return userTab;
	}

	/**
	 * Changes the current container content to the content given as a parameter.
	 * 
	 * @param content a Pane that this container is going to display
	 */
	private void displayContent(Pane content) {
		this.content.getChildren().setAll(content);
	}

	/**
	 * Resets the selections and updates the UserGroup names in startContents
	 * dropdown menu.<br>
	 * Then displays the startContent
	 */
	@Override
	public void refresh() {
		newUserGroup = null;
		selectedUserGroup = null;
		comboBoxItems.clear();
		userGroups = guiController.getUserGroups();
		for (UserGroup group : userGroups) {
			comboBoxItems.add(group.getGroupName());
		}
		displayContent(startContent);
	}

	@Override
	public Node getContent() {
		return content;
	}
}
