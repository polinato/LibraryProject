package com.ua.tpm.libraryproject.tables;

import com.ua.tpm.libraryproject.dbconn.tables.publishing.Publishing;
import com.ua.tpm.libraryproject.dbconn.tables.publishing.PublishingDBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class PublishingController extends PublishingDBHandler {

    private ObservableList<Publishing> publishingData = FXCollections.observableArrayList();
    private Publishing publishing;
    private Publishing currentPublishing;
    private boolean buttonNewFlag = true;

    @FXML
    private TableView<Publishing> publishingTable;

    @FXML
    private TableColumn<Publishing, String> nameColumn;

    @FXML
    private TableColumn<Publishing, String> cityColumn;

    @FXML
    private TableColumn<Publishing, String> streetColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField streetField;

    @FXML
    private Button buttonNew;

    @FXML
    public void initialize() {

        PublishingDBHandler publishingDBHandler = new PublishingDBHandler();
        publishingData = publishingDBHandler.readDBTable();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));

        publishingTable.setItems(publishingData);

        showPublishingDetails(null);

        publishingTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPublishingDetails(newValue));
    }

    private void showPublishingDetails(Publishing publishing) {

        if (publishing != null) {

            nameField.setText(publishing.getName());
            cityField.setText(publishing.getCity());
            streetField.setText(publishing.getStreet());

            currentPublishing = publishing;
        }

        else {

            nameField.setText("Посада");
            cityField.setText("Обов'язки");
            streetField.setText("Вимоги");
        }

    }

    @FXML
    private void handleDeletePublishing() {

        int selectedIndex = publishingTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            PublishingDBHandler publishingDBHandler = new PublishingDBHandler();
            publishingDBHandler.deletePublishing(publishingData.get(selectedIndex));
            publishingTable.getItems().remove(selectedIndex);
        }
        else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(null);
            alert.setTitle("Нічого не обрано!");
            alert.setHeaderText("Не вибрана посада!");
            alert.setContentText("Будь ласка, оберіть посаду в таблиці.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditPublishing() {

        int selectedIndex = publishingTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            PublishingDBHandler positionsDBHandler = new PublishingDBHandler();
            Publishing position = new Publishing();

            position.setName(nameField.getText());
            position.setCity(cityField.getText());
            position.setStreet(streetField.getText());

            positionsDBHandler.changePublishing(selectedIndex, position, currentPublishing.getName());

            /*nameField.setText(position.getName());
            salaryField.setText(String.valueOf(position.getSalary()));
            responsibilitiesField.setText(position.getResponsibilities());
            requirementsField.setText(position.getRequirements());*/
        }
        else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(null);
            alert.setTitle("Нічого не обрано!");
            alert.setHeaderText("Не вибрана посада!");
            alert.setContentText("Будь ласка, оберіть посаду в таблиці.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewPublishing() throws SQLException, ClassNotFoundException{

        if (buttonNewFlag) { // "Новий"

            nameField.setText("Посада");
            cityField.setText("Обов'язки");
            streetField.setText("Вимоги");

            buttonNew.setText("Зберегти");
            buttonNewFlag = !buttonNewFlag;
        }

        else { // "Зберегти"

            Publishing position = new Publishing();
            position.setName(nameField.getText());
            position.setCity(cityField.getText());
            position.setStreet(streetField.getText());

            publishingData.add(position);
            PublishingDBHandler positionsDBHandler = new PublishingDBHandler();
            positionsDBHandler.addPublishingToDB(position);
            publishingTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPublishingDetails(newValue));

            buttonNew.setText("Новий");
            buttonNewFlag = !buttonNewFlag;
        }

    }
}
