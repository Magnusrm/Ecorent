package bike.bikeNew;

import control.Factory;
import changescene.ChangeScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import control.*;

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
    private Button homeBtn;

    @FXML
    private Button bikesBtn;

    @FXML
    private Button docksBtn;

    @FXML
    private Button mapBtn;

    @FXML
    private Button statsBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button adminBtn;

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
            Type type = new Type("funk");
            if(factory.addType(type))System.out.println(type);
            ChangeScene change = new ChangeScene();
            change.setScene(event, "/bike/bikeView.fxml");
        }//end try
        catch(Exception e){
                e.printStackTrace();
            }//end catch


    }//end method

    @FXML
    void changeToBikeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeView.fxml");
    }

    @FXML
    void changeToDockScene(ActionEvent event) {
      /*  ChangeScene cs = new ChangeScene();
        cs.setScene(event, "");*/
    }

    @FXML
    void changeToMapScene(ActionEvent event) {
        /*ChangeScene cs = new ChangeScene();
        cs.setScene(event, "");*/
    }

    @FXML
    void changeToStatsScene(ActionEvent event) {
        /*ChangeScene cs = new ChangeScene();
        cs.setScene(event, "");*/
    }

    @FXML
    void changeToAdminScene(ActionEvent event) {
        /*ChangeScene cs = new ChangeScene();
        cs.setScene(event, "");*/
    }

    @FXML
    void changeToHomeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/main/mainView.fxml");
    }


    @FXML
    void logOut(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/login/loginView.fxml");
    }

}
