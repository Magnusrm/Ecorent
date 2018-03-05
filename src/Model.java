import java.sql.*;

public class Model {
    private Connection forbindelse;
    private Statement setning;
    private String driver;
    private String navn;

    private void startForbindelse() {
        try {

        }catch(ClassNotFoundException e){
            System.out.println();
        }catch(SQLException e){
            System.out.println();
        }catch(Exception e){
            System.out.println();
        }
    }
}
