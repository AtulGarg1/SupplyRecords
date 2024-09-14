package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class EditRecordController implements Initializable {
    public TextField text_partyName;
    public GridPane gridPane;
    public Text text_subTotal;
    public DecimalTextField text_biltiCharge;
    public DecimalTextField text_bardana;
    public DecimalTextField text_labourCost;
    public DecimalTextField text_commission;
    public DecimalTextField text_postage;
    public DecimalTextField text_bazaarCharges;
    public DecimalTextField text_otherExpenses;
    public Text text_total;

    private static final LongProperty recordId = new SimpleLongProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillValues();
        recordId.addListener((observableVal, oldVal, newVal) -> fillValues());
    }

    private void fillValues() {
        // TODO: populate all fields
        text_partyName.setText(String.valueOf(recordId.getValue()));
    }

    public static void setRecordId(long recordId) {
        EditRecordController.recordId.set(recordId);
    }

    public void onSave() {
        // TODO: persist updates to DB
    }
}
