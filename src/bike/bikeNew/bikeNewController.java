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
    private TextField pwrUsage;

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
    void createNewBike(ActionEvent event) throws Exception{
        ChangeScene change = new ChangeScene();
        change.setScene(event, "/bike/bikeView.fxml");
        Factory factory = new Factory();
        Bike bike = new Bike(LocalDate.now(),Double.parseDouble(priceField.getText()),
                makeField.getText(),new Type("Racer"), Double.parseDouble(pwrUsage.getText()));
        factory.addBike(bike);
    }

}
