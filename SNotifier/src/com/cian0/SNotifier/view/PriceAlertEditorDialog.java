package com.cian0.SNotifier.view;

import com.cian0.SNotifier.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class PriceAlertEditorDialog extends RelativeLayout{
	private Spinner symbolCodeSpinner;
	private EditText priceValueEditText;
	private RadioGroup priceDirectionGroup;
	
	private void init(){
		this.setPriceDirectionGroup((RadioGroup) findViewById(R.id.radioGroupPriceDirection));
		this.setSymbolCodeSpinner((Spinner) findViewById(R.id.spinnerSymbolCode));
		this.setPriceValueEditText((EditText) findViewById(R.id.editTextPrice));
	}
	public PriceAlertEditorDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PriceAlertEditorDialog(Context context) {
		super(context);
		init();
	}

	public Spinner getSymbolCodeSpinner() {
		return symbolCodeSpinner;
	}

	public void setSymbolCodeSpinner(Spinner symbolCodeSpinner) {
		this.symbolCodeSpinner = symbolCodeSpinner;
	}

	public EditText getPriceValueEditText() {
		return priceValueEditText;
	}

	public void setPriceValueEditText(EditText priceValueEditText) {
		this.priceValueEditText = priceValueEditText;
	}

	public RadioGroup getPriceDirectionGroup() {
		return priceDirectionGroup;
	}

	public void setPriceDirectionGroup(RadioGroup priceDirectionGroup) {
		this.priceDirectionGroup = priceDirectionGroup;
	}

}
