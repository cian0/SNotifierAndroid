package com.cian0.SNotifier.controller;

import com.cian0.SNotifier.controller.interfaces.IAlertPriceController;

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
	
	
	
}
