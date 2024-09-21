package com.supplyrecord.supplyrecords.Models;

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
    private String firmName;
    private final ArrayList<SupplyInwardRecord> supplyInwardRecordsList;
    private final ArrayList<SupplyOutwardRecord> supplyOutwardRecordsList;
    private final ArrayList<PaymentRecord> paymentsMadeList;
    private final ArrayList<PaymentRecord> paymentsReceivedList;

    private LocalData() {
        supplyInwardRecordsList = fetchSupplyInwardRecordsList();
        supplyOutwardRecordsList = fetchSupplyOutwardRecordsList();
        paymentsMadeList = fetchPaymentsMadeList();
        paymentsReceivedList = fetchPaymentsReceivedList();

    }

    public static synchronized LocalData getInstance() {
        if (localData == null) {
            localData = new LocalData();
        }
        return localData;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
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

    private ArrayList<SupplyInwardRecord> fetchSupplyInwardRecordsList() {
        // TODO: fetch data using firm_name
        return SupplyInwardRecord.generateDummyData();
    }

    private ArrayList<SupplyOutwardRecord> fetchSupplyOutwardRecordsList() {
        // TODO: fetch data using firm_name
        return SupplyOutwardRecord.generateDummyData();
    }

    private ArrayList<PaymentRecord> fetchPaymentsMadeList() {
        // TODO: fetch data using firm_name
        return PaymentRecord.generateDummyData(false);
    }

    private ArrayList<PaymentRecord> fetchPaymentsReceivedList() {
        // TODO: fetch data using firm_name
        return PaymentRecord.generateDummyData(true);
    }

    // TODO: Should be moved to DB interface
    public ArrayList<SupplyItemDetail> fetchSupplyItemDetailsFor(long recordId) {
        // TODO: fetch data using record_id
        return SupplyItemDetail.generateDummyData();
    }
}
