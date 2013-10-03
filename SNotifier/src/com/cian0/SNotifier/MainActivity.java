package com.cian0.SNotifier;

import java.util.ArrayList;

import com.cian0.SNotifier.controller.PSEDataLoader;
import com.cian0.SNotifier.controller.PSEDataLoader.SECTOR;
import com.cian0.SNotifier.utils.Tracer;
import com.cian0.SNotifier.vos.Security;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<ArrayList<Security>>  {
	private final int PSE_LOADER = 0;
	ProgressDialog progressDialog = null;
	PSEDataLoader pseDataLoader = null;
	AlertDialog alertDialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressDialog = ProgressDialog.show(this, "Loading", "Wait while loading...");
		progressDialog.setCancelable(true);
		progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				//abort http request
				if (pseDataLoader!= null){
					pseDataLoader.cancelLoad();
					pseDataLoader.abort();
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							MainActivity.this);
						// set title
						alertDialogBuilder.setTitle("SNotifier");
			 
						// set dialog message
						alertDialogBuilder
							.setMessage("Canceled load! Exit now?")
							.setCancelable(false)
							.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, close
									// current activity
									MainActivity.this.finish();
								}
							  })
							.setNegativeButton("No",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});
			 
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 
							// show it
							alertDialog.show();
						
				}
			}
		});
		startPSELoader();
	}
	
	private void startPSELoader (){
		Loader<ArrayList<Security>> loader = getSupportLoaderManager().initLoader(PSE_LOADER, null, this);
		loader.forceLoad();
	}

	@Override
	public Loader<ArrayList<Security>> onCreateLoader(int id, Bundle params) {
		switch (id){
		case PSE_LOADER:
			pseDataLoader = new PSEDataLoader(this, SECTOR.ALL);
			return pseDataLoader;
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Security>> loader,
			ArrayList<Security> result) {
		if (progressDialog!= null){
			if (progressDialog.isShowing())
				progressDialog.dismiss();
		}
		if (result != null){
			Tracer.trace("Results fetched : " + result.size());
			
		}
		
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Security>> loader) {

		
	}

}
