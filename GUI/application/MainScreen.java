package application;

import controllers.ContainerController;
import controllers.GUI_Controller;
import controllers.HistoryController;
import controllers.LanguageUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Creates the main screen that the user sees after logging in.<br>
 * Calls element builder functions and sets the contents to default.<br>
 * Additionally adds the main screen to controllers screen pool so it can be
 * chosen later.
 * 
 * @author miikk & MrJoXuX & JP
 */
public class MainScreen extends BorderPane implements Screen {
	private VBox navBar;
	private MenuButton optionMenu;
	private AccountContainer accountContainer;
	private HomeContainer homeContainer;
	private CalendarPairContainer calPairContainer;
	private ChartContainer chartContainer;
	private GroupContainer groupContainer;
	private RestrictionContainer restrictContainer;
	private GUI_Controller controller;
	private final ContainerController containerController = new ContainerController(this);
	private final HistoryController hisController = new HistoryController();

	public MainScreen(GUI_Controller controller) {
		this.controller = controller;
		create();
		controller.addScreen("Main", this);
	}

	private void create() {
		homeContainer = new HomeContainer(controller);
		containerController.addContainer("Home", homeContainer);
		calPairContainer = new CalendarPairContainer(containerController, hisController);
		containerController.addContainer("Calendars", calPairContainer);
		chartContainer = new ChartContainer(hisController);
		containerController.addContainer("Charts", chartContainer);
		groupContainer = new GroupContainer(controller, containerController, hisController);
		containerController.addContainer("Group", groupContainer);
		restrictContainer = new RestrictionContainer(controller);
		containerController.addContainer("Restrict", restrictContainer);
		accountContainer = new AccountContainer(controller);
		containerController.addContainer("Account", accountContainer);
		createNavBar();
		createOptionMenu();
		this.setLeft(navBar);
		this.setCenter(homeContainer.getContent());
		this.setRight(optionMenu);
	}

	@Override
	public void display(Container container) {
		this.setCenter(container.getContent());
	}

	/**
	 * Changes contents to container's content after refreshing the container.
	 * 
	 * @param container
	 */
	private void updateCenter(Container container) {
		container.refresh();
		this.setCenter(container.getContent());
	}

	/**
	 * Creates a MenuButton for various options such as exiting the application and
	 * logging out.<br>
	 * On press, the controller is called to handle the action.
	 */
	private void createOptionMenu() {
		optionMenu = new MenuButton(LanguageUtil.translate("Options"));
		optionMenu.setId("optionMenu");

		MenuItem accountItem = new MenuItem(LanguageUtil.translate("Account"));
		accountItem.setId("accountItem");
		accountItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateCenter(accountContainer);
			}
		});
		MenuItem logoutItem = new MenuItem(LanguageUtil.translate("Logout"));
		logoutItem.setId("logoutItem");
		logoutItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.handleLogout();
				controller.activateScreen("Login");
			}
		});
		MenuItem quitItem = new MenuItem(LanguageUtil.translate("Quit"));
		quitItem.setId("quitItem");
		quitItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.handleExit();
			}
		});
		optionMenu.getItems().addAll(accountItem, logoutItem, quitItem);
	}

	/**
	 * Initializes the navigation bar that can be seen on the left side of the
	 * screen once a user has logged in.<br>
	 * 
	 */
	private void createNavBar() {
		navBar = new VBox();
		navBar.setId("naviBar");
		Button defaultBtn = new Button(LanguageUtil.translate("Main menu"));
		defaultBtn.setId("mainmenuBtn");
		defaultBtn.setOnAction(createNavBarBtnHandler(homeContainer));
		Button historyBtn = new Button(LanguageUtil.translate("History"));
		historyBtn.setId("historyBtn");
		historyBtn.setOnAction((ActionEvent event) -> {
			hisController.setDataSource(controller.getUserId());
			calPairContainer.refresh();
			updateCenter(calPairContainer);
		});
		Button groupsBtn = new Button(LanguageUtil.translate("Groups"));
		groupsBtn.setId("groupsBtn");
		groupsBtn.setOnAction((ActionEvent event) -> {
			groupContainer.refresh();
			updateCenter(groupContainer);
		});
		Button restrictBtn = new Button(LanguageUtil.translate("Restrictions"));
		restrictBtn.setOnAction((ActionEvent event) -> {
			restrictContainer = new RestrictionContainer(controller);
			updateCenter(restrictContainer);
		});
		navBar.getChildren().addAll(defaultBtn, historyBtn, groupsBtn, restrictBtn);
	}

	private EventHandler<ActionEvent> createNavBarBtnHandler(Container destination) {
		return (ActionEvent event) -> {
			updateCenter(destination);
		};
	}

}
