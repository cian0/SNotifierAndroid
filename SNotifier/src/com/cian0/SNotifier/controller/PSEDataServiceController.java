package com.cian0.SNotifier.controller;

import android.text.format.Time;

public class PSEDataServiceController {
	private static Time timeNow;
	private static Time pseMorningOpeningTime;
	private static Time pseMorningClosingTime;
	private static Time pseAfternoonOpeningTime;
	private static Time pseAfternoonClosingTime;
	
	{
		timeNow = new Time();
		timeNow.setToNow();
		
		pseMorningOpeningTime = new Time();
		pseMorningOpeningTime.setToNow();
		pseMorningOpeningTime.set(0, 30, 9, timeNow.monthDay, timeNow.MONTH, timeNow.YEAR);
		pseMorningClosingTime = new Time();
		pseMorningClosingTime.setToNow();
		
		pseAfternoonOpeningTime = new Time();
		pseAfternoonOpeningTime.setToNow();
		
		pseAfternoonClosingTime = new Time();
		pseAfternoonClosingTime.setToNow();
	}
}
