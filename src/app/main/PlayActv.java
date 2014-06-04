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
import app.items.AI;
import app.listeners.SBL;
import app.listeners.button.BO_CL;
import app.listeners.button.BO_TL;
import app.utils.CONS;
import app.utils.DBUtils;
import app.utils.Methods;
import app.utils.Tags;

public class PlayActv extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/********************************
		 * 
		 ********************************/

		// Log
		String msg_Log = "onCreate";
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.actv_play);

		this.setTitle(this.getClass().getName());

		////////////////////////////////

		// Get: intent values

		////////////////////////////////
		boolean res = _onCreate_Get_IntentValues();
		
		/******************************
			validate
		 ******************************/
		if (res == false) {
			
			// Log
			msg_Log = "Intent values => cant obtain";
			Log.e("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// debug
			Toast.makeText(this, msg_Log, Toast.LENGTH_SHORT).show();
			
			return;
			
		}

		////////////////////////////////

		// Setup: views

		////////////////////////////////
		res = _onCreate_SetupViews();

		/******************************
			validate
		 ******************************/
		if (res == false) {
			
			// Log
			msg_Log = "Intent values => cant obtain";
			Log.e("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// debug
			Toast.makeText(this, msg_Log, Toast.LENGTH_SHORT).show();
			
			return;
			
		}

		////////////////////////////////

		// Setup: listeners

		////////////////////////////////
		_onCreate_SetListeners();
		
		
		////////////////////////////////

		// Init: vars

		////////////////////////////////
		_onCreate_InitVars();

		////////////////////////////////

		// Prefs

		////////////////////////////////
		_onCreate_ManagePrefs();
		
	}//public void onCreate(Bundle savedInstanceState)

	private void _onCreate_ManagePrefs() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Current file name
		
		// - If the file name of the AI instance passed from
		//		ALActv doesn't match the one stored in the 
		//		pref
		//		=> Replace it with the new one
		////////////////////////////////
		String tmp = Methods.get_Pref_String(
							this,
							CONS.Pref.pname_PlayActv,
							CONS.Pref.pkey_PlayActv_CurrentFileName,
							null);
		
		if(tmp == null
				|| !tmp.equals(CONS.PlayActv.ai.getFile_name())) {
			// Pref current file name => not set yet
			//	=> then, set the passed file name into the pref
			boolean res = Methods.setPref_String(this, 
								CONS.Pref.pname_PlayActv, 
								CONS.Pref.pkey_PlayActv_CurrentFileName, 
								CONS.PlayActv.ai.getFile_name());
			
			if (res == true) {
				
				// Log
				String msg_Log = "Pref: file name => set";
				Log.d("PlayActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			} else {

				// Log
				String msg_Log = "Pref: file name => cant be set";
				Log.d("PlayActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}

		} else {//if (!tmp.equals(CONS.PlayActv.ai.getFile_name()))
			
			// File name given by ALActv does match that in the pref
			//	i.e. The user is playing the same audio file
			//	=> then, set the current position with the one
			//		stored in the pref
			long savedPosition = 
					Methods.getPref_Long(
							this,
							CONS.Pref.pname_PlayActv,
							CONS.Pref.pkey_PlayActv_CurrentPosition,
							CONS.Pref.dflt_LongExtra_value);
			
			// Log
			String msg_Log = "savedPosition = " + savedPosition;
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			
			if (savedPosition != CONS.Pref.dflt_LongExtra_value) {
				
				// Log
				msg_Log = "savedPosition = " + savedPosition;
				Log.d("PlayActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
				// Convert the position value to the seek position
				int fLength = (int) Methods.conv_ClockLabel_to_MillSec(
												CONS.PlayActv.ai.getLength());
				
				int tmp_i = CONS.PlayActv.sb.getMax() * (int)savedPosition / fLength;
				
				// Log
				msg_Log = "tmp_i = " + tmp_i;
				Log.d("PlayActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				// Set: position
				CONS.PlayActv.sb.setProgress(tmp_i);
//				CONS.PlayActv.sb.setProgress((int) savedPosition);
				
			} else {
				
				// Log
				msg_Log = "savedPosition => "
								+ CONS.Pref.dflt_LongExtra_value;
				
				Log.d("PlayActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
		}//if (!tmp.equals(CONS.PlayActv.ai.getFile_name()))
		
	}//private void _onCreate_ManagePrefs()

	private void _onCreate_InitVars() {
		// TODO Auto-generated method stub
		
		CONS.PlayActv.mp = new MediaPlayer();
		
	}

	private void _onCreate_SetListeners() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Get: views

		////////////////////////////////
		/*********************************
		 * 1. Button => Play
		 *********************************/
		Button bt_play = (Button) findViewById(R.id.actv_play_bt_play);
		
		bt_play.setTag(Tags.ButtonTags.actv_play_bt_play);
		
		bt_play.setOnTouchListener(new BO_TL(this));
//		bt_play.setOnClickListener(new ButtonOnClickListener(this));
		bt_play.setOnClickListener(new BO_CL(this, CONS.PlayActv.ai));

		/*********************************
		 * 2. Button => Stop
		 *********************************/
		Button bt_stop = (Button) findViewById(R.id.actv_play_bt_stop);
		
		bt_stop.setTag(Tags.ButtonTags.actv_play_bt_stop);
		
		bt_stop.setOnTouchListener(new BO_TL(this));
		bt_stop.setOnClickListener(new BO_CL(this));

		/*********************************
		 * 3. Button => Back
		 *********************************/
		Button bt_back = (Button) findViewById(R.id.actv_play_bt_back);
		
		bt_back.setTag(Tags.ButtonTags.actv_play_bt_back);
		
		bt_back.setOnTouchListener(new BO_TL(this));
		bt_back.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////

		// SeekBar

		////////////////////////////////
		CONS.PlayActv.sb = (SeekBar) findViewById(R.id.actv_play_sb);
		
		CONS.PlayActv.sb.setOnSeekBarChangeListener(
							new SBL(this, CONS.PlayActv.sb));
		
	}//private void _onCreate_SetListeners()

	private boolean
	_onCreate_SetupViews() {
		// - Get AI instance using db_id and tableName
		// - Get values from the AI instance
		// - Set the values to the views
		
		////////////////////////////////

		// Get: AI

		////////////////////////////////
		CONS.PlayActv.ai = DBUtils.find_AI_ById(
//				AI ai = DBUtils.find_AI_ById(
							this,
							CONS.PlayActv.ai_Db_Id,
							CONS.PlayActv.ai_TableName);
		
		/******************************
			validate
		 ******************************/
		if (CONS.PlayActv.ai == null) {
			
			// Log
			String msg_Log = "Can't build AI instance";
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// debug
			Toast.makeText(this, msg_Log, Toast.LENGTH_SHORT).show();
			
			return false;
			
		}
		
		// Log
		String msg_Log = "ai.getFile_name() = " + CONS.PlayActv.ai.getFile_name();
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// Set: values: File name

		////////////////////////////////
		TextView tv_FileName = 
					(TextView) findViewById(R.id.actv_play_tv_file_name);
		
		if (!CONS.PlayActv.ai.getFile_name().equals("")) {
			
			tv_FileName.setText(CONS.PlayActv.ai.getFile_name());
			
		} else {//if (!ai.getFile_name().equals(""))
			
			tv_FileName.setText(this.getString(R.string.generic_tv_no_data));
			
		}//if (!ai.getFile_name().equals(""))

		////////////////////////////////

		// Set: values: Title

		////////////////////////////////
		TextView tv_Title = (TextView) findViewById(R.id.actv_play_tv_title);
		
		if (CONS.PlayActv.ai.getTitle() != null && !CONS.PlayActv.ai.getTitle().equals("")) {
			
			tv_Title.setText(CONS.PlayActv.ai.getTitle());
			
		} else {//if (!ai.getFile_name().equals(""))
			
//			tv_title.setText(this.getString(R.string.generic_tv_no_data));
			tv_Title.setText((this.getString(R.string.generic_tv_no_data)));
			
		}//if (!ai.getFile_name().equals(""))
		
		////////////////////////////////
		
		// Set: values: Memo
		
		////////////////////////////////
		TextView tv_Memo = (TextView) findViewById(R.id.actv_play_tv_memo);
		
		if (CONS.PlayActv.ai.getMemo() != null && !CONS.PlayActv.ai.getMemo().equals("")) {
			
			tv_Memo.setText(CONS.PlayActv.ai.getMemo());
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Memo set => " + CONS.PlayActv.ai.getMemo());
			
		} else {//if (!ai.getFile_name().equals(""))
			
//			tv_title.setText(this.getString(R.string.generic_tv_no_data));
			tv_Memo.setText((this.getString(R.string.generic_tv_no_data)));
			
		}//if (!ai.getFile_name().equals(""))
		
		////////////////////////////////
		
		// Set: values: Length
		
		////////////////////////////////
		TextView tv_Length = (TextView) findViewById(R.id.actv_play_tv_length);
		
		if (CONS.PlayActv.ai.getLength() != null) {
			
			tv_Length.setText(CONS.PlayActv.ai.getLength());
			
		} else {
			
			tv_Length.setText("xx:xx");
			
		}
		
		////////////////////////////////

		// View: Current position

		////////////////////////////////
		CONS.PlayActv.tvCurrentPosition = 
				(TextView) findViewById(
						R.id.actv_play_tv_current_position);

		return true;
		
	}//_onCreate_SetupViews()

	private boolean _onCreate_Get_IntentValues() {
		
		// Log
		String msg_Log = "_onCreate_Get_IntentValues";
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// TODO Auto-generated method stub
		Intent i = this.getIntent();
		
		CONS.PlayActv.ai_FilePath_Full = 
				i.getStringExtra(CONS.Intent.iKey_AI_FilePath_Full);
		
		CONS.PlayActv.ai_Db_Id = 
				i.getLongExtra(
						CONS.Intent.iKey_AI_Db_Id, 
						CONS.Intent.dflt_LongExtra_value);
		
		CONS.PlayActv.ai_TableName = 
				i.getStringExtra(
						CONS.Intent.iKey_AI_TableName);
		
		/******************************
			validate
		 ******************************/
		if (CONS.PlayActv.ai_FilePath_Full == null) {
			
			// Log
			msg_Log = "CONS.PlayActv.ai_FilePath_Full => null";
			Log.e("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return false;
			
		} else if (CONS.PlayActv.ai_Db_Id == 
						CONS.Intent.dflt_LongExtra_value) {
			
			// Log
			msg_Log = "CONS.PlayActv.ai_Db_Id => "
						+ CONS.Intent.dflt_LongExtra_value;
			
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return false;
			
		} else if (CONS.PlayActv.ai_TableName == null) {
			
			// Log
			msg_Log = "CONS.PlayActv.ai_TableName => null";
			
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return false;
			
		}

//		// Log
//		String msg_Log = "CONS.PlayActv.ai_FilePath_Full = "
//						+ CONS.PlayActv.ai_FilePath_Full;
//		Log.d("PlayActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		// Log
//		msg_Log = "CONS.PlayActv.ai_Db_Id = "
//						+ CONS.PlayActv.ai_Db_Id;
//		
//		Log.d("PlayActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		return true;

	}

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
		// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ?��?��?��\?��b?��h?��E?��X?��^?��u
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ?��?��?��\?��b?��h?��E?��X?��^?��u
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
		// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ?��?��?��\?��b?��h?��E?��X?��^?��u
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		/****************************
		 * memo
			****************************/
		this.finish();
		
		overridePendingTransition(0, 0);
		
	}//public void onBackPressed()

}//public class PlayActv extends Activity
