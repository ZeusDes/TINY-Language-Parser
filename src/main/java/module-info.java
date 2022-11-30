module com.example.mytinylanguageparser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires lombok;

    opens com.example.mytinylanguageparser to javafx.fxml;
    exports com.example.mytinylanguageparser;
}