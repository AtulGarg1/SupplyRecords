package com.supplyrecord.supplyrecords.Controllers.Add;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPartyController implements Initializable {
    public TextField text_partyName;
    public Button btn_save;
    public Label label_err;

    DatabaseApi db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
    }

    public void onSave() {
        String partyName = text_partyName.getText().trim();

        if (partyName.isEmpty()) {
            displayError("Please enter a Party Name.");
        } else if(AutoSuggestions.PartyNames.contains(partyName)) {
            displayError("Party already exists.");
        } else {
            AutoSuggestions.PartyNames.add(partyName);
            db.addParty(partyName);
            getStage().close();
        }
    }

    private void displayError(String msg) {
        label_err.setText(msg);
        label_err.setVisible(true);
    }

    private Stage getStage() {
        return (Stage) btn_save.getScene().getWindow();
    }
}
