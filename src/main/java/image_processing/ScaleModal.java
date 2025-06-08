package image_processing;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;

public class ScaleModal {
	private static Stage modalStage = new Stage(StageStyle.UNDECORATED);

	public static void show() {
		Platform.runLater(() -> {
			double originalWidth = MainBody.rightImg.getWidth();
			double originalHeight = MainBody.rightImg.getHeight();
			modalStage.setResizable(false);
			modalStage.setAlwaysOnTop(true);
			TextField widthInput = new TextField();
			Label widthWarning = new Label("");
			Label heightWarning = new Label("");
			TextField heightInput = new TextField();

			widthInput.addEventFilter(KeyEvent.KEY_TYPED, event -> {
				if (!event.getCharacter().matches("[0-9]")) {
					event.consume();
				} else if (widthInput.getText() != ""
						&& Integer.parseInt(widthInput.getText()) * 10
								+ Integer.parseInt(event.getCharacter()) > 3000) {
					event.consume();
				}
			});
			heightInput.addEventFilter(KeyEvent.KEY_TYPED, event -> {
				if (!event.getCharacter().matches("[0-9]")) {
					event.consume();
				} else if (heightInput.getText() != ""
						&& Integer.parseInt(heightInput.getText()) * 10
								+ Integer.parseInt(event.getCharacter()) > 3000) {
					event.consume();
				}
			});

			HBox inputs = new HBox(new VBox(widthInput, widthWarning), new VBox(heightInput, heightWarning));
			inputs.setSpacing(5.0d);
			inputs.setAlignment(Pos.CENTER);
			Button saveBtn = new Button("Zmień rozmiar");
			Button cancelBtn = new Button("Anuluj");
			Button goBackBtn = new Button("Przywróć oryginalny rozmiar");
			cancelBtn.setOnAction(event -> {
				close();
			});
			saveBtn.setOnAction(event -> {
				if (widthInput.getText().isEmpty()) {
					widthWarning.setText("Pole jest wymagane");
				} else {
					widthWarning.setText("");
				}
				if (heightInput.getText().isEmpty()) {
					heightWarning.setText("Pole jest wymagane");
				} else {
					heightWarning.setText("");
				}
				if (!heightInput.getText().isEmpty() && !widthInput.getText().isEmpty()) {
					MainBody.rightImg = new Image(MainBody.rightImg.getUrl(), Double.parseDouble(widthInput.getText()),
							Double.parseDouble(heightInput.getText()), false, true);
					MainBody.rightIV.setImage(MainBody.rightImg);
				}
			});

			goBackBtn.setOnAction(event -> {
				MainBody.rightImg = new Image(MainBody.rightImg.getUrl(), originalWidth,
						originalHeight, false, true);
				MainBody.rightIV.setImage(MainBody.rightImg);
			});

			HBox buttons = new HBox(saveBtn, goBackBtn, cancelBtn);
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
