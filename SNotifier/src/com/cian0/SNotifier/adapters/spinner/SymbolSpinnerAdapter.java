package com.cian0.SNotifier.adapters.spinner;

import java.util.ArrayList;
import java.util.List;

import com.cian0.SNotifier.R;
import com.cian0.SNotifier.utils.CalendarUtils;
import com.cian0.SNotifier.vos.PriceAlert;
import com.cian0.SNotifier.vos.Security;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class SymbolSpinnerAdapter extends ArrayAdapter<Security> {
//	public SymbolSpinnerAdapter(Context context, int resource,
//			List<Security> objects) {
//		super(context, resource, objects);
//		// TODO Auto-generated constructor stub
//	}
	private ArrayList<Security> securities;
	private FragmentActivity context;
	private LayoutInflater inflater ;
	
	public SymbolSpinnerAdapter (Context context, int src, int srcText, List<Security> objects){
//		super(context, R.layout.list_view_item_security_from_spinner, R.id.textViewSecurityCode, objects);
		super(context,  android.R.layout.simple_spinner_item, objects);
		this.context = (FragmentActivity) context;
		this.securities = (ArrayList<Security>) objects;
		inflater = LayoutInflater.from(context);
	}
	static class ViewHolder {
		public TextView code, desc;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return securities.size();
	}

	@Override
	public Security getItem(int arg0) {
		// TODO Auto-generated method stub
		return securities.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);
		}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		TextView code, desc;
		if (rowView == null){
//			LayoutInflater inflater = LayoutInflater.from(context);
			rowView = inflater.inflate(R.layout.list_view_item_security_from_spinner, parent, false);
			//ViewHolder holder = new ViewHolder();
			
			//rowView.setTag(holder);
		}
		code = (TextView) rowView.findViewById(R.id.textViewSymbolCode);
		desc= (TextView) rowView.findViewById(R.id.TextViewSecurityDesc);
		
		//ViewHolder vHolder = (ViewHolder) rowView.getTag();
		//process it.
		
		Security security = securities.get(position);
		
		code.setText(security.getSymbolCode() + " " + "(" + security.getLastTradePrice().toPlainString() +  ")");
		
		
		desc.setText(security.getSecurityName());
		
		
		
		return rowView;
	}

}
