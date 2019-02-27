package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Charts {

	public StackPane getPieChart() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("YouTube", 13),
				new PieChart.Data("Twitch", 25), new PieChart.Data("Netflix", 10), new PieChart.Data("ViaPlay", 22),
				new PieChart.Data("Ruutu", 30));
		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Used time on different applications");
		final Label caption = new Label("");
		caption.setTextFill(Color.BLACK);
		caption.setStyle("-fx-font: 20 arial;");
		for (final PieChart.Data data : chart.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					caption.setTranslateX(e.getSceneX() - caption.getLayoutX() - 10);
					caption.setTranslateY(e.getSceneY() - caption.getLayoutY() - 10);
					caption.setText(data.getPieValue() + "%");
				}
			});
		}
		chart.setLabelLineLength(10);
		chart.setLegendSide(Side.LEFT);
		StackPane sPane = new StackPane();
		sPane.getChildren().addAll(chart, caption);
		return sPane;
	}
}