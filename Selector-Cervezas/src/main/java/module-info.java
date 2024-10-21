module com.example.cervezas3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cervezas3 to javafx.fxml;
    exports com.example.cervezas3;
}