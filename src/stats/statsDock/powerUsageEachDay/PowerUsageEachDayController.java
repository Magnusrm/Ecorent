package stats.statsDock.powerUsageEachDay;

import changescene.MainMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * PowerUsageEachDayController.java
 * @author Team007
 * @version 1.0
 *
 * This class handles informaiton about the docks power usage each day.
 */
public class PowerUsageEachDayController extends MainMethods implements Initializable {

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private Label headerLbl;

    private double[] days;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headerLbl.setText("Power Usage Each Day");

        days = factory.getDailyPowerUsage();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        barChart.setLegendVisible(false);
        XYChart.Series set1 = new XYChart.Series<>();

        for(int i = 6; i>=0; i--){
            set1.getData().add(new XYChart.Data("" + LocalDate.now().minusDays(i), days[i]));
        }

        barChart.getData().addAll(set1);
    }

}
