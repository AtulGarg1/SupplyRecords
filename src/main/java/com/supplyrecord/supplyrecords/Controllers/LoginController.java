package com.supplyrecord.supplyrecords.Controllers;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.Views.ViewFactory;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Button btn_login;
    public AutoCompleteTextField tf_firmName;
    public TextField tf_password;
    public Button btn_createFirm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> firmNames = new ArrayList<>(); // TODO: fetch all firm names
        tf_firmName.getSuggestions().addAll(firmNames);
    }

    public void onCreateFirm() {
        getStage().close();
        ViewFactory.getInstance().showCreateFirmWindow();
    }

    public void onLogin() {
        if (validate()) {
            String firmName = tf_firmName.getText().trim();
            LocalData.getInstance().setFirmName(firmName);

            getStage().close();
            ViewFactory.getInstance().showNavigationLayout();
        }
    }

    private boolean validate() {
        String firmName = tf_firmName.getText().trim();
        String password = tf_password.getText();
        // TODO: check in DB

        return true;
    }

    private Stage getStage() {
        return (Stage) btn_login.getScene().getWindow();
    }
}
