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

public class SecuritiesContract implements IContract {
	private static final int SECURITIES_CONTRACT = 10;
	private static final int SECURITIES_CONTRACT_ID = 20;
	private static final String TABLE_NAME = "Securities"; //also table name
	public static final Uri CONTENT_URI = Uri.parse("content://" + MainContentProvider.AUTHORITY
		      + "/" + TABLE_NAME);
	public enum COLUMN{
		SECURITY_NAME ("security_name", "TEXT", ""),
		SECURITY_CODE ("security_code", "TEXT", "PRIMARY KEY"),
		SECURITY_PRICE ("security_price", "REAL", ""),
		UPDATE_TIME ("update_time", "DATETIME", "DEFAULT CURRENT_TIMESTAMP")
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
	      + "/securities";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	      + "/security";

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(MainContentProvider.AUTHORITY, TABLE_NAME, SECURITIES_CONTRACT);
		sURIMatcher.addURI(MainContentProvider.AUTHORITY, TABLE_NAME + "/#", SECURITIES_CONTRACT_ID);
	}

	//for singleton access
	private SecuritiesContract(){
	}
	public static SecuritiesContract getInstance() {
		return SNotifierSQLiteHelperHolder.INSTANCE;
	}
	private static class SNotifierSQLiteHelperHolder {
		public static final SecuritiesContract INSTANCE = new SecuritiesContract();
	}
	protected SecuritiesContract readResolve() {
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
	    case SECURITIES_CONTRACT:
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
	    case SECURITIES_CONTRACT:
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
