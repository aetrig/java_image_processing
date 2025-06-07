package image_processing;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Header extends HBox {

	private Image image;
	private ImageView imageView;
	private Label label;

	public Header(String ImageFileName, String Text) {
		String imageLocation = "/image_processing/" + ImageFileName;
		image = new Image(getClass().getResourceAsStream(imageLocation), 60.0d, 60.0d, true, true);
		imageView = new ImageView(image);
		label = new Label(Text);
		label.getStyleClass().add("header-label");
		this.setSpacing(5.0d);
		this.getChildren().add(imageView);
		this.getChildren().add(label);
		this.getStyleClass().add("header");
	}
}
