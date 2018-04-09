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
import model.TypeModel;

public class bikeTypeController implements Initializable{
    private Type type;
    private Factory factory = new Factory();
    private Type deleteType;

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
            //System.out.println(factory.getTypes().get(0).getName());
        }catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void changeToBikeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeView.fxml");
    }


    @FXML
    void deleteType(ActionEvent event) throws Exception {

        //System.out.println(typeListView);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete type");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you would like to delete the selected type?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //... IF OK
            deleteType = new Type(typeListView.getSelectionModel().getSelectedItem());
        } else {
            // ... IF CANCEL
            deleteType = null;
        }
    }


    @FXML
    void newType(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New bike type");
        dialog.setHeaderText(null);
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            //System.out.println(name + " blir registrert som en ny type");
            type = new Type(name);
        });
    }

    @FXML
    void editTypeName(ActionEvent event) throws Exception{
       TextInputDialog dialog = new TextInputDialog("");
       dialog.setTitle("Edit the name of the type");
       dialog.setHeaderText(null);
       dialog.setContentText("Put in the new name of the selected type. NB! " +
       "All bikes under this type will be changed");
       Optional<String> result = dialog.showAndWait();
       result.ifPresent(name ->{
           Type originial = new Type(typeListView.getSelectionModel().getSelectedItem());
           Type edit = new Type(name);
           if(factory.editType(originial,edit)){
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Edit the name of the type");
               alert.setHeaderText(null);
               alert.setContentText("Your type has now a new name: " + name);
               alert.showAndWait();
           }//end if
           else{
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Edit the name of the type");
               alert.setHeaderText(null);
               alert.setContentText("Something went wrong! Please make sure to fill in the name of the type");
               alert.showAndWait();
           }//end else
           ChangeScene cs = new ChangeScene();
           try {
               cs.setScene(event, "/bike/bikeView.fxml");
           } catch (Exception e) {
               e.printStackTrace();
           }
       });
    }//end method

    @FXML
    void saveChanges(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save type");
        alert.setHeaderText(null);
        alert.setContentText("Would you like to save your type?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            if(factory.addType(type) || factory.deleteType(deleteType)) {
                if(type != null) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Saved!");
                    alert1.setHeaderText(null);
                    alert1.setContentText(type.getName() + " has been saved!");
                    alert1.showAndWait();
                }//end if
                if(deleteType != null){
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Saved!");
                    alert1.setHeaderText(null);
                    alert1.setContentText(deleteType.getName() + " has been deleted!");
                    alert1.showAndWait();
                }//end if
            }//end if
            type = null;
            deleteType = null;
        }//end if
        else{
            type = null;
            deleteType = null;
        }//end else
        // change to bike scene
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeView.fxml");
    }

/*    @FXML
    void deleteType(ActionEvent event) throws Exception{
        popupScene ps = new popupScene();
        ps.setScene(event, "/bike/bikeType/bikeTypeDeleteView.fxml");
    }

    @FXML
    void newType(ActionEvent event) throws Exception{
        popupScene ps = new popupScene();
        ps.setScene(event, "/bike/bikeType/bikeTypeNewView.fxml");
    }

    @FXML
    void createNewTypeConfirm(){

    }

    @FXML
    void deleteTypeConfirm(){

    }*/

    @FXML
    void changeToDockScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/dock/dockView.fxml");
    }

    @FXML
    void changeToMapScene(ActionEvent event) {
        /*ChangeScene cs = new ChangeScene();
        cs.setScene(event, "");*/
    }

    @FXML
    void changeToStatsScene(ActionEvent event)throws Exception{
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
