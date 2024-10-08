package com.supplyrecord.supplyrecords.Controllers.PaymentsReceived;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.PaymentRecord;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.Views.ViewFactory;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListController implements Initializable {
    public AutoCompleteTextField text_partyName;
    public DatePicker dp_dateFrom;
    public DatePicker dp_dateTo;
    public GridPane gridPane;

    private ArrayList<PaymentRecord> list;
    private ArrayList<PaymentRecord> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text_partyName.getSuggestions().addAll(AutoSuggestions.PartyNames);
        list = filteredList = LocalData.getInstance().getPaymentsReceivedList();
        setupGridPane();
    }

    private void setupGridPane() {
        gridPane.getChildren().removeIf(TextField.class::isInstance);
        for (int i = 0; i < filteredList.size(); i++) addRow(i);
    }

    private void addRow(int index) {
        int rowNo = gridPane.getRowCount();
        PaymentRecord paymentRecord = filteredList.get(index);

        TextField sno = new TextField((index+1) + ".");
        TextField partyName = new TextField(paymentRecord.partyName());
        TextField amount = new TextField("₹" + paymentRecord.amount());
        TextField bankName = new TextField(paymentRecord.bankName());
        TextField date = new TextField(paymentRecord.formattedDate());

        makeFieldsNonEditable(sno, partyName, amount, bankName, date);
        attachOnClickListeners(paymentRecord, sno, partyName, amount, bankName, date);

        gridPane.add(sno, 0, rowNo);
        gridPane.add(partyName, 1, rowNo);
        gridPane.add(amount, 2, rowNo);
        gridPane.add(bankName, 3, rowNo);
        gridPane.add(date, 4, rowNo);
    }

    public void onSearch() {
        PaymentRecord.Filter filter = new PaymentRecord.Filter(
                text_partyName.getText().trim(),
                dp_dateFrom.getValue() != null ? dp_dateFrom.getValue().atStartOfDay() : null,
                dp_dateTo.getValue() != null ? dp_dateTo.getValue().atStartOfDay() : null
        );
        filteredList = PaymentRecord.filterList(list, filter);
        setupGridPane();
    }

    public void onClear() {
        filteredList = list;
        text_partyName.setText("");
        dp_dateTo.getEditor().setText("");
        dp_dateFrom.getEditor().setText("");
        setupGridPane();
    }

    private void attachOnClickListeners(PaymentRecord record, TextField... textFields) {
        for (TextField textField : textFields) {
            textField.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                ListRecordController.setRecord(record);
                ViewFactory.getInstance().showListRecordPaymentsReceivedWindow();
            });
        }
    }

    private void makeFieldsNonEditable(TextField... textFields) {
        for (TextField textField: textFields) {
            textField.setEditable(false);
            textField.setFocusTraversable(false);
        }
    }
}
