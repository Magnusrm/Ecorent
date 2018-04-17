package stats.statsBarChart;

import changescene.CloseWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsBarChartController implements Initializable {

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private Label headerLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headerLbl.setText("header");
        barChart.setLegendVisible(false);
        XYChart.Series set1 = new XYChart.Series<>();

        set1.getData().add(new XYChart.Data("settInnDatoHer1", 50));
        set1.getData().add(new XYChart.Data("settInnDatoHer2", 40));
        set1.getData().add(new XYChart.Data("settInnDatoHer3", 60));
        set1.getData().add(new XYChart.Data("settInnDatoHer4", 20));
        set1.getData().add(new XYChart.Data("settInnDatoHer5", 10));
        set1.getData().add(new XYChart.Data("settInnDatoHer6", 5));
        set1.getData().add(new XYChart.Data("settInnDatoHer7", 30));

        barChart.getData().addAll(set1);
    }

    @FXML
    void closeBarChart(ActionEvent event){
        CloseWindow cs = new CloseWindow(event);
    }
}
