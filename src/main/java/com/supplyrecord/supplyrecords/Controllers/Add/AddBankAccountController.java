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

public class AddBankAccountController implements Initializable {
    public TextField text_bankAccount;
    public Button btn_save;
    public Label label_err;

    DatabaseApi db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
    }

    public void onSave() {
        String bankName = text_bankAccount.getText().trim();

        if (bankName.isEmpty()) {
            displayError("Please enter a Bank Name.");
        } else if(AutoSuggestions.BankNames.contains(bankName)) {
            displayError("Bank already exists.");
        } else {
            AutoSuggestions.BankNames.add(bankName);
            db.addBankAccount(bankName);
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
