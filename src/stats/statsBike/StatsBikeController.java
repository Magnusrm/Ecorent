package stats.statsBike;

import changescene.ChangeScene;
import control.Budget;
import control.Factory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DockStatsModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StatsBikeController implements Initializable {

    private Factory factory = new Factory();

    @FXML
    private Label totalDistanceLbl;

    @FXML
    private Label avgKmPerTripLbl;

    @FXML
    private Label totalTripsLbl;

    @FXML
    private BarChart<?, ?> barChart;

    private String[][] types;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    factory.updateSystem();
    types = factory.getTypePopularity();


        barChart.setLegendVisible(false);
        XYChart.Series set1 = new XYChart.Series<>();

        for(int i = 0; i<types.length; i++){
            set1.getData().add(new XYChart.Data("" + types[i][0], Double.parseDouble(types[i][1])));
        }

        barChart.getData().addAll(set1);


    totalDistanceLbl.setText("" + (Math.round(factory.getTotalDistance() * 100) / 100)/1000 + " km")  ;
    avgKmPerTripLbl.setText("" + (Math.round(factory.getAvgKmPerTrip() * 100)  /100) /1000 + " km");
    totalTripsLbl.setText("" + factory.getTotalTrips() + " trips");

    }







    // main buttons
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
