module com.example.lab_ads {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires lombok;


    opens com.example.lab_ads to javafx.fxml;
    exports com.example.lab_ads;
}