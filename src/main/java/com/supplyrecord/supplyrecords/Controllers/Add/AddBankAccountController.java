package com.supplyrecord.supplyrecords.Controllers.Add;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBankAccountController implements Initializable {
    public TextField text_bankAccount;
    public Button btn_save;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void onSave() {
        String bankName = text_bankAccount.getText().trim();

        if (validate(bankName)) {
            AutoSuggestions.BankNames.add(bankName);
            // TODO: create entry in DB
            getStage().close();
        }
    }

    private boolean validate(String bankName) {
        return !bankName.isEmpty() && !AutoSuggestions.BankNames.contains(bankName);
    }

    private Stage getStage() {
        return (Stage) btn_save.getScene().getWindow();
    }
}
