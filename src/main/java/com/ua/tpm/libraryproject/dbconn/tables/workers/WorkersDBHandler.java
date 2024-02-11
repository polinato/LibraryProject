package com.ua.tpm.libraryproject.dbconn.tables.workers;

import com.ua.tpm.libraryproject.dbconn.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkersDBHandler extends DBConnector {

    public ObservableList<Workers> readDBTable() {

        ObservableList<Workers> workersObservableList = null;

        List<Workers> workersList = new ArrayList<>();

        try (Connection conn = getDbConnection()) {

            String query = "SELECT * FROM " + WorkersConst.TABLE;

            try (Statement statement = conn.createStatement()) {

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {

                    Workers workers = new Workers();

                    String firstName = resultSet.getString(WorkersConst.FIRSTNAME);
                    String secondName = resultSet.getString(WorkersConst.SECONDNAME);
                    String thirdName = resultSet.getString(WorkersConst.THIRDNAME);
                    String position = resultSet.getString(WorkersConst.POSITION);
                    char sex = resultSet.getString(WorkersConst.SEX).charAt(0);
                    String street = resultSet.getString(WorkersConst.STREET);
                    Date birthdayDate = resultSet.getDate(WorkersConst.BIRTHDAYDATE);
                    String phoneNumber = resultSet.getString(WorkersConst.PHONENUMBER);
                    String passport = resultSet.getString(WorkersConst.PASSPORT);

                    workers.setFirstName(firstName);
                    workers.setSecondName(secondName);
                    workers.setThirdName(thirdName);
                    workers.setPosition(position);
                    workers.setSex(sex);
                    workers.setStreet(street);
                    workers.setBirthday(birthdayDate);
                    workers.setPhoneNumber(phoneNumber);
                    workers.setPassport(passport);

                    workersList.add(workers);
                }
            }

            catch (SQLException e) {

                System.err.println("1Got an exception! ");
                System.err.println(e.getMessage());
            }

            workersObservableList = FXCollections.observableArrayList(workersList);
        }

        catch (ClassNotFoundException |SQLException e) {

            System.err.println("2Got an exception! ");
            System.err.println(e.getMessage());
        }

        return workersObservableList;
    }


    public void addWorkersToDB(Workers workers) throws SQLException, ClassNotFoundException {

        Connection connection;

        try {
            String insertQueryStatement = "INSERT INTO " + WorkersConst.TABLE + " VALUES  (?,?,?,?,?,?,?,?,?,?)";

            connection = getDbConnection();
            PreparedStatement statement = connection.prepareStatement(insertQueryStatement);

            statement.setString(1, null);
            statement.setString(2, workers.getFirstName());
            statement.setString(3, workers.getSecondName());
            statement.setString(4, workers.getThirdName());
            statement.setString(5, workers.getPosition());
            statement.setDate(6, (Date) workers.getBirthday());
            statement.setString(7, Character.toString(workers.getSex()));
            statement.setString(8, workers.getStreet());
            statement.setString(9, workers.getPhoneNumber());
            statement.setString(10, workers.getPassport());
            statement.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void changeWorkers(int index, Workers workers, String prevName){

        Statement statement = null;

        try {
            Connection conn = getDbConnection();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

            String query1 = "UPDATE " + WorkersConst.TABLE + " SET ";
            String query = query1;

            /* query += PositionsConst.NAME + "=" + position.getName() + ", ";
            query += WorkersConst.CITY + "=" + publishing.getCity() + ", ";
            query += WorkersConst.STREET + "=" + publishing.getStreet() + ", ";
            query += PositionsConst.REQUIREMENTS + "=" + publishing.getRequirements() + " WHERE ";*/
           // query += WorkersConst.NAME + "=" + prevName;

            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);

//            query1 += PublishingConst.NAME + "=" + position.getName() + " WHERE ";
            //           query1 += PublishingConst.RESPONSIBILITIES + "=" + position.getResponsibilities();

            //         System.out.println(query1);
            //        resultSet = statement.executeQuery(query1);

            resultSet.absolute(index+1);

            /*resultSet.updateString(PositionsConst.NAME, person.getName());
            resultSet.updateString(PositionsConst.SALARY, String.valueOf(person.getSalary()));
            resultSet.updateString(PositionsConst.RESPONSIBILITIES, person.getResponsibilities());
            resultSet.updateString(PositionsConst.REQUIREMENTS, person.getRequirements());*/
            resultSet.updateRow();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorkers(Workers workers) {

        try {
            Connection connection = getDbConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + WorkersConst.TABLE +
                    " WHERE " +  WorkersConst.FIRSTNAME + " = ?");
            statement.setString(1, workers.getFirstName());
            statement.executeUpdate();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}











