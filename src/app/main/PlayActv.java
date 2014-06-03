package app.main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import cm7.main.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActv extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/********************************
		 * 
		 ********************************/

		super.onCreate(savedInstanceState);

		setContentView(R.layout.actv_play);

		this.setTitle(this.getClass().getName());

//		setup_1_set_file_name();
//		
//		setup_2_set_listeners();
		
	}//public void onCreate(Bundle savedInstanceState)

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_actv_play, menu);
//		mi.inflate(R.menu.menu_actv_play, menu);

		return super.onCreateOptionsMenu(menu);
		
	}//public boolean onCreateOptionsMenu(Menu menu)

	@Override
	protected void onDestroy() {
		/*********************************
		 * memo
		 *********************************/
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy()");
		
//		Methods.stop_player(this);
		
		/***************************************
		 * Clear prefs
		 ***************************************/
//		boolean res = Methods.clearPref(this, CONS.Pref.pname_PlayActv);
		
		super.onDestroy();
		
		/***************************************
		 * Finish activity
		 ***************************************/
		finish();
		
	}//protected void onDestroy()

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			
		case R.id.menu_actv_play_register_pattern://------------------------------------
			
//			Methods.dlg_register_patterns(this);
			
//			Methods_dlg.dlg_patterns(this);
			
			break;// case R.id.menu_actv_play_create_folder
			
		}//switch (item.getItemId())

		
		return super.onOptionsItemSelected(item);
	}//public boolean onOptionsItemSelected(MenuItem item)

	@Override
	protected void onPause() {
		// TODO ?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½ê‚½?¿½?¿½?¿½\?¿½b?¿½h?¿½E?¿½X?¿½^?¿½u
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO ?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½ê‚½?¿½?¿½?¿½\?¿½b?¿½h?¿½E?¿½X?¿½^?¿½u
		super.onResume();
	}

	@Override
	protected void onStart() {
		/*********************************
		 * memo
		 *********************************/
		
		super.onStart();
	}//protected void onStart()

	@Override
	protected void onStop() {
		// TODO ?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½ê‚½?¿½?¿½?¿½\?¿½b?¿½h?¿½E?¿½X?¿½^?¿½u
		super.onStop();
	}
	
}//public class PlayActv extends Activity
