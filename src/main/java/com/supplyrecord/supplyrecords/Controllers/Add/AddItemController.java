package com.supplyrecord.supplyrecords.Controllers.Add;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {
    public TextField text_itemName;
    public TextField text_unit;
    public Button btn_save;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void onSave() {
        String itemName = text_itemName.getText().trim();
        String unit = text_unit.getText().trim();

        if (validate(itemName, unit)) {
            AutoSuggestions.ItemNames.add(itemWithUnit(itemName, unit));
            // TODO: create entry in DB
            getStage().close();
        }
    }

    private boolean validate(String itemName, String unit) {
        return !itemName.isEmpty() && !unit.isEmpty() && !AutoSuggestions.ItemNames.contains(itemWithUnit(itemName, unit));
    }

    private String itemWithUnit(String itemName, String unit) {
        return itemName + " (" + unit + ")";
    }

    private Stage getStage() {
        return (Stage) btn_save.getScene().getWindow();
    }
}
