package com.cian0.SNotifier.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cian0.SNotifier.R;
import com.cian0.SNotifier.activities.MainActivity;
import com.cian0.SNotifier.adapters.spinner.SymbolSpinnerAdapter;
import com.cian0.SNotifier.controller.interfaces.IAlertPriceController;
import com.cian0.SNotifier.dialogs.PriceAlertEditorDialog;
import com.cian0.SNotifier.model.MainContentProvider;
import com.cian0.SNotifier.model.contracts.PriceAlertsContract;
import com.cian0.SNotifier.model.contracts.SecuritiesContract;
import com.cian0.SNotifier.utils.DialogManager;
import com.cian0.SNotifier.utils.Tracer;
import com.cian0.SNotifier.view.PriceAlertEditorDialogView;
import com.cian0.SNotifier.vos.PriceAlert;
import com.cian0.SNotifier.vos.Security;

public class AlertPricesController implements IAlertPriceController {
	private enum ERRORS{
		NO_PRICE,
		NO_SELECTED_RADIO,
		INVALID_PRICE,
		NO_ERROR
	}
	//for singleton access
	private AlertPricesController(){
	}
	public static AlertPricesController getInstance() {
		return PriceListControllerHolder.INSTANCE;
	}
	private static class PriceListControllerHolder {
		public static final AlertPricesController INSTANCE = new AlertPricesController();
	}
	protected AlertPricesController readResolve() {
		return getInstance();
	}
	
	public void notifyUser(Context c, ArrayList<String> ids){
		Intent intent = new Intent(c, MainActivity.class);
		intent.putStringArrayListExtra("ids", ids);
		PendingIntent pIntent = PendingIntent.getActivity(c, 0, intent, 0);

		// build notification
		// the addAction re-use the same intent to keep the example short
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//		builder.setSound(alarmSound);
		Notification n  = new NotificationCompat.Builder(c)
				.setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("Stock alert reached.")
		        .setSound(alarmSound)
		        .setContentText("Stock Notifier")
		        .setContentIntent(pIntent)
		        .build();
		    
		  
		NotificationManager notificationManager = 
		  (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.notify(0, n); 
	}
	@Override
	public void addPriceAlert(PriceAlert pAlert, Context c) {
		// TODO Auto-generated method stub
		
		Cursor cur = c.getContentResolver().query(PriceAlertsContract.CONTENT_URI, new String [] {
			PriceAlertsContract.COLUMN.ALERT_ABOVE_PRICE.getName(),
			PriceAlertsContract.COLUMN.ALERT_PRICE.getName(),
			PriceAlertsContract.COLUMN.SECURITY_CODE.getName()
		}, PriceAlertsContract.COLUMN.ALERT_ABOVE_PRICE.getName() + " = ? AND " +
				PriceAlertsContract.COLUMN.ALERT_PRICE.getName() + " = ? AND "+ 
				PriceAlertsContract.COLUMN.REACHED_PRICE.getName() + " = 0 AND "+ 
				PriceAlertsContract.COLUMN.SECURITY_CODE.getName() + " = ? ", 
			new String [] {
				(pAlert.isShouldAlertAbovePrice() ? "1" : "0"),
				pAlert.getAlertPrice().toPlainString(),
				pAlert.getSymbolCode()
			}
				, null);
		
		if (cur != null)
			return;
		ContentValues cv = new ContentValues();
		cv.put(PriceAlertsContract.COLUMN.ALERT_ABOVE_PRICE.getName(), (pAlert.isShouldAlertAbovePrice() ? 1 : 0));
		cv.put(PriceAlertsContract.COLUMN.ALERT_PRICE.getName(), pAlert.getAlertPrice().toPlainString());
		cv.put(PriceAlertsContract.COLUMN.SECURITY_CODE.getName(), pAlert.getSymbolCode());
		
		c.getContentResolver().insert(PriceAlertsContract.CONTENT_URI, cv);
	}
	@Override
	public void removePriceAlert() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updatePriceAlertList() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void editPriceAlert() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void savePriceAlert() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void showPriceAlerts() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getPriceAlerts() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void showNotifications() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getNotifications() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateNotificationList() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateNotificationsAsRead() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initializePriceAlertEditorDialog(final PriceAlertEditorDialog c, final PriceAlertEditorDialogView view) {
		String projection [] = new String []{SecuritiesContract.COLUMN.SECURITY_CODE.getName(), 
		                                   SecuritiesContract.COLUMN.SECURITY_NAME.getName(), 
		                                   SecuritiesContract.COLUMN.SECURITY_PRICE.getName()};
		Cursor cur = c.getActivity().getContentResolver().query(SecuritiesContract.CONTENT_URI, projection, null, null, null);
		final PriceAlert pAlert = new PriceAlert();
		pAlert.setPriceAlertId(-1);
		final ArrayList<Security> securities = new ArrayList<Security>();

		List<String> list = new ArrayList<String>();
		while(cur.moveToNext()){
			Security sec = new Security();
			sec.setSecurityName(cur.getString(cur.getColumnIndex(SecuritiesContract.COLUMN.SECURITY_NAME.getName())));
			sec.setSymbolCode(cur.getString(cur.getColumnIndex(SecuritiesContract.COLUMN.SECURITY_CODE.getName())));
			sec.setLastTradePrice(new BigDecimal(cur.getString(cur.getColumnIndex(SecuritiesContract.COLUMN.SECURITY_PRICE.getName()))));
			securities.add(sec);
			list.add(sec.getSymbolCode());
		}
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(c.getActivity(),
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		view.getSymbolCodeSpinner().setAdapter(dataAdapter);
		view.getSymbolCodeSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Tracer.trace(securities.get(position).getSecurityName());
				view.getPriceValueEditText().setText(securities.get(position).getLastTradePrice().toPlainString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		view.getSubmitButton().setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if (checkFormErrors(view) != ERRORS.NO_ERROR){
					DialogManager.alert(c.getActivity(), "SNotifier", "Please check form errors.", "Ok", "Cancel", null, null);
					return;
				}
				
				int selectedId = view.getPriceDirectionGroup().getCheckedRadioButtonId();
				switch (selectedId){
				case R.id.radioAbove:
					pAlert.setShouldAlertAbovePrice(true);
					break;
				case R.id.radioBelow:
					pAlert.setShouldAlertAbovePrice(false);
					break;
				}
				
				pAlert.setPriceReached(false);
				pAlert.setAlertPrice(new BigDecimal(view.getPriceValueEditText().getText().toString()));
				pAlert.setSymbolCode(view.getSymbolCodeSpinner().getSelectedItem().toString());
				
				if (pAlert.getPriceAlertId() == -1){
					addPriceAlert(pAlert, c.getActivity());
					DialogManager.alert(c.getActivity(), "SNotifier", "Successfuly added, add more?", "Yes", "No", new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							view.getSymbolCodeSpinner().setSelection(0);
							view.getPriceValueEditText().setText("");
						}
					}, new Runnable() {
						
						@Override
						public void run() {
							c.dismiss();
						}
					});
				}
				
				
			}
		});
		
	}
	private ERRORS checkFormErrors (PriceAlertEditorDialogView view){
		if (view.getPriceDirectionGroup().getCheckedRadioButtonId() == -1){
			Tracer.trace("NO_SELECTED_RADIO");
			return ERRORS.NO_SELECTED_RADIO;
		}
		
		if (view.getPriceValueEditText().getText().toString().trim().length() == 0){
			Tracer.trace("NO_PRICE");
			return ERRORS.NO_PRICE;
		}
		try{
			Double.parseDouble(view.getPriceValueEditText().getText().toString().trim());
		}catch(Exception e){
			Tracer.trace("INVALID_PRICE");
			return ERRORS.INVALID_PRICE;
		}
		return ERRORS.NO_ERROR;
	}
	@Override
	public void addPriceAlert() {
		// TODO Auto-generated method stub
		
	}
//	@Override
//	public void initializePriceAlertEditorDialog(Context c,
//			PriceAlertEditorDialogView view) {
//		// TODO Auto-generated method stub
//		
//	}
	public ArrayList<String> getSatisfiedAlerts(Context c){
		
		Cursor cursor = c.getContentResolver().query(PriceAlertsContract.VIEW_ALERTS_URI, null, null, null, null);
		ArrayList<String> ids = new ArrayList<String>();
		if (cursor != null){
			while (cursor.moveToNext()){
				ids.add(cursor.getString(cursor.getColumnIndex(PriceAlertsContract.COLUMN.PRICE_ALERT_ID.getName())));
			}
		}
		return ids;
	}
	@Override
	public void updateSatisfiedAlerts(Context c, ArrayList<String> ids) {
//		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		
		for (int i =0 ; i < ids.size() ; i++){
//			operations.add(ContentProviderOperation.newUpdate(PriceAlertsContract.CONTENT_URI)
//					.withSelection(
//					PriceAlertsContract.COLUMN.PRICE_ALERT_ID.getName() + " = ?", new String [] {ids.get(i)})
//					.withValue(PriceAlertsContract.COLUMN.REACHED_PRICE.getName(), "1")
//					.withYieldAllowed(true)
//					.build()
//					);
			Tracer.trace("Updating id " + ids.get(i) + " to reached price 1" );
			
			ContentValues cv = new ContentValues();
			cv.put(PriceAlertsContract.COLUMN.REACHED_PRICE.getName(), 1);
			
			
			c.getContentResolver().update(PriceAlertsContract.CONTENT_URI, cv, PriceAlertsContract.COLUMN.PRICE_ALERT_ID.getName() + " = ?", new String [] {ids.get(i)}); 
			
		}
//		try {
//			c.getContentResolver().applyBatch(MainContentProvider.AUTHORITY, operations);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (OperationApplicationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
}
