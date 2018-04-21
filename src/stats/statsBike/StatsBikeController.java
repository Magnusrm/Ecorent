package stats.statsBike;

import changescene.MainMethods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * StatsBikeController.java
 * @author Team 007
 * @version 1.0
 *
 * This class handles stats about bike.
 */
public class StatsBikeController extends MainMethods implements Initializable {

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
    avgKmPerTripLbl.setText("" + (Math.round(factory.getAvgKmPerTrip() * 100)  /100) + " m");
    totalTripsLbl.setText("" + factory.getTotalTrips() + " trips");

    }



}
