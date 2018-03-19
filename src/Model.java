package src;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class Model {
    private String driver = "com.mysql.jdbc.Driver";
    private String dbName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb";

    public Model(){
    }


    //Method that retrieves a bike from the database as an object
    public Bike getBike(int bikeID) throws SQLException, ClassNotFoundException {

        Connection connection = null;

        Type type;
        Bike bike;

        PreparedStatement getDate = null;
        PreparedStatement getPrice = null;
        PreparedStatement getMake = null;
        PreparedStatement getType = null;
        PreparedStatement getPwr = null;

        String dateQuery = "SELECT reg_date FROM bike WHERE bike_id = ?";
        String priceQuery = "SELECT price FROM bike WHERE bike_id = ?";
        String makeQuery = "SELECT make FROM bike WHERE bike_id = ?";
        String typeQuery = "SELECT name FROM type WHERE type_id IN(SELECT type_id FROM bike WHERE bike_id = ?";
        String pwrQuery = "SELECT pwr_usg FROM bike WHERE bike_id = ?";

        ResultSet rsDate = null;
        ResultSet rsPrice = null;
        ResultSet rsMake = null;
        ResultSet rsType = null;
        ResultSet rsPwr = null;

        String regDate;
        LocalDate localDate;
        double price;
        String make;
        String typeName;
        double pwrUsg;

        try {
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            getDate = connection.prepareStatement(dateQuery);
            getDate.setInt(1, bikeID);
            rsDate = getDate.executeQuery();
            rsDate.next();
            regDate = rsDate.getString("reg_date");
            localDate = LocalDate.parse(regDate);

            getPrice = connection.prepareStatement(priceQuery);
            getPrice.setInt(1, bikeID);
            rsPrice = getPrice.executeQuery();
            rsPrice.next();
            price = rsPrice.getDouble("price");

            getMake = connection.prepareStatement(makeQuery);
            getMake.setInt(1, bikeID);
            rsMake = getMake.executeQuery();
            rsMake.next();
            make = rsMake.getString("make");

            getType = connection.prepareStatement(typeQuery);
            getType.setInt(1, bikeID);
            rsType = getType.executeQuery();
            rsType.next();
            typeName = rsType.getString("name");

            getPwr = connection.prepareStatement(pwrQuery);
            getPwr.setInt(1, bikeID);
            rsPwr = getPwr.executeQuery();
            rsPwr.next();
            pwrUsg = rsPwr.getDouble("pwr_usg");

            type = new Type(typeName);
            bike = new Bike(localDate, price, make, type, pwrUsg);

            return bike;
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "getBike()");
        } catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + "getBike()");
        }finally{
            DBCleanup.closeStatement(getDate);
            DBCleanup.closeStatement(getPrice);
            DBCleanup.closeStatement(getMake);
            DBCleanup.closeStatement(getType);
            DBCleanup.closeStatement(getPwr);

            DBCleanup.closeResultSet(rsDate);
            DBCleanup.closeResultSet(rsPrice);
            DBCleanup.closeResultSet(rsMake);
            DBCleanup.closeResultSet(rsType);
            DBCleanup.closeResultSet(rsPwr);

            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    //Updates the values of a given bike
    public boolean editBike(int bikeID, String regDate, double price, String make, double pwrUsg, String typeName)
            throws SQLException, ClassNotFoundException{
        int typeID = typeExists(typeName);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String bikeInsert = "UPDATE bike SET reg_date = ?, price = ?, make = ? ,pwr_usg = ?, type_id = ? " +
                "WHERE bike_id = ?;";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(bikeInsert);
            preparedStatement.setString(1, regDate);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, make);
            preparedStatement.setDouble(4, pwrUsg);
            preparedStatement.setDouble(5, typeID);
            preparedStatement.setInt(6, bikeID);

            if(preparedStatement.executeUpdate(bikeInsert) != 0){
                connection.commit();
                return true;
            }else{
                DBCleanup.rollback(connection);
                return false;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage() + "editBike()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + "editBike()");
        }finally {
          DBCleanup.closeResultSet(resultSet);
          DBCleanup.closeStatement(preparedStatement);
          DBCleanup.setAutoCommit(connection);
          DBCleanup.closeConnection(connection);
        }
        return false;
    }



    //Private method that helps check if the type name exists in the database**
    private int typeExists(String name)throws SQLException, ClassNotFoundException{
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String existsQuery = "SELECT name FROM type WHERE LOWER(type.name = ?";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(existsQuery);
            preparedStatement.setString(1, name.toLowerCase());
            resultSet = preparedStatement.executeQuery();

            boolean exists = false;
            while(resultSet.next()){
                exists = true;
            }
            if(exists){
                resultSet.next();
                return resultSet.getInt("name");
            }else{
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + "typeExists()");
            return -1;
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + "typeExists()");
            return -1;
        }finally{
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
    }


    //Method that lets the user add a new type to the database
    public int addType(String name)throws SQLException, ClassNotFoundException{
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
                    DBCleanup.rollback(connection);
                }
            }else{
                System.out.println("Type already exists");
                return -1;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage() + "addType()");
            //DBCleanup.rollback(connection);
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage() + "addType()");
            //DBCleanup.rollback(connection);
        }finally {
            DBCleanup.setAutoCommit(connection);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    //Returns an arraylist of strings with all the types in the database
    public ArrayList<String> getTypes(int typeID)throws SQLException, ClassNotFoundException{
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
            System.out.println(e.getMessage() + "getTypes()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + "getTypes()");
        }finally {
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    public boolean editType(int typeID, String name)throws SQLException, ClassNotFoundException{
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
                DBCleanup.rollback(connection);
                return false;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage() + "editType()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + "editType()");
        }
        return false;
    }

    //Adds a new bike to the database
    public int addBike(String date, double price, String make, String type)throws SQLException, ClassNotFoundException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int typeID = typeExists(type);

        String bikeInsert = "INSERT INTO bike(bike_id, reg_date, price, make, type_id) VALUES (DEFAULT, ?, ?, ?, ?);";
        String maxBikeID = "SELECT MAX(bike_id) from bike";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(bikeInsert);
            preparedStatement.setString(1, date);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, make);
            preparedStatement.setInt(4, typeID);

            if(preparedStatement.executeUpdate() != 0){
                preparedStatement = connection.prepareStatement(maxBikeID);
                resultSet = preparedStatement.executeQuery()
                connection.commit();
                resultSet.next();
                return resultSet.getInt("MAX(bike_id)");
            }else{
                DBCleanup.rollback(connection);
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + "addBike()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + "addBike()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.setAutoCommit(connection);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }



    //Adds a new dock to the database
    public int addDock(String name, double pwrUsg, double xCord, double yCord)throws SQLException, ClassNotFoundException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String dockInsert = "INSERT INTO dock(dock_id, name, pwr_usage, x_cord, y_cord) VALUES(DEFAULT, ?, ?, ?,?)";
        String maxDockID = "SELECT MAX(dock_id) FROM dock";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(dockInsert);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, pwrUsg);
            preparedStatement.setDouble(3, xCord);
            preparedStatement.setDouble(4, yCord);

            if(preparedStatement.executeUpdate() != 0){
                preparedStatement = connection.prepareStatement(maxDockID);
                resultSet = preparedStatement.executeQuery();
                connection.commit();
                resultSet.next()
                return resultSet.getInt("MAX(dock_id)"));
            }else{
                DBCleanup.rollback(connection);
                return -1;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage() + "addDock()");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage() + "addDock()");
        }finally{
          DBCleanup.setAutoCommit(connection);
          DBCleanup.closeResultSet(resultSet);
          DBCleanup.closeStatement(preparedStatement);
          DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    //Returns a given dock from the database
    public Dock getDock(String name)throws SQLException, ClassNotFoundException{
        Connection connection = null;

        Dock dock = null;

        PreparedStatement getDockID = null;
        PreparedStatement getXCord = null;
        PreparedStatement getYCord = null;

        ResultSet rsDockID = null;
        ResultSet rsXCord = null;
        ResultSet rsYCord = null;

        int dockID;
        double xCord;
        double yCord;

        String dockIDQuery = "SELECT dock_id FROM dock WHERE name = ?";
        String xCordQuery = "SELECT x_cord FROM dock WHERE name = ?";
        String yCordQuery = "SELECT y_cord FROM dock WHERE name = ?";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            getDockID = connection.prepareStatement(dockIDQuery);
            getDockID.setString(1, name);
            rsDockID = getDockID.executeQuery();
            rsDockID.next();
            dockID = rsDockID.getInt("dock_id");

            getXCord = connection.prepareStatement(xCordQuery);
            getXCord.setString(1, name);
            rsXCord = getXCord.executeQuery();
            rsXCord.next();
            xCord = rsXCord.getDouble("x_cord");

            getYCord = connection.prepareCall(yCordQuery);
            getYCord.setString(1, name);
            rsYCord = getYCord.executeQuery();
            rsYCord.next();
            yCord = rsYCord.getDouble("y_cord");

            dock = new Dock(name, /*pwrUsg,*/ xCord, yCord);
            return dock;

        }catch(SQLException e){
            System.out.println(e.getMessage() + "getDock()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + "getDock()");
        }finally {
            DBCleanup.closeResultSet(rsDockID);
            DBCleanup.closeResultSet(rsXCord);
            DBCleanup.closeResultSet(rsYCord);

            DBCleanup.closeStatement(getDockID);
            DBCleanup.closeStatement(getXCord);
            DBCleanup.closeStatement(getYCord);

            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    public boolean editDock(int dockID, String name, double xCord, double yCord)throws  SQLException, ClassNotFoundException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String dockInsert = "UPDATE dock SET name = ?, x_cord = ?, y_cord = ? WHERE dock_id = ?";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(dockInsert);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, xCord);
            preparedStatement.setDouble(3, yCord);
            preparedStatement.setInt(4, dockID);

            if(preparedStatement.executeUpdate() != 0){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage() + "editDock()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + "editDock()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    //Returns an ArrayList of bikeID's that are docked at a certain docking station.
    public ArrayList<Integer> bikesAtDock(int dockID)throws SQLException, ClassNotFoundException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Integer> bikes = new ArrayList<Integer>();

        String bikesQuery = "SELECT bike_id FROM bike NATURAL JOIN dock WHERE(dock_id = ?)";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(bikesQuery);
            preparedStatement.setInt(1, dockID);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                bikes.add(resultSet.getInt("bike_id"));
            }
            return bikes;
        }catch (SQLException e){
            System.out.println(e.getMessage() + "bikesAtDock()");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage() + "bikesAtDock()");
        }finally {
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }




    //Adds the first part of the repair to the database
    public int sendRepair(int bikeID, String dateSent, String bDescription)throws SQLException, ClassNotFoundException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sendInsert = "INSERT INTO repair(repair_id, date_sent, before_desc, date_received, after_desc, price, bike_id)" +
                "VALUES(DEFAULT, ?, ?,NULL, NULL, NULL, ?)";
        String maxQuery = "SELECT MAX(repair_id) FROM repair";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sendInsert);
            preparedStatement.setString(1, dateSent);
            preparedStatement.setString(2, bDescription);
            preparedStatement.setInt(3, bikeID);

            if(preparedStatement.executeUpdate() != 0){
                preparedStatement = connection.prepareStatement(maxQuery);
                resultSet = preparedStatement.executeQuery();
                connection.commit();
                resultSet.next();
                return resultSet.getInt("MAX(repair_id)");
            } else{
                DBCleanup.rollback(connection);
                return -1;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage() + "sendRepair()");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage() + "sendRepair()");
        }finally {
           DBCleanup.setAutoCommit(connection);
           DBCleanup.closeResultSet(resultSet);
           DBCleanup.closeStatement(preparedStatement);
           DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    //Adds the rest of the repair to the database
    public boolean returnRepair(int repairID, String dateReceived, String aDescription, double price)
    throws SQLException, ClassNotFoundException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String returnInsert = "UPDATE repair SET date_received = ?, after_desc = ?, price = ? WHERE repair_id = ?";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(returnInsert);
            preparedStatement.setString(1, dateReceived);
            preparedStatement.setString(2, aDescription);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, repairID);
            preparedStatement.executeUpdate();

        }catch(SQLException e) {
            System.out.println(e.getMessage() + "returnRepair()");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage() + "returnRepair()");
        }finally{
           DBCleanup.closeStatement(preparedStatement);
           DBCleanup.closeConnection(connection);
        }
        return false;
    }

    //Retrieves a repair object from the database
    public Repair getRepair(int repairID)throws SQLException, ClassNotFoundException{
        Connection connection = null;

        PreparedStatement getDateSent = null;
        PreparedStatement getBeforeDesc = null;
        PreparedStatement getDateReceived = null;
        PreparedStatement getAfterDesc = null;
        PreparedStatement getPrice = null;
        PreparedStatement getBikeID = null;

        ResultSet rsDateSent = null;
        ResultSet rsBeforeDesc = null;
        ResultSet rsDateReceived = null;
        ResultSet rsAfterDesc = null;
        ResultSet rsPrice = null;
        ResultSet rsBikeID = null;

        String dateSent;
        String beforeDesc;
        String dateReceived;
        String afterDesc;
        double price;
        int bikeID;

        Repair repair = null;

        String dateSentQuery = "SELECT date_sent FROM repair WHERE repair_id = ?";
        String beforeDescQuery = "SELECT before_desc FROM repair WHERE repair_id = ?";
        String dateReceivedQuery = "SELECT date_received FROM repair WHERE repair_id = ?";
        String afterDescQuery = "SELECT after_desc FROM repair WHERE repair_id = ?";
        String priceQuery = "SELECT price FROM repair WHERE repair_id = ?";
        String bikeIDQuery = "SELECT bike_id FROM repair WHERE repair_id = ?";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            getDateSent = connection.prepareStatement(dateSentQuery);
            getDateSent.setInt(1, repairID);
            rsDateSent = getDateSent.executeQuery();
            rsDateSent.next();
            dateSent = rsDateSent.getString("date_sent");

            getBeforeDesc = connection.prepareStatement(beforeDescQuery);
            getBeforeDesc.setInt(1, repairID);
            rsBeforeDesc = getBeforeDesc.executeQuery();
            rsBeforeDesc.next();
            beforeDesc = rsBeforeDesc.getString("before_desc");

            getDateReceived = connection.prepareStatement(dateReceivedQuery);
            getDateReceived.setInt(1, repairID);
            rsDateReceived = getDateReceived.executeQuery();
            rsDateReceived.next();
            dateReceived = rsDateReceived.getString("date_received");

            getAfterDesc = connection.prepareStatement(afterDescQuery);
            getAfterDesc.setInt(1, repairID);
            rsAfterDesc = getAfterDesc.executeQuery();
            rsAfterDesc.next();
            afterDesc = rsAfterDesc.getString("after_desc");

            getPrice = connection.prepareStatement(priceQuery);
            getPrice.setInt(1, repairID);
            rsPrice = getPrice.executeQuery();
            rsPrice.next();
            price = rsPrice.getDouble("price");

            getBikeID = connection.prepareStatement(bikeIDQuery);
            getBikeID.setInt(1, repairID);
            rsBikeID = getBikeID.executeQuery();
            rsBikeID.next();
            bikeID = rsBikeID.getInt("bike_id");

            repair = new Repair(dateSent, beforeDesc, dateReceived, afterDesc, price, bikeID);
            return repair;

        }catch (SQLException e){
            System.out.println(e.getMessage() + "getRepair()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + "getRepair()");
        }finally{
            DBCleanup.closeStatement(getDateSent);
            DBCleanup.closeStatement(getBeforeDesc);
            DBCleanup.closeStatement(getDateReceived);
            DBCleanup.closeStatement(getAfterDesc);
            DBCleanup.closeStatement(getPrice);
            DBCleanup.closeStatement(getBikeID);

            DBCleanup.closeResultSet(rsDateSent);
            DBCleanup.closeResultSet(rsBeforeDesc);
            DBCleanup.closeResultSet(rsDateReceived);
            DBCleanup.closeResultSet(rsAfterDesc);
            DBCleanup.closeResultSet(rsPrice);
            DBCleanup.closeResultSet(rsBikeID);

            DBCleanup.closeConnection(connection);
        }
        return null;
    }





    //Checks if an email already exists in the database
    public boolean userExists(String email)throws SQLException, ClassNotFoundException{
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
            System.out.println(e.getMessage() + "userExists()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + "userExists()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    public boolean addUser(String email, String salt, String hash, boolean priviliged)throws SQLException, ClassNotFoundException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String userInsert = "INSERT INTO admin(email, salt, hash, priviliged) VALUES(?, ?, ?, ?)";

        byte priv = 1;

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            if(!userExists(email)){
                preparedStatement = connection.prepareStatement(userInsert);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, salt);
                preparedStatement.setString(3, hash);
                if(priviliged == true){
                    preparedStatement.setByte(4, priv);
                }
                preparedStatement.executeUpdate();
                return true;
            }else{
                System.out.println("User already exists");
                return false;
            }
        }catch(SQLException )
        return false;
    }

    //Returns the hash value of a given email
    public String getHash(String email)throws SQLException, ClassNotFoundException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String hash;

        String hashQuery = "SELECT hash FROM admin WHERE email = ?";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            if(userExists(email)){
                preparedStatement = connection.prepareStatement(hashQuery);
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                hash = resultSet.getString("hash");
                return hash;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + "getHash()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + "getHahs()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

}
