package stats.statsAreaChart;

import changescene.CloseWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsAreaChartController implements Initializable {

    @FXML
    private AreaChart<?, ?> areaChart;

    @FXML
    private Label headerLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headerLbl.setText("header");
        areaChart.setLegendVisible(false);
        XYChart.Series set1 = new XYChart.Series<>();

        for(int i = 0; i<7; i++){
            set1.getData().add(new XYChart.Data("Dag " + i, 5 + i*i));
        }


        areaChart.getData().addAll(set1);
    }

    @FXML
    void closeBarChart(ActionEvent event){
        CloseWindow cs = new CloseWindow(event);
    }

}