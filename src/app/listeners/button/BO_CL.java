package app.listeners.button;

import java.io.File;



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
			
		}//switch (tag)
		
	}//public void onClick(View v)

	private void case_BMActv_Back() {
		// TODO Auto-generated method stub
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

	private void case_ActvPlay_BtStop() {
		// TODO Auto-generated method stub

		Methods.stop_Player(actv);
		
	}

	private void case_ActvPlay_BtPlay() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "Play";
		
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		Methods.play_File(actv, ai);
		
	}

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

	