module com.tinylang {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires lombok;
    requires guru.nidi.graphviz;
    requires java.desktop;

    opens com.tinylang to javafx.fxml;
    exports com.tinylang;
    exports com.tinylang.utils;
    exports com.tinylang.utils.dataStructures.Tree;
    exports com.tinylang.utils.Exceptions;
    opens com.tinylang.utils to javafx.fxml;
}