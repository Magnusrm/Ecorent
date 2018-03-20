package bike.bikeType;

import changescene.ChangeScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class bikeTypeController {

    @FXML
    private Button bikeViewBtn;

    @FXML
    private Button saveChangesBtn;

    @FXML
    private Button editTypeNameBtn;

    @FXML
    private Button deleteTypeBtn;

    @FXML
    private Button newTypeBtn;

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
    private ListView<String> typeListView = new ListView<>();
    private ObservableList<String> types = FXCollections.observableArrayList("DBS","DIAMANT","REDBONE");


    @FXML
    void changeToBikeView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeView.fxml");
    }

    @FXML
    void deleteType(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete type");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you would like to delete the selected type?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... IF OK
        } else {
            // ... IF CANCEL
        }
    }

    @FXML
    void editTypeName(ActionEvent event) {
        typeListView.setItems(types);
    }

    @FXML
    void newType(ActionEvent event) throws Exception {


        TextInputDialog dialog = new TextInputDialog("wow");
        dialog.setTitle("New bike type");
        dialog.setHeaderText(null);
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            System.out.println(name + " blir registrert som en ny type");
        });

    }

    @FXML
    void saveChanges(ActionEvent event) throws Exception {

        // change to bike scene
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeView.fxml");
    }

    public void updateInfo(){
        typeListView.setItems(types);
    }


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
