package com.cian0.SNotifier.model.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public interface IContract {
	//db methods
	public void onCreate(SQLiteDatabase db);
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
	//content provider
	public int delete(Uri uri, String selection, String[] selectionArgs, SQLiteDatabase db);
	public Uri insert(Uri uri, ContentValues values, SQLiteDatabase db);
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder, SQLiteDatabase db);
	public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs, SQLiteDatabase db);
}
