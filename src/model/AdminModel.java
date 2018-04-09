package model;

import control.Admin;

import java.sql.*;
import java.util.ArrayList;

public class AdminModel {

    //Checks if an email already exists in the database
    public boolean adminExists(String email){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String emailQuery = "SELECT email FROM admin WHERE email = ?";
        try{
            connection = DBCleanup.getConnection();

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
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }


    //Creates an admin object
    public Admin getAdmin(String email){
        Connection connection = null;

        PreparedStatement getHash = null;
        PreparedStatement getPriviliged = null;

        ResultSet rsHash = null;
        ResultSet rsPriviliged = null;

        String hashQuery = "SELECT hash FROM admin WHERE email = ?";
        String priviligedQuery = "SELECT priviliged FROM admin WHERE email = ?";

        Admin admin;

        String hash;
        boolean isPriviliged;

        try {
            connection = DBCleanup.getConnection();

            if(adminExists(email)) {
                getHash = connection.prepareStatement(hashQuery);
                getHash.setString(1, email);
                rsHash = getHash.executeQuery();
                rsHash.next();
                hash = rsHash.getString("hash");

                getPriviliged = connection.prepareStatement(priviligedQuery);
                getPriviliged.setString(1, email);
                rsPriviliged = getPriviliged.executeQuery();
                rsPriviliged.next();
                if(rsPriviliged.getByte("priviliged") == 1){
                    isPriviliged = true;
                }else{
                    isPriviliged = false;
                }
                return new Admin(email, hash, isPriviliged);
            }else{
                System.out.println("Given email does not exist");
                return null;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getAdmin()");
        }finally{
            DBCleanup.closeStatement(getHash);
            DBCleanup.closeStatement(getPriviliged);
            DBCleanup.closeResultSet(rsHash);
            DBCleanup.closeResultSet(rsPriviliged);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    public boolean addAdmin(String email, String hash, boolean priviliged){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String userInsert = "INSERT INTO admin(email, hash, priviliged) VALUES(?, ?, ?)";

        byte priv = 1;
        byte notPriv = 0;

        try{
            connection = DBCleanup.getConnection();

            if(!adminExists(email)){
                preparedStatement = connection.prepareStatement(userInsert);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, hash);
                if(priviliged){
                    preparedStatement.setByte(3, priv);
                }else{
                    preparedStatement.setInt(3, notPriv);
                }
                preparedStatement.executeUpdate();
                return true;
            }else{
                System.out.println("User already exists");
                return false;
            }
        }catch(SQLException e){
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
            connection = DBCleanup.getConnection();

            if(adminExists(email)) {
                preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1, email);
                if (preparedStatement.executeUpdate() != 0) {
                    return true;
                } else {
                    System.out.println("Zero rows affected");
                    return false;
                }
            }else{
                System.out.println("Given email does not exist");
            }
        }catch(SQLException e){
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
            connection = DBCleanup.getConnection();

            if(adminExists(email)){
                preparedStatement = connection.prepareStatement(saltQuery);
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                salt = resultSet.getString("salt");
                return salt;
            }else{
                System.out.println("Given email does not exist");
                return null;
            }
        }catch(SQLException e){
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
            connection = DBCleanup.getConnection();

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
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    public boolean isPriviliged(String email){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String priviligedQuery = "SELECT priviliged FROM admin WHERE email = ?";

        byte priv = 1;

        try{
            connection = DBCleanup.getConnection();

            if(adminExists(email)){
                preparedStatement = connection.prepareStatement(priviligedQuery);
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                byte res = resultSet.getByte("priviliged");
                if(res == priv){
                    return true; //User is priviliged
                }else{
                    return false;//User is not priviliged
                }
            }else{
                System.out.println("Given email does not exist");
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getPriviliged()");
        }finally{
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    public ArrayList<Admin> getAllAdmins(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Admin> allAdmins = new ArrayList<Admin>();

        String docksQuery = "SELECT email FROM admin";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(docksQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                allAdmins.add(getAdmin(resultSet.getString("email")));
            }
            return allAdmins;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getAllAdmins()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }
}

