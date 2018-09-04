package App.Controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.Data;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import java.io.IOException;

public class NewMemberController {

    //TODO: Get google sheets API example working for duplication/ functionality across application
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
        writeValues(nameSubmit,gradSubmit,IDSubmit,gradeSubmit,positionSubmit);




    }
    private Boolean writeValues(String nameSubmit, String gradSubmit,
                                String schoolID,String gradeSubmit,
                                String positionSubmit) throws GeneralSecurityException, IOException {
        //go through master sheet and search schoolID column for the set ID return false if DNE
        //return true if exists

        //Build API client
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1Wvy4l3bWewgshHGMe9U59x01IM7KtuoeeEu65FdWGyQ";
        final String range = "Members!A2:E";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        //Create Data List of user input
        List<Object> userData = new ArrayList<>();
        userData.add(nameSubmit);
        userData.add(gradSubmit);
        userData.add(schoolID);
        userData.add(gradeSubmit);
        userData.add(positionSubmit);

        try {
            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();

            List<List<Object>> values = response.getValues();
            int dataLength = 0;
            Boolean newMember = true;
            if (values == null || values.isEmpty()) {
                System.out.println("No data found.");
            } else {
                System.out.println("DATA");
                for (List row : values) {
                    dataLength++;
                    // Print columns A and E, which correspond to indices 0 and 4.
                    //System.out.printf("%s, %s, %s, %s, %s\n", row.get(0), row.get(1), row.get(2), row.get(3), row.get(4));
                    if(row.get(0).equals(schoolID)){
                        System.out.println(schoolID+" ALREADY EXISTS Transferring to edit functionality.");
                        newMember = false;
                    }
                }
                System.out.println("NO Matching values found.");
            }

            if(newMember){
                //Add Member Functionality
                try {
                    String dataLengthString = Integer.toString(dataLength);
                    String writeRangeString = "Members!A" + dataLengthString + ":E" + dataLengthString;
                    List<List<Object>> updateValues = new ArrayList<>();
                    updateValues.add(userData);

                    ValueRange vr = new ValueRange().setValues(updateValues).setMajorDimension("ROWS");
                    service.spreadsheets().values()
                            .update(spreadsheetId, writeRangeString, vr).setValueInputOption("RAW").execute();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            if(!newMember){
                //Edit Member Functionality

            }

        }catch(GoogleJsonResponseException e){
            GoogleJsonError error = e.getDetails();
            System.out.println(error);
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
