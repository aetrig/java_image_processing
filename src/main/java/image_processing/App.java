package image_processing;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane screen = new AnchorPane();
        String logoFilePath = "../../resources/image_processing/logo.jpeg";
        Image logo = new Image(getClass().getResourceAsStream(logoFilePath), 40.0d, 40.0d, true, true);
        HBox header = new HBox(
                new Label("Politechnika Wrocławska"),
                new ImageView(logo));

        Label welcome = new Label("Welcome to the app");
        Label footer = new Label("Mateusz Gosztyła");

        VBox mainBody = new VBox();
        mainBody.setAlignment(Pos.CENTER);
        mainBody.getChildren().add(welcome);

        screen.getChildren().add(header);
        screen.getChildren().add(mainBody);
        screen.getChildren().add(footer);

        AnchorPane.setTopAnchor(header, 0.0d);
        AnchorPane.setLeftAnchor(header, 0.0d);

        AnchorPane.setTopAnchor(mainBody, 0.0d);
        AnchorPane.setLeftAnchor(mainBody, 0.0d);
        AnchorPane.setRightAnchor(mainBody, 0.0d);
        AnchorPane.setBottomAnchor(mainBody, 0.0d);

        AnchorPane.setBottomAnchor(footer, 0.0d);
        AnchorPane.setRightAnchor(footer, 0.0d);

        scene = new Scene(screen, 1600, 900);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
