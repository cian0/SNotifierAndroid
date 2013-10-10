package com.cian0.SNotifier.vos;

import java.math.BigDecimal;
import java.util.Calendar;

public class PriceAlert {
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
	
}
