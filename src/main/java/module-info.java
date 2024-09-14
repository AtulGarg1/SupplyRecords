module com.supplyrecord.supplyrecords {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.supplyrecord.supplyrecords to javafx.fxml;
    exports com.supplyrecord.supplyrecords;
    exports com.supplyrecord.supplyrecords.Controllers;
    exports com.supplyrecord.supplyrecords.Controllers.Add;
    exports com.supplyrecord.supplyrecords.Controllers.PaymentsMade;
    exports com.supplyrecord.supplyrecords.Controllers.PaymentsReceived;
    exports com.supplyrecord.supplyrecords.Controllers.SupplyInwards;
    exports com.supplyrecord.supplyrecords.Controllers.SupplyOutwards;
    exports com.supplyrecord.supplyrecords.customComponents;
    exports com.supplyrecord.supplyrecords.Models;
    exports com.supplyrecord.supplyrecords.Views;
}