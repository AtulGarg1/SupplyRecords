package com.supplyrecord.supplyrecords.Controllers.Add;

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
        if (validate()) {
            getStage().close();
            // TODO: create entry in DB
        }
    }

    private boolean validate() {
        return !text_bankAccount.getText().isEmpty();
    }

    private Stage getStage() {
        return (Stage) btn_save.getScene().getWindow();
    }
}
