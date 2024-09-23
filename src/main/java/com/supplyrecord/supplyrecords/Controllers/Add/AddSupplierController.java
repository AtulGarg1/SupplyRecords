package com.supplyrecord.supplyrecords.Controllers.Add;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.customComponents.UppercaseTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupplierController implements Initializable {
    public UppercaseTextField text_supplierName;
    public Button btn_save;
    public Label label_err;

    private DatabaseApi db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
    }

    public void onSave() {
        String supplierName = text_supplierName.getText().trim();

        if (supplierName.isEmpty()) {
            displayError("Please enter a Supplier Name.");
        } else if(AutoSuggestions.SupplierNames.contains(supplierName)) {
            displayError("Supplier already exists.");
        } else {
            AutoSuggestions.SupplierNames.add(supplierName);
            db.addSupplier(supplierName);
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
