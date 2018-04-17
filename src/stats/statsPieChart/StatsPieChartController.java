package stats.statsPieChart;

import changescene.CloseWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.DockStatsModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StatsPieChartController implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private Label headerLbl;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DockStatsModel dsm = new DockStatsModel();
        ArrayList<int[]> checkouts = dsm.getMaxCheckouts();


        headerLbl.setText("Daily Power Usage");
        pieChart.setLegendVisible(false);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(

        );

        for(int[] i : checkouts){
           pieChartData.add(new PieChart.Data("Dock: " + i[0], i[1]));

        }


        // datos.add(new Tabla(v) );
/*
        for(int i = 0; i<7; i++){
            new*/


        pieChart.setData(pieChartData);

    }

    @FXML
    void closeBarChart(ActionEvent event){
        CloseWindow cs = new CloseWindow(event);
    }
}
