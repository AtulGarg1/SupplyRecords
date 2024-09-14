package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.ViewSelected;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditController implements Initializable {
    public AutoCompleteTextField text_partyName;
    public DatePicker dp_dateFrom;
    public DatePicker dp_dateTo;
    public GridPane gridPane;

    private ArrayList<SupplyInwardRecord> list;
    private ArrayList<SupplyInwardRecord> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text_partyName.getSuggestions().addAll(AutoSuggestions.PartyNames);
        list = filteredList = fetchData();

        gridPane.setOnMouseClicked(this::showDetailsFor);
        setupGridPane();
    }

    private ArrayList<SupplyInwardRecord> fetchData() {
        // TODO: fetch from DB
        return SupplyInwardRecord.generateDummyData();
    }

    private void setupGridPane() {
        gridPane.getChildren().removeIf(Text.class::isInstance);
        for (int i = 0; i < filteredList.size(); i++) addRow(i);
    }

    private void addRow(int index) {
        int rowNo = gridPane.getRowCount();
        SupplyInwardRecord placeholderClass = filteredList.get(index);

        gridPane.add(new Text((index+1) + "."), 0, rowNo);
        gridPane.add(new Text(placeholderClass.partyName()), 1, rowNo);
        gridPane.add(new Text("₹" + placeholderClass.amount()), 2, rowNo);
        gridPane.add(new Text(placeholderClass.formattedDate()), 3, rowNo);
    }

    public void onSearch() {
        SupplyInwardRecord.Filter filter = new SupplyInwardRecord.Filter(
                text_partyName.getText(),
                dp_dateFrom.getValue(),
                dp_dateTo.getValue()
        );
        filteredList = SupplyInwardRecord.filterList(list, filter);
        setupGridPane();
    }

    public void onClear() {
        filteredList = list;
        text_partyName.setText("");
        dp_dateTo.getEditor().setText("");
        dp_dateFrom.getEditor().setText("");
        setupGridPane();
    }

    private void showDetailsFor(MouseEvent event) {
        if (GridPane.getRowIndex((Node) event.getTarget()) != null) {
            int index = GridPane.getRowIndex((Node) event.getTarget()) - 1;
            EditRecordController.setRecordId(filteredList.get(index).recordId());
            ViewSelected.getInstance().setSelected(ViewSelected.EditRecordSupplyInwards);
        }
    }
}
