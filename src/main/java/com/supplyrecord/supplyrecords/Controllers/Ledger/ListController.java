package com.supplyrecord.supplyrecords.Controllers.Ledger;

import com.supplyrecord.supplyrecords.customComponents.AutoCompleteTextField;
import com.supplyrecord.supplyrecords.customComponents.DecimalTextField;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ListController implements Initializable {
    public AutoCompleteTextField text_partyName;
    public GridPane gridPane;
    public DecimalTextField text_totalCredit;
    public DecimalTextField text_totalDebit;
    public DecimalTextField text_totalBalance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onSearch() {

    }

    public void onClear() {

    }
}
