package App.Controller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EditEventController {

    @FXML
    private Button homeBtn;





    @FXML
    private void returnHome() throws IOException {
        Stage newHomeStage;
        Parent newHomeRoot;

        newHomeStage=(Stage)homeBtn.getScene().getWindow();
        newHomeRoot=FXMLLoader.load(getClass().getResource("../../../resources/FXML/Main Screen.fxml"));
        Scene scene = new Scene(newHomeRoot);
        newHomeStage.setScene(scene);
        newHomeStage.show();
    }

}
