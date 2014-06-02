/*********************************
 * ALACtv.java (Audio list activity)
 * 
 *********************************/
package app.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cm7.main.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import app.utils.CONS;

public class ALActv extends ListActivity {

	/****************************************
	 * Methods
	 ****************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/****************************
		 * Steps
		 * 1. Super
		 * 2. Set content
		 * 3. Basics
		 * 
		 * 4. Set up
		 * 5. Initialize vars
		****************************/
		super.onCreate(savedInstanceState);

		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onCreate()");
		
		//
//		setContentView(R.layout.thumb_activity);
		setContentView(R.layout.actv_al);
		
		/****************************
		 * 3. Basics
			****************************/
		this.setTitle(this.getClass().getName());
		
		////////////////////////////////

		// Get: Current path

		////////////////////////////////
		_onCreate_Get_CurrentPath();
		
		
		/****************************
		 * 5. Initialize vars
			****************************/
//		checkedPositions = new ArrayList<Integer>();

		/*********************************
		 * Current position => Initialize
		 *********************************/
//		boolean res = 
//				Methods.set_pref(
//							this,
//							CONS.pname_mainActv,
//							CONS.pkey_current_image_position,
//							-1);
//		
//		if (res == true) {
//			// Log
//			Log.d("ALActv.java"
//					+ "["
//					+ Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + "]",
//					"Pref set: " + CONS.pkey_current_image_position);
//			
//		} else {//if (result == true)
//			// Log
//			Log.d("ALActv.java"
//					+ "["
//					+ Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + "]",
//					"Set pref => Failed: " + CONS.pkey_current_image_position);
//			
//		}//if (result == true)

		
	}//public void onCreate(Bundle savedInstanceState)


	private void _onCreate_Get_CurrentPath() {
		// TODO Auto-generated method stub
		
		Intent i = this.getIntent();
		
		CONS.ALActv.currentPath = 
				i.getStringExtra(CONS.Intent.iKey_CurrentPath_MainActv);
		
		// Log
		String msg_Log = "currentPath = " + CONS.ALActv.currentPath;
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}


	@Override
	protected void onPause() {
		
		super.onPause();
		
	}

	@Override
	protected void onResume() {
		/*********************************
		 * 1. super
		 * 2. Notify adapter
		 * 
		 * 3. Set selection
		 *********************************/
		// TODO ?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½ê‚½?¿½?¿½?¿½\?¿½b?¿½h?¿½E?¿½X?¿½^?¿½u
		super.onResume();
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onResume()");

		
	}//protected void onResume()

	@Override
	protected void onStart() {
		/*********************************
		 * 1. super
		 * 
		 * 2. Set up
		 * 
		 * 3. Debug: Store file length data
		 *********************************/
		super.onStart();
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onStart()");
		
		CONS.Admin.vib = 
				(Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		
		/****************************
		 * 2. Set up
			****************************/

	}//protected void onStart()

	@Override
	protected void onStop() {
		// TODO ?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½ê‚½?¿½?¿½?¿½\?¿½b?¿½h?¿½E?¿½X?¿½^?¿½u
		super.onStop();
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onStop()");

	}

	@Override
	protected void onDestroy() {
		/*********************************
		 * 1. super
		 * 2. move_mode => falsify
		 * 
		 * 3. History mode => Off
		 *********************************/
		
		super.onDestroy();
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy()");
		
		
		/*********************************
		 * Current position => Clear
		 *********************************/
		
		
	}//protected void onDestroy()

	@Override
	public void onBackPressed() {
		/****************************
		 * memo
			****************************/
		this.finish();
		
		overridePendingTransition(0, 0);
		
	}//public void onBackPressed()

	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		/*********************************
		 * 0. Vibrate
		 * 
		 * 1. Get item
		 * 2. Intent
		 * 		2.1. Set data
		 * 
		 * 9. Start intent
		 *********************************/
		/****************************
		 * 0. Vibrate
			****************************/
//		vib.vibrate(Methods.vibLength_click);

	}//protected void onListItemClick(ListView lv, View v, int position, long id)

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_al_actv, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/****************************
		 * Steps
		 * 1. R.id.thumb_actv_menu_move_mode
		 * 2. R.id.thumb_actv_menu_move_files
			****************************/
			
		}//switch (item.getItemId())
		
		
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSxelected(MenuItem item)

}//public class TNActv
