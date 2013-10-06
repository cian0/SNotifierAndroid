package com.cian0.SNotifier.controller;

public class AlertPricesController {

	//for singleton access
	private AlertPricesController(){
	}
	public static AlertPricesController getInstance() {
		return PriceListControllerHolder.INSTANCE;
	}
	private static class PriceListControllerHolder {
		public static final AlertPricesController INSTANCE = new AlertPricesController();
	}
	protected AlertPricesController readResolve() {
		return getInstance();
	}
	
	
	
}
