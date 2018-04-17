package bike.bikeType;

import changescene.ChangeScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import loginAdm.CurrentAdmin;

public class BikeTypeController implements Initializable{
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

    /**
     * @Author Team 007
     *
     * Gives the client an option to either accept or decline the deletion of the selected type.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void deleteType(ActionEvent event) throws Exception {

        //System.out.println(typeListView);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete type");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you would like to delete the selected type?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(factory.deleteType(new Type(typeListView.getSelectionModel().getSelectedItem()))){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Delete type");
                alert1.setHeaderText(null);
                alert1.setContentText("Type deleted!");
                alert1.showAndWait();
            }else{
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Delete type");
                alert1.setHeaderText(null);
                alert1.setContentText("Something went wrong! Type not deleted");
                alert1.showAndWait();
            }//end else
            try{
                saveChanges(event);
                updateList();
            } catch(Exception e){
                e.printStackTrace();
            }

        } else {
            //IF CANCEL
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Delete type");
            alert1.setHeaderText(null);
            alert1.setContentText("Type will not be deleted");
            alert1.showAndWait();
        }//end else
    }//end method


    /**
     * @Author Team 007
     *
     * Gives the client a textfield and created a new type based on the input.
     *
     * @param event
     */
    @FXML
    void newType(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New bike type");
        dialog.setHeaderText(null);
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
           // System.out.println(name + " blir registrert som en ny type");
            type = new Type(name);
            try{
                saveChanges(event);
                updateList();
            } catch(Exception e){
                System.out.println("Error:" + e);
            }
        });
    }


    /**
     * @Author Team 007
     *
     * Gives the client an option to enter a new type name.
     * Confirming will change the type based on the clients input.
     *
     * @param event
     * @throws Exception
     */
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
           try{
               saveChanges(event);
               updateList();
           } catch(Exception e){

           }

       });
    }//end method


    /**
     * @Author Team 007
     *
     * Saves changes.
     * This method gets called each time
     *
     * @param event
     * @throws Exception
     */
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
    }


    /**
     * @Author Team 007
     *
     * Updates the listview of types.
     * This gets called after clients changes name.
     *
     */
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

    /**
     *
     *
     *
     * @param event
     */
    @FXML
    void deleteAllBikesWithoutType(ActionEvent event){
        factory.updateSystem();
        if(factory.deleteAllBikes()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("All bikes with type null deleted");
            alert.setHeaderText(null);
            alert.setContentText("Operation successful! All bikes have a type");
            alert.showAndWait();
        }//end if
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OOPS");
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong! There might not be any bikes without a type.");
            alert.showAndWait();
        }//end else
    }




    

    // main buttons below

    @FXML
    void changeToBikeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/BikeView.fxml");
    }

    @FXML
    void changeToDockScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/dock/DockView.fxml");
    }

    @FXML
    void changeToMapScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/map/MapView.fxml");
    }

    @FXML
    void changeToStatsScene(ActionEvent event)throws Exception{
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/stats/StatsView.fxml");
    }

    @FXML
    void changeToAdminScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/admin/AdminView.fxml");
    }

    @FXML
    void changeToHomeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/main/MainView.fxml");
    }


    @FXML
    void logOut(ActionEvent event) throws Exception {
        CurrentAdmin.getInstance().setAdmin(null);
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/login/LoginView.fxml");
    }
}
