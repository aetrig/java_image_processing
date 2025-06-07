package image_processing;

import javafx.scene.control.Label;

import javafx.scene.layout.HBox;

public class Footer extends HBox {
	private Label label;

	public Footer(String Text) {
		label = new Label(Text);
		this.getChildren().add(label);
	}
}
