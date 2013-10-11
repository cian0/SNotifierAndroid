package com.cian0.SNotifier.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cian0.SNotifier.adapters.spinner.SymbolSpinnerAdapter;
import com.cian0.SNotifier.controller.interfaces.IAlertPriceController;
import com.cian0.SNotifier.model.contracts.SecuritiesContract;
import com.cian0.SNotifier.utils.Tracer;
import com.cian0.SNotifier.view.PriceAlertEditorDialogView;
import com.cian0.SNotifier.vos.PriceAlert;
import com.cian0.SNotifier.vos.Security;

public class AlertPricesController implements IAlertPriceController {

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
	@Override
	public void addPriceAlert() {
		// TODO Auto-generated method stub
		
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
	public void initializePriceAlertEditorDialog(Context c, final PriceAlertEditorDialogView view) {
		String projection [] = new String []{SecuritiesContract.COLUMN.SECURITY_CODE.getName(), 
		                                   SecuritiesContract.COLUMN.SECURITY_NAME.getName(), 
		                                   SecuritiesContract.COLUMN.SECURITY_PRICE.getName()};
		Cursor cur = c.getContentResolver().query(SecuritiesContract.CONTENT_URI, projection, null, null, null);
		
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
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(c,
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
				
			}
		});
		
//		SymbolSpinnerAdapter sAdapter = new SymbolSpinnerAdapter(c,-1, -1, securities);
		
//		view.getSymbolCodeSpinner().setAdapter(sAdapter);
	}
	
	
}
