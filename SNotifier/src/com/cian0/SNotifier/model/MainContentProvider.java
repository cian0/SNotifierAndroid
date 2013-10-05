package com.cian0.SNotifier.model;

import java.util.ArrayList;

import com.cian0.SNotifier.model.contracts.IContract;
import com.cian0.SNotifier.model.contracts.PriceAlertsContract;
import com.cian0.SNotifier.model.contracts.SecuritiesContract;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MainContentProvider extends ContentProvider{
	SNotifierSQLiteHelper helper;
	//hack to optimize speed
	public static int target = -1;
	public static final String AUTHORITY = "com.cian0.SNotifier";
	
	public static final int TARGET_SECURITIES = 0;
	public static final int TARGET_PRICE_ALERTS= 1;
	private static ArrayList<IContract> contracts = new ArrayList<IContract>();
	static {
		contracts.add(SecuritiesContract.getInstance());
		contracts.add(PriceAlertsContract.getInstance());
	}
	@Override
	public String getType(Uri uri) {
		return null;
	}
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		helper = new SNotifierSQLiteHelper(getContext());
		return false;
	}

	
	//DDL operations
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int retVal = -1;
		if (target!= -1){
			retVal = contracts.get(target).delete(uri, selection, selectionArgs, helper.getWritableDatabase());
			if (retVal!= -1)
				return retVal;
		}
		for (IContract contract : contracts){
			retVal = contract.delete(uri, selection, selectionArgs, helper.getWritableDatabase());
			if (retVal!= -1)
				break;
		}
		return retVal;
	}
	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		int retVal = -1;
		if (target!= -1){
			retVal = contracts.get(target).bulkInsert(uri, values, helper.getWritableDatabase(), getContext());
			if (retVal!= -1)
				return retVal;
		}
		for (IContract contract : contracts){
			retVal = contract.bulkInsert(uri, values, helper.getWritableDatabase(), getContext());
			if (retVal!= -1)
				break; 
		}
		return retVal;
	}
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		Uri retVal = null;
		if (target!= -1){
			retVal = contracts.get(target).insert(uri, values, helper.getWritableDatabase(), getContext());
			if (retVal!= null)
				return retVal;
		}
		
		
		for (IContract contract : contracts){
			retVal = contract.insert(uri, values, helper.getWritableDatabase(), getContext());
			if (retVal!= null)
				break; 
		}
		return retVal;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor retVal = null;
		if (target!= -1){
			retVal = contracts.get(target).query(uri, projection, selection, selectionArgs, sortOrder, helper.getWritableDatabase(), getContext());
			if (retVal!= null)
				return retVal;
		}
		for (IContract contract : contracts){
			retVal = contract.query(uri, projection, selection, selectionArgs, sortOrder, helper.getWritableDatabase(), getContext());
			if (retVal!= null)
				break;
		}
		return retVal;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int retVal = -1;
		if (target!= -1){
			retVal = contracts.get(target).update(uri, values, selection, selectionArgs, helper.getWritableDatabase(), getContext());
			if (retVal!= -1)
				return retVal;
		}
		for (IContract contract : contracts){
			retVal = contract.update(uri, values, selection, selectionArgs, helper.getWritableDatabase(), getContext());
			if (retVal!= -1)
				break;
		}
		return retVal;
	}

}
