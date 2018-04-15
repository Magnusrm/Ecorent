
package control;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Repair {
    private int repair_id;
    private LocalDate date_sent;
    private String before_desc;
    private LocalDate date_received;
    private String after_desc;
    private double price;
    private int bike_id;

    @Override
    public boolean equals(Object o){
        if (o == null) { throw new IllegalArgumentException("The object you are comparing cannot be null"); }
        if (!(o instanceof Repair)) {
            return false;
        }

        Repair r = (Repair) o;

        return (((Repair) o).getBikeId() == r.getBikeId() && (((Repair) o).getAfterDesc().equals(r.getAfterDesc())
                && (((Repair)o).getBeforeDesc()).equals(r.getBeforeDesc()) && (((Repair)o).getDateSent()).equals(r.getDateSent())
                && (((Repair)o).getDateReceived()).equals(r.getDateReceived()) &&(((Repair)o).getPrice()) == r.getPrice()));

    }

    public Repair(String dateSent, String beforeDesc, int bikeId){
        this.repair_id = -1; //use the database to set repairId

        this.date_sent = toDate(dateSent);
        this.before_desc = beforeDesc;
        this.date_received = null;
        this.after_desc = null;
        this.price = 0;
        this.bike_id = bikeId;
    }
    public Repair(String dateSent, String beforeDesc, String dateReceived, String afterDesc, double price, int bikeId){
        this.repair_id = -1; //use the database to set repairId

        this.date_sent = toDate(dateSent);
        this.before_desc = beforeDesc;
        this.date_received = toDate(dateReceived);
        this.after_desc = afterDesc;
        this.price = price;
        this.bike_id = bikeId;
    }

    public int getRepair_id(){
        return repair_id;
    }

    public LocalDate getDateReceived() {
        return date_received;
    }

    public LocalDate getDateSent() {
        return date_sent;
    }

    public String getBeforeDesc() {
        return before_desc;
    }

    public String getAfterDesc() {
        return after_desc;
    }

    public double getPrice() {
        return price;
    }

    public int getBikeId() {
        return bike_id;
    }

    public void setRepairId(int id){
        this.repair_id = id;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setAfterDesc(String after_desc) {
        this.after_desc = after_desc;
    }

    public void setBeforeDesc(String before_desc) {
        this.before_desc = before_desc;
    }

    public void setBikeId(int bike_id) {
        this.bike_id = bike_id;
    }

    public void setDateSent(String dateSent) {
        this.date_sent = toDate(dateSent);
    }

    public void setDateReceived(String dateReceived) {
        this.date_received = toDate(dateReceived);
    }

    public LocalDate toDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate toDate = LocalDate.parse(date, formatter);
        return toDate;
    }

    public String toString(){
        return "control.Repair ID: " + repair_id
                +"\nBike ID: " + bike_id
                + "\nDate sent: " + date_sent
                + "\nDescription before: " + before_desc
                + "\nDate received: " + date_received
                + "\nDescrifton after: " + after_desc
                + "\nPrice: " + price;
    }

    //Test
    public static void main(String[] args) throws ParseException {
        String now = "2018-03-12";
        Repair test = new Repair(now, "Problems with front wheel", 1);
        Repair test2 = new Repair("2018-02-22", "Punctured tire", "2018-02-27", "Change tube and rutine check", 30.4, 1);
        System.out.println("\nOutprint of bike before testing\n");
        System.out.println(test2);
        System.out.println("\nTests");
        if(test2.getPrice() == 30.4){
            System.out.println("Test 1 successful");
        }
        test2.setPrice(45);
        if(test2.getPrice() == 45){
            System.out.println("Test 2 successful");
        }
        test2.setRepairId(3);
        test2.setAfterDesc("setTEST");
        test2.setBeforeDesc("setTEST");
        test2.setDateSent("2000-01-01");
        test2.setDateReceived("2001-01-01");
        test2.setBikeId(5);

        System.out.println("\nOutprint of repair after testing\n");
        System.out.println(test2);
    }
}