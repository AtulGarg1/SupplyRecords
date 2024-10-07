package com.supplyrecord.supplyrecords.Controllers.PaymentsReceived;

import com.supplyrecord.supplyrecords.Models.DataClasses.PaymentRecord;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ListRecordController implements Initializable {
    public AutoCompleteTextField text_partyName;
    public DecimalTextField text_amount;
    public AutoCompleteTextField text_bankName;

    private static final ObjectProperty<PaymentRecord> record = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeFieldsNonEditable(text_partyName, text_amount, text_bankName);
        fillValues();
        record.addListener((observableVal, oldVal, newVal) -> fillValues());
    }

    private void fillValues() {
        text_partyName.setText(record.getValue().partyName());
        text_amount.setText(String.valueOf(record.getValue().amount()));
        text_bankName.setText(record.getValue().bankName());
    }

    public static void setRecord(PaymentRecord record) {
        ListRecordController.record.set(record);
    }

    private void makeFieldsNonEditable(TextField... textFields) {
        for (TextField textField: textFields) {
            textField.setEditable(false);
            textField.setFocusTraversable(false);
        }
    }
}
