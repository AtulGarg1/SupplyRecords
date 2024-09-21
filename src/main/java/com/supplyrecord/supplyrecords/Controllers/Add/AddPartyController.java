package com.supplyrecord.supplyrecords.Controllers.Add;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPartyController implements Initializable {
    public TextField text_partyName;
    public Button btn_save;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void onSave() {
        String partyName = text_partyName.getText().trim();

        if (validate(partyName)) {
            AutoSuggestions.PartyNames.add(partyName);
            // TODO: create entry in DB
            getStage().close();
        }
    }

    private boolean validate(String partyName) {
        return !partyName.isEmpty() && !AutoSuggestions.PartyNames.contains(partyName);
    }

    private Stage getStage() {
        return (Stage) btn_save.getScene().getWindow();
    }
}
