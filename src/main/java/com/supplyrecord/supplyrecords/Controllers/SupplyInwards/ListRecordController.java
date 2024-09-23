package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import com.supplyrecord.supplyrecords.customComponents.UppercaseTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListRecordController implements Initializable {
    public UppercaseTextField text_supplierName;
    public GridPane gridPane;
    public DecimalTextField text_total;
    public ScrollPane scrollPane;

    private DatabaseApi db;
    private static final ObjectProperty<SupplyInwardRecord> record = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
        makeFieldsNonEditable(text_supplierName, text_total);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        fillValues();
        record.addListener((observableVal, oldVal, newVal) -> fillValues());
    }

    private void fillValues() {
        gridPane.getChildren().removeIf(TextField.class::isInstance);
        SupplyInwardRecord supplyInwardRecord = record.getValue();

        text_supplierName.setText(String.valueOf(supplyInwardRecord.supplierName()));

        ArrayList<SupplyItemDetail> supplyItemDetails =
                db.fetchSupplyItemDetailsFor(supplyInwardRecord.recordId());

        for (int i = 0; i < supplyItemDetails.size(); i++) {
            SupplyItemDetail supplyItemDetail = supplyItemDetails.get(i);
            int rowNo = gridPane.getRowCount();
            double itemTotal = supplyItemDetail.qty() * supplyItemDetail.price();

            TextField sno = new TextField((i+1) + ".");
            TextField itemName = new TextField(supplyItemDetail.itemName());
            TextField qty = new TextField(String.valueOf(supplyItemDetail.qty()));
            TextField price = new TextField(String.valueOf(supplyItemDetail.price()));
            TextField total = new TextField(String.valueOf(itemTotal));

            makeFieldsNonEditable(sno, itemName, qty, price, total);

            gridPane.add(sno, 0, rowNo);
            gridPane.add(itemName, 1, rowNo);
            gridPane.add(qty, 2, rowNo);
            gridPane.add(price, 3, rowNo);
            gridPane.add(total, 4, rowNo);
        }

        text_total.setText(String.valueOf(supplyInwardRecord.totalAmount()));
    }

    private void makeFieldsNonEditable(TextField... textFields) {
        for (TextField textField: textFields) {
            textField.setEditable(false);
            textField.setFocusTraversable(false);
        }
    }

    public static void setRecord(SupplyInwardRecord record) {
        ListRecordController.record.set(record);
    }
}
