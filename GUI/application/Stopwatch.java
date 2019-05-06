package application;

import controllers.GUI_Controller;
import controllers.LanguageUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Creates a Stopwatch that can be used to start a Sitting and track its elapsed time.<br>
 * A Sitting can be started by pressing the start recording button.
 * 
 * @author miikk & MrJoXuX
 *
 */
public class Stopwatch extends VBox {
	private int hrs = 0, mins = 0, secs = 0, millis = 0;
	private boolean recording;
	private GUI_Controller controller;

	public Stopwatch(GUI_Controller controller) {
		this.controller = controller;
		create();
	}

	private void create() {
		Text timerText = new Text("00:00:00");
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				change(timerText);
				millis++;
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);
		Button startButton = new Button(LanguageUtil.translate("Start"));
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (recording) {
					timeline.pause();
					controller.stopRecording();
					hrs = 0;
					mins = 0;
					secs = 0;
					millis = 0;
					timerText.setText("00:00:00");
					startButton.setText(LanguageUtil.translate("Start"));
					recording = false;
				} else {
					controller.startRecording();
					recording = true;
					timeline.play();
					startButton.setText(LanguageUtil.translate("Stop"));
				}
			}
		});
		timerText.setId("timerNum");
		HBox swBtnContainer = new HBox(30);
		swBtnContainer.setAlignment(Pos.CENTER);
		swBtnContainer.getChildren().addAll(startButton);
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(timerText, swBtnContainer);
	}

	private void change(Text text) {
		if (millis == 1000) {
			secs++;
			millis = 0;
		}
		if (secs == 60) {
			mins++;
			secs = 0;
		}
		if (mins == 60) {
			hrs++;
			mins = 0;
		}
		text.setText((((hrs / 10) == 0) ? "0" : "") + hrs + ":" + (((mins / 10) == 0) ? "0" : "") + mins + ":"
				+ (((secs / 10) == 0) ? "0" : "") + secs);
	}
}
