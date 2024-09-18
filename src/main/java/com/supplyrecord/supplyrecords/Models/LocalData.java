package com.supplyrecord.supplyrecords.Models;

import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyInwardRecord;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;

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

    private LocalData() {
        supplyInwardRecordsList = fetchSupplyInwardRecordsList();
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

    private ArrayList<SupplyInwardRecord> fetchSupplyInwardRecordsList() {
        // TODO: fetch data using firm_name
        return SupplyInwardRecord.generateDummyData();
    }

    public ArrayList<SupplyInwardRecord> getSupplyInwardRecordsList() {
        return supplyInwardRecordsList;
    }

    // TODO: Should be moved to DB interface
    public ArrayList<SupplyItemDetail> fetchSupplyItemDetailsFor(long recordId) {
        // TODO: fetch data using record_id
        return SupplyItemDetail.generateDummyData();
    }
}
