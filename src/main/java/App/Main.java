package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Stage firstWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Authentication Screen.fxml"));
        firstWindow.setTitle("IEEE Attendance Application");
        firstWindow.setScene(new Scene(root, 600, 450));
        firstWindow.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
