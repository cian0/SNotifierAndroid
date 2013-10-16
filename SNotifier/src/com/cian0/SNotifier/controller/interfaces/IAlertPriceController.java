package com.cian0.SNotifier.controller.interfaces;

import java.util.ArrayList;

import com.cian0.SNotifier.dialogs.PriceAlertEditorDialog;
import com.cian0.SNotifier.view.PriceAlertEditorDialogView;
import com.cian0.SNotifier.vos.PriceAlert;

import android.content.Context;
import android.os.Bundle;

public interface IAlertPriceController {
	
	//needs reference to spinner, edit text and radio group.
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
	public void updateSatisfiedAlerts(Context c, ArrayList<String> ids);
	//public void initializePriceAlertEditorDialog (Context c, PriceAlertEditorDialogView view);

	void addPriceAlert(PriceAlert alert , Context c);

	void initializePriceAlertEditorDialog(PriceAlertEditorDialog c,
			PriceAlertEditorDialogView view);
}
