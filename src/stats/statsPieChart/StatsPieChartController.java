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

import java.net.URL;
import java.util.ResourceBundle;

public class StatsPieChartController implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private Label headerLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headerLbl.setText("header");
        pieChart.setLegendVisible(false);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(

            new PieChart.Data("settInnDockName", 50),
            new PieChart.Data("settInnDockName", 40),
            new PieChart.Data("settInnDockName", 60),
            new PieChart.Data("settInnDockName", 20),
            new PieChart.Data("settInnDockName", 10),
            new PieChart.Data("settInnDockName", 50),
            new PieChart.Data("settInnDockName", 30)
        );

        pieChart.setData(pieChartData);

    }

    @FXML
    void closeBarChart(ActionEvent event){
        CloseWindow cs = new CloseWindow(event);
    }
}
