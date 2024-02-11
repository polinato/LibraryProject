package com.ua.tpm.libraryproject.tables;

import com.ua.tpm.libraryproject.dbconn.tables.workers.Workers;
import com.ua.tpm.libraryproject.dbconn.tables.workers.WorkersDBHandler;
import com.ua.tpm.libraryproject.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class WorkersController extends WorkersDBHandler {

    private ObservableList<Workers> workersData = FXCollections.observableArrayList();
    private Workers workers;
    private Workers currentWorker;
    private boolean buttonNewFlag = true;

    @FXML
    private TableView<Workers> workersTable;

    @FXML
    private TableColumn<Workers, String> firstNameColumn;

    @FXML
    private TableColumn<Workers, String> secondNameColumn;

    @FXML
    private TableColumn<Workers, String> thirdNameColumn;

    @FXML
    private TableColumn<Workers, String> positionColumn;

    @FXML
    private TableColumn<Workers, Date> birthdayDateColumn;

    @FXML
    private TableColumn<Workers, Character> sexColumn;

    @FXML
    private TableColumn<Workers, String> streetColumn;

    @FXML
    private TableColumn<Workers, String> phoneNumberColumn;

    @FXML
    private TableColumn<Workers, String> passportColumn;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField secondNameField;

    @FXML
    private TextField thirdNameField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField birthdayDateField;

    @FXML
    private TextField sexField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField passportField;

    @FXML
    private Button buttonNew;

    @FXML
    public void initialize() {

        WorkersDBHandler workersDBHandler = new WorkersDBHandler();
        workersData = workersDBHandler.readDBTable();

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        thirdNameColumn.setCellValueFactory(new PropertyValueFactory<>("thirdName"));
        birthdayDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdayDate"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        passportColumn.setCellValueFactory(new PropertyValueFactory<>("passport"));

        workersTable.setItems(workersData);

        showWorkersDetails(null);

        workersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showWorkersDetails(newValue));
    }

    private void showWorkersDetails(Workers workers) {

        if (workers != null) {

            firstNameField.setText(workers.getFirstName());
            secondNameField.setText(workers.getSecondName());
            thirdNameField.setText(workers.getThirdName());
            positionField.setText(workers.getPosition());
            streetField.setText(workers.getStreet());
            birthdayDateField.setText(DateUtil.format(workers.getBirthdayDate()));
            sexField.setText(Character.toString(workers.getSex()));
            phoneNumberField.setText(workers.getPhoneNumber());
            passportField.setText(workers.getPassport());

            currentWorker = workers;
        }

        else {

            firstNameField.setText("");
            secondNameField.setText("");
            thirdNameField.setText("");
            positionField.setText("");
            streetField.setText("");
            birthdayDateField.setText("");
            sexField.setText("");
            phoneNumberField.setText("");
            passportField.setText("");
        }

    }

    @FXML
    private void handleDeleteWorkers() {

        int selectedIndex = workersTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            WorkersDBHandler workersDBHandler = new WorkersDBHandler();
            workersDBHandler.deleteWorkers(workersData.get(selectedIndex));
            workersTable.getItems().remove(selectedIndex);
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
    private void handleEditWorkers() {

        int selectedIndex = workersTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            WorkersDBHandler workersDBHandler = new WorkersDBHandler();
            Workers workers = new Workers();

            workers.setFirstName(firstNameField.getText());
            workers.setSecondName(secondNameField.getText());
            workers.setThirdName(thirdNameField.getText());
            workers.setPosition(positionField.getText());
            workers.setStreet(streetField.getText());
            workers.setBirthdayDate(LocalDate.parse(birthdayDateField.getText()));
            workers.setSex((sexField.getText()).charAt(0));
            workers.setPhoneNumber(phoneNumberField.getText());
            workers.setPassport(passportField.getText());

            workersDBHandler.changeWorkers(selectedIndex, workers, currentWorker.getFirstName());

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
    private void handleNewWorkers() throws SQLException, ClassNotFoundException{

        if (buttonNewFlag) { // "Новий"

            firstNameField.setText("");
            secondNameField.setText("");
            thirdNameField.setText("");
            positionField.setText("");
            streetField.setText("");
            birthdayDateField.setText("");
            sexField.setText("");
            phoneNumberField.setText("");
            passportField.setText("");

            buttonNew.setText("Зберегти");
            buttonNewFlag = !buttonNewFlag;
        }

        else { // "Зберегти"

            Workers workers = new Workers();

            workers.setFirstName(firstNameField.getText());
            workers.setSecondName(secondNameField.getText());
            workers.setThirdName(thirdNameField.getText());
            workers.setPosition(positionField.getText());
            workers.setStreet(streetField.getText());
            workers.setBirthdayDate(DateUtil.parse(birthdayDateField.getText()));
            workers.setSex((sexField.getText()).charAt(0));
            workers.setPhoneNumber(phoneNumberField.getText());
            workers.setPassport(passportField.getText());

            workersData.add(workers);
            WorkersDBHandler positionsDBHandler = new WorkersDBHandler();
            positionsDBHandler.addWorkersToDB(workers);
            workersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showWorkersDetails(newValue));

            buttonNew.setText("Новий");
            buttonNewFlag = !buttonNewFlag;
        }

    }
}
