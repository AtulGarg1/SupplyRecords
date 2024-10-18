package com.supplyrecord.supplyrecords.Database;

import com.supplyrecord.supplyrecords.Models.AutoSuggestions;
import com.supplyrecord.supplyrecords.Models.DataClasses.*;

import java.util.ArrayList;

public interface DatabaseApi {
    /* ----CREATE---- */
    void createFirm(String firmName, String password);

    void addBankAccount(String bank);
    void addParty(String party);
    void addItem(AutoSuggestions.Item item);
    void addSupplyRecord(SupplyRecord supplyRecord);
    void addSupplyItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails, long recordId);
    void addPaymentRecord(PaymentRecord paymentRecord);

    /* ----READ---- */
    boolean verifyLogin(String firmName, String password);

    ArrayList<String> fetchFirmNames();
    ArrayList<AutoSuggestions.Item> fetchItems();
    ArrayList<String> fetchBankNames();
    ArrayList<String> fetchPartyNames();

    ArrayList<SupplyRecord> fetchSupplyInwardRecordsList(String firmName);
    ArrayList<SupplyRecord> fetchSupplyOutwardRecordsList(String firmName);
    ArrayList<SupplyItemDetail> fetchSupplyItemDetailsFor(long recordId);
    ArrayList<PaymentRecord> fetchPaymentsMadeList(String firmName);
    ArrayList<PaymentRecord> fetchPaymentsReceivedList(String firmName);

    long getLatestRecordId();

    /* ----UPDATE---- */
    void updateSupplyRecord(SupplyRecord supplyRecord);
    void updatePaymentRecord(PaymentRecord paymentRecord);

    void deleteSupplyItemDetails(ArrayList<SupplyItemDetail> supplyItemDetails);
}
