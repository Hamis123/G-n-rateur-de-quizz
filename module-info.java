module com.example.quizz{
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.datatransfer;

    opens com.example.quizz to javafx.fxml;
    exports com.example.quizz;
}