package com.cian0.SNotifier.utils;

import java.util.Calendar;

import android.util.Log;

public class Tracer {
	public static void trace (String what){
		Log.i("com.cian0.SNotifier", what);
	}
	public static void trace (Calendar what){
		Log.i("com.cian0.SNotifier", (what.get(Calendar.MONTH) + 1) + "/" + what.get(Calendar.DATE) + "/" + what.get(Calendar.YEAR));
		Log.i("com.cian0.SNotifier", (what.get(Calendar.HOUR_OF_DAY) ) + ":" + what.get(Calendar.MINUTE) + ":" + what.get(Calendar.SECOND));
		
	}
}
