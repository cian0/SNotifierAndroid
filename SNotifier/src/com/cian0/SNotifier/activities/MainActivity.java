package com.cian0.SNotifier.activities;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cian0.SNotifier.R;
import com.cian0.SNotifier.R.id;
import com.cian0.SNotifier.R.layout;
import com.cian0.SNotifier.R.menu;
import com.cian0.SNotifier.dialogs.PriceAlertEditorDialog;
import com.cian0.SNotifier.loaders.PSEDataLoader;
import com.cian0.SNotifier.loaders.PSEDataLoader.SECTOR;
import com.cian0.SNotifier.model.contracts.PriceAlertsContract;
import com.cian0.SNotifier.model.contracts.SecuritiesContract;
import com.cian0.SNotifier.services.PSEDataServiceController;
import com.cian0.SNotifier.services.PSEFetchIntentService;
import com.cian0.SNotifier.utils.DialogManager;
import com.cian0.SNotifier.utils.Tracer;
import com.cian0.SNotifier.view.PriceAlertEditorDialogView;
import com.cian0.SNotifier.view.PriceAlertView;
import com.cian0.SNotifier.vos.Security;

import android.os.Bundle;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;

public class MainActivity extends SherlockFragmentActivity implements LoaderCallbacks<ArrayList<Security>> {
	private final int PSE_LOADER = 0;
	private final int CURSOR_LOADER = 1;
	ProgressDialog progressDialog = null;
	PSEDataLoader pseDataLoader = null;
	AlertDialog alertDialog = null;
	boolean isServiceRunning = false;
	PriceAlertView priceAlertView;
	private PriceAlertEditorDialogView pDialogView;
	LoaderCallbacks<Cursor> cursorLoaderCallBack = new LoaderCallbacks<Cursor>() {
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle params) {
			switch (id){
			case CURSOR_LOADER:
				return null;// new CursorLoader(MainActivity.this, MainContentProvider.CONTENT_URI, null, null, null, null);
			}
			return null;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			
		}
	};
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()){
		
		case R.id.add_alert:
			new PriceAlertEditorDialog().show(getSupportFragmentManager(), "price_alert_editor");
			break;
		case R.id.start_service:
			startPSEService();
			
//			stopService(new Intent(this, PSEFetchIntentService.class));
			break;
		case R.id.stop_service:
			stopService(new Intent(this, PSEDataServiceController.class));
			
			break;
		}
		
		
		return true;
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   MenuInflater inflater = getSupportMenuInflater();
	   inflater.inflate(R.menu.main, menu);
	   return true;
	}
	private void showProgressDialog(){
		progressDialog = ProgressDialog.show(this, "Loading", "Wait while loading...");
		progressDialog.setCancelable(true);
		progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				//abort http request
				if (pseDataLoader!= null){
					pseDataLoader.cancelLoad();
					pseDataLoader.abort();
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							MainActivity.this);
						// set title
						alertDialogBuilder.setTitle("SNotifier");
			 
						// set dialog message
						alertDialogBuilder
							.setMessage("Canceled load! Exit now?")
							.setCancelable(false)
							.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, close
									// current activity
									MainActivity.this.finish();
								}
							  })
							.setNegativeButton("No",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});
			 
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 
							// show it
							alertDialog.show();
						
				}
			}
		});
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getLayoutInflater();
		priceAlertView = (PriceAlertView) inflater.inflate(R.layout.activity_main, null);
		pDialogView = (PriceAlertEditorDialogView) inflater.inflate(R.layout.dialog_add_alert, null);
		
		setContentView(priceAlertView);
		
		ArrayList<String> ids = getIntent().getStringArrayListExtra("ids");
		if (ids != null){
			Tracer.trace("ids exist");
			StringBuilder sb = new StringBuilder();
			for (int i = 0 ; i < ids.size() ; i++){
				Cursor cur = getContentResolver().query(PriceAlertsContract.CONTENT_URI, new String [] {PriceAlertsContract.COLUMN.SECURITY_CODE.getName()}, PriceAlertsContract.COLUMN.PRICE_ALERT_ID.getName() + " = ?", new String [] {ids.get(i)}, null);
				if (cur.moveToFirst()){
					Tracer.trace("cur exist");
					Cursor cur2 = getContentResolver().query(SecuritiesContract.CONTENT_URI,
							new String [] {SecuritiesContract.COLUMN.SECURITY_CODE.getName(),
							SecuritiesContract.COLUMN.SECURITY_NAME.getName(),
							SecuritiesContract.COLUMN.SECURITY_PRICE.getName()
							
					}, SecuritiesContract.COLUMN.SECURITY_CODE.getName() + " = ?", new String [] {cur.getString(cur.getColumnIndex(PriceAlertsContract.COLUMN.SECURITY_CODE.getName()))}, null);
					
					if (cur2.moveToFirst()){

						Tracer.trace("cur2 exist");
						sb.append("Symbol " + cur2.getString(cur2.getColumnIndex(SecuritiesContract.COLUMN.SECURITY_CODE.getName())));
						sb.append(" now at " + cur2.getString(cur2.getColumnIndex(SecuritiesContract.COLUMN.SECURITY_PRICE.getName())));
						sb.append("\n");
					}
				}
				
			}
			if (sb.toString().length() > 0){
				DialogManager.alert(this, "Alerts", sb.toString(), "Ok", "Cancel", null, null);
			}
		}
//		startPSELoader();
		startPSEService();
	}
	private void startPSEService(){
		if (!isMyServiceRunning()){
			Intent i= new Intent(this, PSEDataServiceController.class);	
			startService(i); 
			
		}
		isServiceRunning = true;
	}
	private void startPSELoader (){
		Loader<ArrayList<Security>> loader = getSupportLoaderManager().initLoader(PSE_LOADER, null, this);
		loader.forceLoad();
	}
	private boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (PSEDataServiceController.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	private void startCursorLoader (){
		Loader<Cursor> loader = getSupportLoaderManager().initLoader(CURSOR_LOADER, null, cursorLoaderCallBack);
		loader.forceLoad();
	}
	
	@Override
	public Loader<ArrayList<Security>> onCreateLoader(int id, Bundle params) {
		switch (id){
		case PSE_LOADER:
			pseDataLoader = new PSEDataLoader(this, SECTOR.ALL);
			return pseDataLoader;
		
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Security>> loader,
			ArrayList<Security> result) {
		if (progressDialog!= null){
			if (progressDialog.isShowing())
				progressDialog.dismiss();
		}
		if (result != null){
			Tracer.trace("Results fetched : " + result.size());
			insertAllSecurities(result);
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
	}
	
	@Override
	public void onLoaderReset(Loader<ArrayList<Security>> loader) {

		
	}

}
