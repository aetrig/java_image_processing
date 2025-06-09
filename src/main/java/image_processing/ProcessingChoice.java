package image_processing;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
		operationTypes.add("Progowanie");
		operationTypes.add("Konturowanie");
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
				try {
					Image rightImg = MainBody.rightImg;
					WritableImage negativeImage = new WritableImage((int) rightImg.getWidth(),
							(int) rightImg.getHeight());
					NegativeThreaded[] threads = new NegativeThreaded[4];
					for (int i = 0; i < 4; i++) {
						threads[i] = new NegativeThreaded(i, rightImg, negativeImage);
					}
					for (int i = 0; i < 4; i++) {
						threads[i].start();
					}
					for (int i = 0; i < 4; i++) {
						threads[i].join();
					}
					MainBody.rightImg = negativeImage;
					MainBody.rightIV.setImage(MainBody.rightImg);
					App.ProcessedImage.setValue(true);
					Toast.show((Stage) App.scene.getWindow(), "Negatyw został wygenerowany pomyślnie!", 2000);
				} catch (Exception e) {
					Toast.show((Stage) App.scene.getWindow(), "Nie udało się wygenerować negatywu.", 2000);
				}
			}
			if (operationType.getValue() == "Progowanie") {
				ThresholdingModal.show();
			}
			if (operationType.getValue() == "Konturowanie") {
				try {
					Image rightImg = MainBody.rightImg;
					WritableImage contourImage = new WritableImage((int) rightImg.getWidth(),
							(int) rightImg.getHeight());

					ContourThreaded[] threads = new ContourThreaded[4];
					for (int i = 0; i < 4; i++) {
						threads[i] = new ContourThreaded(i, rightImg, contourImage);
					}
					for (int i = 0; i < 4; i++) {
						threads[i].start();
					}
					for (int i = 0; i < 4; i++) {
						threads[i].join();
					}

					MainBody.rightImg = contourImage;
					MainBody.rightIV.setImage(MainBody.rightImg);
					App.ProcessedImage.setValue(true);
					Toast.show((Stage) App.scene.getWindow(), "Konturowanie zostało przeprowadzone pomyślnie!", 2000);
				} catch (Exception e) {
					System.out.println(e);
					Toast.show((Stage) App.scene.getWindow(), "Nie udało się wykonać konturowania.", 2000);
				}
			}
		});

		// HBox for ChoiceBox and Button
		operationChoice.getChildren().addAll(operationType, executeOperationBtn);
		operationChoice.setAlignment(this.getAlignment());
		operationChoice.setSpacing(10.0d);

		this.getChildren().add(label);
		this.getChildren().add(operationChoice);
	}
}
