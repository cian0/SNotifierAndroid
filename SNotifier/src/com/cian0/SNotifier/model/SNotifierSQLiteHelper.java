package com.cian0.SNotifier.model;

import java.util.ArrayList;

import com.cian0.SNotifier.model.contracts.IContract;
import com.cian0.SNotifier.model.contracts.PriceAlertsContract;
import com.cian0.SNotifier.model.contracts.SecuritiesContract;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SNotifierSQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "db.sqlite";
	private static final int DATABASE_VERSION = 1;
	private ArrayList<IContract> contracts;
	
	public SNotifierSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		contracts = new ArrayList<IContract>();
		contracts.add(SecuritiesContract.getInstance());
		contracts.add(PriceAlertsContract.getInstance());
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (IContract contract : contracts){
			contract.onCreate(db);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (IContract contract : contracts){
			contract.onUpgrade(db, oldVersion, newVersion);
		}
	}

}
