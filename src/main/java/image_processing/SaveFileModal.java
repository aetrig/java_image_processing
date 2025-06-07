package image_processing;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SaveFileModal {
	private static Stage modalStage = new Stage();

	public static void show() {
		Platform.runLater(() -> {
			modalStage.setResizable(false);
			TextField fileNameInput = new TextField();
			Button saveBtn = new Button("Zapisz");
			Button cancelBtn = new Button("Anuluj");
			cancelBtn.setOnAction(event -> {
				close();
			});
			HBox buttons = new HBox(saveBtn, cancelBtn);
			buttons.setAlignment(Pos.CENTER);
			buttons.setSpacing(20.0d);
			VBox container = new VBox(fileNameInput, buttons);
			container.setAlignment(Pos.CENTER);
			container.setSpacing(20.0d);
			Scene modalScene = new Scene(container, 200, 100);
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
