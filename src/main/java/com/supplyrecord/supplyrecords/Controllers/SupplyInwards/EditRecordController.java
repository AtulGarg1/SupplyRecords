package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.LocalData;
import com.supplyrecord.supplyrecords.Models.ViewSelected;
import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditRecordController implements Initializable {
    public AutoCompleteTextField text_partyName;
    public GridPane gridPane;
    public DecimalTextField text_total;
    public Button btn_save;
    public Label label_err;

    private DatabaseApi db;
    private static final ObjectProperty<SupplyInwardRecord> record = new SimpleObjectProperty<>();
    private ArrayList<SupplyItemDetail> oldSupplyItemDetails;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new DatabaseImpl();
        text_partyName.getSuggestions().addAll(AutoSuggestions.PartyNames);
        makeNotEditable(text_total);
        fillValues();
        record.addListener((observableVal, oldVal, newVal) -> fillValues());
    }

    private void fillValues() {
        gridPane.getChildren().removeIf(TextField.class::isInstance);
        SupplyInwardRecord supplyInwardRecord = record.getValue();

        text_partyName.setText(String.valueOf(supplyInwardRecord.partyName()));

        oldSupplyItemDetails =
                db.fetchSupplyInwardItemDetailsFor(supplyInwardRecord.recordId());

        for (SupplyItemDetail supplyItemDetail : oldSupplyItemDetails) {
            int rowNo = gridPane.getRowCount();
            double itemTotal = supplyItemDetail.qty() * supplyItemDetail.price();

            TextField sno = new TextField(rowNo + ".");
            AutoCompleteTextField itemName = new AutoCompleteTextField(supplyItemDetail.itemName(), AutoSuggestions.ItemNames);
            DecimalTextField qty = new DecimalTextField(String.valueOf(supplyItemDetail.qty()));
            DecimalTextField price = new DecimalTextField(String.valueOf(supplyItemDetail.price()));
            DecimalTextField total = new DecimalTextField(String.valueOf(itemTotal));

            makeNotEditable(sno, total);

            qty.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            price.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            total.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));

            gridPane.add(sno, 0, rowNo);
            gridPane.add(itemName, 1, rowNo);
            gridPane.add(qty, 2, rowNo);
            gridPane.add(price, 3, rowNo);
            gridPane.add(total, 4, rowNo);
        }

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

            makeNotEditable(sno, total);

            qty.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            price.textProperty().addListener((observableVal, oldVal, newVal) -> updateItemTotal(qty, price, total));
            total.textProperty().addListener((observableVal, oldVal, newVal) -> updateTotal(oldVal, newVal));

            gridPane.add(sno, 0, rowNo);
            gridPane.add(itemName, 1, rowNo);
            gridPane.add(qty, 2, rowNo);
            gridPane.add(price, 3, rowNo);
            gridPane.add(total, 4, rowNo);
        }
    }

    public void onSave() {
        label_err.setVisible(false);
        String partyName = text_partyName.getText().trim();

        if (partyName.isEmpty()) {
            displayError("Please enter the Party Name.");
        } else if (!AutoSuggestions.PartyNames.contains(partyName)) {
            displayError("Party does not exist.");
        } else {
            long recordId = record.getValue().recordId();

            SupplyInwardRecord supplyInwardRecord =
                    new SupplyInwardRecord(
                            recordId, LocalData.getInstance().getFirmName(), partyName,
                            isDouble(text_total.getText()) ? Double.parseDouble(text_total.getText()) : 0,
                            LocalDate.now()
                    );

            ArrayList<SupplyItemDetail> supplyItemDetails = new ArrayList<>();
            int i = 6;
            for (; i <= 251; i += 5) {
                String item = ((AutoCompleteTextField) gridPane.getChildren().get(i)).getText();
                String qty = ((DecimalTextField) gridPane.getChildren().get(i + 1)).getText();
                String price = ((DecimalTextField) gridPane.getChildren().get(i + 2)).getText();

                if (item.isEmpty() && qty.isEmpty() && price.isEmpty()) {
                    continue;
                } else if (item.isEmpty()) {
                    displayError("An Item Name is missing");
                    break;
                } else if (!AutoSuggestions.ItemNames.contains(item)) {
                    displayError("Item does not exist.");
                    break;
                } else if (!isDouble(qty)) {
                    displayError("A Quantity is missing or invalid.");
                    break;
                } else if (!isDouble(price)) {
                    displayError("A Price is missing or invalid.");
                    break;
                } else {
                    SupplyItemDetail supplyItemDetail =
                            new SupplyItemDetail(recordId, item, Double.parseDouble(qty), Double.parseDouble(price));
                    supplyItemDetails.add(supplyItemDetail);
                }
            }

            if (i > 251) {
                LocalData.getInstance().updateSupplyInwardRecordsList(supplyInwardRecord);
                persistToDb(supplyInwardRecord, supplyItemDetails);
                ViewSelected.getInstance().setSelected(ViewSelected.Dashboard);
            }
        }
    }

    private void persistToDb(SupplyInwardRecord supplyInwardRecord, ArrayList<SupplyItemDetail> newList) {
        db.updateSupplyInwardRecord(supplyInwardRecord);
        diff(oldSupplyItemDetails, newList, supplyInwardRecord.recordId());
    }

    private void diff(ArrayList<SupplyItemDetail> oldList, ArrayList<SupplyItemDetail> newList, long recordId) {
        ArrayList<SupplyItemDetail> toBeRemoved = new ArrayList<>();
        ArrayList<SupplyItemDetail> toBeInserted = new ArrayList<>();
        int lim = Math.min(oldList.size(), newList.size());

        for (int i = 0; i < lim; i++) {
            if (oldList.get(i) != newList.get(i)) {
                toBeRemoved.add(oldList.get(i));
                toBeInserted.add(newList.get(i));
            }
        }

        for (int i = lim; i < newList.size(); i++) {
            toBeInserted.add(newList.get(i));
        }

        db.deleteSupplyInwardItemDetails(toBeRemoved);
        db.addSupplyInwardItemDetails(toBeInserted, recordId);
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

    private void updateTotal(String oldVal, String newVal) {
        double total =
                (isDouble(text_total.getText()) ? Double.parseDouble(text_total.getText()) : 0) -
                        (isDouble(oldVal) ? Double.parseDouble(oldVal) : 0) +
                        (isDouble(newVal) ? Double.parseDouble(newVal) : 0);

        text_total.setText(
                total == 0 ? "" : String.format("%.2f", total)
        );
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

    public static void setRecord(SupplyInwardRecord record) {
        EditRecordController.record.set(record);
    }
}
