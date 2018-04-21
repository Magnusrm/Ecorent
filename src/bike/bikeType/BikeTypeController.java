package bike.bikeType;

import changescene.MainMethods;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * BikeTypeController.java
 * @author Team 007
 * @version 1.0
 *
 * This class handles adding, deleting and editing bike types.
 */
public class BikeTypeController extends MainMethods implements Initializable {

    @FXML
    private ListView<String> typeListView;


    //Notice the types are converted to String array.
    //This is to simplify the clicking and fetching process.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
     * Gives the client an option to either accept or decline the deletion of the selected type.
     *
     * @param event button click
     * @throws Exception if updating the list doesn't work.
     */
    @FXML
    void deleteType(ActionEvent event) throws Exception {

        //System.out.println(typeListView);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete type");
        alert.setHeaderText("Are you sure you would like to delete the selected type? This will delete all the bikes with this type.");
        alert.setContentText("");
        Image image = new Image("/resources/warning.png");
        ImageView imageView = new ImageView(image);
        alert.setGraphic(imageView);




        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (factory.deleteType(new Type(typeListView.getSelectionModel().getSelectedItem()))) {
                newInfoAlert("Delete type", "Type deleted!");
            } else {
                newInfoAlert("Delete type", "Something went wrong! Type not deleted");
            }//end else
            try {
                updateList();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            //IF CANCEL
            newInfoAlert("Delete type", "Type will not be deleted");
        }//end else
    }//end method


    /**
     *
     * Gives the client a textfield and created a new type based on the input.
     *
     * @param event button click
     */
    @FXML
    void newType(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New bike type");
        dialog.setHeaderText(null);
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            type = new Type(name);
            try {
                saveChanges(event);
                updateList();
            } catch (Exception e) {
                System.out.println("Error:" + e);
            }
        });
    }


    /**
     * Gives the client an option to enter a new type name.
     * Confirming will change the type based on the clients input.
     *
     * @param event button click
     * @throws Exception if saveChanges or updateList methods fail
     */
    @FXML
    void editTypeName(ActionEvent event) throws Exception {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Edit the name of the type");
        dialog.setHeaderText(null);
        dialog.setContentText("Put in the new name of the selected type. NB! " +
                "All bikes under this type will be changed");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            Type originial = new Type(typeListView.getSelectionModel().getSelectedItem());
            Type edit = new Type(name);
            if (factory.editType(originial, edit)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                newInfoAlert("Edit the name of the type", "Your type has now a new name: " + name);
            }//end if
            else {
                newInfoAlert("Edit the name of the type", "Something went wrong! Please make sure to fill in the name of the type");
            }//end else
            try {
                saveChanges(event);
                updateList();
            } catch (Exception e) {

            }

        });
    }//end method


    /**
     * Saves changes.
     * This method gets called each time
     *
     * @param event button click
     * @throws Exception if adding the type fails
     */
    @FXML
    void saveChanges(ActionEvent event){
        if (factory.addType(type)) {
            newInfoAlert("Saved!", type.getName() + " has been saved!");
        }//end if
    }


    /**
     * Updates the listview of types.
     * This gets called after clients changes name.
     */
    @FXML
    void updateList() {
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



}
