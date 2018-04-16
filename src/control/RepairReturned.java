/**
 * RepairReturned.java
 * @author Team007
 *
 * This is a sub-class of Repair.java
 * Created to keep different repair statuses from
 * each other.
 * This is a class for completed/returned repairs.
 */

package control;

public class RepairReturned extends Repair{
    public RepairReturned(String dateSent, String beforeDesc, String dateReceived,
                  String afterDesc, double price, int bikeId){
        super(dateSent,beforeDesc,dateReceived,afterDesc,price,bikeId);
    }//end constructor

    public RepairReturned(String dateReceived, String afterDesc, double price, int bikeId){
        super(dateReceived,afterDesc,price,bikeId);
    }//end constructor


}//end class
