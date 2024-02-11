package com.ua.tpm.libraryproject.requests;

import com.ua.tpm.libraryproject.dbconn.requests.staff.Staff;
import com.ua.tpm.libraryproject.dbconn.requests.staff.StaffDBHandler;
import com.ua.tpm.libraryproject.dbconn.tables.positions.PositionsDBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class StaffController extends StaffDBHandler {

    private ObservableList<Staff> staffData = FXCollections.observableArrayList();
    private Staff staff;

    @FXML
    private TableView<Staff> staffTable;

    @FXML
    private TableColumn<Staff, String> firstNameColumn;

    @FXML
    private TableColumn<Staff, String> secondNameColumn;

    @FXML
    private TableColumn<Staff, String> positionColumn;

    @FXML
    private TableColumn<Staff, Float> salaryColumn;

    @FXML
    public void initialize() {

        StaffDBHandler staffDBHandler = new StaffDBHandler();
        staffDBHandler.staffRequest();
        staffData = staffDBHandler.readDBTable();

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        staffTable.setItems(staffData);
    }
}
