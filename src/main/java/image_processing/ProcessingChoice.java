package image_processing;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProcessingChoice extends VBox {
	private Label label;
	private ChoiceBox<String> operationType = new ChoiceBox<String>();
	private List<String> operationTypes = new ArrayList<String>();
	private Button executeOperationBtn = new Button("Wykonaj");
	private HBox operationChoice = new HBox();

	public ProcessingChoice(String labelText) {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10.0d);
		// Label
		label = new Label(labelText);
		label.getStyleClass().add("welcome-label");

		// ChoiceBox
		operationTypes.add("Negatyw");
		operationType.getItems().addAll(operationTypes);
		operationType.setValue(null);
		operationType.getStyleClass().add("operation-choice-box");

		// Button
		executeOperationBtn.disableProperty().bind(App.BtnsDisabled);
		executeOperationBtn.setOnAction(event -> {
			if (operationType.getValue() == null) {
				Toast.show((Stage) App.scene.getWindow(), "Nie wybrano operacji do wykonania", 2000);
			}
			if (operationType.getValue() == "Negatyw") {
				Image rightImg = MainBody.rightImg;
				WritableImage negativeImage = new WritableImage((int) rightImg.getWidth(), (int) rightImg.getHeight());
				PixelWriter pw = negativeImage.getPixelWriter();
				PixelReader pr = rightImg.getPixelReader();
				for (int x = 0; x < rightImg.getHeight(); x++) {
					for (int y = 0; y < rightImg.getWidth(); y++) {
						pw.setColor(x, y, pr.getColor(x, y).invert());
					}
				}
				MainBody.rightImg = negativeImage;
				MainBody.rightIV.setImage(MainBody.rightImg);
				App.ProcessedImage.setValue(true);
			}
			App.ProcessedImage.setValue(true);
		});

		// HBox for ChoiceBox and Button
		operationChoice.getChildren().addAll(operationType, executeOperationBtn);
		operationChoice.setAlignment(this.getAlignment());
		operationChoice.setSpacing(10.0d);

		this.getChildren().add(label);
		this.getChildren().add(operationChoice);
	}
}
