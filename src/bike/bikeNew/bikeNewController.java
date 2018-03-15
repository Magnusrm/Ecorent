package bike.bikeNew;

import Control.Factory;
import changescene.ChangeScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Control.*;

import java.time.LocalDate;

public class bikeNewController {

    @FXML
    private TextField makeField;

    @FXML
    private Button bikeViewBtn;

    @FXML
    private TextField priceField;

    @FXML
    private TextField buyDateField;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private Button saveBtn;

    @FXML
    void changeToBikeView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeView.fxml");
    }


    @FXML
    void createNewBike(ActionEvent event) {
        try {
            System.out.println("Funker enda før factory");
            Factory factory = new Factory();
            System.out.println("Funker før new bike");
            System.out.println(priceField.getText());
            Bike bike = new Bike(LocalDate.now(), Double.parseDouble(priceField.getText()),
                    makeField.getText(), new Type("Racer"), 22.2);
            System.out.println(LocalDate.now().toString());
            System.out.println("Klarer å skifte scene");
            System.out.println(bike);
            System.out.println(factory.addBike(bike));
            for(Bike b: factory.getBikes()){
                System.out.println(b);
            }//end loop
            ChangeScene change = new ChangeScene();
            change.setScene(event, "/bike/bikeView.fxml");
        }//end try
        catch(Exception e){
                e.printStackTrace();
            }//end catch


    }//end method

}
