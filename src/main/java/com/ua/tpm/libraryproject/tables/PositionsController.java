package com.ua.tpm.libraryproject.tables;

import com.ua.tpm.libraryproject.dbconn.tables.positions.Position;
import com.ua.tpm.libraryproject.dbconn.tables.positions.PositionsDBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class PositionsController extends PositionsDBHandler {

    private ObservableList<Position> positionsData = FXCollections.observableArrayList();
    private Position position;
    private Position currentPosition;
    private boolean buttonNewFlag = true;

    @FXML
    private TableView<Position> positionsTable;

    @FXML
    private TableColumn<Position, String> nameColumn;

    @FXML
    private TableColumn<Position, Float> salaryColumn;

    @FXML
    private TableColumn<Position, String> responsibilitiesColumn;

    @FXML
    private TableColumn<Position, String> requirementsColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField responsibilitiesField;

    @FXML
    private TextField requirementsField;

    @FXML
    private Button buttonNew;

    @FXML
    public void initialize() {

        PositionsDBHandler positionsDBHandler = new PositionsDBHandler();
        positionsData = positionsDBHandler.readDBTable();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        responsibilitiesColumn.setCellValueFactory(new PropertyValueFactory<>("responsibilities"));
        requirementsColumn.setCellValueFactory(new PropertyValueFactory<>("requirements"));

        positionsTable.setItems(positionsData);

        showPositionDetails(null);

        positionsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                showPositionDetails(newValue));
    }

    private void showPositionDetails(Position position) {

        if (position != null) {

            nameField.setText(position.getName());
            salaryField.setText(String.valueOf(position.getSalary()));
            responsibilitiesField.setText(position.getResponsibilities());
            requirementsField.setText(position.getRequirements());

            currentPosition = position;
        }

        else {

            nameField.setText("Посада");
            salaryField.setText("10000");
            responsibilitiesField.setText("Обов'язки");
            requirementsField.setText("Вимоги");
        }

    }

    @FXML
    private void handleDeletePosition() {

        int selectedIndex = positionsTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            PositionsDBHandler positionsDBHandler = new PositionsDBHandler();
            positionsDBHandler.deletePosition(positionsData.get(selectedIndex));
            positionsTable.getItems().remove(selectedIndex);
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
    private void handleEditPosition() {

        int selectedIndex = positionsTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            PositionsDBHandler positionsDBHandler = new PositionsDBHandler();
            Position position = new Position();

            position.setName(nameField.getText());
            position.setSalary(Float.parseFloat(salaryField.getText()));
            position.setResponsibilities(responsibilitiesField.getText());
            position.setRequirements(requirementsField.getText());

            positionsDBHandler.changePosition(selectedIndex, position, currentPosition.getName());

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
    private void handleNewPosition() throws SQLException, ClassNotFoundException{

        if (buttonNewFlag) { // "Новий"

            nameField.setText("Посада");
            salaryField.setText("10000");
            responsibilitiesField.setText("Обов'язки");
            requirementsField.setText("Вимоги");

            buttonNew.setText("Зберегти");
            buttonNewFlag = !buttonNewFlag;
        }

        else { // "Зберегти"

            Position position = new Position();
            position.setName(nameField.getText());
            position.setSalary(Float.parseFloat(salaryField.getText()));
            position.setResponsibilities(responsibilitiesField.getText());
            position.setRequirements(requirementsField.getText());

            positionsData.add(position);
            PositionsDBHandler positionsDBHandler = new PositionsDBHandler();
            positionsDBHandler.addPositionToDB(position);
            positionsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                    showPositionDetails(newValue));

            buttonNew.setText("Новий");
            buttonNewFlag = !buttonNewFlag;
        }
    }
}
