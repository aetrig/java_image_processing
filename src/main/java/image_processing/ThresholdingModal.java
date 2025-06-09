package image_processing;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ThresholdingModal {
	private static Stage modalStage = new Stage(StageStyle.UNDECORATED);

	public static void show() {
		Platform.runLater(() -> {
			modalStage.setResizable(false);
			modalStage.setAlwaysOnTop(true);
			TextField thresholdInput = new TextField();

			thresholdInput.addEventFilter(KeyEvent.KEY_TYPED, event -> {
				if (!event.getCharacter().matches("[0-9]")) {
					event.consume();
				} else if (thresholdInput.getText() != ""
						&& Integer.parseInt(thresholdInput.getText()) * 10
								+ Integer.parseInt(event.getCharacter()) > 255) {
					event.consume();
				}
			});

			HBox inputs = new HBox(thresholdInput);
			inputs.setSpacing(5.0d);
			inputs.setAlignment(Pos.CENTER);
			Button saveBtn = new Button("Wykonaj progowanie");
			Button cancelBtn = new Button("Anuluj");
			cancelBtn.setOnAction(event -> {
				close();
			});
			saveBtn.setOnAction(event -> {
				if (!thresholdInput.getText().isEmpty()) {
					try {
						Image rightImg = MainBody.rightImg;
						WritableImage thresholdImage = new WritableImage((int) rightImg.getWidth(),
								(int) rightImg.getHeight());
						PixelWriter pw = thresholdImage.getPixelWriter();
						PixelReader tpr = thresholdImage.getPixelReader();
						PixelReader pr = rightImg.getPixelReader();
						for (int x = 0; x < rightImg.getHeight(); x++) {
							for (int y = 0; y < rightImg.getWidth(); y++) {
								pw.setColor(x, y, pr.getColor(x, y).grayscale());
								if (tpr.getColor(x, y).getRed() * 255 < Double.parseDouble(thresholdInput.getText())) {
									pw.setColor(x, y, Color.BLACK);
								} else {
									pw.setColor(x, y, Color.WHITE);
								}
							}
						}
						MainBody.rightImg = thresholdImage;
						MainBody.rightIV.setImage(MainBody.rightImg);
						App.ProcessedImage.setValue(true);
						Toast.show((Stage) App.scene.getWindow(), "Progowanie zostało przeprowadzone pomyślnie!", 2000);
					} catch (NumberFormatException e) {
						Toast.show((Stage) App.scene.getWindow(), "Nie udało się wykonać progowania.", 2000);
					}
				} else {
					Toast.show((Stage) App.scene.getWindow(), "Nie udało się wykonać progowania.", 2000);
				}
			});

			HBox buttons = new HBox(saveBtn, cancelBtn);
			buttons.setAlignment(Pos.CENTER);
			buttons.setSpacing(20.0d);
			VBox container = new VBox(inputs, buttons);
			container.setAlignment(Pos.CENTER);
			container.setSpacing(20.0d);
			container.setStyle("-fx-background-color: lightslategray;");
			Scene modalScene = new Scene(container, 550, 200);

			modalStage.setScene(modalScene);
			modalStage.show();
		});
	}

	public static void close() {
		if (modalStage.isShowing()) {
			modalStage.close();
		}
	}
}
