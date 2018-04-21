package control;

/**
 * RepairSent.java
 * @author Team007
 *
 * This is a sub-class of Repair.java
 * It keeps an overview of repairs sent, but not returned.
 * Created to make it easier to keep different repair statuses from
 * each other.
 * Has it own versions of equals- and toString-method.
 */
public class RepairSent extends Repair{
    public RepairSent(String dateSent, String beforeDesc, int bikeId){
        super(dateSent,beforeDesc,bikeId);
    }//end constructor

    @Override
    public boolean equals(Object o){
        if (o == null) { throw new IllegalArgumentException("The object you are comparing cannot be null"); }
        if (!(o instanceof Repair)) {
            return false;
        }//end condition

        Repair r = (Repair) o;

        return (((Repair) o).getBikeId() == r.getBikeId()
                && (((Repair)o).getBeforeDesc()).equals(r.getBeforeDesc()) && (((Repair)o).getDateSent()).equals(r.getDateSent()));
    }//end method

    @Override
    public String toString(){
        return "Repair ID: " + getRepair_id()
                +"\nREPAIR NOT RETURNED"
                +"\nBike ID: " + getBikeId()
                + "\nDate sent: " + getDateSent()
                + "\nDescription before: " + getBeforeDesc();
    }//end method
}//end class
