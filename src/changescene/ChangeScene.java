package changescene;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeScene {


    public void setScene(ActionEvent event, String fxmlname) throws Exception{

        Parent parent = FXMLLoader.load(getClass().getResource(fxmlname));
        Scene scene = new Scene(parent);

        // hent stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();

    }

}
