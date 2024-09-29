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

    private DatabaseApi db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
    }

    public void onSave() {
        String itemName = text_itemName.getText().trim();
        String unit = text_unit.getText().trim();

        if (itemName.isEmpty()) {
            displayError("Please enter an Item Name.");
        } else if (unit.isEmpty()) {
            displayError("Please enter a Unit.");
        } else if(AutoSuggestions.ItemNames.contains(itemName)) {
            displayError("Item already exists.");
        } else {
            AutoSuggestions.addItem(itemName, unit);
            db.addItem(new AutoSuggestions.Item(itemName, unit));
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
