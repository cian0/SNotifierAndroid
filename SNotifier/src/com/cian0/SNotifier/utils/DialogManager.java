package com.cian0.SNotifier.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogManager {
	private static AlertDialog alertD;
	public static void alert(Context c, String title, String message, String acceptText, String denyText, final Runnable onAffirmative, final Runnable onNegative){
		
		final AlertDialog.Builder a = new AlertDialog.Builder(c)
	    .setTitle(title)
	    .setMessage(message)
	    .setPositiveButton(acceptText, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	if (onAffirmative != null)
	        		onAffirmative.run();
	        	alertD.dismiss();
	        }
	     })
	    .setNegativeButton(denyText, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	if (onNegative != null)
	        		onNegative.run();
	        	alertD.dismiss();
	        }
	     });
	     
		alertD = a.show();
	}
}
