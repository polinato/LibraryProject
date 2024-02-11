package com.ua.tpm.libraryproject.dbconn.tables.publishing;

import com.ua.tpm.libraryproject.dbconn.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublishingDBHandler extends DBConnector {

    public ObservableList<Publishing> readDBTable() {

        ObservableList<Publishing> publishingObservableList = null;

        List<Publishing> publishingList = new ArrayList<>();

        try (Connection conn = getDbConnection()) {

            String query = "SELECT * FROM " + PublishingConst.TABLE;

            try (Statement statement = conn.createStatement()) {

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {

                    Publishing publishing = new Publishing();

                    String name = resultSet.getString(PublishingConst.NAME);
                    String city = resultSet.getString(PublishingConst.CITY);
                    String street = resultSet.getString(PublishingConst.STREET);

                    publishing.setName(name);
                    publishing.setCity(city);
                    publishing.setStreet(street);

                    publishingList.add(publishing);
                }
            }

            catch (SQLException e) {

                System.err.println("1Got an exception! ");
                System.err.println(e.getMessage());
            }

            publishingObservableList = FXCollections.observableArrayList(publishingList);
        }

        catch (ClassNotFoundException |SQLException e) {

            System.err.println("2Got an exception! ");
            System.err.println(e.getMessage());
        }

        return publishingObservableList;
    }


    public void addPublishingToDB(Publishing publishing) throws SQLException, ClassNotFoundException {

        Connection connection;

        try {
            String insertQueryStatement = "INSERT INTO " + PublishingConst.TABLE + " VALUES  (?,?,?,?)";
            connection = getDbConnection();
            PreparedStatement statement = connection.prepareStatement(insertQueryStatement);
            statement.setString(1, null);
            statement.setString(2, publishing.getName());
            statement.setString(3, publishing.getCity());
            statement.setString(4, publishing.getStreet());
            statement.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void changePublishing(int index, Publishing publishing, String prevName){

        Statement statement = null;

        try {
            Connection conn = getDbConnection();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

            String query1 = "UPDATE " + PublishingConst.TABLE + " SET ";
            String query = query1;

            // query += PositionsConst.NAME + "=" + position.getName() + ", ";
            query += PublishingConst.CITY + "=" + publishing.getCity() + ", ";
            query += PublishingConst.STREET + "=" + publishing.getStreet() + ", ";
           // query += PositionsConst.REQUIREMENTS + "=" + publishing.getRequirements() + " WHERE ";
            query += PublishingConst.NAME + "=" + prevName;

            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.absolute(index+1);

            resultSet.updateRow();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void deletePublishing(Publishing publishing) {

        try {
            Connection connection = getDbConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + PublishingConst.TABLE +
                    " WHERE " +  PublishingConst.NAME + " = ?");
            statement.setString(1, publishing.getName());
            statement.executeUpdate();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
