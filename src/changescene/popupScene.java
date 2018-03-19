package changescene;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class popupScene {

    public void setScene(ActionEvent event, String fxmlname) throws Exception{


        Parent parent = FXMLLoader.load(getClass().getResource(fxmlname));
        Scene scene = new Scene(parent);

        // lag ny stage
        Stage popup = new Stage();
        popup.setScene(scene);
        popup.show();

    }
}
