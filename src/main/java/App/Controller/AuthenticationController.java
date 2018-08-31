package App.Controller;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import javax.swing.*;

public class AuthenticationController {

    private String TempPass = "loginpass";

    @FXML
    private PasswordField authPassField;
    @FXML
    private Button authEnterBtn;
    @FXML
    private Button authResetBtn;

    @FXML
    private ResourceBundle resources;

    public AuthenticationController(){
    }

    @FXML
    private void initialize(){}

    @FXML
    private void printPassword(){
        System.out.print(authPassField.getText());
    }
    @FXML
    private void resetPassField(){
        authPassField.setText("");
    }
    @FXML
    private void checkPassword() throws IOException {
        Stage appStage;
        Parent root;

            if (authPassField.getText().equals(TempPass)) {
                //Shift scenes into Main Screen
                appStage=(Stage)authEnterBtn.getScene().getWindow();
                root=FXMLLoader.load(getClass().getResource("../../../resources/FXML/Main Screen.fxml"));
                Scene scene = new Scene(root);
                appStage.setScene(scene);
                appStage.show();
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Password!");
            }
        }


}
