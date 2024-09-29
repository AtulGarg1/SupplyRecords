package com.supplyrecord.supplyrecords.Database;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.PaymentRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyOutwardRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseImpl implements DatabaseApi {
    private Connection connection;

    private Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:supply-records.db");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    @Override
    public void dbTest() {
        String query = "SELECT * FROM firm_credentials";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String name = result.getString("firm_name");
                String pass = result.getString(2);

                System.out.println("name: " + name + ", pass: " + pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createFirm(String firmName, String password) {

    }

    @Override
    public ArrayList<String> fetchFirmNames() {
        return new ArrayList<>(Arrays.asList("ABC", "RAJENDER ANIL"));
    }

    @Override
    public ArrayList<AutoSuggestions.Item> fetchItems() {
        return new ArrayList<>(Arrays.asList(
                new AutoSuggestions.Item("ALMOND", "KGS"),
                new AutoSuggestions.Item("CASHEW", "KGS"),
                new AutoSuggestions.Item("WALNUT", "BAGS"),
                new AutoSuggestions.Item("PISTA", "UNIT")
        ));
    }

    @Override
    public ArrayList<String> fetchBankNames() {
        return new ArrayList<>(Arrays.asList("PNB", "BOB", "AXIS BANK", "YES BANK"));
    }

    @Override
    public ArrayList<String> fetchPartyNames() {
        return new ArrayList<>(Arrays.asList("LT/CH", "OD", "ST/CH", "GT/KLK", "DEV SHRI", "RV TRADERS", "JKTC", "ATC"));
    }

    @Override
    public ArrayList<SupplyInwardRecord> fetchSupplyInwardRecordsList(String firmName) {
        return SupplyInwardRecord.generateDummyData();
    }

    @Override
    public ArrayList<SupplyOutwardRecord> fetchSupplyOutwardRecordsList(String firmName) {
        return SupplyOutwardRecord.generateDummyData();
    }

    @Override
    public ArrayList<PaymentRecord> fetchPaymentsMadeList(String firmName) {
        return PaymentRecord.generateDummyData(false);
    }

    @Override
    public ArrayList<PaymentRecord> fetchPaymentsReceivedList(String firmName) {
        return PaymentRecord.generateDummyData(true);
    }

    @Override
    public ArrayList<SupplyItemDetail> fetchSupplyInwardItemDetailsFor(long recordId) {
        return SupplyItemDetail.generateDummyData();
    }

    @Override
    public ArrayList<SupplyItemDetail> fetchSupplyOutwardItemDetailsFor(long recordId) {
        return SupplyItemDetail.generateDummyData();
    }

    @Override
    public void addBankAccount(String bank) {

    }

    @Override
    public void addParty(String party) {

    }

    @Override
    public void addItem(AutoSuggestions.Item item) {

    }

    @Override
    public void addSupplyInwardRecord(SupplyInwardRecord supplyInwardRecord) {

    }

    @Override
    public long getLatestRecordId() {
        return 0;
    }

    @Override
    public void updateSupplyInwardRecord(SupplyInwardRecord supplyInwardRecord) {

    }

    @Override
    public void addSupplyOutwardRecord(SupplyOutwardRecord supplyOutwardRecord) {

    }

    @Override
    public void addSupplyInwardItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails, long recordId) {

    }

    @Override
    public void addSupplyOutwardItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails, long recordId) {

    }

    @Override
    public void updateSupplyOutwardRecord(SupplyOutwardRecord supplyOutwardRecord) {

    }

    @Override
    public void addPaymentRecord(PaymentRecord paymentRecord) {

    }

    @Override
    public void updatePaymentRecord(PaymentRecord paymentRecord) {

    }

    @Override
    public void deleteSupplyInwardItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails) {

    }

    @Override
    public void deleteSupplyOutwardItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails) {

    }

    @Override
    public boolean verifyLogin(String firmName, String password) {
        return true;
    }
}
