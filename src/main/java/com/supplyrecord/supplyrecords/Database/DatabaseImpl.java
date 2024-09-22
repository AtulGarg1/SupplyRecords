package com.supplyrecord.supplyrecords.Database;

import com.supplyrecord.supplyrecords.Models.DataClasses.PaymentRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyOutwardRecord;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseImpl implements DatabaseApi{
    @Override
    public void createFirm(String firmName, String password) {

    }

    @Override
    public ArrayList<String> fetchFirmNames() {
        return new ArrayList<>(Arrays.asList("ABC", "RAJENDER ANIL"));
    }

    @Override
    public ArrayList<String> fetchItemNames() {
        return new ArrayList<>(Arrays.asList("ALMOND (KILOS)", "KAJU (KILOS)", "AKHROT (BAGS)", "ANJEER (KILOS)", "PISTA (UNIT)"));
    }

    @Override
    public ArrayList<String> fetchBankNames() {
        return new ArrayList<>(Arrays.asList("PNB", "BOB", "AXIS BANK", "YES BANK"));
    }

    @Override
    public ArrayList<String> fetchPartyNames() {
        return new ArrayList<>(Arrays.asList("LT/CH", "OD", "ST/CH", "GT/KLK"));
    }

    @Override
    public ArrayList<String> fetchSupplierNames() {
        return new ArrayList<>(Arrays.asList("DEV SHRI", "KRISHNA TRADING", "MANISHA TRADING", "MA TRADERS"));
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
    public void addBankAccount(String bank) {

    }

    @Override
    public void addParty(String party) {

    }

    @Override
    public void addSupplier(String supplier) {

    }

    @Override
    public void addItem(String item) {

    }

    @Override
    public boolean verifyLogin(String firmName, String password) {
        return true;
    }
}
