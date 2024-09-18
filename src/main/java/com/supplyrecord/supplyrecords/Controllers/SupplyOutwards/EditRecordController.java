package com.supplyrecord.supplyrecords.Controllers.SupplyOutwards;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import com.supplyrecord.supplyrecords.customComponents.UppercaseTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditRecordController implements Initializable {
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
    public Button btn_save;

    private static final ObjectProperty<SupplyInwardRecord> record = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeNotEditable(text_total, text_subTotal);
        attachUpdateTotal();
        fillValues();
        record.addListener((observableVal, oldVal, newVal) -> fillValues());
    }

    private void fillValues() {
        gridPane.getChildren().removeIf(TextField.class::isInstance);
        SupplyInwardRecord supplyInwardRecord = record.getValue();

        text_partyName.setText(String.valueOf(supplyInwardRecord.partyName()));
        text_biltiCharge.setText(String.valueOf(supplyInwardRecord.biltiCharge()));
        text_bardana.setText(String.valueOf(supplyInwardRecord.bardana()));
        text_labourCost.setText(String.valueOf(supplyInwardRecord.labourCost()));
        text_commission.setText(String.valueOf(supplyInwardRecord.commission()));
        text_postage.setText(String.valueOf(supplyInwardRecord.postage()));
        text_bazaarCharges.setText(String.valueOf(supplyInwardRecord.bazaarCharges()));
        text_otherExpenses.setText(String.valueOf(supplyInwardRecord.otherExpenses()));

        ArrayList<SupplyItemDetail> supplyItemDetails =
                LocalData.getInstance().fetchSupplyItemDetailsFor(supplyInwardRecord.recordId());
        double subTotal = 0;

        for (SupplyItemDetail supplyItemDetail : supplyItemDetails) {
            int rowNo = gridPane.getRowCount();
            double itemTotal = supplyItemDetail.qty() * supplyItemDetail.price();

            TextField sno = new TextField(rowNo + ".");
            AutoCompleteTextField itemName = new AutoCompleteTextField(supplyItemDetail.itemName(), AutoSuggestions.ItemNames);
            DecimalTextField qty = new DecimalTextField(String.valueOf(supplyItemDetail.qty()));
            DecimalTextField price = new DecimalTextField(String.valueOf(supplyItemDetail.price()));
            DecimalTextField total = new DecimalTextField(String.valueOf(itemTotal));

            makeNotEditable(sno);
            makeNotEditable(total);

            qty.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            price.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            total.textProperty().addListener((observableVal, oldVal, newVal) -> updateSubTotal(oldVal, newVal));

            gridPane.add(sno, 0, rowNo);
            gridPane.add(itemName, 1, rowNo);
            gridPane.add(qty, 2, rowNo);
            gridPane.add(price, 3, rowNo);
            gridPane.add(total, 4, rowNo);
            subTotal += itemTotal;
        }

        text_subTotal.setText(String.valueOf(subTotal));
        text_total.setText(String.valueOf(supplyInwardRecord.totalAmount()));

        addEmptyRows();
    }

    private void addEmptyRows() {
        for (int rowNo = gridPane.getRowCount(); rowNo <= 50; rowNo++) {
            TextField sno = new TextField(rowNo + ".");
            AutoCompleteTextField itemName = new AutoCompleteTextField(AutoSuggestions.ItemNames);
            DecimalTextField qty = new DecimalTextField();
            DecimalTextField price = new DecimalTextField();
            DecimalTextField total = new DecimalTextField();

            makeNotEditable(sno);
            makeNotEditable(total);

            qty.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            price.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            total.textProperty().addListener((observableVal, oldVal, newVal) -> updateSubTotal(oldVal, newVal));

            gridPane.add(sno, 0, rowNo);
            gridPane.add(itemName, 1, rowNo);
            gridPane.add(qty, 2, rowNo);
            gridPane.add(price, 3, rowNo);
            gridPane.add(total, 4, rowNo);
        }
    }

    public void onSave() {
        SupplyInwardRecord supplyInwardRecord =
                new SupplyInwardRecord(
                        record.getValue().recordId(), LocalData.getInstance().getFirmName(),
                        text_partyName.getText(),
                        isDouble(text_total.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                        LocalDate.now(),
                        isDouble(text_biltiCharge.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                        isDouble(text_bardana.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                        isDouble(text_labourCost.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                        isDouble(text_commission.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                        isDouble(text_postage.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                        isDouble(text_bazaarCharges.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                        isDouble(text_otherExpenses.getText()) ? Double.parseDouble(text_total.getText()) : 0
                );

        // TODO: update values using the recordId
        long recordId = record.getValue().recordId();

        ArrayList<SupplyItemDetail> supplyItemDetails = new ArrayList<>();
        for (int i = 6; i <= 251; i += 5) {
            String item = ((AutoCompleteTextField) gridPane.getChildren().get(i)).getText();
            String qty = ((DecimalTextField) gridPane.getChildren().get(i + 1)).getText();
            String price = ((DecimalTextField) gridPane.getChildren().get(i + 2)).getText();

            if (isValid(item, qty, price)) {
                SupplyItemDetail supplyItemDetail =
                        new SupplyItemDetail(recordId, item, Double.parseDouble(qty), Double.parseDouble(price));
                supplyItemDetails.add(supplyItemDetail);
            }
        }

        // TODO: remove existing entries with this recordId and add new ones to DB
    }

    private void makeNotEditable(TextField... textFields) {
        for (TextField textField: textFields) {
            textField.setEditable(false);
            textField.setFocusTraversable(false);
        }
    }

    private void updateItemTotal(DecimalTextField tf_qty, DecimalTextField tf_price, DecimalTextField tf_total) {
        String qty = tf_qty.getText();
        String price = tf_price.getText();

        double itemTotal =
                (isDouble(qty) ? Double.parseDouble(qty) : 0) *
                        (isDouble(price) ? Double.parseDouble(price) : 0);

        tf_total.setText(
                itemTotal == 0 ? "" : String.format("%.2f", itemTotal)
        );
    }

    private void updateSubTotal(String oldVal, String newVal) {
        double subTotal =
                (isDouble(text_subTotal.getText()) ? Double.parseDouble(text_subTotal.getText()) : 0) -
                        (isDouble(oldVal) ? Double.parseDouble(oldVal) : 0) +
                        (isDouble(newVal) ? Double.parseDouble(newVal) : 0);

        text_subTotal.setText(
                subTotal == 0 ? "" : String.format("%.2f", subTotal)
        );
    }

    private void updateTotal(String oldVal, String newVal) {
        double total =
                (isDouble(text_total.getText()) ? Double.parseDouble(text_total.getText()) : 0) -
                        (isDouble(oldVal) ? Double.parseDouble(oldVal) : 0) +
                        (isDouble(newVal) ? Double.parseDouble(newVal) : 0);

        text_total.setText(
                total == 0 ? "" : String.format("%.2f", total)
        );
    }

    private void attachUpdateTotal() {
        text_subTotal.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_biltiCharge.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_bardana.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_labourCost.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_commission.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_postage.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_bazaarCharges.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
        text_otherExpenses.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));
    }

    private boolean isValid(String item, String qty, String price) {
        return !item.isEmpty() && isDouble(qty) && isDouble(price);
    }

    private boolean isDouble(String val) {
        try {
            Double.parseDouble(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void setRecord(SupplyInwardRecord record) {
        EditRecordController.record.set(record);
    }
}
