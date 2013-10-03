package com.cian0.SNotifier.vos;

import java.math.BigDecimal;

public class Security {
	private String securityName, symbolCode, lastTradeDate;
	private BigDecimal lastTradePrice;
	private double freeFloatLevel, percentWeight;
	public static final String TAG_TOTALMARKETCAPITALIZATION = "totalMarketCapitalization";
	public static final String TAG_FREEFLOATLEVEL = "freeFloatLevel";
	public static final String TAG_LASTTRADEDATE = "lastTradeDate";
	public static final String TAG_LASTTRADEPRICE = "lastTradePrice";
	public static final String TAG_PERCENTWEIGHT = "percentWeight";
	public static final String TAG_SECURITYID = "securityID";
	public static final String TAG_MARKETCAPITILIZATION = "marketCapitilization";
	public static final String TAG_SECURITYSYMBOL = "securitySymbol";
	public static final String TAG_SECURITYNAME = "securityName";
	public static final String TAG_COMPANYID = "companyId";
	public static final String TAG_OUTSTANDINGSHARES = "outstandingShares";
	
	/*
	 * "totalMarketCapitalization": 9.69133929758592E12,
		"freeFloatLevel": 33,
		"lastTradeDate": "2013-10-03",
		"lastTradePrice": 1.16,
		"percentWeight": 0.0033275070668543068,
		"securityID": "581",
		"marketCapitilization": 3.2248E8,
		"securitySymbol": "YEHEY",
		"securityName": "YEHEY!CORPORATION",
		"companyId": "638",
		"outstandingShares": 2.78E8
	 * */
	//0 is position num
	//1 Security Name
	//2 Symbol
	//3 Last Trade Date
	//4 Last Trade Price
	//5 Outstanding Shares
	//6 Full Market Capitalization
	//7 % Weight
	
	public String getSymbolCode() {
		return symbolCode;
	}
	public void setSymbolCode(String symbolCode) {
		this.symbolCode = symbolCode;
	}
	public String getLastTradeDate() {
		return lastTradeDate;
	}
	public void setLastTradeDate(String lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}
	public BigDecimal getLastTradePrice() {
		return lastTradePrice;
	}
	public void setLastTradePrice(BigDecimal lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}
	public double getFreeFloatLevel() {
		return freeFloatLevel;
	}
	public void setFreeFloatLevel(double freeFloatLevel) {
		this.freeFloatLevel = freeFloatLevel;
	}
	public double getPercentWeight() {
		return percentWeight;
	}
	public void setPercentWeight(double percentWeight) {
		this.percentWeight = percentWeight;
	}
	public long getOutstandingShares() {
		return outstandingShares;
	}
	public void setOutstandingShares(long outstandingShares) {
		this.outstandingShares = outstandingShares;
	}
	private long outstandingShares, freeFloatMarketCap;
	public long getFreeFloatMarketCap() {
		return freeFloatMarketCap;
	}
	public void setFreeFloatMarketCap(long freeFloatMarketCap) {
		this.freeFloatMarketCap = freeFloatMarketCap;
	}
	public String getSecurityName() {
		return securityName;
	}
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
}
