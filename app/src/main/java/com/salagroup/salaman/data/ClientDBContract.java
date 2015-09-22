package com.salagroup.salaman.data;

/**
 * Client SQLite database
 */
public class ClientDBContract {
    /**
     * Table Region
     */
    public class TableRegion {
        public static final String TABLE_REGION = "T00_Region";
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
    }
}
