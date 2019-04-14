package application;

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
 * Creates a stopwatch that can be used to track the screentime of a certain
 * application.<br>
 * Includes all the buttons and methods for the stopwatch to work independently.
 * 
 * @author miikk & MrJoXuX
 *
 */
class Stopwatch extends VBox {
	private int hrs = 0, mins = 0, secs = 0, millis = 0;
	private boolean pause = true;

	public Stopwatch() {
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
		Button startButton = new Button("Start");
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (pause) {
					timeline.play();
					pause = false;
					startButton.setText("Stop");
				} else {
					timeline.pause();
					pause = true;
					startButton.setText("Start");
				}
			}
		});
		Button resetButton = new Button("Reset");
		resetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mins = 0;
				secs = 0;
				millis = 0;
				timeline.pause();
				timerText.setText("00:00:00");
				if (!pause) {
					pause = true;
					startButton.setText("Start");
				}
			}
		});
		timerText.setId("timerNum");

		HBox swBtnContainer = new HBox(30);
		swBtnContainer.setAlignment(Pos.CENTER);
		swBtnContainer.getChildren().addAll(startButton, resetButton);
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
