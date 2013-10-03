package com.cian0.SNotifier.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;


public class HTTPPostHandler {
	private String userAgent = "Mozilla/5.0";
	private String contentType = "application/x-www-form-urlencoded";
	private String result = null;
	private String paramCharset = "UTF-8";
	private HttpClient httpClient;
	private List<NameValuePair> params = null;
	private String url;
	private String host = "gateway.zscaler.net";
	private int port = 9400;
	private HttpHost proxy = null; 
	private HttpPost httpPost = null;
	
	private int connTimeout = 5000;
	private int socketTimeout = 10000;
	
	public HTTPPostHandler(String url){
		
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, connTimeout);
		HttpConnectionParams.setSoTimeout(httpParameters, socketTimeout);
		httpClient = new DefaultHttpClient(httpParameters);
		this.url = url;
	}
	
	public HTTPPostHandler(String url, int connTimeout, int socketTimeout){
		
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, connTimeout);
		HttpConnectionParams.setSoTimeout(httpParameters, socketTimeout);
		httpClient = new DefaultHttpClient(httpParameters);
		this.url = url;
	}
	
	public void setProxy (int port){
		this.port = port;
	}
	public void setHost (String host){
		this.host = host;
	}
	private void setupProxy(){
		proxy = new HttpHost(host, port);
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}
	public String sendRequestWithProxy() throws ClientProtocolException, IOException{
		setupProxy();
		return sendRequest();
	}
	public void abort (){
		if (httpPost != null)
			httpPost.abort();
	}
	public String sendRequest() throws ClientProtocolException, IOException{
		
		httpPost = new HttpPost(url);
		httpPost.setHeader("User-agent", userAgent);
		
		httpPost.setHeader("Content-Type", contentType);
		if (params != null){
			if (params.size() > 0){
				httpPost.setEntity(new UrlEncodedFormEntity(params, paramCharset));
			}
		}
		
		HttpResponse response = httpClient.execute(httpPost);
		
		Tracer.trace("\nSending 'POST' request to URL : " + url);
		Tracer.trace("Post parameters : " + params);
		
		int responseCode = response.getStatusLine().getStatusCode();
		if (responseCode == HttpStatus.SC_OK){
			
			Tracer.trace("Response Code : " + responseCode);
			
			BufferedReader rd = new BufferedReader(
		                new InputStreamReader(response.getEntity().getContent()));
		 
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			this.result = result.toString();
			Tracer.trace(result.toString());
			return this.result;
		}else{
			this.result = null;
		}
		return null;
		
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public void setParamCharset(String paramCharset) {
		this.paramCharset = paramCharset;
	}
	public List<NameValuePair> getParams() {
		return params;
	}
	public void setParams(List<NameValuePair> params) {
		this.params = params;
	}
}
