package com.supplyrecord.supplyrecords.Models;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;
import com.supplyrecord.supplyrecords.Models.DataClasses.PaymentRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyOutwardRecord;

import java.util.ArrayList;

/**
 * <ul>
 *     <li>After login, it will fetch all data for that firm and store it for use.</li>
 *     <li>Any modifications done will be visible here as well as propagated to DB.</li>
 *     <li>It only contains data recognized by a firm_name. Any other data will have to be fetched by the corresponding DB interface at the time of query.</li>
 * </ul>
 * */
public class LocalData {
    private static LocalData localData;
    private static DatabaseApi db;

    private static String firmName;
    private static ArrayList<SupplyInwardRecord> supplyInwardRecordsList;
    private static ArrayList<SupplyOutwardRecord> supplyOutwardRecordsList;
    private static ArrayList<PaymentRecord> paymentsMadeList;
    private static ArrayList<PaymentRecord> paymentsReceivedList;

    private LocalData() {
        db = new DatabaseImpl();
        supplyInwardRecordsList = new ArrayList<>();
        supplyOutwardRecordsList = new ArrayList<>();
        paymentsMadeList = new ArrayList<>();
        paymentsReceivedList = new ArrayList<>();
    }

    public static synchronized LocalData getInstance() {
        if (localData == null) {
            localData = new LocalData();
        }
        return localData;
    }

    public static void fetchLists() {
        supplyInwardRecordsList = db.fetchSupplyInwardRecordsList(firmName);
        supplyOutwardRecordsList = db.fetchSupplyOutwardRecordsList(firmName);
        paymentsMadeList = db.fetchPaymentsMadeList(firmName);
        paymentsReceivedList = db.fetchPaymentsReceivedList(firmName);
    }

    public void setFirmName(String firmName) {
        LocalData.firmName = firmName;
    }

    public String getFirmName() {
        return firmName;
    }

    public ArrayList<SupplyInwardRecord> getSupplyInwardRecordsList() {
        return supplyInwardRecordsList;
    }

    public ArrayList<SupplyOutwardRecord> getSupplyOutwardRecordsList() {
        return supplyOutwardRecordsList;
    }

    public ArrayList<PaymentRecord> getPaymentsMadeList() {
        return paymentsMadeList;
    }

    public ArrayList<PaymentRecord> getPaymentsReceivedList() {
        return paymentsReceivedList;
    }

    // TODO: Should be moved to DB interface
    public ArrayList<SupplyItemDetail> fetchSupplyItemDetailsFor(long recordId) {
        // TODO: fetch data using record_id
        return SupplyItemDetail.generateDummyData();
    }
}
