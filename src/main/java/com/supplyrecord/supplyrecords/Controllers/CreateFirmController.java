package com.supplyrecord.supplyrecords.Controllers;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Views.ViewFactory;
import com.supplyrecord.supplyrecords.customComponents.UppercaseTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateFirmController implements Initializable {
    public UppercaseTextField tf_firmName;
    public TextField tf_password;
    public TextField tf_confirmPassword;
    public Button btn_create;
    public Label label_err;

    DatabaseApi db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
    }

    public void onCreate() {
        String firm = tf_firmName.getText().trim();
        String pass = tf_password.getText();
        String confirmPass = tf_confirmPassword.getText();

        if (firm.isEmpty()) {
            displayError("Please enter a Firm Name.");
        } else if (AutoSuggestions.FirmNames.contains(firm)) {
            displayError("Firm already exists.");
        } else if (pass.isEmpty()) {
            displayError("Please enter a Password.");
        } else if (confirmPass.isEmpty()) {
            displayError("Please confirm the Password.");
        } else if (!pass.equals(confirmPass)) {
            displayError("Confirm Password does not match Password.");
        } else {
            db.createFirm(firm, pass);
            AutoSuggestions.FirmNames.add(firm);
            getStage().close();
            ViewFactory.getInstance().showLoginWindow();
        }
    }

    private void displayError(String msg) {
        label_err.setText(msg);
        label_err.setVisible(true);
    }

    private Stage getStage() {
        return (Stage) btn_create.getScene().getWindow();
    }
}
