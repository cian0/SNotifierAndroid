package com.cian0.SNotifier.model.contracts;

import com.cian0.SNotifier.model.MainContentProvider;
import com.cian0.SNotifier.utils.Tracer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PriceAlertsContract implements IContract {
	private static final int ALERT_PRICE_CONTRACT = 30;
	private static final int ALERT_PRICE_CONTRACT_ID = 40;
	public static final int ALERT_ABOVE = 0;
	public static final int ALERT_BELOW = 1;
	private static final String TABLE_NAME = "PriceAlerts"; //also table name
	public static final Uri CONTENT_URI = Uri.parse("content://" + MainContentProvider.AUTHORITY
		      + "/" + TABLE_NAME);
	public enum COLUMN{
		PRICE_ALERT_ID ("price_alert_id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"),
		SECURITY_CODE ("security_code", "TEXT", ""),
		IS_READ ("is_read", "INTEGER", "DEFAULT 0"),
		ALERT_PRICE ("alert_price", "REAL", ""),
		REACHED_PRICE ("has_reached_price", "INTEGER", "DEFAULT 0"),
		ALERT_ABOVE_PRICE ("alert_above_price", "INTEGER", ""),
		DATE_ADDED ("date_added", "DATETIME", "DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime'))"),
		DATE_REACHED("date_reached", "DATETIME", "")
		;
		private final String id, type, additionalParams;
		COLUMN(String id, String type, String additionalParams){
			this.id = id;
			this.type = type;
			this.additionalParams = additionalParams;
		}
		public String getName() { return id; }
		public String getType() { return type; }
		public String getAdditionalParams() { return additionalParams; }
	}
	
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
	      + "/alertPrices";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	      + "/alertPrice";

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(MainContentProvider.AUTHORITY, TABLE_NAME, ALERT_PRICE_CONTRACT);
		sURIMatcher.addURI(MainContentProvider.AUTHORITY, TABLE_NAME + "/#", ALERT_PRICE_CONTRACT_ID);
	}

	//for singleton access
	private PriceAlertsContract(){
	}
	public static PriceAlertsContract getInstance() {
		return SNotifierSQLiteHelperHolder.INSTANCE;
	}
	private static class SNotifierSQLiteHelperHolder {
		public static final PriceAlertsContract INSTANCE = new PriceAlertsContract();
	}
	protected PriceAlertsContract readResolve() {
		return getInstance();
	}
	
	// db methods
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( ";
		int length = COLUMN.values().length;
		for (int i = 0 ; i < length ; i++){
			COLUMN column = COLUMN.values()[i];
			createQuery += column.getName() + " " + column.getType() + " " + column.getAdditionalParams();
			if (i != length - 1){
				createQuery += ", ";
			}
		}
		createQuery+= ")";
		Tracer.trace(createQuery);
		
		db.execSQL(createQuery);
		
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	//content provider methods	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs,
			SQLiteDatabase db) {
		return 0;
	}
	@Override
	public Uri insert(Uri uri, ContentValues values, SQLiteDatabase db, Context context) {
		int uriType = sURIMatcher.match(uri);
	    SQLiteDatabase sqlDB = db;
	    long id = 0;
	    switch (uriType) {
	    case ALERT_PRICE_CONTRACT:
	    	id = sqlDB.insert(TABLE_NAME, null, values);
	    	break;
	    default:
	    	return null;
	    }
	    context.getContentResolver().notifyChange(uri, null);
	    return Uri.parse(TABLE_NAME + "/" + id);
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder, SQLiteDatabase db, Context context) {
		return null;
	}
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs, SQLiteDatabase db, Context context) {
		return 0;
	}
	@Override
	public int bulkInsert(Uri uri, ContentValues[] values, SQLiteDatabase db,
			Context context) {
		int uriType = sURIMatcher.match(uri);
		int numInserted = -1;
		SQLiteDatabase sqlDB = db;
		
		switch (uriType) {
	    case ALERT_PRICE_CONTRACT:
	    	sqlDB.beginTransaction();
		    try {
		    	sqlDB.delete(TABLE_NAME, null, null);
		        for (ContentValues cv : values) {
//		            long newID = 
            		sqlDB.insertWithOnConflict(TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);//(TABLE_NAME, null, cv);
//		            if (newID <= 0) {
//		                throw new SQLException("Failed to insert row into " + uri);
//		            }
		        }
		        sqlDB.setTransactionSuccessful();
		        context.getContentResolver().notifyChange(uri, null);
		        numInserted = values.length;
		    } finally {
		        sqlDB.endTransaction();
		    }
	    	break;
	    default:
	    	return -1;
	    }
		
	    
		return numInserted;
	}

}
