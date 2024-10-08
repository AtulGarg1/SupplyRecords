package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyRecord;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.Models.ViewSelected;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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

    private ArrayList<SupplyRecord> list;
    private ArrayList<SupplyRecord> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text_partyName.getSuggestions().addAll(AutoSuggestions.PartyNames);
        list = filteredList = LocalData.getInstance().getSupplyInwardRecordsList();
        setupGridPane();
    }

    private void setupGridPane() {
        gridPane.getChildren().removeIf(TextField.class::isInstance);
        for (int i = 0; i < filteredList.size(); i++) addRow(i);
    }

    private void addRow(int index) {
        int rowNo = gridPane.getRowCount();
        SupplyRecord supplyInwardRecord = filteredList.get(index);

        TextField sno = new TextField((index+1) + ".");
        TextField partyName = new TextField(supplyInwardRecord.partyName());
        TextField totalAmount = new TextField("₹" + supplyInwardRecord.totalAmount());
        TextField date = new TextField(supplyInwardRecord.formattedDate());

        totalAmount.setAlignment(Pos.CENTER_RIGHT);

        makeFieldsNonEditable(sno, partyName, totalAmount, date);
        attachOnClickListener(supplyInwardRecord, sno, partyName, totalAmount, date);

        gridPane.add(sno, 0, rowNo);
        gridPane.add(partyName, 1, rowNo);
        gridPane.add(totalAmount, 2, rowNo);
        gridPane.add(date, 3, rowNo);
    }

    public void onSearch() {
        SupplyRecord.Filter filter = new SupplyRecord.Filter(
                text_partyName.getText(),
                dp_dateFrom.getValue() != null ? dp_dateFrom.getValue().atStartOfDay() : null,
                dp_dateTo.getValue() != null ? dp_dateTo.getValue().atStartOfDay() : null
        );
        filteredList = SupplyRecord.filterList(list, filter);
        setupGridPane();
    }

    public void onClear() {
        filteredList = list;
        text_partyName.setText("");
        dp_dateTo.getEditor().setText("");
        dp_dateFrom.getEditor().setText("");
        setupGridPane();
    }

    private void attachOnClickListener(SupplyRecord supplyInwardRecord, TextField... textFields) {
        for (TextField textField: textFields) {
            textField.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                ListRecordController.setRecord(supplyInwardRecord);
                ViewSelected.getInstance().setSelected(ViewSelected.ListRecordSupplyInwards);
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
