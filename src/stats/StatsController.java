package stats;

import changescene.ChangeScene;
import changescene.MainMethods;
import changescene.PopupScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StatsController extends MainMethods {

    @FXML
    void changeToBarChartScene(ActionEvent event) throws Exception{
        newPopup("/stats/statsDock/powerUsageEachDay/PowerUsageEachDayView.fxml", "Power Usage");
    }

    @FXML
    void changeToPieChartScene(ActionEvent event) throws Exception{
        newPopup("/stats/statsDock/totalCheckouts/TotalCheckoutsView.fxml", "Checkouts");
    }

    @FXML
    void changeToAreaChartScene(ActionEvent event) throws Exception{
        newPopup("/stats/statsDock/totalPowerUsage/TotalPowerUsageView.fxml", "Power Usage");
    }

    @FXML
    void changeToEconomyScene(ActionEvent event) throws Exception{
        changeScene(event, "/stats/statsEconomy/StatsEconomyView.fxml");
    }

    @FXML
    void changeToBikeStatsScene(ActionEvent event) throws Exception{
        changeScene(event, "/stats/statsBike/StatsBikeView.fxml");
    }
}
