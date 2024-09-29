package com.supplyrecord.supplyrecords.Controllers.SupplyOutwards;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyOutwardRecord;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.Models.ViewSelected;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
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

    private ArrayList<SupplyOutwardRecord> list;
    private ArrayList<SupplyOutwardRecord> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text_partyName.getSuggestions().addAll(AutoSuggestions.PartyNames);
        list = filteredList = LocalData.getInstance().getSupplyOutwardRecordsList();
        setupGridPane();
    }

    private void setupGridPane() {
        gridPane.getChildren().removeIf(TextField.class::isInstance);
        for (int i = 0; i < filteredList.size(); i++) addRow(i);
    }

    private void addRow(int index) {
        int rowNo = gridPane.getRowCount();
        SupplyOutwardRecord supplyOutwardRecord = filteredList.get(index);

        TextField sno = new TextField((index+1) + ".");
        TextField partyName = new TextField(supplyOutwardRecord.partyName());
        TextField totalAmount = new TextField("₹" + supplyOutwardRecord.totalAmount());
        TextField date = new TextField(supplyOutwardRecord.formattedDate());

        makeFieldsNonEditable(sno, partyName, totalAmount, date);
        attachOnClickListener(supplyOutwardRecord, sno, partyName, totalAmount, date);

        gridPane.add(sno, 0, rowNo);
        gridPane.add(partyName, 1, rowNo);
        gridPane.add(totalAmount, 2, rowNo);
        gridPane.add(date, 3, rowNo);
    }

    public void onSearch() {
        SupplyOutwardRecord.Filter filter = new SupplyOutwardRecord.Filter(
                text_partyName.getText(),
                dp_dateFrom.getValue(),
                dp_dateTo.getValue()
        );
        filteredList = SupplyOutwardRecord.filterList(list, filter);
        setupGridPane();
    }

    public void onClear() {
        filteredList = list;
        text_partyName.setText("");
        dp_dateTo.getEditor().setText("");
        dp_dateFrom.getEditor().setText("");
        setupGridPane();
    }

    private void attachOnClickListener(SupplyOutwardRecord SupplyOutwardRecord, TextField... textFields) {
        for (TextField textField: textFields) {
            textField.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                ListRecordController.setRecord(SupplyOutwardRecord);
                ViewSelected.getInstance().setSelected(ViewSelected.ListRecordSupplyOutwards);
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
