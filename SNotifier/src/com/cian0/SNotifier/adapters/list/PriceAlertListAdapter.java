package com.cian0.SNotifier.adapters.list;

import java.util.ArrayList;
import java.util.List;

import com.cian0.SNotifier.R;
import com.cian0.SNotifier.utils.CalendarUtils;
import com.cian0.SNotifier.vos.PriceAlert;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PriceAlertListAdapter extends ArrayAdapter<PriceAlert>{
	private final String ALERT_TEXT = "%1 has %2 %3.";
	private final String RISEN = "risen above";
	private final String DROPPED = "dropped below";
	
	private ArrayList<PriceAlert> priceAlerts;
	private final Activity context;
	public PriceAlertListAdapter(Activity context,
			List<PriceAlert> objects) {
		super(context, R.layout.list_view_item_price_alert, objects);
		priceAlerts = (ArrayList<PriceAlert>) objects;
		this.context = context;
	}

	static class ViewHolder {
		public TextView created, updated, alert;
		public ImageView reachedImg;
	}
	@Override
	public int getCount() {
		return priceAlerts.size();
	}

	@Override
	public PriceAlert getItem(int position) {
		return priceAlerts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		
		if (rowView == null){
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.list_view_item_price_alert, parent);
			ViewHolder holder = new ViewHolder();
			holder.alert = (TextView) rowView.findViewById(R.id.textViewAlertText);
			holder.created= (TextView) rowView.findViewById(R.id.textViewCreated);
			holder.updated= (TextView) rowView.findViewById(R.id.TextViewUpdated);
			holder.reachedImg = (ImageView) rowView.findViewById(R.id.imageViewReached);
			rowView.setTag(holder);
		}
		
		ViewHolder vHolder = (ViewHolder) rowView.getTag();
		//process it.
		
		PriceAlert priceAlert = priceAlerts.get(position);
		
		if (priceAlert.isPriceReached()){
			vHolder.reachedImg.setVisibility(View.VISIBLE);
		}else{
			vHolder.reachedImg.setVisibility(View.INVISIBLE);
		}
		
		String code, remark, price;
		code = priceAlert.getSymbolCode();
		if (priceAlert.isShouldAlertAbovePrice()){
			remark = RISEN;
		}else{
			remark = DROPPED;
		}
		price = priceAlert.getAlertPrice().toPlainString();
		
		String alertText = ALERT_TEXT.replace("%1", code).replace("%2", remark).replace("%3", price);
		vHolder.alert.setText(alertText);
		
		vHolder.created.setText("Created on " +CalendarUtils.calendarToString(priceAlert.getDateAdded()));
		vHolder.updated.setText("Updated on " +CalendarUtils.calendarToString(priceAlert.getDateReached()));
		
		
		
		return rowView;
	}

}
