package com.ua.tpm.libraryproject.dbconn.requests.staff;

import com.ua.tpm.libraryproject.dbconn.DBConnector;
import com.ua.tpm.libraryproject.dbconn.tables.positions.PositionsConst;
import com.ua.tpm.libraryproject.dbconn.tables.workers.WorkersConst;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDBHandler extends DBConnector {

    /*public float getSalary(String[][] posAndSalArray, String position) {

        float salary = 0;

        for (int i = 0; i == posAndSalArray.length - 1; i++) {

            if (posAndSalArray[0][i] == position) {

                salary = Float.parseFloat(posAndSalArray[1][i]);
                break;
            }
        }

        return salary;
    }*/

    public float getSalary (String position) {

        float salary = 0;

        Statement statement = null;

        try {

            Connection conn = getDbConnection();
            statement = conn.createStatement();

            String query = "SELECT " + PositionsConst.SALARY + " FROM " + PositionsConst.TABLE + " WHERE " +
                    PositionsConst.NAME + "=\"" + position + "\";";
            System.out.println(query);

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            System.out.println(resultSet.getString(PositionsConst.SALARY));
            salary = Float.parseFloat(resultSet.getString(PositionsConst.SALARY));
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return salary;
    }

    public void staffRequest() {

        Statement statement = null;

        try {

            Connection conn = getDbConnection();
            statement = conn.createStatement();

            String query1 = "DELETE FROM " + StaffConst.TABLE + " WHERE " + StaffConst.ID + "<0;";
            PreparedStatement statement1 = conn.prepareStatement(query1);
            System.out.println(query1);

            String query = "SELECT " + WorkersConst.FIRSTNAME + ", " + WorkersConst.SECONDNAME + ", " +
                    WorkersConst.POSITION + " FROM " + WorkersConst.TABLE + ";";

            System.out.println(query);

            ResultSet resultSet = statement.executeQuery(query);

            Staff staff = new Staff();

            while (resultSet.next()) {

                String firstName = resultSet.getString(WorkersConst.FIRSTNAME);
                String secondName = resultSet.getString(WorkersConst.SECONDNAME);
                String position = resultSet.getString(WorkersConst.POSITION);
                float salary = getSalary(position);

                System.out.println(firstName + " " + secondName + " " + position + " " + salary);

                staff.setFirstName(firstName);
                staff.setSecondName(secondName);
                staff.setPosition(position);
                staff.setSalary(salary);

                newStaff(staff);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

 /*   public ObservableList<Staff> getStaff() {

        ObservableList<Staff> staffObservableList = null;
        List<Staff> staffList = new ArrayList<>();

        Statement statement = null;

        try {

            Connection conn = getDbConnection();
            statement = conn.createStatement();

            String[][] posAndSalArray = getPosAndSal();

            String query = "SELECT " + WorkersConst.FIRSTNAME + ", " + WorkersConst.SECONDNAME + ", " +
                    WorkersConst.POSITION + " FROM " + WorkersConst.TABLE + ";";

            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {

                Staff staff = new Staff();

                String firstName = resultSet.getString(WorkersConst.FIRSTNAME);
                String secondName = resultSet.getString(WorkersConst.SECONDNAME);
                String position = resultSet.getString(WorkersConst.POSITION);
                float salary = getSalary(posAndSalArray, position);

                staff.setFirstName(firstName);
                staff.setSecondName(secondName);
                staff.setPosition(position);
                staff.setSalary(salary);

                newStaff(staff);
                staffList.add(staff);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return staffObservableList = FXCollections.observableArrayList(staffList);
    }

    public String[][] getPosAndSal() {

        String[][] posAndSalArray = null;

        try (Connection conn = getDbConnection()) {

            String query = "SELECT " + PositionsConst.NAME + ", " + PositionsConst.SALARY + " FROM " + PositionsConst.TABLE + ";";

            try (Statement statement = conn.createStatement()) {

                ResultSet resultSet = statement.executeQuery(query);

                int i = 0;

                while (resultSet.next()) {

                    posAndSalArray[0][i] = resultSet.getString(PositionsConst.NAME);
                    posAndSalArray[1][i] = resultSet.getString(PositionsConst.SALARY);
                    i++;
                }

            }
        }
        catch (ClassNotFoundException | SQLException e) {

            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }


        return posAndSalArray;
    }*/

    public void newStaff(Staff staff) {

        Connection connection;

        try {
            String insertQueryStatement = "INSERT INTO " + StaffConst.TABLE + " VALUES  (?,?,?,?,?)";

            connection = getDbConnection();
            PreparedStatement statement = connection.prepareStatement(insertQueryStatement);

            statement.setString(1, null);
            statement.setString(2, staff.getFirstName());
            statement.setString(3, staff.getSecondName());
            statement.setString(4, staff.getPosition());
            statement.setString(5, Float.toString(staff.getSalary()));
            statement.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Staff> readDBTable() {

        ObservableList<Staff> staffObservableList = null;

        List<Staff> staffList = new ArrayList<>();

        try (Connection conn = getDbConnection()) {

            String query = "SELECT * FROM " + StaffConst.TABLE;

            try (Statement statement = conn.createStatement()) {

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {

                    Staff staff = new Staff();

                    String firstName = resultSet.getString(StaffConst.FIRSTNAME);
                    String secondName = resultSet.getString(StaffConst.SECONDNAME);
                    String position = resultSet.getString(StaffConst.POSITION);
                    float salary = Float.parseFloat(resultSet.getString(StaffConst.SALARY));

                    staff.setFirstName(firstName);
                    staff.setSecondName(secondName);
                    staff.setPosition(position);
                    staff.setSalary(salary);

                    staffList.add(staff);
                }
            }

            catch (SQLException e) {

                System.err.println("1Got an exception! ");
                System.err.println(e.getMessage());
            }

            staffObservableList = FXCollections.observableArrayList(staffList);
        }

        catch (ClassNotFoundException |SQLException e) {

            System.err.println("2Got an exception! ");
            System.err.println(e.getMessage());
        }
        return staffObservableList;
    }
}
