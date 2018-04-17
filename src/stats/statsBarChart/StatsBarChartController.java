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
        headerLbl.setText("");
        barChart.setLegendVisible(false);
        XYChart.Series set1 = new XYChart.Series<>();

        for(int i = 0; i<7; i++){
            set1.getData().add(new XYChart.Data("Dag " + i, 5 + i*i));
        }

        barChart.getData().addAll(set1);
    }

    @FXML
    void closeBarChart(ActionEvent event){
        CloseWindow cs = new CloseWindow(event);
    }
}
