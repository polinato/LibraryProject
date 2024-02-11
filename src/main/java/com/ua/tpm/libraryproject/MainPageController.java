package com.ua.tpm.libraryproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

//import java.io.IOException;

public class MainPageController {

    @FXML
    private BorderPane pBorderPane;

    @FXML
    public void handleActionLoadPositionsTable(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("tables/Positions.fxml"));
        pBorderPane.setCenter(root);
    }

    @FXML
    public void handleActionLoadPublishingTable(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("tables/Publishing.fxml"));
        pBorderPane.setCenter(root);
    }

    @FXML
    public void handleActionLoadWorkersTable(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("tables/Workers.fxml"));
        pBorderPane.setCenter(root);
    }

    @FXML
    public void handleActionLoadStaffRequest(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("requests/Staff.fxml"));
        pBorderPane.setCenter(root);
    }
    @FXML
    public void handleActionSetDarkTheme(ActionEvent e) throws IOException {

        //scene.getStylesheets().add(getClass().getResource("styles/darkTheme.css").toExternalForm());
    }
}