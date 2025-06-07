package image_processing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Header header = new Header("logo.jpg", "Politechnika Wrocławska");
        Footer footer = new Footer("Mateusz Gosztyła");
        MainBody mainBody = new MainBody("Welcome to the app");

        MainScreen screen = new MainScreen(header, footer, mainBody);

        scene = new Scene(screen, 1600, 900);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
