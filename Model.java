import javax.xml.transform.Result;
import java.sql.*;
import java.util.Date;



public class Model {
    private Connection connection;
    private Statement statement;
    private String driver;
    private String name;

    public Model(){
        this.driver = "com.mysql.jdbc.Driver";
        this.name = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb";
        startConnection();
    }

    private void startConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(name);
            statement = connection.createStatement();
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    //Closing the connection and the statement
    public void endConnection() {
        try{
            connection.close();
            statement.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Metod that retrieves a bike from the database as an object
    public Bike getBike(int bikeID) {
        Bike bike = null;

        try {
            ResultSet rs1 = statement.executeQuery("SELECT reg_date FROM bike WHERE bike_id = '" + bikeID + "';");
            Date regDate = rs1.getDate(0);

            ResultSet rs2 = statement.executeQuery("SELECT price FROM bike WHERE bike_id = '" + bikeID + "';");
            Double price = rs2.getDouble(0);

            ResultSet rs3 = statement.executeQuery("SELECT make FROM bike WHERE bike_id = '" + bikeID + "';");
            String make = rs3.getString(0);

            ResultSet rs4 = statement.executeQuery("SELECT name FROM type WHERE type_id IN(SELECT type_id FROM bike WHERE bike_id ='" + bikeID + "');");
            String type = rs4.getString(0);

            bike = new Bike(bikeID, regDate, price, make, type);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bike;
    }

    //Private method that helps check if the type name exists in the database**
    private boolean typeExists(String name){
        try{
            name.toLowerCase();
            ResultSet rs = statement.executeQuery("SELECT name FROM type WHERE LOWER(type.name ='" + name + "');");
            boolean exists = false;
            while(rs.next()){
                exists = true;
            }
            if(exists){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }


    //Method that lets the user add a new type to the database
    public int addType(String name){
        String typeInsert = "INSERT INTO type(type_id, name) VALUES (DEFAULT,'" + name +"');";
        try{
            connection.setAutoCommit(false);

            if(!typeExists(name)) { //Checks if given name is in the database already
                if (statement.executeUpdate(typeInsert) != 0) {
                    connection.commit();
                    return (statement.executeQuery("SELECT MAX(type_id) FROM type").getInt(0));
                } else {
                    connection.rollback();
                    return -1;
                }
            }else{
                System.out.println("Type already exists");
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                connection.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return -1;
    }

    //Adds a new bike to the database
    public int addBike(String date, double price, String make){
        String bikeInsert = "INSERT INTO bike(bike_id, reg_date, price, make) VALUES (DEFAULT,'" + date + "','" + price +
                "','" + make + "');";

        try{
            connection.setAutoCommit(false);

            if(statement.executeUpdate(bikeInsert) != 0){
                connection.commit();
                return (statement.executeQuery("SELECT MAX(bike_id) from bike").getInt(0));
            }else{
                connection.rollback();
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                connection.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return -1;
    }



    //Adds a new dock to the database
    public int addDock(String name, double pwrUsg, double xCord, double yCord){
        String dockInsert = "INSERT INTO dock(dock_id, name, pwr_usage, x_cord, y_cord) VALUES(DEFAULT,'" + name + "','" + pwrUsg +
                "','" + xCord + "','" + yCord + "';)";
        try{
            connection.setAutoCommit(false);

            if(statement.executeUpdate(dockInsert) != 0){
                connection.commit();
                return (statement.executeQuery("SELECT MAX(dock_id) FROM dock").getInt(0));
            }else{
                connection.rollback();
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                connection.setAutoCommit(true);
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return -1;
    }

    //Returns a given dock from the database
    public Dock getDock(String name){
        Dock dock = null;

        try{
            ResultSet rs1 = statement.executeQuery("SELECT dock_id FROM dock WHERE name = '" + name + "';");
            int dockID = rs1.getInt(0);

            ResultSet rs2 = statement.executeQuery("SELECT pwr_usage FROM dock WHERE name = '" + name + "';");
            double pwrUsg = rs2.getDouble(0);

            ResultSet rs3 = statement.executeQuery("SELECT x_cord FROM dock WHERE name = '" + name + "';");
            double xCord = rs3.getDouble(0);

            ResultSet rs4 = statement.executeQuery("SELECT y_cord FROM dock WHERE name = '" + name + "';");
            double yCord = rs4.getDouble(0);

            dock = new Dock(dockID, name, pwrUsg, xCord, yCord);

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return dock;
    }




    //Adds the first part of the repair to the database
    public int sendRepair(int bikeID, String dateSent, String bDescription){
        String sendInsert = "INSERT INTO repair(repair_id, date_sent, before_desc, date_received, after_desc, price, bike_id)" +
                "VALUES(DEFAULT,'" + dateSent + "','" + bDescription + "',NULL, NULL, NULL,'" + bikeID +"');";
        try{
            connection.setAutoCommit(false);

            if(statement.executeUpdate(sendInsert) != 0){
                connection.commit();
                return (statement.executeQuery("SELECT MAX(repair_id) FROM repair").getInt(0));
            } else{
                connection.rollback();
                return -1;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                connection.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return -1;
    }

    //Adds the rest of the repair to the database
    public boolean returnRepair(int repairID, String dateReceived, String aDescription, double price){
        String returnInsert = "UPDATE repair SET date_received = '" + dateReceived + "', after_desc = '" + aDescription +
                "', price = '" + price + "'WHERE repair_id = '" + repairID + "';";
        try{
            connection.setAutoCommit(false);

            if(statement.executeUpdate(returnInsert) != 0){
                connection.commit();
                return true;
            }else{
                connection.rollback();
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                connection.setAutoCommit(true);
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    //Retrieves a repair object from the database
    public Repair getRepair(int repairID){
        Repair repair = null;

        try{
            ResultSet rs1 = statement.executeQuery("SELECT date_sent from repair WHERE repair_id ='" + repairID + "';");
            Date dateSent = rs1.getDate(0);

            ResultSet rs2 = statement.executeQuery("SELECT before_desc from repair WHERE repair_id ='" + repairID + "';");
            String beforeDesc = rs2.getString(0);

            ResultSet rs3 = statement.executeQuery("SELECT date_received from repair WHERE repair_id ='" + repairID + "';");
            Date dateReceived = rs3.getDate(0);

            ResultSet rs4 = statement.executeQuery("SELECT after_desc from repair WHERE repair_id ='" + repairID + "';");
            String afterDesc = rs4.getString(0);

            ResultSet rs5 = statement.executeQuery("SELECT price from repair WHERE repair_id ='" + repairID + "';");
            double price = rs5.getDouble(0);

            ResultSet rs6 = statement.executeQuery("SELECT bike_id from repair WHERE repair_id ='" + repairID + "';");
            int bikeID = rs6.getInt(0);

            repair = new Repair(repairID, dateSent, beforeDesc, dateReceived, afterDesc, price, bikeID);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return repair;
    }





    //Checks if an email already exists in the database
    public boolean userExists(String email){
        try{
            ResultSet rs = statement.executeQuery("SELECT email FROM admin WHERE email = '" + email + "';");
            boolean exists = false;
            while(rs.next()){
                exists = true;
            }
            if(exists){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Returns the hash value of a given email
    public String getHash(String email){
        String hash = null;
        try{
            if(userExists(email)){
                ResultSet rs = statement.executeQuery("SELECT hash FROM admin WHERE email = '" + email + "';");
                hash = rs.getString(0);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return hash;
    }

}
