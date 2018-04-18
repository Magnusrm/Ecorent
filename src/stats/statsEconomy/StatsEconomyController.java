package stats.statsEconomy;

import changescene.ChangeScene;
import control.Budget;
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

    private double income = 0;

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
       netIncomeLbl.setText("" + income);


    }

    /**
     * Returnerer en ObservableList med income som String
     * @return
     */
    public ObservableList<Budget> getIncome(){
        ObservableList<Budget> budget = FXCollections.observableArrayList();

        budget.add(new Budget("Leieinnteker", 45000));
        System.out.println("før inome:" +income);

        income += budget.get(0).getAmount();
        System.out.println("etter income " + income);
        return budget;
    }

    /**
     * Returnerer en ObservableList med expenses som String
     * @return
     */
    public ObservableList<Budget> getExpenses(){
        ObservableList<Budget> budget = FXCollections.observableArrayList();

        budget.add(new Budget("Sykkelkjøp", 25000));
        budget.add(new Budget("Strøm", 15000));
        System.out.println("før expenses " + income);

        income -= budget.get(0).getAmount();
        income -= budget.get(1).getAmount();
        System.out.println("etter expenses " + income);

        return budget;
    }











// main buttons below

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
