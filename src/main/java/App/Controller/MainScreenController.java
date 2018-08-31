package App.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;
import javafx.scene.control.MenuItem;
import java.io.IOException;

public class MainScreenController {

    public MainScreenController(){}
    @FXML
    private MenuItem newMemberItem,editMemberItem,newEventItem,editEventItem,checkUpdatesItem;

    @FXML
    private Button logoutBtn;
    @FXML
    private void initialize(){}

    @FXML
    private void logout(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?",
                "Exit Confirmation",dialogButton);
        if(dialogResult == 0){
            System.exit(0);
        }
    }
    @FXML
    private void newMember() throws IOException {
        //Bring up new member sign up "form" and then save this data to the master sheet.
        //inputs a new members info into the master sheet

        Stage newMemberStage;
        Parent newMemberRoot;

        newMemberStage=(Stage)logoutBtn.getScene().getWindow();
        newMemberRoot=FXMLLoader.load(getClass().getResource("../../../resources/FXML/Member/New Member Screen.fxml"));
        Scene scene = new Scene(newMemberRoot);
        newMemberStage.setScene(scene);
        newMemberStage.show();
    }
    @FXML
    private void editMember() throws IOException {

        //Edits the master sheet based upon user selection - allow search of users by name
        Stage editMemberStage;
        Parent editMemberRoot;

        editMemberStage=(Stage)logoutBtn.getScene().getWindow();
        editMemberRoot=FXMLLoader.load(getClass().getResource("../../../resources/FXML/Member/Member Search Screen.fxml"));
        Scene scene = new Scene(editMemberRoot);
        editMemberStage.setScene(scene);
        editMemberStage.show();

    }
    @FXML
    private void newEvent() throws IOException {

        //Bring up newEvent dialog & then create the swipe screen
        //new event dialog lets users choose different types of attendance meeting, e-board, outside event
        //writes new excel sheet with all card swipe results
        //increments the proper excel sheet for event tracking
        Stage newEventStage;
        Parent newEventRoot;

        newEventStage=(Stage)logoutBtn.getScene().getWindow();
        newEventRoot=FXMLLoader.load(getClass().getResource("../../../resources/FXML/Event/New Event Screen.fxml"));
        Scene scene = new Scene(newEventRoot);
        newEventStage.setScene(scene);
        newEventStage.show();

    }
    @FXML
    private void editEvent() throws IOException {

        //Bring up Edit Event dialog & then show title of event & Date at top followed by listview
        //Brings up list of events/dates to choose from based upon master excel sheet
        // of all people in attendance on that event
        //allow click features etc. for removing people and adding people from the master file
        Stage editEventStage;
        Parent editEventRoot;

        editEventStage=(Stage)logoutBtn.getScene().getWindow();
        editEventRoot=FXMLLoader.load(getClass().getResource("../../../resources/FXML/Event/Event Search Screen.fxml"));
        Scene scene = new Scene(editEventRoot);
        editEventStage.setScene(scene);
        editEventStage.show();

    }
    @FXML
    private void checkUpdates(){

        //Distributable jar/ exe upgrades in future
    }





}
