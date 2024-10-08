package com.supplyrecord.supplyrecords.Controllers.Ledger;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.LedgerRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.PaymentRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.Record;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyRecord;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.Models.ViewSelected;
import com.supplyrecord.supplyrecords.Views.ViewFactory;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ListController implements Initializable {
    public AutoCompleteTextField text_partyName;
    public GridPane gridPane;
    public DecimalTextField text_totalCredit;
    public DecimalTextField text_totalDebit;
    public DecimalTextField text_totalBalance;
    public Label label_err;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text_partyName.getSuggestions().addAll(AutoSuggestions.PartyNames);
        makeFieldsNonEditable(text_totalCredit, text_totalDebit, text_totalBalance);
    }

    public void onSearch() {
        label_err.setVisible(false);
        clearFields();

        String partyName = text_partyName.getText().trim();
        if (partyName.isEmpty()) {
            displayError("Please enter a party name.");
        } else if (!AutoSuggestions.PartyNames.contains(partyName)) {
            displayError("Party does not exist.");
        } else {
            ArrayList<LedgerRecord> ledgerRecords = getLedgerRecordFor(partyName);
            setupGridPane(ledgerRecords);
        }
    }

    public void onClear() {
        text_partyName.setText("");
        clearFields();
    }

    private void setupGridPane(ArrayList<LedgerRecord> ledgerRecords) {
        int position = 1;
        double totalBalance = 0, totalCredit = 0, totalDebit = 0;

        for (LedgerRecord ledgerRecord: ledgerRecords) {
            TextField sno = new TextField(position + ".");
            TextField desc = new TextField(ledgerRecord.description());
            TextField date = new TextField(ledgerRecord.formattedDate());
            TextField credit = new TextField();
            TextField debit = new TextField();
            TextField balance = new TextField();

            if (ledgerRecord.isCredit()) {
                credit.setText(String.valueOf(ledgerRecord.amount()));
                totalBalance += ledgerRecord.amount();
                totalCredit += ledgerRecord.amount();
            } else {
                debit.setText(String.valueOf(ledgerRecord.amount()));
                totalBalance -= ledgerRecord.amount();
                totalDebit -= ledgerRecord.amount();
            }

            balance.setText(String.valueOf(totalBalance));

            makeFieldsNonEditable(sno, desc, date, credit, debit, balance);
            attachOnClickListeners(ledgerRecord.reference(), sno, desc, date, credit, debit, balance);
            credit.setAlignment(Pos.CENTER_RIGHT);
            debit.setAlignment(Pos.CENTER_RIGHT);
            balance.setAlignment(Pos.CENTER_RIGHT);
            gridPane.addRow(gridPane.getRowCount(), sno, desc, date, credit, debit, balance);

            position++;
        }

        text_totalCredit.setText(String.valueOf(totalCredit));
        text_totalDebit.setText(String.valueOf(totalDebit));
        text_totalBalance.setText(String.valueOf(totalBalance));
    }

    private ArrayList<LedgerRecord> getLedgerRecordFor(String partyName) {
        List<LedgerRecord> supplyInward = LocalData.getInstance()
                .getSupplyInwardRecordsList()
                .stream()
                .filter(supplyRecord -> supplyRecord.partyName().equals(partyName))
                .map(supplyRecord ->
                        new LedgerRecord("Inward", supplyRecord.date(), supplyRecord.totalAmount(), supplyRecord)
                )
                .toList();

        List<LedgerRecord> supplyOutward = LocalData.getInstance()
                .getSupplyOutwardRecordsList()
                .stream()
                .filter(supplyRecord -> supplyRecord.partyName().equals(partyName))
                .map(supplyRecord ->
                        new LedgerRecord("Outward", supplyRecord.date(), supplyRecord.totalAmount(), supplyRecord)
                )
                .toList();

        List<LedgerRecord> paymentReceived = LocalData.getInstance()
                .getPaymentsReceivedList()
                .stream()
                .filter(paymentRecord -> paymentRecord.partyName().equals(partyName))
                .map(paymentRecord ->
                        new LedgerRecord("Inward", paymentRecord.date(), paymentRecord.amount(), paymentRecord)
                )
                .toList();

        List<LedgerRecord> paymentMade = LocalData.getInstance()
                .getPaymentsMadeList()
                .stream()
                .filter(paymentRecord -> paymentRecord.partyName().equals(partyName))
                .map(paymentRecord ->
                        new LedgerRecord("Outward", paymentRecord.date(), paymentRecord.amount(), paymentRecord)
                )
                .toList();

        ArrayList<LedgerRecord> ledgerRecords = new ArrayList<>();

        ledgerRecords.addAll(supplyInward);
        ledgerRecords.addAll(supplyOutward);
        ledgerRecords.addAll(paymentReceived);
        ledgerRecords.addAll(paymentMade);

        ledgerRecords.sort(Comparator.comparing(LedgerRecord::date));

        return ledgerRecords;
    }

    private void attachOnClickListeners(Record reference, TextField... textFields) {
        for (TextField textField: textFields) {
            textField.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (reference instanceof SupplyRecord supplyRecord) {
                    if (supplyRecord.isInward()) {
                        com.supplyrecord.supplyrecords.Controllers.SupplyInwards.ListRecordController.setRecord(supplyRecord);
                        ViewSelected.getInstance().setSelected(ViewSelected.ListRecordSupplyInwards);
                    } else {
                        com.supplyrecord.supplyrecords.Controllers.SupplyOutwards.ListRecordController.setRecord(supplyRecord);
                        ViewSelected.getInstance().setSelected(ViewSelected.ListRecordSupplyOutwards);
                    }
                }
                else if (reference instanceof PaymentRecord paymentRecord) {
                    if (paymentRecord.isCredit()) {
                        com.supplyrecord.supplyrecords.Controllers.PaymentsReceived.ListRecordController.setRecord(paymentRecord);
                        ViewFactory.getInstance().showListRecordPaymentsReceivedWindow();
                    } else {
                        com.supplyrecord.supplyrecords.Controllers.PaymentsMade.ListRecordController.setRecord(paymentRecord);
                        ViewFactory.getInstance().showListRecordPaymentsMadeWindow();
                    }
                }
            });
        }
    }

    private void clearFields() {
        gridPane.getChildren().removeIf(TextField.class::isInstance);
        text_totalCredit.setText("");
        text_totalDebit.setText("");
        text_totalBalance.setText("");
    }

    private void makeFieldsNonEditable(TextField... textFields) {
        for (TextField textField: textFields) {
            textField.setEditable(false);
            textField.setFocusTraversable(false);
        }
    }

    private void displayError(String msg) {
        label_err.setText(msg);
        label_err.setVisible(true);
    }
}
