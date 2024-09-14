package com.supplyrecord.supplyrecords.Controllers;

import com.supplyrecord.supplyrecords.Views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Button btn_login;
    public TextField tf_firmName;
    public TextField tf_password;
    public Button btn_createFirm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void onCreateFirm() {
        getStage().close();
        ViewFactory.getInstance().showCreateFirmWindow();
    }

    public void onLogin() {
        if (validate()) {
            getStage().close();
            ViewFactory.getInstance().showNavigationLayout();
        }
    }

    private boolean validate() {
        // TODO: check in DB
        return true;
    }

    private Stage getStage() {
        return (Stage) btn_login.getScene().getWindow();
    }
}
