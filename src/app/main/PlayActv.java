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
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import app.items.AI;
import app.items.BM;
import app.listeners.SBL;
import app.listeners.STL;
import app.listeners.button.BO_CL;
import app.listeners.button.BO_TL;
import app.listeners.text.TV_LCL;
import app.listeners.text.TV_TL;
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

		// Init: vars

		////////////////////////////////
		if (CONS.Admin.vib == null) {
			
			CONS.Admin.vib = 
					(Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
			
		}
		
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

		// Init: vars

		////////////////////////////////
		_onCreate_InitVars();

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

		// Prefs

		////////////////////////////////
		_onCreate_ManagePrefs();
		
	}//public void onCreate(Bundle savedInstanceState)

	private void _onCreate_ManagePrefs() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Current file name
		
		//	- If the file name of the AI instance passed from
		//		ALActv doesn't match the one stored in the 
		//		pref
		//		=> Replace it with the new one
		//	- Set "currentPosition" pref => 0
		//	- Set player => seekto ~~> 0
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
			boolean res = Methods.set_Pref_String(this, 
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

			////////////////////////////////

			// Reset: pref "currentPosition"

			////////////////////////////////
			res = Methods.setPref_Long(this, 
									CONS.Pref.pname_PlayActv, 
									CONS.Pref.pkey_PlayActv_CurrentPosition,
									0);
			
			if (res == true) {
				
				// Log
				String msg_Log = "Pref: currentPosition => set to 0";
				Log.d("PlayActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			} else {
				
				// Log
				String msg_Log = "Pref: currentPosition => can't be set to 0";
				Log.d("PlayActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);

			}
			
//					Methods.getPref_Long(
//							this,
//							CONS.Pref.pname_PlayActv,
//							CONS.Pref.pkey_PlayActv_CurrentPosition,
//							CONS.Pref.dflt_LongExtra_value);
			
			////////////////////////////////

			// Player => seek to 0

			////////////////////////////////
			CONS.PlayActv.sb.setProgress(0);
			
			
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
		
		////////////////////////////////

		// Get: AI

		////////////////////////////////
		CONS.PlayActv.ai = DBUtils.find_AI_ById(
//				AI ai = DBUtils.find_AI_ById(
							this,
							CONS.PlayActv.ai_Db_Id,
							CONS.PlayActv.ai_TableName);
		
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

		// "See bookmarks"

		////////////////////////////////
		Button btSeeBM = (Button) findViewById(R.id.actv_play_bt_see_bm);
		
		btSeeBM.setTag(Tags.ButtonTags.actv_play_bt_see_bm);
		
		btSeeBM.setOnTouchListener(new BO_TL(this));
		btSeeBM.setOnClickListener(new BO_CL(this, CONS.PlayActv.ai));
		
		////////////////////////////////

		// "Add bookmark"

		////////////////////////////////
		Button btAddBM = (Button) findViewById(R.id.actv_play_bt_add_bm);
		
		btAddBM.setTag(Tags.ButtonTags.actv_play_bt_add_bm);
		
		btAddBM.setOnTouchListener(new BO_TL(this));
		btAddBM.setOnClickListener(new BO_CL(this, CONS.PlayActv.ai));
		
		////////////////////////////////

		// SeekBar

		////////////////////////////////
		CONS.PlayActv.sb = (SeekBar) findViewById(R.id.actv_play_sb);
		
		CONS.PlayActv.sb.setOnSeekBarChangeListener(
							new SBL(this, CONS.PlayActv.sb));
		
		////////////////////////////////

		// TV: title

		////////////////////////////////
		TextView tv_Title = (TextView) findViewById(R.id.actv_play_tv_title);
		
		tv_Title.setTag(Tags.TVTags.PLAYACTV_TITLE);
//		tv_Title.setTag(Tags.ButtonTags.actv_play_tv_title);
		
		tv_Title.setOnTouchListener(new TV_TL(this));
		tv_Title.setOnLongClickListener(new TV_LCL(this, CONS.PlayActv.ai));
		
		////////////////////////////////

		// Linear layout: controls

		////////////////////////////////
		LinearLayout ll_Control = 
				(LinearLayout) findViewById(R.id.actv_play_ll_controls);
		
		ll_Control.setTag(Tags.SwipeTags.ACTV_PLAY);
		
		ll_Control.setOnTouchListener(new STL(this, CONS.PlayActv.ai));
//		ll_Control.setOnTouchListener(new STL(this));
		
	}//private void _onCreate_SetListeners()

	private boolean
	_onCreate_SetupViews() {
		// - Get values from the AI instance
		// - Set the values to the views
		
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
		
//		////////////////////////////////
//		
//		// Set: values: Bookmark
//		
//		////////////////////////////////
//		int bm_Value = Methods.get_Pref_Int(
//								this, 
//								CONS.Pref.pname_BMActv, 
//								CONS.Pref.pkey_CurrentPosition_BMActv, 
//								CONS.Pref.dflt_IntExtra_value);
//
//		if (bm_Value != CONS.Pref.dflt_IntExtra_value
//				&& CONS.BMActv.bmList != null) {
//			
//			BM bm = CONS.BMActv.bmList.get(bm_Value);
//					
//			TextView tv_Bm = (TextView) findViewById(R.id.actv_play_tv_bm_lbl);
//			
//			tv_Bm.setText(bm.getPosition());
//		
//		} else {
//			
//			// Log
//			msg_Log = "bm_Value = " + bm_Value;
//			Log.d("PlayActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			msg_Log = "CONS.BMActv.bmList == null => " 
//						+ (CONS.BMActv.bmList == null);
//			
//			Log.d("PlayActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			
//		}
		
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
		// TODO ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ黷ｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ\?�ｿｽ�ｿｽb?�ｿｽ�ｿｽh?�ｿｽ�ｿｽE?�ｿｽ�ｿｽX?�ｿｽ�ｿｽ^?�ｿｽ�ｿｽu
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ黷ｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ\?�ｿｽ�ｿｽb?�ｿｽ�ｿｽh?�ｿｽ�ｿｽE?�ｿｽ�ｿｽX?�ｿｽ�ｿｽ^?�ｿｽ�ｿｽu
		super.onResume();
	}

	@Override
	protected void onStart() {
		/*********************************
		 * memo
		 *********************************/
		super.onStart();
		
		////////////////////////////////
		
		// Set: values: Bookmark
		
		////////////////////////////////
		int bm_Value = Methods.get_Pref_Int(
								this, 
								CONS.Pref.pname_BMActv, 
								CONS.Pref.pkey_CurrentPosition_BMActv, 
								CONS.Pref.dflt_IntExtra_value);

		// Log
		String msg_Log = "bm_Value => " + bm_Value;
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
		if (bm_Value != CONS.Pref.dflt_IntExtra_value
				&& CONS.BMActv.bmList != null) {
			
			BM bm = CONS.BMActv.bmList.get(bm_Value);
					
			TextView tv_Bm = (TextView) findViewById(R.id.dlg_edit_ai_lbl_file_path);
			
			tv_Bm.setText(bm.getPosition());
		
		} else {
			
			// Log
			msg_Log = "bm_Value = " + bm_Value;
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			msg_Log = "CONS.BMActv.bmList == null => " 
						+ (CONS.BMActv.bmList == null);
			
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			
		}
		
	}//protected void onStart()

	@Override
	protected void onStop() {
		// TODO ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ黷ｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ\?�ｿｽ�ｿｽb?�ｿｽ�ｿｽh?�ｿｽ�ｿｽE?�ｿｽ�ｿｽX?�ｿｽ�ｿｽ^?�ｿｽ�ｿｽu
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		/****************************
		 * memo
			****************************/
		Methods.stop_Player(this);
		
		
		this.finish();
		
		overridePendingTransition(0, 0);
		
	}//public void onBackPressed()

	@Override
	protected void
	onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == CONS.Intent.REQUEST_CODE_SEE_BOOKMARKS) {
			
			if (resultCode == CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_OK) {
				
				onActivityResult_BM_OK(data);
				
//				long position = data.getLongExtra(CONS.Intent.bmactv_key_position, -1);
//				
//				// Log
//				Log.d("PlayActv.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber()
//						+ ":"
//						+ Thread.currentThread().getStackTrace()[2]
//								.getMethodName() + "]", "Returned position => " + position);
				
			} else if (resultCode == CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_CANCEL) {//if (resultCode == CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_OK)
				
				onActivityResult_BM_Cancel();
//				// Log
//				Log.d("PlayActv.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber()
//						+ ":"
//						+ Thread.currentThread().getStackTrace()[2]
//								.getMethodName() + "]", "Cancelled");
				
			}//if (resultCode == CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_OK)
			
			
		} else {//if (requestCode == CONS.Intent.REQUEST_CODE_SEE_BOOKMARKS)
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "request code => " + requestCode);
			
		}//if (requestCode == CONS.Intent.REQUEST_CODE_SEE_BOOKMARKS)
		
	}//onActivityResult(int requestCode, int resultCode, Intent data)

	private void onActivityResult_BM_Cancel() {
		// TODO Auto-generated method stub
		// Log
		Log.d("PlayActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2]
						.getMethodName() + "]", "Cancelled");
		
		////////////////////////////////

		// Reset: SeekBar

		////////////////////////////////
		
		
//		long currentPosition = Methods.conv_ClockLabel_to_MillSec(position);
		long currentPosition = Methods.getPref_Long(this, 
							CONS.Pref.pname_PlayActv, 
							CONS.Pref.pkey_PlayActv_CurrentPosition, 
							CONS.Pref.dflt_LongExtra_value);
		
		long length =
				Methods.conv_ClockLabel_to_MillSec(CONS.PlayActv.ai.getLength());
		
		int seekPositon = (int)
//					((currentPosition / length)
					(((float)currentPosition / length)
							* CONS.PlayActv.sb.getMax());
//		
		CONS.PlayActv.sb.setProgress(seekPositon);


	}

	private void
	onActivityResult_BM_OK(Intent data) {
		// TODO Auto-generated method stub
		String position = data.getStringExtra(CONS.Intent.iKey_BMActv_Position);
//		long position = data.getLongExtra(CONS.Intent.iKey_BMActv_Position, -1);
		
		long aiDbId = data.getLongExtra(
							CONS.Intent.iKey_BMActv_AI_Id,
							CONS.Intent.dflt_LongExtra_value);
		
		String aiTableName = data.getStringExtra(
						CONS.Intent.iKey_BMActv_TableName);

		/***************************************
		 * Set: Preference
		 ***************************************/
		boolean res = 
				Methods.setPref_Long(
						this,
						CONS.Pref.pname_PlayActv,
//						CONS.Pref.pkey_PlayActv_position,
						CONS.Pref.pkey_PlayActv_CurrentPosition,
//						CONS.Pref.pkey_CurrentPosition,
						Methods.conv_ClockLabel_to_MillSec(position));
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "Position => Stored in a preference");

		/***************************************
		 * Set: Seekbar
		 ***************************************/
		long currentPosition = Methods.conv_ClockLabel_to_MillSec(position);
		long length =
				Methods.conv_ClockLabel_to_MillSec(CONS.PlayActv.ai.getLength());
		
		int seekPositon = (int)
//					((currentPosition / length)
					(((float)currentPosition / length)
							* CONS.PlayActv.sb.getMax());
//		
		CONS.PlayActv.sb.setProgress(seekPositon);
		
		// Log
		String msg_Log = "currentPosition = " + currentPosition
						+ " // "
						+ "seekPositon = " + seekPositon
						+ " // "
						+ "length = " + length
						;
		
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
	}//onActivityResult_BM_OK(Intent data)

	
}//public class PlayActv extends Activity
