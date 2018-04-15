package model;

import control.Admin;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * The class that handles saving, deleting and editing new administrators to the database.
 */
public class AdminModel {

    /**
     * Checks if an admin is in the database by searching by email.
     *
     * @param  email    the email that we want to know if exists.
     * @return true     if that email is already in the database.
     * @return false    if that email isn't in the database.
     */
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
            return exists;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - userExists()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }


    /**
     * Returns an admin Object from the database.
     *
     *
     * @param email         the email that is searched for in the database.
     * @return Object       an admin object with that emails corresponding data.
     * @return null         if the method fails.
     */
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

    /**

     * Adds a new admin to the database.
     *
     * @param email         the email of the new user.
     * @param hash          the value of the hashed password.
     * @param priviliged    if the administrator has priviliged rights.
     * @return true         if the method is successful.
     * @return false        if the method fails.
     */
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

    /**
     * Deletes an admin from the database.
     *
     * @param email         the email of the administrator that is to be deleted from the database.
     * @return true         if the method is successful.
     * @return false        if the method fails.
     */
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


    /**
     * Returns the hashed (with salt) value that belongs to a given email.
     *
     * @param email         the email address that the hash value belongs to.
     * @return hash         the hashed password.
     * @return null         if the method fails.
     */
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

    /**
     * Checks if an admin has the rights to make new admins.
     *
     * @param email         the email of the admin that is to be checked.
     * @return true         if the admin has priviliged rights.
     * @return false        if the admin doesn't have priviliged rights.
     */
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

                return res == priv;
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

    /**

     * Returns an ArrayList of all the admins in the database.
     *
     * @return allAdmins        an ArrayList of admin-objects.
     * @return null             if the method fails.
     */
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