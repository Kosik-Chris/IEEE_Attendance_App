package App.Controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class NewEventController {

    //Class will both write a csv file and store on drive and update the attendance sheet
    //Stored csv file can serve as a back-up of recorded values

    //TODO: Eliminate duplicate access credential code muck
    //TODO: make the app look like a non soviet union project

    private static final String APPLICATION_NAME = "IEEE Attendance";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream info = getClass().getClassLoader().getResourceAsStream(CREDENTIALS_FILE_PATH);
        InputStreamReader streamReader = new InputStreamReader(info);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, streamReader);

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }



    @FXML
    private Button homeBtn,createEventBtn;
    @FXML
    private ChoiceBox<String> eventTypeSelection;
    @FXML
    private TextField eventNotesField;

    @FXML
    private void createEvent() throws IOException{
        //make a new tab in attendance sheet after getting what number it should be
        //show success/error message
        //ask if they would like to take attendance now (you can pre-create events)



    }

    private void writeCsvFile(){

    }



    @FXML
    private void returnHome() throws IOException {
        Stage newHomeStage;
        Parent newHomeRoot;
        try {
            newHomeStage = (Stage) homeBtn.getScene().getWindow();
            newHomeRoot = FXMLLoader.load(getClass().getResource("../../../resources/FXML/Main Screen.fxml"));
            Scene scene = new Scene(newHomeRoot);
            newHomeStage.setScene(scene);
            newHomeStage.show();
        }catch (NullPointerException e){
            System.out.println("Null Pointer");
        }
    }

}
