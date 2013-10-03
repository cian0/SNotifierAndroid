package com.cian0.SNotifier;

import java.util.ArrayList;

import com.cian0.SNotifier.controller.PSEDataLoader;
import com.cian0.SNotifier.controller.PSEDataLoader.SECTOR;
import com.cian0.SNotifier.utils.Tracer;
import com.cian0.SNotifier.vos.Security;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<ArrayList<Security>>  {
	private final int PSE_LOADER = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
			return new PSEDataLoader(this, SECTOR.ALL);
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Security>> loader,
			ArrayList<Security> result) {
		if (result != null){
			Tracer.trace("Results fetched : " + result.size());
			
		}
		
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Security>> loader) {

		
	}

}
