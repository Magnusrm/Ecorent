package stats;

import changescene.ChangeScene;
import changescene.PopupScene;
import control.Budget;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsController{

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
    void changeToBarChartScene(ActionEvent event) throws Exception{
        PopupScene ps = new PopupScene(event, "/stats/statsBarChart/StatsBarChartView.fxml", "Power usage");
    }

    @FXML
    void changeToPieChartScene(ActionEvent event) throws Exception{
        PopupScene ps = new PopupScene(event, "/stats/statsPieChart/StatsPieChartView.fxml", "Checkouts");

    }

    @FXML
    void changeToAreaChartScene(ActionEvent event) throws Exception{
        PopupScene ps = new PopupScene(event, "/stats/statsAreaChart/StatsAreaChartView.fxml", "Power usage");
    }

    @FXML
    void changeToEconomyScene(ActionEvent event) throws Exception{
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/stats/statsEconomy/StatsEconomyView.fxml");
    }

    @FXML
    void changeToBikeStatsScene(ActionEvent event) throws Exception{
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/stats/statsBike/StatsBikeView.fxml");
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
