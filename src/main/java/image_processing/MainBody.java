package image_processing;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainBody extends VBox {
	private Label label;

	public MainBody(String labelText) {
		label = new Label(labelText);
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(label);
	}
}
