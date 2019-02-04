package Models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Stopwatch{
	private VBox vBox;
	private HBox hBox;
	private Button sButton, rButton;
	private Text timerText;
	private Timeline timeline;
	private int  hrs = 0, mins = 0, secs = 0, millis = 0;
	private boolean sos = true;
	
	

	void change(Text text) {
		if(millis == 1000) {
			secs++;
			millis = 0;
		}
		if(secs == 60) {
			mins++;
			secs = 0;
		}
		if(mins == 60) {
			hrs++;
			mins = 0;
		}
		text.setText((((mins/10) == 0) ? "0" : "") + mins + ":"
		 + (((secs/10) == 0) ? "0" : "") + secs + ":" 
			+ (((millis/10) == 0) ? "00" : (((millis/100) == 0) ? "0" : "")) + millis++);
    }
	
	public VBox getVBox() {
		timerText = new Text("00:00:000");
		timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
            	change(timerText);
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);
		sButton = new Button("Start");
		sButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(sos) {
            		timeline.play();
            		sos = false;
            		sButton.setText("Stop");
            	} else {
            		timeline.pause();
            		sos = true;
            		sButton.setText("Start");
            	}
            }
        });
		rButton = new Button("Reset");
		rButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	mins = 0;
            	secs = 0;
            	millis = 0;
            	timeline.pause();
            	timerText.setText("00:00:000");
            	if(!sos) {
            		sos = true;
            		sButton.setText("Start");
            	}
            }
        });
		timerText.setId("timerNum");

		hBox = new HBox(30);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(sButton, rButton);
		vBox = new VBox(30);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(timerText, hBox);
		return vBox;
	}
}
