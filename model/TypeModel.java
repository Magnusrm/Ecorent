package model;

import java.sql.*;
import java.util.ArrayList;

public class TypeModel {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String dbName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true";

    //method that helps check if the type name exists in the database**
    public static int typeExists(String name){
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        String existsQuery = "SELECT name FROM type WHERE LOWER(type.name = ?)";
        String idQuery = "SELECT type_id FROM type WHERE LOWER(type.name = ?)";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(existsQuery);
            preparedStatement.setString(1, name.toLowerCase());
            resultSet = preparedStatement.executeQuery();


            if(resultSet.next()){
                preparedStatement = connection.prepareStatement(idQuery);
                preparedStatement.setString(1, name.toLowerCase());
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return resultSet.getInt("type_id");
            }else{
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - typeExists()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - typeExists()");
        }finally{
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    //Method that lets the user add a new type to the database
    public int addType(String name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String typeInsert = "INSERT INTO type(type_id, name) VALUES (DEFAULT, ?);";
        String maxType = "SELECT MAX(type_id) FROM type";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(typeInsert);
            preparedStatement.setString(1, name);

            if(typeExists(name) < 0) { //Checks if given name is in the database already
                if(preparedStatement.executeUpdate() != 0) {
                    preparedStatement = connection.prepareStatement(maxType);
                    resultSet = preparedStatement.executeQuery();
                    connection.commit();
                    resultSet.next();
                    return resultSet.getInt("MAX(type_id)");
                } else{
                    connection.rollback();
                }
            }else{
                System.out.println("Type already exists");
                return -1;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage() + " - addType()");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage() + " - addType()");
        }finally {
            DBCleanup.setAutoCommit(connection);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    public boolean editType(int typeID, String name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String typeInsert = "UPDATE type SET name = ? WHERE type_id = ?;";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(typeInsert);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, typeID);


            if(preparedStatement.executeUpdate() != 0){
                connection.commit();
                return true;
            }else{
                connection.rollback();
                return false;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage() + " - editType()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - editType()");
        }
        return false;
    }

    public boolean deleteType(String typeName){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int typeID = typeExists(typeName);

        String deleteUpdate = "DELETE FROM type WHERE type_id = ?";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(deleteUpdate);
            preparedStatement.setInt(1, typeID);
            if(preparedStatement.executeUpdate() != 0){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - deleteType()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - deleteType()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    //Returns an arraylist of strings with all the types in the database
    public ArrayList<String> getTypes(int typeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<String> types = new ArrayList<String>();

        String typesQuery = "SELECT name FROM type";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(typesQuery);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                types.add(resultSet.getString("name"));
            }
            return types;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getTypes()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - getTypes()");
        }finally {
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }
}
