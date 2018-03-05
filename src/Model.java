import java.sql.*;

public class Model {
    private Connection connection;
    private Statement statement;
    private String driver;
    private String name;
    
    public Model(String driver, String name){
        this.driver = driver;
        this.name = name;
        startConnection();
    }

    //
    private void startConnection() {
        try {
            Class.forName(driver);
            connection = Driver
        }catch(ClassNotFoundException e){
            System.out.println();
        }catch(SQLException e){
            System.out.println();
        }catch(Exception e){
            System.out.println();
        }
    }
}
