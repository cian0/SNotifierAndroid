package com.cian0.SNotifier.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.cian0.SNotifier.utils.HTTPPostHandler;
import com.cian0.SNotifier.vos.Security;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class PSEDataLoader extends AsyncTaskLoader<ArrayList<Security>> {
	private final String PSE_STOCKS_URL = "http://www.pse.com.ph/stockMarket/marketInfo-marketActivity-indicesComposition.html?method=getCompositionIndices&ajax=true";
	public static enum SECTORS{
		ALL ("ALL"),
		PSEI ("PSE"),
		FINANCIALS ("FIN"),
		HOLDING_FIRMS ("HGD"),
		PROPERTY ("PRO"),
		MINING_AND_OIL ("M-O"),
		SERVICES ("SVC"),
		INDUSTRIALS  ("IND")
		;
		
		private final String id;
		SECTORS(String id){
			this.id = id;
		}
		public String getValue() { return id; }
	}
	private SECTORS sector = SECTORS.ALL;
	private List<NameValuePair> sectorParam;
	
	public PSEDataLoader(Context context) {
		super(context);
		
	}

	@Override
	public ArrayList<Security> loadInBackground() {
		HTTPPostHandler handler = new HTTPPostHandler(PSE_STOCKS_URL);
		sectorParam = new ArrayList<NameValuePair>();
		sectorParam.add(new BasicNameValuePair("sector", sector.getValue()));
		
		handler.setParams(sectorParam);
		
		return null;
	}
	private void parseResult(String result){
		
		
	}

	public SECTORS getSector() {
		return sector;
	}

	public void setSector(SECTORS sector) {
		this.sector = sector;
	}

}
