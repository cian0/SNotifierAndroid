package com.cian0.SNotifier.view;

import com.cian0.SNotifier.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class PriceAlertView extends RelativeLayout {

	private ListView priceAlertsList;
	
	public PriceAlertView(Context context) {
		super(context);
		setPriceAlertsList((ListView) findViewById(R.id.listViewPriceLists));	
	}
	public PriceAlertView(Context context, AttributeSet attr) {
		super(context, attr);
		setPriceAlertsList((ListView) findViewById(R.id.listViewPriceLists));	
	}
	public ListView getPriceAlertsList() {
		return priceAlertsList;
	}
	private void setPriceAlertsList(ListView priceAlertsList) {
		this.priceAlertsList = priceAlertsList;
	}
}