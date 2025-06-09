package image_processing;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;

    public static BooleanProperty BtnsDisabled = new SimpleBooleanProperty(true);
    public static BooleanProperty ProcessedImage = new SimpleBooleanProperty(false);

    public static File logFile;

    // 0 - CRITICAL + INFO
    // 1 - INFO
    public static int verbosityLevel;
    public static FileWriter fw;

    @Override
    public void start(Stage stage) throws IOException {
        verbosityLevel = 0;
        Path logFilePath = Paths.get(System.getProperty("user.dir"), "\\logs.txt");
        if (!Files.exists(logFilePath)) {
            Files.createFile(logFilePath);
        }
        logFile = logFilePath.toFile();
        logFile.setWritable(true);
        fw = new FileWriter(logFile, true);
        if (verbosityLevel <= 1) {
            fw.write("    === INFO: \n        Aplikacja uruchomiona " + LocalDateTime.now() + "\n");
        }
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
            try {
                if (verbosityLevel <= 1) {
                    fw.write("    === INFO: \n        Aplikacja zamknięta " + LocalDateTime.now() + "\n");
                }
                fw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

}
