package bike;

import changescene.MainMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * BikeController.java
 * @author Team007
 * @version 1.0
 *
 * This class handles deleting bikes based on the bikeID given in bikeIdField, and the views BikeEditView.fxml, BikeTypeView.fxml, BikeNewView.fxml, BikeRepairView.fxml and BikeInfoView.fxml.
 */
public class BikeController extends MainMethods {

    @FXML
    private TextField bikeIdField;

    @FXML
    void changeToBikeEditView(ActionEvent event) throws Exception {
        changeScene(event, "/bike/bikeEdit/BikeEditView.fxml");
    }

    @FXML
    void changeToBikeTypeView(ActionEvent event) throws Exception {
        changeScene(event, "/bike/bikeType/BikeTypeView.fxml");
    }

    @FXML
    void changeToBikeNewView(ActionEvent event) throws Exception {
        changeScene(event, "/bike/bikeNew/BikeNewView.fxml");
    }

    @FXML
    void changeToBikeRepairView(ActionEvent event) throws Exception {
        changeScene(event,"/bike/bikeRepair/BikeRepairView.fxml");
    }

    @FXML
    void changeToBikeInfoView(ActionEvent event) throws Exception {
        changeScene(event, "/bike/bikeInfo/BikeInfoView.fxml");
    }


    /**
     * Deleted the bike based on the clients input in the TextField.
     *
     * @param event Buttonclick
     */
    @FXML
    void deleteBike(ActionEvent event) {
        factory.updateSystem();
        boolean confirmation = newConfirmationAlert("Are you sure?", "Are you sure you would like to delete this bike?");

        if(confirmation){
            if(factory.delBike(Integer.parseInt(bikeIdField.getText()))){
                newInfoAlert("Success!","Bike " + bikeIdField.getText() + " is now deleted!" );
            }else{
                newInfoAlert("Failed!", "Something went wrong! Please try again");
            }//end condition
        }else{
            newInfoAlert("Cancelling", "Bike " + bikeIdField.getText() + " will not be deleted" );
        }//end condition
    }//end method
}
