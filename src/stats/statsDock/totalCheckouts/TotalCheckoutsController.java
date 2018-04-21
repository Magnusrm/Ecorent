package stats.statsDock.totalCheckouts;

import changescene.MainMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TotalCheckoutsController extends MainMethods implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private Label headerLbl;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<int[]> checkouts = factory.getMaxCheckouts();

        headerLbl.setText("Total Checkouts on Each Dock");
        pieChart.setLegendVisible(false);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(

        );

        for (int[] i : checkouts) {
            pieChartData.add(new PieChart.Data(factory.getDockName(i[0]), i[1]));

        }


        pieChart.setData(pieChartData);

    }

    @FXML
    void closeBarChart(ActionEvent event) {
        closeWindow(event);
    }
}
