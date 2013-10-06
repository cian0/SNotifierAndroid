package com.cian0.SNotifier.controller.interfaces;

public interface IAlertPriceController {
	public void addPriceAlert();
	public void removePriceAlert();
	public void updatePriceAlertList();
	public void editPriceAlert();
	public void savePriceAlert();
	public void showPriceAlerts();
	public void getPriceAlerts();
	
	public void showNotifications();
	public void getNotifications();
	public void updateNotificationList();
	public void updateNotificationsAsRead();
	
}
