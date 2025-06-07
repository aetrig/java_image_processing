module image_processing {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires javafx.graphics;

    opens image_processing to javafx.fxml;

    exports image_processing;
}
