package model;

import java.sql.*;

public class AdminModel {
    private String driver = "com.mysql.jdbc.Driver";
    private String dbName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true";

    //Checks if an email already exists in the database
    public boolean adminExists(String email){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String emailQuery = "SELECT email FROM admin WHERE email = ?";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(emailQuery);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            boolean exists = false;
            while(resultSet.next()){
                exists = true;
            }
            if(exists){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - userExists()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - userExists()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    public boolean addAdmin(String email, String salt, String hash, boolean priviliged){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String userInsert = "INSERT INTO admin(email, salt, hash, priviliged) VALUES(?, ?, ?, ?)";

        byte priv = 1;
        byte notPriv = 0;

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            if(!adminExists(email)){
                preparedStatement = connection.prepareStatement(userInsert);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, salt);
                preparedStatement.setString(3, hash);
                if(priviliged == true){
                    preparedStatement.setByte(4, priv);
                }else{
                    preparedStatement.setInt(4, notPriv);
                }
                preparedStatement.executeUpdate();
                return true;
            }else{
                System.out.println("User already exists");
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - addUser()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - addUser()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    public boolean deleteAdmin(String email){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteQuery = "DELETE FROM admin WHERE email = ?";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, email);
            if(preparedStatement.executeUpdate() != 0){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - deleteAdmin()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - deleteAdmin()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    public String getSalt(String email){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String salt;

        String saltQuery = "SELECT hash FROM admin WHERE email = ?";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            if(adminExists(email)){
                preparedStatement = connection.prepareStatement(saltQuery);
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                salt = resultSet.getString("salt");
                return salt;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getSalt()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - getSalt()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    //Returns the hash value of a given email
    public String getHash(String email){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String hash;

        String hashQuery = "SELECT hash FROM admin WHERE email = ?";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            if(adminExists(email)){
                preparedStatement = connection.prepareStatement(hashQuery);
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                hash = resultSet.getString("hash");
                return hash;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getHash()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - getHash()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    public boolean getPriviliged(String email){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String priviligedQuery = "SELECT priviliged FROM admin WHERE email = ?";

        byte priv = 1;

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            if(adminExists(email)){
                preparedStatement = connection.prepareStatement(priviligedQuery);
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                byte res = resultSet.getByte("priviliged");
                if(res == priv){
                    return true;
                }else{
                    return false;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getPriviliged()");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage() + " - getPriviliged()");
        }finally{
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }
}
