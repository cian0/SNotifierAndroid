package com.cian0.SNotifier.vos;

import java.math.BigDecimal;
import java.util.Calendar;

import com.cian0.SNotifier.utils.CalendarUtils;

import android.os.Parcel;
import android.os.Parcelable;

public class PriceAlert implements Parcelable {
/*
 * 
 *
		PRICE_ALERT_ID ("price_alert_id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"),
		SECURITY_CODE ("security_code", "TEXT", ""),
		IS_READ ("is_read", "INTEGER", "DEFAULT 0"),
		ALERT_PRICE ("alert_price", "REAL", ""),
		REACHED_PRICE ("has_reached_price", "INTEGER", "DEFAULT 0"),
		ALERT_ABOVE_PRICE ("alert_above_price", "INTEGER", ""),
		DATE_ADDED ("date_added", "DATETIME", "DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime'))"),
		DATE_REACHED("date_reached", "DATETIME", "") */
	
	
	private int priceAlertId;
	private boolean isRead, shouldAlertAbovePrice, priceReached;
	private String symbolCode;
	private Calendar dateAdded, dateReached;
	private BigDecimal alertPrice;
	public int getPriceAlertId() {
		return priceAlertId;
	}
	public void setPriceAlertId(int priceAlertId) {
		this.priceAlertId = priceAlertId;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public boolean isShouldAlertAbovePrice() {
		return shouldAlertAbovePrice;
	}
	public void setShouldAlertAbovePrice(boolean shouldAlertAbovePrice) {
		this.shouldAlertAbovePrice = shouldAlertAbovePrice;
	}
	public boolean isPriceReached() {
		return priceReached;
	}
	public void setPriceReached(boolean priceReached) {
		this.priceReached = priceReached;
	}
	public String getSymbolCode() {
		return symbolCode;
	}
	public void setSymbolCode(String symbolCode) {
		this.symbolCode = symbolCode;
	}
	public Calendar getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Calendar dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Calendar getDateReached() {
		return dateReached;
	}
	public void setDateReached(Calendar dateReached) {
		this.dateReached = dateReached;
	}
	public BigDecimal getAlertPrice() {
		return alertPrice;
	}
	public void setAlertPrice(BigDecimal alertPrice) {
		this.alertPrice = alertPrice;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	static final Parcelable.Creator<PriceAlert> CREATOR = new Parcelable.Creator<PriceAlert>() {

		@Override
		public PriceAlert createFromParcel(Parcel arg0) {
			// TODO Auto-generated method stub
			
			return new PriceAlert(arg0);
		}

		@Override
		public PriceAlert[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return new PriceAlert [arg0];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.alertPrice.toPlainString());
		dest.writeString(CalendarUtils.calendarToString(this.dateAdded));
		dest.writeString(CalendarUtils.calendarToString(this.dateReached));
		dest.writeByte((byte)((isRead) ? 1 : 0));
		dest.writeInt(this.priceAlertId);
		dest.writeByte((byte)((priceReached) ? 1 : 0));
		dest.writeByte((byte)((shouldAlertAbovePrice) ? 1 : 0));
		dest.writeString(this.symbolCode);
	}
	
	private PriceAlert (Parcel in){
		this.alertPrice = new BigDecimal(in.readString());
		this.dateAdded = CalendarUtils.stringToCalendar(in.readString());
		this.dateReached = CalendarUtils.stringToCalendar(in.readString());
		this.isRead = in.readByte() != 0;
		this.priceAlertId = in.readInt();
		this.priceReached = in.readByte() != 0;
		this.shouldAlertAbovePrice = in.readByte() != 0;
		this.symbolCode = in.readString();
	}
	public PriceAlert() {
		// TODO Auto-generated constructor stub
	}
	
}
