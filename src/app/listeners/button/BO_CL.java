package app.listeners.button;

import java.io.File;

import cm7.main.R;

import android.app.ListActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import app.items.AI;
import app.items.BM;
import app.main.BMActv;
import app.utils.CONS;
import app.utils.DBUtils;
import app.utils.Methods;
import app.utils.Methods_dlg;
import app.utils.Tags;



public class BO_CL implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	//
	int position;
	
	//
	ListView lv;
	
	//
	AI ai;
	
	public BO_CL(Activity actv) {
		//
		this.actv = actv;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

//	@Override
	public BO_CL(Activity actv, AI ai) {
		
		this.actv = actv;
		
		this.ai = ai;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}//public ButtonOnClickListener(Activity actv, AI ai)

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();

		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		switch (tag) {
		/*********************************
		 * 1. actv_play.xml
		 *********************************/
		case actv_play_bt_play://----------------------------------------------------
			
			case_ActvPlay_BtPlay();
//			// Log
//			String msg_Log = "Play";
//			Log.d("BO_CL.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			
//			Methods.play_file(actv, ai);
			
			break;// case actv_play_bt_play
			
		case actv_play_bt_stop://----------------------------------------------------
			
			case_ActvPlay_BtStop();
//			// Log
//			msg_Log = "Stop";
//			Log.d("BO_CL.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			
//			Methods.stop_player(actv);
			
			break;// case actv_play_bt_stop
			
		case actv_play_bt_back://----------------------------------------------------
			
			case_ActvPlay_BtBack();
			
//			actv.finish();
//			
//			actv.overridePendingTransition(0, 0);
			
			break;// case actv_play_bt_back

		case actv_play_bt_see_bm://----------------------------------------------------
			
			case_PlayActv_See_BM();
		
			break;// case actv_play_bt_see_bm

		case actv_play_bt_add_bm://----------------------------------------------------
			
			case_PlayActv_Add_BM();
			
			break;// case actv_play_bt_add_bm

		case actv_bm_bt_back:
			
			case_BMActv_Back();
			
			break;
			
		case actv_bm_ib_bottom:
			
			case_BMActv_Ib_Bottom();
			
			break;
			
		case actv_bm_ib_top:
			
			case_BMActv_Ib_Top();
			
			break;
			
		case actv_bm_ib_down:
			
			case_BMActv_Ib_Down();
			
			break;
			
		case actv_bm_ib_up:
			
			case_BMActv_Ib_Up();
			
			break;
			
		case ACTV_PLAY_BT_FORWARD:
			
			case_ACTV_PLAY_BT_FORWARD();
			
			break;
			
		case ACTV_PLAY_BT_BACKWARD:
			
			case_ACTV_PLAY_BT_BACKWARD();
			
			break;
			
		}//switch (tag)
		
	}//public void onClick(View v)

	private void 
	case_ACTV_PLAY_BT_BACKWARD() {
		/******************************
			validate
		 ******************************/
		if (CONS.PlayActv.ai == null) {
			
			String msg = "CONS.PlayActv.ai => null";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return;
			
		}
		
		////////////////////////////////
	
		// get: data
	
		////////////////////////////////
		long cur_Position_long = 
				Methods.getPref_Long(
					actv,
					CONS.Pref.pname_PlayActv,
					CONS.Pref.pkey_PlayActv_CurrentPosition,
					CONS.Pref.dflt_LongExtra_value);
	
		long length =
				Methods.conv_ClockLabel_to_MillSec(
								CONS.PlayActv.ai.getLength());
		
		int seekPosition = (int)
				(((float)cur_Position_long / length)
						* CONS.PlayActv.sb.getMax());
		
		// Log
		String msg_Log = "seek position, before => "
						+ seekPosition;
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
		
		// Backward
		
		////////////////////////////////
		// Pref
		String pref_StepLength = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				actv.getString(R.string.pkey_prefactv_step_length), 
				null);
		
//		// Log
//		msg_Log = "pref_StepLength => " + pref_StepLength;
//		Log.d("BO_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		if (pref_StepLength != null) {
			
			// Converting from minutes to mill seconds
			CONS.PlayActv.stepValue = 
					Integer.parseInt(pref_StepLength) * (1000 * 60);
			
		} else {
	
			// Defalut value => 60000 mill seconds
			CONS.PlayActv.stepValue = CONS.PlayActv.dflt_StepValue;
			
		}
		
		cur_Position_long -= CONS.PlayActv.stepValue;
//		cur_Position_long += CONS.PlayActv.stepValue;
	
		/******************************
			validate
		 ******************************/
		if (cur_Position_long < 0) {
//			if (cur_Position_long >= length) {
			
			// Log
			msg_Log = "No more backwarding possible";
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		/******************************
			set: pref
		 ******************************/
		boolean res = Methods.setPref_Long(
					actv,
					CONS.Pref.pname_PlayActv,
	//				CONS.Pref.pkey_PlayActv_position,
					CONS.Pref.pkey_PlayActv_CurrentPosition,
	//				CONS.Pref.pkey_CurrentPosition,
					cur_Position_long);
		
		/******************************
			validate: pref set?
		 ******************************/
		if (res == false) {
			
			String msg = "Can't set pref: current position";
			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg);
			
			return;
			
		}
		
		// Log
		msg_Log = "pref position set => " 
						+ cur_Position_long;
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
	
		// set: seek bar
	
		////////////////////////////////
		
	//	int seekPosition = (int)
		seekPosition = (int)
	//			((currentPosition / length)
				(((float)cur_Position_long / length)
						* CONS.PlayActv.sb.getMax());
		
		CONS.PlayActv.sb.setProgress(seekPosition);
		
		// Log
		msg_Log = "seekPosition set => " + seekPosition;
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//case_ACTV_PLAY_BT_BACKWARD()

	private void 
	case_ACTV_PLAY_BT_FORWARD() {
		// TODO Auto-generated method stub
		/******************************
			validate
		 ******************************/
		if (CONS.PlayActv.ai == null) {
			
			String msg = "CONS.PlayActv.ai => null";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return;
			
		}
		
		////////////////////////////////

		// get: data

		////////////////////////////////
		long cur_Position_long = 
				Methods.getPref_Long(
					actv,
					CONS.Pref.pname_PlayActv,
	//				CONS.Pref.pkey_PlayActv_position,
					CONS.Pref.pkey_PlayActv_CurrentPosition,
	//				CONS.Pref.pkey_CurrentPosition,
					CONS.Pref.dflt_LongExtra_value);

		long length =
				Methods.conv_ClockLabel_to_MillSec(
								CONS.PlayActv.ai.getLength());
		
		int seekPosition = (int)
//				((currentPosition / length)
				(((float)cur_Position_long / length)
						* CONS.PlayActv.sb.getMax());
		
		// Log
		String msg_Log = "seek position, before => "
						+ seekPosition;
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		////////////////////////////////
		
		// forward
		
		////////////////////////////////
		// Pref
		String pref_StepLength = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				actv.getString(R.string.pkey_prefactv_step_length), 
				null);
		
		// Log
		msg_Log = "pref_StepLength => " + pref_StepLength;
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		if (pref_StepLength != null) {
			
			// Converting from minutes to mill seconds
			CONS.PlayActv.stepValue = 
					Integer.parseInt(pref_StepLength) * (1000 * 60);
			
		} else {

			// Defalut value => 60000 mill seconds
			CONS.PlayActv.stepValue = CONS.PlayActv.dflt_StepValue;
			
		}
		
		cur_Position_long += CONS.PlayActv.stepValue;
//		cur_Position_long += length / CONS.PlayActv.stepValue;
//		cur_Position_long += 60000;
//		cur_Position_long += 15000;

		/******************************
			validate
		 ******************************/
		if (cur_Position_long >= length) {
			
			// Log
			msg_Log = "No more forwarding possible";
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		/******************************
			set: pref
		 ******************************/
		boolean res = Methods.setPref_Long(
					actv,
					CONS.Pref.pname_PlayActv,
	//				CONS.Pref.pkey_PlayActv_position,
					CONS.Pref.pkey_PlayActv_CurrentPosition,
	//				CONS.Pref.pkey_CurrentPosition,
					cur_Position_long);
		
		/******************************
			validate: pref set?
		 ******************************/
		if (res == false) {
			
			String msg = "Can't set pref: current position";
			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg);
			
			return;
			
		}
		
		// Log
		msg_Log = "pref position set => " 
						+ cur_Position_long;
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// set: seek bar

		////////////////////////////////
		
//		int seekPosition = (int)
		seekPosition = (int)
//				((currentPosition / length)
				(((float)cur_Position_long / length)
						* CONS.PlayActv.sb.getMax());
		
		CONS.PlayActv.sb.setProgress(seekPosition);
		
		// Log
		msg_Log = "seekPosition set => " + seekPosition;
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//case_ACTV_PLAY_BT_FORWARD()

	private void case_BMActv_Ib_Up() {
		// TODO Auto-generated method stub
		/******************************
			validate: list
		 ******************************/
		if (CONS.BMActv.bmList == null) {
			
			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
			CONS.BMActv.bmList = dbu.get_BMList(actv, CONS.BMActv.ai.getDb_id());
		}
		
//		ListView lv = ((ListActivity) actv);
		ListView lv = ((ListActivity) actv).getListView();
		
		int lastPos = lv.getLastVisiblePosition();
		
		int childCount = lv.getChildCount();
		
		int new_Position;
		
		if (lastPos - (childCount * 2) + 2 > 0) {
			
			new_Position = lastPos - (childCount * 2) + 2;
			
		} else {
			
			new_Position = 0;

		}
		
		lv.setSelection(new_Position);
		
//		int new_Position = lv.getLastVisiblePosition();
//		
//		if((new_Position + lv.getChildCount()) > CONS.BMActv.bmList.size()) {
//			
//			new_Position = CONS.BMActv.bmList.size() - lv.getChildCount();
//			
//		}
//		
//		lv.setSelection(new_Position);
//		
//		// Log
//		String msg_Log = String.format(
//				"lv.getLastVisiblePosition() => %d", 
//				lv.getLastVisiblePosition());
//		
//		Log.d("BO_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
		
	}
	
	private void case_BMActv_Ib_Down() {
		// TODO Auto-generated method stub
		/******************************
			validate: list
		 ******************************/
		if (CONS.BMActv.bmList == null) {
			
			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
			CONS.BMActv.bmList = dbu.get_BMList(actv, CONS.BMActv.ai.getDb_id());
		}
		
//		ListView lv = ((ListActivity) actv);
		ListView lv = ((ListActivity) actv).getListView();
		
		int new_Position = lv.getLastVisiblePosition();
		
		if((new_Position + lv.getChildCount()) > CONS.BMActv.bmList.size()) {
			
			new_Position = CONS.BMActv.bmList.size() - lv.getChildCount();
			
		}
		
		lv.setSelection(new_Position);
		
		// Log
		String msg_Log = String.format(
				"lv.getLastVisiblePosition() => %d", 
				lv.getLastVisiblePosition());
		
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
//		int curretBottomPosition = lv.getLastVisiblePosition();
//		
//		// Log
//		String msg_Log = String.format(
//						"curretBottomPosition => %d",
//						curretBottomPosition);
//		
//		Log.d("BO_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		
//		int numOfGroups = CONS.BMActv.bmList.size() / lv.getChildCount();
//		
//		int indexOfLastChild = lv.getChildCount() * numOfGroups;
//		
//		lv.setSelection(indexOfLastChild);
		
	}
	
	private void case_BMActv_Ib_Bottom() {
		// TODO Auto-generated method stub
		/******************************
			validate: list
		 ******************************/
		if (CONS.BMActv.bmList == null) {
			
			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
			CONS.BMActv.bmList = dbu.get_BMList(actv, CONS.BMActv.ai.getDb_id());
		}
		
//		ListView lv = ((ListActivity) actv);
		ListView lv = ((ListActivity) actv).getListView();
		
		int numOfGroups = CONS.BMActv.bmList.size() / lv.getChildCount();
		
		int indexOfLastChild = lv.getChildCount() * numOfGroups;
		
		lv.setSelection(indexOfLastChild);
		
	}
	
	private void case_BMActv_Ib_Top() {
		// TODO Auto-generated method stub
		/******************************
			validate: list
		 ******************************/
		if (CONS.BMActv.bmList == null) {
			
			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
			CONS.BMActv.bmList = dbu.get_BMList(actv, CONS.BMActv.ai.getDb_id());
		}
		
//		ListView lv = ((ListActivity) actv);
		ListView lv = ((ListActivity) actv).getListView();
		
		lv.setSelection(0);

//		int numOfGroups = CONS.BMActv.bmList.size() / lv.getChildCount();
//		
//		int indexOfLastChild = lv.getChildCount() * numOfGroups;
//		
//		lv.setSelection(indexOfLastChild);
		
	}

	private void case_BMActv_Back() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Set: last position

		////////////////////////////////
		int lastPosition = 
				((ListActivity) actv).getListView()
								.getLastVisiblePosition();
		
		boolean res = Methods.set_Pref_Int(actv, 
				CONS.Pref.pname_BMActv, 
				CONS.Pref.pkey_LastVisiblePosition_BMActv, 
				lastPosition);
		
		if (res == true) {
			
			// Log
			String msg_Log = String.format(
					"Pref => set: %s = %d", 
					CONS.Pref.pkey_LastVisiblePosition_BMActv, 
					lastPosition);
			
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
			
			// Log
			String msg_Log = "Pref => can't be set: " 
					+ CONS.Pref.pkey_LastVisiblePosition_BMActv;
			
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		
		////////////////////////////////

		// intent

		////////////////////////////////
		actv.setResult(CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_CANCEL);
		
		actv.finish();
		
		actv.overridePendingTransition(0, 0);
		
	}

	private void case_ActvPlay_BtBack() {
		// TODO Auto-generated method stub
		Methods.stop_Player(actv);
		
		actv.finish();
		
		actv.overridePendingTransition(0, 0);

	}

	private void 
	case_ActvPlay_BtStop() {
		// TODO Auto-generated method stub

		Methods.stop_Player(actv);
		
		////////////////////////////////

		// change: button

		////////////////////////////////
		Button bt_Stop = 
				(Button) actv.findViewById(R.id.actv_play_bt_stop);
		
		bt_Stop.setVisibility(View.GONE);
		
		Button bt_Play = 
				(Button) actv.findViewById(R.id.actv_play_bt_play);
		
		bt_Play.setVisibility(View.VISIBLE);

		
//		Button bt_Stop = 
//				(Button) actv.findViewById(R.id.actv_play_bt_stop);
////		(Button) actv.findViewById(R.id.actv_play_bt_play);
//		
//		bt_Stop.setTag(Tags.ButtonTags.actv_play_bt_play);
////		bt_play.setTag(Tags.ButtonTags.actv_play_bt_play);
//		
//		bt_Stop.setText(actv.getString(R.string.actv_play_bt_play));
////		bt_Stop.setText(actv.getString(R.string.actv_play_bt_stop));
//		
//		bt_Stop.setTextColor(
//						actv.getResources().getColor(R.color.blue1));
//		
//		bt_Stop.setOnTouchListener(new BO_TL(actv));
////		bt_play.setOnClickListener(new ButtonOnClickListener(this));
//		bt_Stop.setOnClickListener(new BO_CL(actv, CONS.PlayActv.ai));
		
	}//case_ActvPlay_BtStop()

	private void 
	case_ActvPlay_BtPlay() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "Play";
		
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		Methods.play_File(actv, ai);
		
		////////////////////////////////

		// change: button

		////////////////////////////////
		Button bt_Stop = 
				(Button) actv.findViewById(R.id.actv_play_bt_stop);
		
		bt_Stop.setVisibility(View.VISIBLE);
		
		Button bt_Play = 
				(Button) actv.findViewById(R.id.actv_play_bt_play);
		
		bt_Play.setVisibility(View.GONE);
		
//		Button bt_Play = 
//				(Button) actv.findViewById(R.id.actv_play_bt_play);
//		
//		bt_Play.setTag(Tags.ButtonTags.actv_play_bt_stop);
////		bt_play.setTag(Tags.ButtonTags.actv_play_bt_play);
//		
//		bt_Play.setText(actv.getString(R.string.actv_play_bt_stop));
//		
//		bt_Play.setTextColor(actv.getResources().getColor(R.color.red));
//		
//		bt_Play.setOnTouchListener(new BO_TL(actv));
////		bt_play.setOnClickListener(new ButtonOnClickListener(this));
//		bt_Play.setOnClickListener(new BO_CL(actv, CONS.PlayActv.ai));
		
	}//case_ActvPlay_BtPlay()

	private void case_PlayActv_See_BM() {
		// TODO Auto-generated method stub

		/***************************************
		 * Validate: Any bookmarks?
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		long numOfBM = dbu.getNumOfEntries_BM(actv, CONS.DB.tname_BM, ai.getDb_id());
		
		if (numOfBM < 1) {
			
			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "numOfBM < 1");
			
			// debug
			Toast.makeText(actv, "No bookmarks", Toast.LENGTH_LONG).show();
			
			return;
			
		} else {//if (numOfBM == condition)
			
			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "numOfBM=" + numOfBM);
			
		}//if (numOfBM == condition)
		
		/***************************************
		 * Intent
		 ***************************************/
		Intent i = new Intent();
		
		i.setClass(actv, BMActv.class);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
//		i.putExtra("ai_dbId", ai.getDb_id());
		i.putExtra(CONS.Intent.iKey_BMActv_AI_Id, ai.getDb_id());
//		i.putExtra(CONS.Intent.bmactv_key_ai_id, ai.getDb_id());
		
		i.putExtra(CONS.Intent.iKey_BMActv_TableName, ai.getTable_name());
//		i.putExtra(CONS.Intent.bmactv_key_table_name, ai.getTable_name());
		
//		actv.startActivity(i);
		actv.startActivityForResult(i, CONS.Intent.REQUEST_CODE_SEE_BOOKMARKS);

	}//private void case_PlayActv_See_BM()

	private void case_PlayActv_Add_BM() {
		// TODO Auto-generated method stub
		/***************************************
		 * Is the media player playing?
		 ***************************************/
		
		
		if (CONS.PlayActv.mp == null) {
			
			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Player => null");
			
			// debug
			Toast.makeText(actv, "Player => null", Toast.LENGTH_LONG).show();
			
			return;
			
		} else if (CONS.PlayActv.mp.isPlaying()) {//if (PlayActv.mp == null)
			
			_case_PlayActv_Add_BM__Playing();
			
			
//		} else if (!PlayActv.mp.isPlaying()) {//if (PlayActv.mp == null)
//		} else if () {//if (PlayActv.mp == null)
//			
//			// Log
//			Log.d("BO_CL.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ ":"
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", "Player => Not playing");
//			
//			// debug
//			Toast.makeText(actv, "Player => Not playing", Toast.LENGTH_LONG).show();
//			
//			return;
			
		
		} else if (!CONS.PlayActv.mp.isPlaying()) {//if (PlayActv.mp == null)
			
			_case_PlayActv_Add_BM__NotPlaying();
			
//			// Log
//			String msg_Log = "Player => not playing";
//			Log.d("BO_CL.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			// Log
//			msg_Log = "position => " + CONS.PlayActv.mp.getCurrentPosition();
//			Log.d("BO_CL.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			
			
			
			
		} else {//if (PlayActv.mp == null)
			
			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Player => Unknown state");
			
		}//if (PlayActv.mp == null)
		
//		/***************************************
//		 * Get: Current position
//		 ***************************************/
//		int currentPosition = PlayActv.mp.getCurrentPosition();
//		
//		// Log
//		Log.d("BO_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ ":"
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", "currentPosition=" + currentPosition);
//		
//		/***************************************
//		 * Get: Table name and db id of the ai instance
//		 ***************************************/
//		String tableName = ai.getTable_name();
//		long aiDbId = ai.getDb_id();
//		
//		// Log
//		Log.d("BO_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ ":"
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]",
//				"tableName=" + tableName
//				+ "/"
//				+ "aiDbId=" + aiDbId);
//		
//		/***************************************
//		 * Insert BM data into db
//		 * 1. Build a BM instance
//		 * 2. Insert data using the instance
//		 ***************************************/
//		/***************************************
//		 * 1. Build a BM instance
//		 ***************************************/
//		BM bm = new BM.Builder()
//					.setPosition(currentPosition)
//					.setTitle(ai.getTitle())
//					.setMemo(ai.getMemo())
//					.setAiId(ai.getDb_id())
//					.setAiTableName(ai.getTable_name())
//					.build();
//		
//		// Log
//		Log.d("BO_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ ":"
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", "ai.getTitle()=" + ai.getTitle());
//		// Log
//		Log.d("BO_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ ":"
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", "bm.getTitle()=" + bm.getTitle());
//		
//		/***************************************
//		 * 2. Insert data using the instance
//		 ***************************************/
//		DBUtils dbu = new DBUtils(actv, CONS.dbName);
//		boolean res = dbu.insertData_bm(actv, bm);
//		
//		if (res == true) {
//		
//			// Log
//			Log.d("BO_CL.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ ":"
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", "res=" + res);
//			
//			// debug
//			Toast.makeText(actv, "Bookmark inserted", Toast.LENGTH_LONG).show();
//			
//		} else {//if (res == true)
//
//			// Log
//			Log.d("BO_CL.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ ":"
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", "res=" + res);
//
//		}//if (res == true)
		
		
		
		//debug
//		debug_case_actv_play_bt_add_bm();
		
		
		
		
	}//case_PlayActv_Add_BM

	private void
	_case_PlayActv_Add_BM__NotPlaying() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "Player => not playing";
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		String currentPosition = 
				CONS.PlayActv.tvCurrentPosition.getText().toString();
		
		// Log
		msg_Log = "position => " + currentPosition;
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		long currentPosition_long = 
						Methods.conv_ClockLabel_to_MillSec(currentPosition);
		
		if (currentPosition_long == 0) {
			
			// Log
			msg_Log = "Current position => at 0";
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// debug
			Toast.makeText(actv, msg_Log, Toast.LENGTH_SHORT).show();
			
			return;
			
		}
		
		/***************************************
		 * Get: Table name and db id of the ai instance
		 ***************************************/
		String tableName = ai.getTable_name();
		long aiDbId = ai.getDb_id();
		
		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]",
				"tableName=" + tableName
				+ "/"
				+ "aiDbId=" + aiDbId);
		
		/***************************************
		 * Insert BM data into db
		 * 1. Build a BM instance
		 * 2. Insert data using the instance
		 ***************************************/
		/***************************************
		 * 1. Build a BM instance
		 ***************************************/
		BM bm = new BM.Builder()
					.setPosition(currentPosition)
					.setTitle(ai.getTitle())
					.setMemo(ai.getMemo())
					.setAiId(ai.getDb_id())
					.setAiTableName(ai.getTable_name())
					.build();
		
		/***************************************
		 * 2. Insert data using the instance
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		boolean res = dbu.insertData_BM(actv, bm);
		
		if (res == true) {
		
			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "res=" + res);
			
			// debug
			Toast.makeText(actv, "Bookmark inserted", Toast.LENGTH_LONG).show();
			
		} else {//if (res == true)

			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "res=" + res);

		}//if (res == true)			
		
	}//_case_PlayActv_Add_BM__NotPlaying()

	private void _case_PlayActv_Add_BM__Playing() {
		// TODO Auto-generated method stub
		/***************************************
		 * Get: Current position
		 ***************************************/
//		int currentPosition = CONS.PlayActv.mp.getCurrentPosition();
		String currentPosition = 
				Methods.conv_MillSec_to_ClockLabel(
						CONS.PlayActv.mp.getCurrentPosition());
		
		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "currentPosition=" + currentPosition);
		
		/***************************************
		 * Get: Table name and db id of the ai instance
		 ***************************************/
		String tableName = ai.getTable_name();
		long aiDbId = ai.getDb_id();
		
		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]",
				"tableName=" + tableName
				+ "/"
				+ "aiDbId=" + aiDbId);
		
		/***************************************
		 * Insert BM data into db
		 * 1. Build a BM instance
		 * 2. Insert data using the instance
		 ***************************************/
		/***************************************
		 * 1. Build a BM instance
		 ***************************************/
		BM bm = new BM.Builder()
					.setPosition(currentPosition)
					.setTitle(ai.getTitle())
					.setMemo(ai.getMemo())
					.setAiId(ai.getDb_id())
					.setAiTableName(ai.getTable_name())
					.build();
		
//		// Log
//		Log.d("BO_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ ":"
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", "ai.getTitle()=" + ai.getTitle());
//		// Log
//		Log.d("BO_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ ":"
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", "bm.getTitle()=" + bm.getTitle());
		
		/***************************************
		 * 2. Insert data using the instance
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		boolean res = dbu.insertData_BM(actv, bm);
		
		if (res == true) {
		
			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "res=" + res);
			
			// debug
			Toast.makeText(actv, "Bookmark inserted", Toast.LENGTH_LONG).show();
			
		} else {//if (res == true)

			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "res=" + res);

		}//if (res == true)		
		
	}//private void _case_PlayActv_Add_BM__Playing()

}//public class ButtonOnClickListener implements OnClickListener

	