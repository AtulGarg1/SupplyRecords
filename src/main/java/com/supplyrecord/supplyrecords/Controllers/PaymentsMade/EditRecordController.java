package com.supplyrecord.supplyrecords.Controllers.PaymentsMade;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.PaymentRecord;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditRecordController implements Initializable {
    public AutoCompleteTextField text_partyName;
    public DecimalTextField text_amount;
    public AutoCompleteTextField text_bankName;
    public Button btn_save;
    public Label label_err;

    private DatabaseApi db;
    private static final ObjectProperty<PaymentRecord> record = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
        text_partyName.getSuggestions().addAll(AutoSuggestions.PartyNames);
        text_bankName.getSuggestions().addAll(AutoSuggestions.BankNames);
        fillValues();
        record.addListener((observableVal, oldVal, newVal) -> fillValues());
    }

    public void onSave() {
        String partyName = text_partyName.getText().trim();
        String bankName = text_bankName.getText().trim();
        String amount = text_amount.getText().trim();

        if (partyName.isEmpty()) {
            displayError("Please enter a Party Name.");
        } else if (amount.isEmpty()) {
            displayError("Please enter an Amount.");
        } else if (!AutoSuggestions.PartyNames.contains(partyName)) {
            displayError("Party Name does not exist.");
        } else if (!isDouble(amount)) {
            displayError("Please enter a valid Amount.");
        } else if (!bankName.isEmpty() && !AutoSuggestions.BankNames.contains(bankName)) {
            displayError("Bank Name does not exist.");
        } else {
            PaymentRecord paymentRecord = new PaymentRecord(
                    record.getValue().recordId(), LocalData.getInstance().getFirmName(), partyName,
                    Double.parseDouble(amount), bankName, LocalDate.now(), false
            );
            LocalData.getInstance().updatePaymentsMadeList(paymentRecord);
            db.updatePaymentRecord(paymentRecord);

            getStage().close();
        }
    }

    private void fillValues() {
        text_partyName.setText(record.getValue().partyName());
        text_amount.setText(String.valueOf(record.getValue().amount()));
        text_bankName.setText(record.getValue().bankName());
    }

    private void displayError(String msg) {
        label_err.setText(msg);
        label_err.setVisible(true);
    }

    private boolean isDouble(String val) {
        try {
            Double.parseDouble(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void setRecord(PaymentRecord record) {
        EditRecordController.record.set(record);
    }

    private Stage getStage() {
        return (Stage) btn_save.getScene().getWindow();
    }
}
