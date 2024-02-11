module com.example.libraryproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.ua.tpm.libraryproject to javafx.fxml;
    exports com.ua.tpm.libraryproject;
    opens com.ua.tpm.libraryproject.tables to javafx.fxml;
    exports com.ua.tpm.libraryproject.tables;
    opens com.ua.tpm.libraryproject.requests to javafx.fxml;
    exports com.ua.tpm.libraryproject.requests;
    opens com.ua.tpm.libraryproject.dbconn.tables.positions to javafx.fxml;
    exports com.ua.tpm.libraryproject.dbconn.tables.positions;
    opens com.ua.tpm.libraryproject.dbconn.tables.publishing to javafx.fxml;
    exports com.ua.tpm.libraryproject.dbconn.tables.publishing;
    opens com.ua.tpm.libraryproject.dbconn.tables.workers to javafx.fxml;
    exports com.ua.tpm.libraryproject.dbconn.tables.workers;
    opens com.ua.tpm.libraryproject.dbconn.requests.staff to javafx.fxml;
    exports com.ua.tpm.libraryproject.dbconn.requests.staff;
}