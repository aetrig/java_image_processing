package image_processing;

import javafx.scene.layout.AnchorPane;

public class MainScreen extends AnchorPane {
	private Header header;
	private Footer footer;
	private MainBody mainBody;

	public MainScreen(Header head, Footer foot, MainBody body) {
		header = head;
		footer = foot;
		mainBody = body;

		this.getChildren().addAll(header, footer, mainBody);
		AnchorPane.setTopAnchor(header, 0.0d);
		AnchorPane.setLeftAnchor(header, 0.0d);

		AnchorPane.setTopAnchor(mainBody, 0.0d);
		AnchorPane.setLeftAnchor(mainBody, 0.0d);
		AnchorPane.setRightAnchor(mainBody, 0.0d);
		AnchorPane.setBottomAnchor(mainBody, 0.0d);

		AnchorPane.setBottomAnchor(footer, 0.0d);
		AnchorPane.setRightAnchor(footer, 0.0d);
	}
}
