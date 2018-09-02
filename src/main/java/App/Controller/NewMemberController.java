package App.Controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import java.io.IOException;

public class NewMemberController {

    //TODO: Get google sheets API example working for duplication/ functionality across application
    private static final String APPLICATION_NAME = "IEEE Attendance";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "C:\\Users\\chris\\IdeaProjects\\IEEE_Attendance_App\\src\\main\\resources";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream info = NewMemberController.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(info));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }


   public NewMemberController(){
        initializeLists();
    }


    @FXML
    private Button homeBtn,submitBtn,clearBtn;
    @FXML
    private TextField nameInput,gradYearInput,schoolIDInput;
    @FXML
    private ChoiceBox<String> gradeInput,positionInput;


    private void initializeLists(){

    }



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
    @FXML
    private void submitValues() throws GeneralSecurityException, IOException {
        //get current values filled out by user make sure none are null
        if(nameInput.getText().equals("") || nameInput.getText() == null){
            JOptionPane.showMessageDialog(null,"Must fill out Name Field");
            return;
        }
        if(gradYearInput.getText().equals("") || gradYearInput.getText() == null){
            JOptionPane.showMessageDialog(null,"Must fill out Grad year Field");
            return;
        }
        if(schoolIDInput.getText().equals("") || schoolIDInput.getText() == null){
            JOptionPane.showMessageDialog(null,"Must fill out School ID Field");
            return;
        }
        if(gradeInput.getSelectionModel().getSelectedItem() == null){
            JOptionPane.showMessageDialog(null,"Must select a grade");
            return;
        }
        if(positionInput.getSelectionModel().getSelectedItem() == null){
            JOptionPane.showMessageDialog(null,"Must select a position");
            return;
        }
        String nameSubmit = nameInput.getText();
        String gradSubmit = gradYearInput.getText();
        String IDSubmit = schoolIDInput.getText();
        String gradeSubmit = gradeInput.getSelectionModel().getSelectedItem();
        String positionSubmit = positionInput.getSelectionModel().getSelectedItem();

        //Write to master file after checking to see if schoolID already exists
        //if ID already exists then call Edit Member functions to update values and notify user
        //else write new member info on a new line
        Boolean searchResult = searchSchoolID(IDSubmit);
        if(!searchResult){
            //Good to write values!

        }
        else{
            //Values already exist! move to edit functions
            System.out.println("Values found");
        }




    }
    private Boolean searchSchoolID(String schoolID) throws GeneralSecurityException, IOException {
        //go through master sheet and search schoolID column for the set ID return false if DNE
        //return true if exists

        //Build API client
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
        final String range = "Class Data!A2:E";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Name, Major");
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                System.out.printf("%s, %s\n", row.get(0), row.get(4));
            }
        }


        return true;
    }

    @FXML
    private void clearValues(){
        nameInput.setText("");
        gradYearInput.setText("");
        schoolIDInput.setText("");
        gradeInput.getSelectionModel().clearSelection();
        positionInput.getSelectionModel().clearSelection();


    }



}
