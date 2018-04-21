package admin;

import changescene.ChangeScene;
import changescene.CloseWindow;
import changescene.PopupScene;
import control.Factory;
import email.SendEmail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import control.*;
import loginAdm.CurrentAdmin;
import model.AdminModel;

public class AdminController {
    Factory factory = new Factory();
    AdminModel model = new AdminModel();
    @FXML
    private Button homeBtn;

    @FXML
    private Button bikesBtn;

    @FXML
    private Button docksBtn;

    @FXML
    private Button mapBtn;

    @FXML
    private Button statsBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button adminBtn;

    @FXML
    private Button createNewAdminBtn;

    @FXML
    private CheckBox mainAdminCheck;

    @FXML
    private TextField newAdminEmailField;

    @FXML
    private TextField deleteAdminEmailField;

    @FXML
    private TextField newPasswordField2;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField oldPasswordField;



    @FXML
    void createNewAdmin(ActionEvent event) throws Exception {
        PopupScene ps = new PopupScene();
        ps.setScene(event, "/admin/AdminNewAdminView.fxml");
    }//end method

    @FXML
    void deleteAdmin(ActionEvent event) throws Exception {
        PopupScene ps = new PopupScene();
        ps.setScene(event, "/admin/AdminDeleteAdminView.fxml");
    }

    @FXML
    void changePassword(ActionEvent event) throws Exception {
        PopupScene ps = new PopupScene();
        ps.setScene(event, "/admin/AdminChangePasswordView.fxml");
    }

    /**
     * @Author Team 007
     *
     * Confirms the creation of a new admin.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void createNewAdminConfirm(ActionEvent event) throws Exception{
        if(CurrentAdmin.getInstance().getAdmin().isMainAdmin()) {
            String email = newAdminEmailField.getText();
            boolean main = mainAdminCheck.isSelected();
            String defaultPassword = SendEmail.sendFromGmail(email);
            String hashed = Password.hashPassword(defaultPassword);
            Admin admin = new Admin(email, hashed, main);
            if (factory.addAdmin(admin)) System.out.println(admin);
            CloseWindow cw = new CloseWindow(event);
        }//end if
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Permission denied");
            alert.setHeaderText(null);
            alert.setContentText("You do not have main admin privileges and cannot create other admins." +
            " Contact your supervisor to require more privileges");
            alert.showAndWait();
        }//end else
    }//end method


    /**
     * @Author
     *
     * Confirms the deletion of the admin.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void deleteAdminConfirm(ActionEvent event) throws Exception{
        String email = deleteAdminEmailField.getText();
        if(email.equals(CurrentAdmin.getInstance().getAdmin().getEmail())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Permission denied");
            alert.setHeaderText(null);
            alert.setContentText("You cannot delete yourself");
            alert.showAndWait();
            CloseWindow cw = new CloseWindow(event);
        }//end if
        if(model.deleteAdmin(email)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete success");
            alert.setHeaderText(null);
            alert.setContentText("Admin with email " + email + " is deleted");
            alert.showAndWait();
        }//end if
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong!");
            alert.showAndWait();
        }
        CloseWindow cw = new CloseWindow(event);

    }//end

    /**
     * @Author Team 007
     *
     * Confirms the change of password.
     * The password will change if the old password is correct,
     * as well as if the new password meets the requirements.
     *
     * @param event
     */
    @FXML
    void changePasswordConfirm(ActionEvent event) {
        boolean continiue = true;//Makes it easier to choose which error message to display
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String newPassword2 = newPasswordField2.getText();

        if(newPassword.length()<8 || newPassword.length()>30)continiue = false;

        if(Password.check(oldPassword,CurrentAdmin.getInstance().getAdmin().getPassword())&&newPassword.equals(newPassword2)&&
                continiue){
            String email = CurrentAdmin.getInstance().getAdmin().getEmail();
            boolean main = CurrentAdmin.getInstance().getAdmin().isMainAdmin();
            String password = Password.hashPassword(newPassword);
            model.deleteAdmin(CurrentAdmin.getInstance().getAdmin().getEmail());
            if(factory.addAdmin(new Admin(email,password,main))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password changed");
                alert.setHeaderText(null);
                alert.setContentText(email + " has now password " + newPassword);
                alert.showAndWait();
            }//end if
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something went wrong!");
                alert.setHeaderText(null);
                alert.setContentText("Operation failed. Please make sure you fill out everything in the correct format and" +
                                " have internet access");
                alert.showAndWait();
                CloseWindow cw = new CloseWindow(event);
            }
        }//end if

        else{
            if(continiue) { //if the passwords do not match
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something went wrong!");
                alert.setHeaderText(null);
                alert.setContentText("Your passwords do not match!");
                alert.showAndWait();
                CloseWindow cw = new CloseWindow(event);
            }//end if
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something went wrong!");
                alert.setHeaderText(null);
                alert.setContentText("Your password must be between 8 and 30 characters!");
                alert.showAndWait();
                CloseWindow cw = new CloseWindow(event);
            }//end else
        }//end else

        CloseWindow cw = new CloseWindow(event);
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
        CurrentAdmin.getInstance().setAdmin(null);
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/login/LoginView.fxml");
    }
}
