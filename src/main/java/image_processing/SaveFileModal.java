package image_processing;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;

public class SaveFileModal {
	private static Stage modalStage = new Stage(StageStyle.UNDECORATED);

	public static void show() {
		Platform.runLater(() -> {
			modalStage.setResizable(false);
			modalStage.setAlwaysOnTop(true);
			TextField fileNameInput = new TextField();
			Label tooLittleChars = new Label();

			fileNameInput.addEventFilter(KeyEvent.KEY_TYPED, event -> {
				if (fileNameInput.getLength() >= 100) {
					event.consume();
				}
				if (fileNameInput.getLength() >= 2) {
					tooLittleChars.setText("");
					tooLittleChars.setStyle("-fx-font-size: 15px;");
				}
			});
			Button saveBtn = new Button("Zapisz");
			Button cancelBtn = new Button("Anuluj");
			cancelBtn.setOnAction(event -> {
				close();
			});
			Label warning = new Label();
			if (!App.ProcessedImage.getValue()) {
				warning.setText("Na pliku nie zostały wykonane żadne operacje!");
				warning.setStyle("-fx-font-size: 15px;");
				warning.setTextFill(Color.ORANGE);
				warning.setWrapText(true);
			}
			saveBtn.setOnAction(event -> {
				if (fileNameInput.getLength() < 3) {
					tooLittleChars.setText("Wpisz co najmniej 3 znaki");
					tooLittleChars.setStyle("-fx-font-size: 15px;");
				}
			});
			HBox buttons = new HBox(saveBtn, cancelBtn);
			buttons.setAlignment(Pos.CENTER);
			buttons.setSpacing(20.0d);
			VBox container = new VBox(warning, fileNameInput, tooLittleChars, buttons);
			container.setAlignment(Pos.CENTER);
			container.setSpacing(20.0d);
			container.setStyle("-fx-background-color: lightslategray;");
			Scene modalScene = new Scene(container, 350, 200);

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
