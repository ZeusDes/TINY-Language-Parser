module com.tinylang {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires lombok;

    opens com.tinylang to javafx.fxml;
    exports com.tinylang;
    exports com.tinylang.utils;
    opens com.tinylang.utils to javafx.fxml;
}