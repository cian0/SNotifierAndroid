package com.cian0.SNotifier.view;

import com.cian0.SNotifier.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class PriceAlertEditorDialogView extends RelativeLayout{
	private Spinner symbolCodeSpinner;
	private EditText priceValueEditText;
	private RadioGroup priceDirectionGroup;
	private Button submitButton;
	private enum MODE {
		ADD,
		EDIT
	}
	@Override
	public void addView(View child) {
		// TODO Auto-generated method stub
		super.addView(child);
	}
	
	@Override
	public void addView(View child, int index,
			android.view.ViewGroup.LayoutParams params) {
		// TODO Auto-generated method stub
		super.addView(child, index, params);
	}
	@Override
	public void addView(View child, int index) {
		// TODO Auto-generated method stub
		super.addView(child, index);
	}
	@Override
	public void addView(View child, int width, int height) {
		// TODO Auto-generated method stub
		super.addView(child, width, height);
	}
	@Override
	public void addView(View child, android.view.ViewGroup.LayoutParams params) {
		// TODO Auto-generated method stub
		super.addView(child, params);
	}
	
	public void init(){
		this.setPriceDirectionGroup((RadioGroup) findViewById(R.id.radioGroupPriceDirection));
		this.setSymbolCodeSpinner((Spinner) findViewById(R.id.spinnerSymbolCode));
		this.setPriceValueEditText((EditText) findViewById(R.id.editTextPrice));
		this.setSubmitButton((Button) findViewById(R.id.buttonSubmit));
	}
	public PriceAlertEditorDialogView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		init();
	}

	public PriceAlertEditorDialogView(Context context) {
		super(context);
//		init();
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

	public Button getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(Button submitButton) {
		this.submitButton = submitButton;
	}

}
