package image_processing;

import java.io.File;
import java.time.LocalDateTime;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainBody extends AnchorPane {
	private ImageView leftIV;
	public static ImageView rightIV;
	private ProcessingChoice choice;
	private Image leftImg;
	public static Image rightImg;
	private Button fileBtn = new Button("Wybierz plik");
	private Button saveBtn = new Button("Zapisz plik");
	FileChooser fileChooser = new FileChooser();
	File chosenImg;
	private VBox leftVBox;
	private VBox rightVBox;
	private VBox BtnVbox;
	private Button scaleBtn = new Button("Skalowanie");
	private Button rotateLeftBtn = new Button();
	private Button rotateRightBtn = new Button();
	private HBox rotateBtns = new HBox(rotateLeftBtn, rotateRightBtn);

	public MainBody(ProcessingChoice choice) {
		this.choice = choice;

		Image rotateLeftIcon = new Image(getClass().getResourceAsStream("/image_processing/rotate_left.png"));
		Image rotateRightIcon = new Image(getClass().getResourceAsStream("/image_processing/rotate_right.png"));
		rotateLeftBtn.setGraphic(new ImageView(rotateLeftIcon));
		rotateRightBtn.setGraphic(new ImageView(rotateRightIcon));
		rotateBtns.setSpacing(5.0d);
		rotateBtns.setAlignment(Pos.CENTER);
		leftIV = new ImageView();
		leftIV.setFitWidth(400.0d);
		leftIV.setFitHeight(600.0d);
		leftIV.setPreserveRatio(true);
		rightIV = new ImageView();
		rightIV.setFitWidth(400.0d);
		rightIV.setFitHeight(600.0d);
		rightIV.setPreserveRatio(true);
		leftVBox = new VBox(leftIV);
		leftVBox.setAlignment(Pos.CENTER);
		rightVBox = new VBox(rightIV);
		rightVBox.setAlignment(Pos.CENTER);

		saveBtn.disableProperty().bind(App.BtnsDisabled);
		scaleBtn.disableProperty().bind(App.BtnsDisabled);
		rotateLeftBtn.disableProperty().bind(App.BtnsDisabled);
		rotateRightBtn.disableProperty().bind(App.BtnsDisabled);

		BtnVbox = new VBox(fileBtn, saveBtn, scaleBtn);
		BtnVbox.setAlignment(Pos.CENTER);
		BtnVbox.setSpacing(5.0d);

		fileChooser.setTitle("Wybierz obraz");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("JPG", "*.jpg"));
		choice.getChildren().add(BtnVbox);
		choice.getChildren().add(rotateBtns);

		fileBtn.setOnAction(event -> {
			chosenImg = fileChooser.showOpenDialog(fileBtn.getScene().getWindow());
			if (chosenImg != null && chosenImg.exists() && chosenImg.getName().endsWith(".jpg")) { // ||
				// chosenImg.getName().endsWith(".jpeg")
				Toast.show((Stage) App.scene.getWindow(), "Pomyślnie załadowano plik", 2000);
				leftImg = new Image(chosenImg.toURI().toString());
				leftIV.setImage(leftImg);
				rightImg = new Image(chosenImg.toURI().toString());
				rightIV.setImage(rightImg);
				App.BtnsDisabled.setValue(false);
				App.ProcessedImage.setValue(false);
				try {
					if (App.verbosityLevel <= 1) {
						App.fw.write("=== INFO: \n    Załadowano obraz "
								+ LocalDateTime.now() + "\n");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			} else if (chosenImg != null && !chosenImg.getName().endsWith(".jpg")) {
				Toast.show((Stage) App.scene.getWindow(), "Niedozwolony format pliku", 2000);
				leftImg = null;
				rightImg = null;
				leftIV.setImage(null);
				rightIV.setImage(null);
				App.BtnsDisabled.setValue(true);
				App.ProcessedImage.setValue(false);
				try {
					if (App.verbosityLevel <= 0) {
						App.fw.write("=== ERROR: \n    Próba załadowania niedozwolonego formatu pliku "
								+ LocalDateTime.now() + "\n");
					}
				} catch (Exception e) {
					System.out.println(e);
				}

			} else {
				Toast.show((Stage) App.scene.getWindow(), "Nie udało się załadować pliku", 2000);
				try {
					if (App.verbosityLevel <= 1) {
						App.fw.write("=== ERROR: \n    Ładowanie pliku nie powiodło się "
								+ LocalDateTime.now() + "\n");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				leftImg = null;
				rightImg = null;
				leftIV.setImage(null);
				rightIV.setImage(null);
				App.BtnsDisabled.setValue(true);
				App.ProcessedImage.setValue(false);
			}
		});
		saveBtn.setOnAction(event -> {
			SaveFileModal.show();
		});
		scaleBtn.setOnAction(event -> {
			ScaleModal.show();
		});
		rotateLeftBtn.setOnAction(event -> {
			WritableImage rotatedImage = new WritableImage((int) rightImg.getHeight(), (int) rightImg.getWidth());
			PixelWriter pw = rotatedImage.getPixelWriter();
			PixelReader pr = rightImg.getPixelReader();
			for (int x = 0; x < rightImg.getHeight(); x++) {
				for (int y = 0; y < rightImg.getWidth(); y++) {
					pw.setColor(x, y, pr.getColor((int) rightImg.getWidth() - 1 - y, x));
				}
			}
			rightImg = rotatedImage;
			rightIV.setImage(rightImg);
			App.ProcessedImage.setValue(true);
			try {
				if (App.verbosityLevel <= 1) {
					App.fw.write("=== INFO: \n    Obrócono obraz w lewo "
							+ LocalDateTime.now() + "\n");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		});
		rotateRightBtn.setOnAction(event -> {
			WritableImage rotatedImage = new WritableImage((int) rightImg.getHeight(), (int) rightImg.getWidth());
			PixelWriter pw = rotatedImage.getPixelWriter();
			PixelReader pr = rightImg.getPixelReader();
			for (int x = 0; x < rightImg.getHeight(); x++) {
				for (int y = 0; y < rightImg.getWidth(); y++) {
					pw.setColor(x, y, pr.getColor(y, (int) rightImg.getHeight() - 1 - x));
				}
			}
			rightImg = rotatedImage;
			rightIV.setImage(rightImg);
			App.ProcessedImage.setValue(true);
			try {
				if (App.verbosityLevel <= 1) {
					App.fw.write("=== INFO: \n    Obrócono obraz w prawo "
							+ LocalDateTime.now() + "\n");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		});
		AnchorPane.setLeftAnchor(leftVBox, 20.0d);
		AnchorPane.setTopAnchor(leftVBox, 0.0d);
		AnchorPane.setBottomAnchor(leftVBox, 0.0d);

		AnchorPane.setRightAnchor(rightVBox, 20.0d);
		AnchorPane.setTopAnchor(rightVBox, 0.0d);
		AnchorPane.setBottomAnchor(rightVBox, 0.0d);

		AnchorPane.setLeftAnchor(choice, 20.0d);
		AnchorPane.setRightAnchor(choice, 20.0d);
		AnchorPane.setTopAnchor(choice, 0.0d);
		AnchorPane.setBottomAnchor(choice, 0.0d);

		this.getChildren().add(this.leftVBox);
		this.getChildren().add(this.choice);
		this.getChildren().add(this.rightVBox);
	}
}
