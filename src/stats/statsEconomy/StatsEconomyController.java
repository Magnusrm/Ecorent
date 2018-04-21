package stats.statsEconomy;

import changescene.MainMethods;
import control.Budget;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * StatsEconomyController.java
 * @author Team 007
 * @version 1.0
 * This class shows statistics of the economy in a GUI.
 */
public class StatsEconomyController extends MainMethods implements Initializable {


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
     * This method gets the income from the database.
     * @return An ObservableList with income as a String.
     */
    public ObservableList<Budget> getIncome(){
        ObservableList<Budget> budget = FXCollections.observableArrayList();

        budget.add(new Budget("Rent", Math.round(factory.getRentIncome() * 100.0) / 100.0));

        return budget;
    }

    /**
     * This method gets the expenses from the database.
     * @return an ObservalbeList with expenses as a String
     */
    public ObservableList<Budget> getExpenses(){
        ObservableList<Budget> budget = FXCollections.observableArrayList();

        budget.add(new Budget("Bike Purchase" ,Math.round(factory.getBikePurchaseExpenses()  * 100.0) / 100.0));
        budget.add(new Budget("Power", Math.round(factory.getPowerExpenses()  * 100.0) / 100.0));
        budget.add(new Budget("Repairs", Math.round(factory.getRepairExpenses()  * 100.0) / 100.0));

        return budget;
    }
}
