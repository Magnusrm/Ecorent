package bike.bikeType;

import changescene.ChangeScene;
import changescene.popupScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
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
    private ListView<?> typeListView;

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

    }

    @FXML
    void newType(ActionEvent event) throws Exception {

        TextInputDialog dialog = new TextInputDialog("wow");
        dialog.setTitle("New bike type");
        dialog.setHeaderText(null);
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> System.out.println("Name: " + name));

    }

    @FXML
    void saveChanges(ActionEvent event) throws Exception {

        // change to bike scene
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeView.fxml");
    }

}
