package com.supplyrecord.supplyrecords.Controllers.SupplyInwards;

import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    public GridPane gridPane;
    public TextField text_partyName;
    public Text text_subTotal;
    public DecimalTextField text_biltiCharge;
    public DecimalTextField text_bardana;
    public DecimalTextField text_labourCost;
    public DecimalTextField text_commission;
    public DecimalTextField text_postage;
    public DecimalTextField text_bazaarCharges;
    public DecimalTextField text_otherExpenses;
    public Text text_total;
    public Button btn_save;

    private int sno;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.sno = 1;
        setupGridPane();
    }

    public void onSave() {
//        for (int i = 6; i <= 254; i += 5) {
//            String item = ((AutoCompleteTextField) gridPane.getChildren().get(i)).getText();
//            Double qty = Double.valueOf(((DecimalTextField) gridPane.getChildren().get(i + 1)).getText());
//            Double price = Double.valueOf(((DecimalTextField) gridPane.getChildren().get(i + 2)).getText());
//            Double total = Double.valueOf(((Text) gridPane.getChildren().get(i + 3)).getText());
//        }
        Text t = (Text) gridPane.getChildren().get(254);
        t.setText("hehe");
        System.out.println(t);
        // TODO: extract rows items, validate them and update DB
    }

    private void setupGridPane() {
        for (int i = 0; i < 50; i++) addRow();
    }

    private void addRow() {
        int rowNo = gridPane.getRowCount();

        DecimalTextField qty = new DecimalTextField();
        DecimalTextField price = new DecimalTextField();
        Text total = new Text();

        qty.textProperty().addListener(((observableVal, oldVal, newVal) -> updateTotal(qty, price, total)));
        price.textProperty().addListener(((observableVal, oldVal, newVal) -> updateTotal(qty, price, total)));

        gridPane.add(new Text(this.sno + "."), 0, rowNo);
        gridPane.add(new AutoCompleteTextField(AutoSuggestions.ItemNames), 1, rowNo);
        gridPane.add(qty, 2, rowNo);
        gridPane.add(price, 3, rowNo);
        gridPane.add(total, 4, rowNo);

        this.sno++;
    }

    private void updateTotal(DecimalTextField tf_qty, DecimalTextField tf_price, Text tf_total) {
        String qty = tf_qty.getText();
        String price = tf_price.getText();

        if (!qty.isEmpty() && !price.isEmpty()) {
            tf_total.setText(
                    String.valueOf(
                            Double.parseDouble(qty) * Double.parseDouble(price)
                    )
            );
        }
    }
}
