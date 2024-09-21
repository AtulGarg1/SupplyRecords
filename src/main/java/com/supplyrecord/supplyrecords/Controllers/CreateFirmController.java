package com.supplyrecord.supplyrecords.Controllers;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Views.ViewFactory;
import com.supplyrecord.supplyrecords.customComponents.UppercaseTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateFirmController implements Initializable {
    public UppercaseTextField tf_firmName;
    public TextField tf_password;
    public TextField tf_confirmPassword;
    public Button btn_create;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void onCreate() {
        if (validate()) {
            String firmName = tf_firmName.getText().trim();
            String pass = tf_password.getText();
            // TODO: create an entry in DB

            getStage().close();
            ViewFactory.getInstance().showLoginWindow();
        }
    }

    private boolean validate() {
        String firm = tf_firmName.getText().trim();
        String pass = tf_password.getText();
        String confirmPass = tf_confirmPassword.getText();
        // TODO: check is firm exists in DB
        return !firm.isEmpty() && !pass.isEmpty() && pass.equals(confirmPass);
    }

    private Stage getStage() {
        return (Stage) btn_create.getScene().getWindow();
    }
}
