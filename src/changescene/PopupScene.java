package changescene;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupScene {

    private Stage popup;

    public PopupScene(ActionEvent event, String fxmlname, String title){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(fxmlname));
            Scene scene = new Scene(parent);

            // lag ny stage
            popup = new Stage();
            popup.setScene(scene);
            popup.setTitle(title);
            popup.setResizable(false);
            popup.show();
        }catch(Exception e){
         System.out.println("Error i popupscene kontruktør: " + e);
        }
    }

    public PopupScene(){

    }

    public void setScene(ActionEvent event, String fxmlname) throws Exception{

        Parent parent = FXMLLoader.load(getClass().getResource(fxmlname));
        Scene scene = new Scene(parent);

        // lag ny stage
        popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setScene(scene);
        popup.show();

    }

    public void setTitle(String tittel){
        popup.setTitle(tittel);
    }
}
