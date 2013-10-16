package com.cian0.SNotifier.services;

import java.util.ArrayList;

import com.cian0.SNotifier.controller.AlertPricesController;
import com.cian0.SNotifier.loaders.PSEDataLoader;
import com.cian0.SNotifier.loaders.PSEDataLoader.SECTOR;
import com.cian0.SNotifier.model.contracts.SecuritiesContract;
import com.cian0.SNotifier.utils.Tracer;
import com.cian0.SNotifier.vos.Security;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.widget.Toast;
public class PSEFetchIntentService extends IntentService {
	private static final String INTENT_SERVICE_NAME = "PSEFetcher";
	public PSEFetchIntentService() {
		super(INTENT_SERVICE_NAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		PSEDataLoader loader = new PSEDataLoader(this, SECTOR.ALL);
		ArrayList<Security> securities = loader.loadInBackground();
		if (securities != null){
			insertAllSecurities(securities);
		}
	}

	private void insertAllSecurities (ArrayList<Security> securities){
		ContentValues [] cvs = new ContentValues[securities.size()];
		int i = 0;
		for (Security security : securities){
			ContentValues cv = new ContentValues();
			cv.put(SecuritiesContract.COLUMN.SECURITY_CODE.getName(), security.getSymbolCode());
			cv.put(SecuritiesContract.COLUMN.SECURITY_NAME.getName(), security.getSecurityName());
			cv.put(SecuritiesContract.COLUMN.SECURITY_PRICE.getName(), security.getLastTradePrice().toPlainString());
			cvs[i] = cv;
			i++;
		}
		getContentResolver().bulkInsert(SecuritiesContract.CONTENT_URI, cvs);
		
		ArrayList<String> ids = AlertPricesController.getInstance().getSatisfiedAlerts(this);
		if (ids.size() > 0){
			//Toast.makeText(this, "New alerts", Toast.LENGTH_LONG).show();
			Tracer.trace("has alerts");
			AlertPricesController.getInstance().updateSatisfiedAlerts(this, ids);
			AlertPricesController.getInstance().notifyUser(this, ids);
		}
		
	}
}
