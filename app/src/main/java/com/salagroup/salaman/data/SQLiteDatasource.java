package com.salagroup.salaman.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.salagroup.salaman.pojo.Industry;
import com.salagroup.salaman.pojo.Region;
import com.salagroup.salaman.pojo.Shop;

import java.util.ArrayList;

public class SQLiteDatasource {

    private ClientDBHelper clientDBHelper;
    private SQLiteDatabase sqLiteDatabase;

    public SQLiteDatasource(Context context) {
        clientDBHelper = ClientDBHelper.newInstance(context);
        clientDBHelper.createDatabase();
        sqLiteDatabase = clientDBHelper.openDatabase();
    }

    public void closeDBConnection() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  CRUD Table Region
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Region> getRegion(int level) {
        ArrayList<Region> regions = null;

        // Query
        String table = ClientDBContract.TableRegion.TABLE_NAME;
        String[] columns = {ClientDBContract.TableRegion.COL_ID, ClientDBContract.TableRegion.COL_REGION_NAME};
        String selection = ClientDBContract.TableRegion.COL_REGION_LEVEL + " = " + level;
        Cursor cursor = sqLiteDatabase.query(table, columns, selection, null, null, null, null);

        // Save data to list
        if (cursor.moveToNext()) {
            regions = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                Region region = new Region();
                region.set_id(cursor.getLong(cursor.getColumnIndex(ClientDBContract.TableRegion.COL_ID)));
                region.setRegionName(cursor.getString(cursor.getColumnIndex(ClientDBContract.TableRegion.COL_REGION_NAME)));
                regions.add(region);
                cursor.moveToNext();
            }
        }

        return regions;
    }

    public ArrayList<Region> getRegion(long parentID) {
        ArrayList<Region> regions = null;

        // Query
        String table = ClientDBContract.TableRegion.TABLE_NAME;
        String[] columns = {ClientDBContract.TableRegion.COL_ID, ClientDBContract.TableRegion.COL_REGION_NAME};
        String selection = ClientDBContract.TableRegion.COL_PARENT_ID + " = " + parentID;
        Cursor cursor = sqLiteDatabase.query(table, columns, selection, null, null, null, null);

        // Save data to list
        if (cursor.moveToNext()) {
            regions = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                Region region = new Region();
                region.set_id(cursor.getLong(cursor.getColumnIndex(ClientDBContract.TableRegion.COL_ID)));
                region.setRegionName(cursor.getString(cursor.getColumnIndex(ClientDBContract.TableRegion.COL_REGION_NAME)));
                regions.add(region);
                cursor.moveToNext();
            }
        }
        return regions;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  CRUD Table Industry
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Industry> getAllIndustries() {
        ArrayList<Industry> industries = null;

        // Query
        String table = ClientDBContract.TableIndustry.TABLE_NAME;
        String[] columns = {ClientDBContract.TableIndustry.COL_ID, ClientDBContract.TableIndustry.COL_INDUSTRY_NAME};
        Cursor cursor = sqLiteDatabase.query(table, columns, null, null, null, null, null);

        // Save data to list
        if (cursor.moveToNext()) {
            industries = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                Industry industry = new Industry();
                industry.set_id(cursor.getLong(cursor.getColumnIndex(ClientDBContract.TableIndustry.COL_ID)));
                industry.setIndustryName(cursor.getString(cursor.getColumnIndex(ClientDBContract.TableIndustry.COL_INDUSTRY_NAME)));
                industries.add(industry);
                cursor.moveToNext();
            }
        }
        return industries;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  CRUD Table Shop
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public long createShop(ContentValues shopValues) {
        return sqLiteDatabase.insert(ClientDBContract.TableShop.TABLE_NAME, ClientDBContract.TableShop.COL_ID, shopValues);
    }

    public Shop getShopByName(String shopName) {
        Shop shop = null;

        // Query
        String table = ClientDBContract.TableShop.TABLE_NAME;
        String[] columns = {ClientDBContract.TableShop.COL_ID};
        String selection = ClientDBContract.TableShop.COL_SHOP_NAME + " = '" + shopName + "'";
        Cursor cursor = sqLiteDatabase.query(table, columns, selection, null, null, null, null);

        // Save data to list
        if (cursor.moveToNext()) {
            shop = new Shop();
            shop.set_id(cursor.getLong(cursor.getColumnIndex(ClientDBContract.TableShop.COL_ID)));
        }

        return shop;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  CRUD Table Shop Industry
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public long insertShopIndustry(ContentValues shopIndustryValues) {
        return sqLiteDatabase.insert(ClientDBContract.TableShopIndustry.TABLE_NAME, ClientDBContract.TableIndustry.COL_ID, shopIndustryValues);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  CRUD Table Shop Working Period
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public long insertSWP(ContentValues swpValues) {
        return sqLiteDatabase.insert(ClientDBContract.TableSWP.TABLE_NAME, ClientDBContract.TableIndustry.COL_ID, swpValues);
    }
}
