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

public class AddItemController implements Initializable {
    public TextField text_itemName;
    public TextField text_unit;
    public Button btn_save;
    public Label label_err;

    DatabaseApi db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
    }

    public void onSave() {
        String itemName = text_itemName.getText().trim();
        String unit = text_unit.getText().trim();
        String itemUnit = itemWithUnit(itemName, unit);

        if (itemName.isEmpty()) {
            displayError("Please enter an Item Name.");
        } else if (unit.isEmpty()) {
            displayError("Please enter a Unit.");
        } else if(AutoSuggestions.ItemNames.contains(itemUnit)) {
            displayError("Item already exists.");
        } else {
            AutoSuggestions.ItemNames.add(itemUnit);
            db.addItem(itemUnit);
            getStage().close();
        }
    }

    private void displayError(String msg) {
        label_err.setText(msg);
        label_err.setVisible(true);
    }

    private String itemWithUnit(String itemName, String unit) {
        return itemName + " (" + unit + ")";
    }

    private Stage getStage() {
        return (Stage) btn_save.getScene().getWindow();
    }
}
