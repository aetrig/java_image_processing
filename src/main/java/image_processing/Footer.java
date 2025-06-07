package image_processing;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Footer extends HBox {
	private Label label;

	public Footer(String Text) {
		label = new Label(Text);
		label.getStyleClass().add("footer-label");
		label.setAlignment(Pos.BOTTOM_RIGHT);

		this.getChildren().add(label);
		this.setAlignment(Pos.BASELINE_RIGHT);
		this.getStyleClass().add("footer");
	}
}
