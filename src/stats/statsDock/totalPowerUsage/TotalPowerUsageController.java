package stats.statsDock.totalPowerUsage;

import changescene.MainMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.DockStatsModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TotalPowerUsageController extends MainMethods implements Initializable {

    @FXML
    private AreaChart<?, ?> areaChart;

    @FXML
    private Label headerLbl;

    private double[] days;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DockStatsModel dsm = new DockStatsModel();
        days = dsm.getWeeklyMaxPowerUsage();
        headerLbl.setText("Total Power Usage");
        areaChart.setLegendVisible(false);
        XYChart.Series set1 = new XYChart.Series<>();

        for(int i = 6; i>=0; i--){
            set1.getData().add(new XYChart.Data("" + LocalDate.now().minusDays(i), days[i]));
        }

        areaChart.getData().addAll(set1);
    }

    @FXML
    void closeBarChart(ActionEvent event){
        closeWindow(event);
    }

}