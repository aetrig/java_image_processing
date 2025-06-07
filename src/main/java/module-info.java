module image_processing {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens image_processing to javafx.fxml;

    exports image_processing;
}
