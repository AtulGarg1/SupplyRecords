package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyRecord;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import com.supplyrecord.supplyrecords.customComponents.UppercaseTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListRecordController implements Initializable {
    public UppercaseTextField text_partyName;
    public GridPane gridPane;
    public DecimalTextField text_subTotal;
    public DecimalTextField text_biltiCharge;
    public DecimalTextField text_bardana;
    public DecimalTextField text_labourCost;
    public DecimalTextField text_commission;
    public DecimalTextField text_postage;
    public DecimalTextField text_bazaarCharges;
    public DecimalTextField text_otherExpenses;
    public DecimalTextField text_total;

    private DatabaseApi db;
    private static final ObjectProperty<SupplyRecord> record = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
        makeFieldsNonEditable(
                text_partyName, text_subTotal, text_biltiCharge, text_bardana, text_labourCost,
                text_commission, text_postage, text_bazaarCharges, text_otherExpenses, text_total
        );
        fillValues();
        record.addListener((observableVal, oldVal, newVal) -> fillValues());
    }

    private void fillValues() {
        gridPane.getChildren().removeIf(TextField.class::isInstance);
        SupplyRecord supplyInwardRecord = record.getValue();

        text_partyName.setText(String.valueOf(supplyInwardRecord.partyName()));
        text_biltiCharge.setText(String.valueOf(supplyInwardRecord.biltiCharge()));
        text_bardana.setText(String.valueOf(supplyInwardRecord.bardana()));
        text_labourCost.setText(String.valueOf(supplyInwardRecord.labourCost()));
        text_commission.setText(String.valueOf(supplyInwardRecord.commission()));
        text_postage.setText(String.valueOf(supplyInwardRecord.postage()));
        text_bazaarCharges.setText(String.valueOf(supplyInwardRecord.bazaarCharges()));
        text_otherExpenses.setText(String.valueOf(supplyInwardRecord.otherExpenses()));

        ArrayList<SupplyItemDetail> supplyItemDetails =
                db.fetchSupplyInwardItemDetailsFor(supplyInwardRecord.recordId());
        double subTotal = 0;

        for (int i = 0; i < supplyItemDetails.size(); i++) {
            SupplyItemDetail supplyItemDetail = supplyItemDetails.get(i);
            int rowNo = gridPane.getRowCount();
            double itemTotal = supplyItemDetail.qty() * supplyItemDetail.price();

            TextField sno = new TextField((i+1) + ".");
            TextField itemName = new TextField(supplyItemDetail.itemName());
            TextField qty = new TextField(String.valueOf(supplyItemDetail.qty()));
            TextField unit = new TextField(AutoSuggestions.getUnit(supplyItemDetail.itemName()));
            TextField price = new TextField(String.valueOf(supplyItemDetail.price()));
            TextField total = new TextField(String.valueOf(itemTotal));

            makeFieldsNonEditable(sno, itemName, qty, unit, price, total);

            qty.setAlignment(Pos.CENTER_RIGHT);
            price.setAlignment(Pos.CENTER_RIGHT);
            total.setAlignment(Pos.CENTER_RIGHT);

            gridPane.add(sno, 0, rowNo);
            gridPane.add(itemName, 1, rowNo);
            gridPane.add(qty, 2, rowNo);
            gridPane.add(unit, 3, rowNo);
            gridPane.add(price, 4, rowNo);
            gridPane.add(total, 5, rowNo);

            subTotal += itemTotal;
        }

        text_subTotal.setText(String.valueOf(subTotal));
        text_total.setText(String.valueOf(supplyInwardRecord.totalAmount()));
    }

    private void makeFieldsNonEditable(TextField... textFields) {
        for (TextField textField: textFields) {
            textField.setEditable(false);
            textField.setFocusTraversable(false);
        }
    }

    public static void setRecord(SupplyRecord record) {
        ListRecordController.record.set(record);
    }
}
