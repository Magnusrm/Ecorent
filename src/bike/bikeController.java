package bike;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import changescene.ChangeScene;

public class bikeController {

    @FXML
    private Button editBikeBtn;

    @FXML
    private Button infoBikeBtn;

    @FXML
    private Button deleteBikeBtn;

    @FXML
    private Button repairBikeBtn;

    @FXML
    private Button mainViewBtn;

    @FXML
    private Button newBikeBtn;

    @FXML
    private Button editBikeTypesBtn;

    @FXML
    private TextField bikeIdField;

    @FXML
    void changeToBikeEditView(ActionEvent event) throws Exception {

    }

    @FXML
    void changeToBikeTypeView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeType/bikeTypeView.fxml");
    }


    @FXML
    void changeToBikeNewView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeNew/bikeNewView.fxml");
    }

    @FXML
    void changeToBikeRepairView(ActionEvent event) {

    }

    @FXML
    void changeToDockScene(ActionEvent event) {

    }

    @FXML
    void changeToMainView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/main/mainView.fxml");
    }

    @FXML
    void changetoBikeInfoView(ActionEvent event) {

    }

    @FXML
    void deleteBike(ActionEvent event) {

    }

}
