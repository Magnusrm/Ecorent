package stats.statsEconomy;

import changescene.ChangeScene;
import control.Budget;
import control.Factory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsEconomyController implements Initializable {

    private Factory factory = new Factory();

    @FXML
    private TableView<Budget> incomeTableView;

    @FXML
    private TableView<Budget> expensesTableView;

    @FXML
    private TableColumn<Budget, String> incomeType;

    @FXML
    private TableColumn<Budget, String> incomeAmount;

    @FXML
    private TableColumn<Budget, String> expensesType;

    @FXML
    private TableColumn<Budget, String> expensesAmount;

    @FXML
    private Label netIncomeLbl;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // set up the columns in the table
        incomeType.setCellValueFactory(new PropertyValueFactory<>("type"));
        incomeAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        expensesType.setCellValueFactory(new PropertyValueFactory<>("type"));
        expensesAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // load dummy data
       incomeTableView.setItems(getIncome());
       expensesTableView.setItems(getExpenses());
       netIncomeLbl.setText("" + Math.round(factory.getNetIncome() * 100.0) / 100.0);


    }

    /**
     * Returnerer en ObservableList med income som String
     * @return
     */
    public ObservableList<Budget> getIncome(){
        ObservableList<Budget> budget = FXCollections.observableArrayList();

        budget.add(new Budget("Rent", Math.round(factory.getRentIncome() * 100.0) / 100.0));

        return budget;
    }

    /**
     * Returnerer en ObservableList med expenses som String
     * @return
     */
    public ObservableList<Budget> getExpenses(){
        ObservableList<Budget> budget = FXCollections.observableArrayList();

        budget.add(new Budget("Bike Purchase" ,Math.round(factory.getBikePurchaseExpenses()  * 100.0) / 100.0));
        budget.add(new Budget("Power", Math.round(factory.getPowerExpenses()  * 100.0) / 100.0));
        budget.add(new Budget("Repairs", Math.round(factory.getRepairExpenses()  * 100.0) / 100.0));

        return budget;
    }







    // main buttons
    @FXML
    void changeToBikeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/BikeView.fxml");
    }

    @FXML
    void changeToDockScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/dock/DockView.fxml");
    }

    @FXML
    void changeToMapScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/map/MapView.fxml");
    }

    @FXML
    void changeToStatsScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/stats/StatsView.fxml");
    }

    @FXML
    void changeToAdminScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/admin/AdminView.fxml");
    }

    @FXML
    void changeToHomeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/main/MainView.fxml");
    }

    @FXML
    void logOut(ActionEvent event) throws Exception {

        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/login/LoginView.fxml");

    }
}
