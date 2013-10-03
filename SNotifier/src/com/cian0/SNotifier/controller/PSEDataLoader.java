package com.cian0.SNotifier.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cian0.SNotifier.utils.HTTPPostHandler;
import com.cian0.SNotifier.utils.Tracer;
import com.cian0.SNotifier.vos.Security;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class PSEDataLoader extends AsyncTaskLoader<ArrayList<Security>> {
	private HTTPPostHandler handler = null;
	private final String PSE_STOCKS_URL = "http://www.pse.com.ph/stockMarket/marketInfo-marketActivity-indicesComposition.html?method=getCompositionIndices&ajax=true";
	private boolean isCanceled = false;
	public static enum SECTOR{
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
		SECTOR(String id){
			this.id = id;
		}
		public String getValue() { return id; }
	}
	private final String TAG_COUNT = "count";
	private final String TAG_RECORDS = "records";
	private SECTOR sector = SECTOR.ALL;
	private List<NameValuePair> sectorParam;
	
	public PSEDataLoader(Context context, SECTOR sector) {
		super(context);
		this.sector = sector;
	}
	public void abort (){
		isCanceled = true;
		if (handler != null)
			handler.abort();
		
	}
	@Override
	public void onCanceled(ArrayList<Security> data) {
		// TODO Auto-generated method stub
		super.onCanceled(data);
		abort();
	}
	@Override
	public ArrayList<Security> loadInBackground() {
		handler = new HTTPPostHandler(PSE_STOCKS_URL);
		sectorParam = new ArrayList<NameValuePair>();
		sectorParam.add(new BasicNameValuePair("sector", sector.getValue()));
		
		handler.setParams(sectorParam);
		String result= null;
		if (isCanceled)
			return null;
		try {
			result = handler.sendRequestWithProxy();
			return parseResult(result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = null;
		}
		
		return null;
	}
	private ArrayList<Security> parseResult(String result) throws JSONException{
		JSONObject resultingJSON = new JSONObject(result);
		JSONArray secArray = resultingJSON.getJSONArray(TAG_RECORDS);
		ArrayList<Security> securities = new ArrayList<Security>();
		for (int i = 0 ; i < secArray.length() ; i++){
			Security security = new Security();
			Tracer.trace(" obj : " + secArray.getJSONObject(i).get(Security.TAG_LASTTRADEPRICE).toString());
			security.setLastTradePrice(new BigDecimal(secArray.getJSONObject(i).getDouble(Security.TAG_LASTTRADEPRICE)));
			security.setSecurityName(secArray.getJSONObject(i).getString(Security.TAG_SECURITYNAME));
			security.setSymbolCode(secArray.getJSONObject(i).getString(Security.TAG_SECURITYSYMBOL));
			securities.add(security);
		}
		
		return securities;
	}


}
