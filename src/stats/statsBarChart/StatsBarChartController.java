package stats.statsBarChart;

import changescene.CloseWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.DockStatsModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StatsBarChartController implements Initializable {

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private Label headerLbl;

    private double[] days;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headerLbl.setText("Power Usage Each Day");
        DockStatsModel dsm = new DockStatsModel();
        days = dsm.getDailyPowerUsage();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        headerLbl.setText("");
        barChart.setLegendVisible(false);
        XYChart.Series set1 = new XYChart.Series<>();

        for(int i = 6; i>=0; i--){
            set1.getData().add(new XYChart.Data("" + LocalDate.now().minusDays(i), days[i]));
        }

        barChart.getData().addAll(set1);
    }

    @FXML
    void closeBarChart(ActionEvent event){
        CloseWindow cs = new CloseWindow(event);
    }
}
