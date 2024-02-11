package com.ua.tpm.libraryproject.dbconn.tables.positions;

import com.ua.tpm.libraryproject.dbconn.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionsDBHandler extends DBConnector {

    public ObservableList<Position> readDBTable() {

        ObservableList<Position> positionObservableList = null;

        List<Position> positionList = new ArrayList<>();

        try (Connection conn = getDbConnection()) {

            String query = "SELECT * FROM " + PositionsConst.TABLE;

            try (Statement statement = conn.createStatement()) {

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {

                    Position position = new Position();

                    String name = resultSet.getString(PositionsConst.NAME);
                    float salary = Float.parseFloat(resultSet.getString(PositionsConst.SALARY));
                    String responsibilities = resultSet.getString(PositionsConst.RESPONSIBILITIES);
                    String requirements = resultSet.getString(PositionsConst.REQUIREMENTS);

                    position.setName(name);
                    position.setSalary(salary);
                    position.setResponsibilities(responsibilities);
                    position.setRequirements(requirements);

                    positionList.add(position);
                }
            }

            catch (SQLException e) {

                System.err.println("1Got an exception! ");
                System.err.println(e.getMessage());
            }

            positionObservableList = FXCollections.observableArrayList(positionList);
        }

        catch (ClassNotFoundException |SQLException e) {

            System.err.println("2Got an exception! ");
            System.err.println(e.getMessage());
        }

        return positionObservableList;
    }


    public void addPositionToDB(Position position) throws SQLException, ClassNotFoundException {

        Connection connection;

        try {
            String insertQueryStatement = "INSERT INTO " + PositionsConst.TABLE + " VALUES  (?,?,?,?,?)";
            connection = getDbConnection();
            PreparedStatement statement = connection.prepareStatement(insertQueryStatement);
            statement.setString(1, null);
            statement.setString(2, position.getName());
            statement.setFloat(3, position.getSalary());
            statement.setString(4, position.getResponsibilities());
            statement.setString(5, position.getRequirements());
            statement.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void changePosition(int index, Position position, String prevName){

        Statement statement = null;

        try {
            Connection conn = getDbConnection();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

            String query1 = "UPDATE " + PositionsConst.TABLE + " SET ";
            String query = query1;

           // query += PositionsConst.NAME + "=" + position.getName() + ", ";
            query += PositionsConst.SALARY + "=" + position.getSalary() + ", ";
            query += PositionsConst.RESPONSIBILITIES + "=" + position.getResponsibilities() + ", ";
            query += PositionsConst.REQUIREMENTS + "=" + position.getRequirements() + " WHERE ";
            query += PositionsConst.NAME + "=" + prevName;

            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);

            query1 += PositionsConst.NAME + "=" + position.getName() + " WHERE ";
            query1 += PositionsConst.RESPONSIBILITIES + "=" + position.getResponsibilities();

            System.out.println(query1);
            resultSet = statement.executeQuery(query1);

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

    public void deletePosition(Position position) {

        try {
            Connection connection = getDbConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + PositionsConst.TABLE +
                    " WHERE " +  PositionsConst.NAME + " = ?");
            statement.setString(1, position.getName());
            statement.executeUpdate();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
