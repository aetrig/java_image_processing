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
import javafx.scene.paint.Color;
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
					PixelWriter pw = negativeImage.getPixelWriter();
					PixelReader pr = rightImg.getPixelReader();
					for (int x = 0; x < rightImg.getWidth(); x++) {
						for (int y = 0; y < rightImg.getHeight(); y++) {
							pw.setColor(x, y, pr.getColor(x, y).invert());
						}
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
					int[][] convolutionMatrix = { { -1, -1, -1 }, { -1, 8, -1 }, { -1, -1, -1 } };
					Image rightImg = MainBody.rightImg;
					WritableImage contourImage = new WritableImage((int) rightImg.getWidth(),
							(int) rightImg.getHeight());

					PixelWriter pw = contourImage.getPixelWriter();
					PixelReader pr = rightImg.getPixelReader();
					for (int x = 0; x < rightImg.getWidth(); x++) {
						pw.setColor(x, 0, pr.getColor(x, 0).grayscale());
						pw.setColor(x, (int) rightImg.getHeight() - 1,
								pr.getColor(x, (int) rightImg.getHeight() - 1).grayscale());
					}
					for (int y = 0; y < rightImg.getHeight(); y++) {
						pw.setColor(0, y, pr.getColor(0, y).grayscale());
						pw.setColor((int) rightImg.getWidth() - 1, y,
								pr.getColor((int) rightImg.getWidth() - 1, y).grayscale());
					}
					for (int x = 1; x < rightImg.getWidth() - 1; x++) {
						for (int y = 1; y < rightImg.getHeight() - 1; y++) {
							Color[][] colors = new Color[3][3];
							for (int i = 0; i < 3; i++) {
								for (int j = 0; j < 3; j++) {
									colors[i][j] = pr.getColor(x + i - 1, y + j - 1).grayscale();
								}
							}
							int[][] grays = new int[3][3];
							for (int i = 0; i < 3; i++) {
								for (int j = 0; j < 3; j++) {
									grays[i][j] = (int) (colors[i][j].getRed() * 255 * convolutionMatrix[i][j]);
								}
							}
							int Gray = 0;
							for (int i = 0; i < 3; i++) {
								for (int j = 0; j < 3; j++) {
									Gray += grays[i][j];
								}
							}
							Gray = Math.max(Gray, 0);
							Gray = Math.min(Gray, 255);
							pw.setColor(x, y, Color.grayRgb(Gray));
						}
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
