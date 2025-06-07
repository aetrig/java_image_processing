package image_processing;

import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class Toast {
	public static void show(Stage parentStage, String toastText, int durationMilliseconds) {
		Platform.runLater(() -> {
			Stage toastStage = new Stage();
			toastStage.initStyle(StageStyle.TRANSPARENT);
			toastStage.setResizable(false);
			toastStage.setAlwaysOnTop(true);
			Label toastLabel = new Label(toastText);
			toastLabel.setWrapText(true);
			toastLabel.setStyle(
					" -fx-color: rgba(0, 0, 0, 0.7);" +
							"-fx-background-color: rgba(191, 191, 191, 0.7);" +
							"-fx-font-size: 17px;");

			Scene toastScene = new Scene(toastLabel);

			toastStage.setScene(toastScene);

			double parentX = parentStage.getX();
			double parentY = parentStage.getY();
			double parentWidth = parentStage.getWidth();
			double parentHeight = parentStage.getHeight();

			toastStage.show();
			double toastWidth = toastStage.getWidth();
			toastStage.hide();

			toastStage.setX(parentX + parentWidth / 2.0 - toastWidth / 2);
			toastStage.setY(parentY + parentHeight - 250);

			toastStage.show();
			PauseTransition pause = new PauseTransition(Duration.millis(durationMilliseconds));
			pause.setOnFinished(event -> toastStage.close());
			pause.play();
		});
	}
}
