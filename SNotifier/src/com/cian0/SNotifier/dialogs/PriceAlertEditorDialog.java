package com.cian0.SNotifier.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.cian0.SNotifier.R;
import com.cian0.SNotifier.controller.AlertPricesController;
import com.cian0.SNotifier.view.PriceAlertEditorDialogView;

public class PriceAlertEditorDialog extends SherlockDialogFragment{
	
	private PriceAlertEditorDialogView pALertDialogView;
	public PriceAlertEditorDialog (){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		pALertDialogView = (PriceAlertEditorDialogView) inflater.inflate(R.layout.dialog_add_alert, container);
		pALertDialogView.init();
		AlertPricesController.getInstance().initializePriceAlertEditorDialog(this, pALertDialogView);
		return pALertDialogView;
	}
}
