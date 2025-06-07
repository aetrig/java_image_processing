package image_processing;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    public static BooleanProperty BtnsDisabled = new SimpleBooleanProperty(true);
    public static BooleanProperty ProcessedImage = new SimpleBooleanProperty(false);

    @Override
    public void start(Stage stage) throws IOException {
        Header header = new Header("logo.jpg", "Aplikacja do obróbki obrazów");
        Footer footer = new Footer("Mateusz Gosztyła");
        ProcessingChoice choice = new ProcessingChoice("Wybierz operację do wykonania oraz obraz");
        MainBody body = new MainBody(choice);
        MainScreen screen = new MainScreen(header, footer, body);

        scene = new Scene(screen, 1600, 900);
        scene.getStylesheets().add(getClass().getResource("/image_processing/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(event -> {
            SaveFileModal.close();
            Toast.close();
        });
    }

    public static void main(String[] args) {
        launch();
    }

}
