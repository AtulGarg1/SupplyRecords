package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
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
    public AutoCompleteTextField text_supplierName;
    public DatePicker dp_dateFrom;
    public DatePicker dp_dateTo;
    public GridPane gridPane;

    private ArrayList<SupplyInwardRecord> list;
    private ArrayList<SupplyInwardRecord> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text_supplierName.getSuggestions().addAll(AutoSuggestions.SupplierNames);
        list = filteredList = LocalData.getInstance().getSupplyInwardRecordsList();
        setupGridPane();
    }

    private void setupGridPane() {
        gridPane.getChildren().removeIf(TextField.class::isInstance);
        for (int i = 0; i < filteredList.size(); i++) addRow(i);
    }

    private void addRow(int index) {
        int rowNo = gridPane.getRowCount();
        SupplyInwardRecord supplyInwardRecord = filteredList.get(index);

        TextField sno = new TextField((index+1) + ".");
        TextField supplierName = new TextField(supplyInwardRecord.supplierName());
        TextField totalAmount = new TextField("₹" + supplyInwardRecord.totalAmount());
        TextField date = new TextField(supplyInwardRecord.formattedDate());

        makeFieldsNonEditable(sno, supplierName, totalAmount, date);
        attachOnClickListener(supplyInwardRecord, sno, supplierName, totalAmount, date);

        gridPane.add(sno, 0, rowNo);
        gridPane.add(supplierName, 1, rowNo);
        gridPane.add(totalAmount, 2, rowNo);
        gridPane.add(date, 3, rowNo);
    }

    public void onSearch() {
        SupplyInwardRecord.Filter filter = new SupplyInwardRecord.Filter(
                text_supplierName.getText(),
                dp_dateFrom.getValue(),
                dp_dateTo.getValue()
        );
        filteredList = SupplyInwardRecord.filterList(list, filter);
        setupGridPane();
    }

    public void onClear() {
        filteredList = list;
        text_supplierName.setText("");
        dp_dateTo.getEditor().setText("");
        dp_dateFrom.getEditor().setText("");
        setupGridPane();
    }

    private void attachOnClickListener(SupplyInwardRecord supplyInwardRecord, TextField... textFields) {
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
