package bike.bikeType;

import changescene.ChangeScene;
import changescene.popupScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import control.*;

public class bikeTypeController implements Initializable{
    private Type type;
    private Factory factory = new Factory();

    @FXML
    private ListView<String> typeListView;

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
    private Button createTypeBtn;

    @FXML
    private Button deleteTypeBtn;

    @FXML
    private TextField newTypeField;



    //Notice the types are converted to String array.
    //This is to simplify the clicking and fetching process.
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            factory.updateSystem();
            ObservableList<String> types = FXCollections.observableArrayList();
            String[] visualized = new String[factory.getTypes().size()];
            for (int i = 0; i < visualized.length; i++) {
                visualized[i] = factory.getTypes().get(i).getName();
            }//end loop
            types.addAll(visualized);
            typeListView.setItems(types);
        }catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void deleteType(ActionEvent event) throws Exception {

        System.out.println(typeListView);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete type");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you would like to delete the selected type?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //... IF OK
            factory.deleteType(new Type(typeListView.getSelectionModel().getSelectedItem()));
            try{
                saveChanges(event);
                updateList();
            } catch(Exception e){

            }

        } else {
            // ... IF CANCEL
        }
    }

    @FXML
    void newType(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog("wow");
        dialog.setTitle("New bike type");
        dialog.setHeaderText(null);
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            System.out.println(name + " blir registrert som en ny type");

            type = new Type(result.get());
            try{
                saveChanges(event);
                updateList();
            } catch(Exception e){
                System.out.println("Error:" + e);
            }
        });
    }

    @FXML
    void editTypeName(ActionEvent event) throws Exception {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New type name");
        dialog.setHeaderText(null);
        dialog.setContentText("New name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            System.out.println(name + " har blitt sattsom nytt navn.");
            try{
                saveChanges(event);
                updateList();
            } catch(Exception e){

            }
        });

    }

    @FXML
    void saveChanges(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save type");
        alert.setHeaderText(null);
        alert.setContentText("Would you like to save your type?");
        if (factory.addType(type)) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Saved!");
            alert1.setHeaderText(null);
            alert1.setContentText(type.getName() + " has been saved!");
        }//end if
        type = null;
    }

    @FXML
    void updateList(){

        ObservableList<String> types = FXCollections.observableArrayList();
        String[] visualized = new String[factory.getTypes().size()];
        for (int i = 0; i < visualized.length; i++) {
            visualized[i] = factory.getTypes().get(i).getName();
        }//end loop

        for (int i = 0; i < visualized.length; i++) {
            types.add(visualized[i]); // add nytt innhold
        }

        typeListView.setItems(types);
    }







    // main buttons below

    @FXML
    void changeToBikeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeView.fxml");
    }

    @FXML
    void changeToDockScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/dock/dockView.fxml");
    }

    @FXML
    void changeToMapScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/map/mapView.fxml");
    }

    @FXML
    void changeToStatsScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/stats/statsView.fxml");
    }

    @FXML
    void changeToAdminScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/admin/adminView.fxml");
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
