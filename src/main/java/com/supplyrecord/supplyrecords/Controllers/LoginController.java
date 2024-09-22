package com.supplyrecord.supplyrecords.Controllers;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.Views.ViewFactory;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Button btn_login;
    public AutoCompleteTextField tf_firmName;
    public TextField tf_password;
    public Button btn_createFirm;
    public Label label_err;

    DatabaseApi db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
        AutoSuggestions.fetchLists();
        tf_firmName.getSuggestions().addAll(AutoSuggestions.FirmNames);
    }

    public void onCreateFirm() {
        getStage().close();
        ViewFactory.getInstance().showCreateFirmWindow();
    }

    public void onLogin() {
        String firmName = tf_firmName.getText().trim();
        String password = tf_password.getText();

        if (firmName.isEmpty()) {
            displayError("Please enter a Firm Name.");
        } else if (!AutoSuggestions.FirmNames.contains(firmName)) {
            displayError("Firm does not exist.");
        } else if (password.isEmpty()) {
            displayError("Please enter the Password.");
        } else if (!verifyPassword(firmName, password)) {
            displayError("Password is incorrect.");
        } else {
            LocalData.getInstance().setFirmName(firmName);
            LocalData.fetchLists();
            getStage().close();
            ViewFactory.getInstance().showNavigationLayout();
        }
    }

    private boolean verifyPassword(String firmName, String password) {
        return db.verifyLogin(firmName, password);
    }

    private void displayError(String msg) {
        label_err.setText(msg);
        label_err.setVisible(true);
    }

    private Stage getStage() {
        return (Stage) btn_login.getScene().getWindow();
    }
}
