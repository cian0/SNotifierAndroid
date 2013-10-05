package com.cian0.SNotifier.controller;

import java.util.Calendar;
import java.util.TimeZone;

import com.cian0.SNotifier.MainActivity;
import com.cian0.SNotifier.PSENotifierApp;
import com.cian0.SNotifier.R;
import com.cian0.SNotifier.utils.Tracer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


public class PSEDataServiceController extends Service {
	private Calendar timeNow;
	private Calendar pseMorningOpeningTime;
	private Calendar pseMorningClosingTime;
	private Calendar pseAfternoonOpeningTime;
	private Calendar pseAfternoonClosingTime;
	private String ASIA_HK_TIMEZONE = "Asia/Hong_Kong";
	private long interval = 1000 * 60 * 5; //5 minute interval
	Handler handler;
	//for singleton access
	public PSEDataServiceController(){
		handler = new Handler();
		if (PSENotifierApp.DEBUG_MODE)
			interval = 15000;
		timeNow = Calendar.getInstance();
		pseMorningOpeningTime = Calendar.getInstance(TimeZone.getTimeZone(ASIA_HK_TIMEZONE));
		pseMorningOpeningTime.set(Calendar.HOUR_OF_DAY, 9);
		pseMorningOpeningTime.set(Calendar.MINUTE, 30);
		
		pseMorningClosingTime = Calendar.getInstance(TimeZone.getTimeZone(ASIA_HK_TIMEZONE));
		pseMorningClosingTime.set(Calendar.HOUR_OF_DAY, 12);
		pseMorningClosingTime.set(Calendar.MINUTE, 0);
		
		pseAfternoonOpeningTime = Calendar.getInstance(TimeZone.getTimeZone(ASIA_HK_TIMEZONE));
		pseAfternoonOpeningTime.set(Calendar.HOUR_OF_DAY, 13);
		pseAfternoonOpeningTime.set(Calendar.MINUTE, 30);
		
		pseAfternoonClosingTime = Calendar.getInstance(TimeZone.getTimeZone(ASIA_HK_TIMEZONE));
		pseAfternoonClosingTime.set(Calendar.HOUR_OF_DAY, 15);
		pseAfternoonClosingTime.set(Calendar.MINUTE, 30);
		
	}
	private void showRunningNotification(){
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), Notification.FLAG_ONGOING_EVENT);
	    Notification not = new NotificationCompat.Builder(this)
	            .setContentTitle("PSE Stock Price Notifier is running." )
	            .setContentText("")
	            .setSmallIcon(R.drawable.ic_launcher)
	            .setContentIntent(contentIntent)
	            .build();

	    		
	    not.flags = Notification.FLAG_ONGOING_EVENT;
	    
	    startForeground(1337, not);
	}
	private void refreshTime(){
		//based on current locale of device
		timeNow = Calendar.getInstance(TimeZone.getTimeZone(ASIA_HK_TIMEZONE));
		
		pseMorningOpeningTime.set(Calendar.MONTH, timeNow.get(Calendar.MONTH));
		pseMorningOpeningTime.set(Calendar.DATE, timeNow.get(Calendar.DATE));
		pseMorningOpeningTime.set(Calendar.YEAR, timeNow.get(Calendar.YEAR));
		
		
		pseMorningClosingTime.set(Calendar.MONTH, timeNow.get(Calendar.MONTH));
		pseMorningClosingTime.set(Calendar.DATE, timeNow.get(Calendar.DATE));
		pseMorningClosingTime.set(Calendar.YEAR, timeNow.get(Calendar.YEAR));
		
		
		pseAfternoonOpeningTime.set(Calendar.MONTH, timeNow.get(Calendar.MONTH));
		pseAfternoonOpeningTime.set(Calendar.DATE, timeNow.get(Calendar.DATE));
		pseAfternoonOpeningTime.set(Calendar.YEAR, timeNow.get(Calendar.YEAR));
		
		
		pseAfternoonClosingTime.set(Calendar.MONTH, timeNow.get(Calendar.MONTH));
		pseAfternoonClosingTime.set(Calendar.DATE, timeNow.get(Calendar.DATE));
		pseAfternoonClosingTime.set(Calendar.YEAR, timeNow.get(Calendar.YEAR));
		
	}
	private boolean isPSEOpen(){
		refreshTime();
		Tracer.trace("time now");
		Tracer.trace(timeNow);
		
		Tracer.trace("pseMorningOpeningTime");
		Tracer.trace(pseMorningOpeningTime);
		
		Tracer.trace("pseMorningClosingTime");
		Tracer.trace(pseMorningClosingTime);
		
		Tracer.trace("pseAfternoonOpeningTime");
		Tracer.trace(pseAfternoonOpeningTime);

		Tracer.trace("pseAfternoonClosingTime");
		Tracer.trace(pseAfternoonClosingTime);
		
		if (timeNow.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || timeNow.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			return false;
		return ((timeNow.after(pseMorningOpeningTime) && timeNow.before(pseMorningClosingTime)) || 
				(timeNow.after(pseAfternoonOpeningTime) && timeNow.before(pseAfternoonClosingTime)));
		
	}
	
	private void repeatingOperation(){
		
		if (PSENotifierApp.DEBUG_MODE){
			Tracer.trace("PSE is open today. (debug mode)");
			startService( new Intent(this, PSEFetchIntentService.class));
			return;
		}
		if (!isPSEOpen())
		{
			Tracer.trace("PSE is not open today.");
			return;
		}else{
			Tracer.trace("PSE is open today.");
			startService( new Intent(this, PSEFetchIntentService.class));
		}
		
	}
	Runnable r = new Runnable() {
		@Override
		public void run() {
			repeatingOperation();
			recursivePostDelayed(handler);
		}
	};
	public void recursivePostDelayed(Handler handler){
		handler.postDelayed(r, interval);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO do something useful.
		showRunningNotification();
		repeatingOperation();
		recursivePostDelayed(handler);
		return Service.START_NOT_STICKY;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
