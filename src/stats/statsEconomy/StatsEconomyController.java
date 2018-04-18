package stats.statsEconomy;

import changescene.ChangeScene;
import control.Budget;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsEconomyController implements Initializable {

    @FXML
    private TableView<Budget> budget;

    @FXML
    private TableColumn<Budget, String> type;

    @FXML
    private TableColumn<Budget, String> amount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // set up the columns in the table
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // load dummy data
        budget.setItems(getBudget());

    }

    /**
     * Returnerer en ObservableList med Strings
     * @return
     */
    public ObservableList<Budget> getBudget(){
        ObservableList<Budget> budget = FXCollections.observableArrayList();

        budget.add(new Budget("Sykkelkjøp", -25000));
        budget.add(new Budget("Strøm", -15000));
        budget.add(new Budget("Leieinnteker", 45000));
        budget.add(new Budget("Sum", 5000));


        return budget;
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
    void changeToStatsScene(ActionEvent event) throws Exception {
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

        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/login/LoginView.fxml");

    }
}
