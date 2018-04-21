package dock;

import changescene.MainMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.DockModel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * DockController.java
 * @author Team 007
 * @version 1.0
 *
 * This class handles deleting docks and navigating between DockNewView.fxml, DockInfoView.fxml, and DockEditView.fxml
 */
public class DockController extends MainMethods implements Initializable {

    @FXML
    private ComboBox<String> dockNameComboBox;

    //Notice the docks are converted to String array.
    //This is to simplify the clicking and fetching process.
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            factory.updateSystem();
            ObservableList<String> docks = FXCollections.observableArrayList();
            String[] visualized = new String[factory.getDocks().size()];
            for (int i = 0; i < visualized.length; i++) {
                visualized[i] = factory.getDocks().get(i).getName();
            }//end loop
            docks.addAll(visualized);
            dockNameComboBox.setItems(docks);
        }catch (Exception e){e.printStackTrace();}
    }//end constructor


    @FXML
    void changeToNewDockView(ActionEvent event) throws Exception{
        changeScene(event, "/dock/dockNew/DockNewView.fxml");
    }

    @FXML
    void changeToDockEditView(ActionEvent event) throws Exception {
        changeScene(event, "/dock/dockEdit/DockEditView.fxml");
    }

    @FXML
    void changeToDockInfoView(ActionEvent event) throws Exception {
       changeScene(event, "/dock/dockInfo/DockInfoView.fxml");
    }


    /**
     * Deletes a dock based on the dock selected in the dock combobox.
     * @param event Buttonclick
     * @throws Exception If there is something wrong with database connection, selected dock or DockView.fxml
     */
    @FXML
    void deleteDock(ActionEvent event) throws Exception{
        boolean confirmation = newConfirmationAlert("Delete dock", "Are you sure you would like to delete the selected dock?");
        if (confirmation){
            DockModel dm = new DockModel();
            dm.deleteDock(dockNameComboBox.getValue());
            changeScene(event,"/dock/DockView.fxml");
        }
    }
}
