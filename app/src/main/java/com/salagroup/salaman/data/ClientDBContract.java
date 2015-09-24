package com.salagroup.salaman.data;

/**
 * Client SQLite database
 */
public class ClientDBContract {
    /**
     * Table Region
     */
    public class TableRegion {
        public static final String TABLE_NAME = "T00_Region";
        public static final String COL_ID = "_id";
        public static final String COL_REGION_CODE = "RegionCode";
        public static final String COL_REGION_LEVEL = "RegionLevel";
        public static final String COL_REGION_NAME = "RegionName";
        public static final String COL_DESCRIPTION = "Description";
        public static final String COL_ORDINAL = "Ordinal";
        public static final String COL_PARENT_ID = "ParentID";
        public static final String COL_PARENT_CODE = "ParentCode";
        public static final String COL_NOTE = "Note";
        public static final String COL_CREATED_BY = "CreatedBy";
        public static final String COL_CREATED_DATETIME = "CreatedDateTime";
        public static final String COL_LAST_UPDATED_BY = "LastUpdatedBy";
        public static final String COL_LAST_UPDATED_DATETIME = "LastUpdatedDateTime";

        public static final int REGION_LEVEL_4 = 4;
    }

    /**
     * Table Industry
     */
    public class TableIndustry {
        public static final String TABLE_NAME = "T01_Industry";
        public static final String COL_ID = "_id";
        public static final String COL_INDUSTRY_NAME = "IndustryName";
        public static final String COL_INDUSTRY_NAME_E = "IndustryNameE";
        public static final String COL_INDUSTRY_LEVEL = "IndustryLevel";
        public static final String COL_ORDINAL = "Ordinal";
        public static final String COL_PARENT_ID = "ParentID";
        public static final String COL_NOTE = "Note";
        public static final String COL_CREATED_BY = "CreatedBy";
        public static final String COL_CREATED_DATETIME = "CreatedDateTime";
        public static final String COL_LAST_UPDATED_BY = "LastUpdatedBy";
        public static final String COL_LAST_UPDATED_DATETIME = "LastUpdatedDateTime";
    }

    /**
     * Table Shop
     */
    public class TableShop {
        public static final String TABLE_NAME = "T01_Shop";
        public static final String COL_ID = "_id";
        public static final String COL_CODE = "Code";
        public static final String COL_SHOP_NAME = "ShopName";
        public static final String COL_USER_ID = "UserID";
        public static final String COL_DESCRIPTION = "Description";
        public static final String COL_INVOICE_PREFIX = "InvoicePrefix";
        public static final String COL_TAX_CODE = "TaxCode";
        public static final String COL_TAX = "Fax";
        public static final String COL_EMAIL = "Email";
        public static final String COL_PHONE = "Phone";
        public static final String COL_PHONE_2 = "Phone2";
        public static final String COL_ADDRESS = "Address";
        public static final String COL_REGION_L1 = "RegionL1";
        public static final String COL_REGION_L2 = "RegionL2";
        public static final String COL_REGION_L3 = "RegionL3";
        public static final String COL_REGION_L4 = "RegionL4";
        public static final String COL_REGION_L5 = "RegionL5";
        public static final String COL_REGION_L6 = "RegionL6";
        public static final String COL_REGION_L7 = "RegionL7";
        public static final String COL_LATITUDE = "Latitude";
        public static final String COL_LONGITUDE = "Longitude";
        public static final String COL_CONTACT_NAME = "ContactName";
        public static final String COL_CONTACT_JOB_TITLE = "ContactJobTitle";
        public static final String COL_CONTACT_EMAIL = "ContactEmail";
        public static final String COL_CONTACT_PHONE = "ContactPhone";
        public static final String COL_CONTACT_ADDRESS = "ContactAddress";
        public static final String COL_NOTE = "Note";
        public static final String COL_CREATED_BY = "CreatedBy";
        public static final String COL_CREATED_DATETIME = "CreatedDateTime";
        public static final String COL_LAST_UPDATED_BY = "LastUpdatedBy";
        public static final String COL_LAST_UPDATED_DATETIME = "LastUpdatedDateTime";
    }

    /**
     * Table Shop Industry
     */
    public class TableShopIndustry {
        public static final String TABLE_NAME = "T01_Shop_Industry";
        public static final String COL_ID = "_id";
        public static final String COL_CODE = "Code";
        public static final String COL_SHOP_ID = "ShopID";
        public static final String COL_INDUSTRY_ID = "IndustryID";
        public static final String COL_NOTE = "Note";
        public static final String COL_CREATED_BY = "CreatedBy";
        public static final String COL_CREATED_DATETIME = "CreatedDateTime";
        public static final String COL_LAST_UPDATED_BY = "LastUpdatedBy";
        public static final String COL_LAST_UPDATED_DATETIME = "LastUpdatedDateTime";
    }

    /**
     * Table Shop Working Period
     */
    public class TableSWP {
        public static final String TABLE_NAME = "T01_Shop_WorkingPeriod";
        public static final String COL_ID = "_id";
        public static final String COL_SHOP_ID = "ShopID";
        public static final String COL_DAY = "Day";
        public static final String COL_TIME = "Time";
        public static final String COL_NOTE = "Note";
        public static final String COL_CREATED_BY = "CreatedBy";
        public static final String COL_CREATED_DATETIME = "CreatedDateTime";
        public static final String COL_LAST_UPDATED_BY = "LastUpdatedBy";
        public static final String COL_LAST_UPDATED_DATETIME = "LastUpdatedDateTime";
    }
}
