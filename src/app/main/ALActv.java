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
import android.app.Activity;
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
import app.adapters.Adp_AIList;
import app.items.AI;
import app.listeners.LOI_LCL;
import app.listeners.button.BO_CL;
import app.listeners.button.BO_TL;
import app.utils.CONS;
import app.utils.DBUtils;
import app.utils.Methods;
import app.utils.Tags;

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
		setContentView(R.layout.actv_al);
		
		/****************************
		 * 3. Basics
			****************************/
		this.setTitle(this.getClass().getName());
		
	}//public void onCreate(Bundle savedInstanceState)


	private void _onCreate_Setup() {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// Get: Current path
		
		////////////////////////////////
		_onCreate_Get_CurrentPath();
		
		/******************************
		validate: current path => obtained?
		******************************/
		if (CONS.ALActv.currentPath == null) {
		
		// Log
		String msg_Log = "CONS.ALActv.currentPath => null";
		Log.e("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// debug
		Toast.makeText(this, msg_Log, Toast.LENGTH_SHORT).show();
		
		return;
		
		}
		
		////////////////////////////////
		
		// Get: AI list
		
		////////////////////////////////
		boolean res = _onCreate_Get_AIList();
		
		/******************************
		validate
		******************************/
		if (res == false) {
		
		return;
		
		}
		
		// Log
		String msg_Log = "CONS.ALActv.list_AI.size() = "
			+ CONS.ALActv.list_AI.size();
		Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
		
		// Sort list
		Methods.sort_List_ai_List(
					CONS.ALActv.list_AI,
					CONS.Enums.SortType.FileName, 
					CONS.Enums.SortOrder.DEC);
		
		////////////////////////////////
		
		// Adapter
		
		////////////////////////////////
		CONS.ALActv.adp_AIList = new Adp_AIList(
					this,
					R.layout.list_row_ai_list,
					CONS.ALActv.list_AI
		);
		
		////////////////////////////////
		
		// Set adapter
		
		////////////////////////////////
		this.setListAdapter(CONS.ALActv.adp_AIList);
		
		////////////////////////////////
		
		// Set: list position
		
		////////////////////////////////
		_onCreate_SetSelection();
		
		
		////////////////////////////////

		// set: listeners: listview

		////////////////////////////////
		_onCreate_SetListeners();
		
		
	}//_onCreate_Setup


	private void _onCreate_SetListeners() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// listview

		////////////////////////////////
		ListView lv = this.getListView();
		
		lv.setTag(Tags.ListTags.actv_ALActv_lv);
		
		lv.setOnItemLongClickListener(new LOI_LCL(this));
		
		////////////////////////////////

		// button: back

		////////////////////////////////
		ImageButton ib_Back = (ImageButton) findViewById(R.id.actv_al_ib_back);
		
		ib_Back.setTag(Tags.ButtonTags.ACTV_AL_IB_BACK);
		
		ib_Back.setOnTouchListener(new BO_TL(this));
		ib_Back.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// button: up
		
		////////////////////////////////
		ImageButton ib_Up = (ImageButton) findViewById(R.id.actv_al_ib_up);
		
		ib_Up.setTag(Tags.ButtonTags.ACTV_AL_IB_UP);
		
		ib_Up.setOnTouchListener(new BO_TL(this));
		ib_Up.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// button: up
		
		////////////////////////////////
		ImageButton ib_Down = (ImageButton) findViewById(R.id.actv_al_ib_down);
		
		ib_Down.setTag(Tags.ButtonTags.ACTV_AL_IB_DOWN);
		
		ib_Down.setOnTouchListener(new BO_TL(this));
		ib_Down.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// button: top
		
		////////////////////////////////
		ImageButton ib_Top = (ImageButton) findViewById(R.id.actv_al_ib_toTop);
		
		ib_Top.setTag(Tags.ButtonTags.ACTV_AL_IB_TOP);
		
		ib_Top.setOnTouchListener(new BO_TL(this));
		ib_Top.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// button: bottom
		
		////////////////////////////////
		ImageButton ib_Bottom = (ImageButton) findViewById(R.id.actv_al_ib_toBottom);
		
		ib_Bottom.setTag(Tags.ButtonTags.ACTV_AL_IB_BOTTOM);
		
		ib_Bottom.setOnTouchListener(new BO_TL(this));
		ib_Bottom.setOnClickListener(new BO_CL(this));
		
	}


	private void
	_onCreate_SetSelection() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: position => set?
		 ******************************/
		if(CONS.ALActv.display_TopPosition_Current == -1
				|| CONS.ALActv.display_TopPosition_Previous == -1)
			
			return;
		
		////////////////////////////////

		// Calculate: target position

		////////////////////////////////
		int target_Position;
		
		// If the current is larger than the previous,
		//	i.e. the position is increasing
		//	=> modify the target
		if (CONS.ALActv.display_TopPosition_Current > 
				CONS.ALActv.display_TopPosition_Previous) {
			
			target_Position = CONS.ALActv.display_TopPosition_Current - 5;
			
		} else {
			
			// If the current is smaller than the previous,
			//	i.e. the position is decreasing
			//	=> set the target with the current
			target_Position = CONS.ALActv.display_TopPosition_Current;

		}
		
		// Log
		String msg_Log = "CONS.ALActv.display_TopPosition_Current = "
						+ CONS.ALActv.display_TopPosition_Current
						+ " // "
						+ "CONS.ALActv.display_TopPosition_Previous = "
						+ CONS.ALActv.display_TopPosition_Previous
						+ " // "
						+ "target_Position = "
						+ target_Position
						;
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		//REF http://stackoverflow.com/questions/7561353/programmatically-scroll-to-a-specific-position-in-an-android-listview answered Sep 26 '11 at 21:39
		this.getListView().setSelection(target_Position);
		
//		int pref_CurrentPosition = 
//    			Methods.get_Pref_Int(
//    					this, 
//    					CONS.Pref.pname_ALActv, 
//    					CONS.Pref.pkey_CurrentPosition_ALActv,
//    					-1);
//		
//		// 1. If the current position is non-"-1" and
//		//	(position - 3) is non-"less than 0"
//		//	=> set the list selection to the position
//		// 2. The value -3 is arbitrary;
//		//	=> Change to your choice if favorable
//		if (pref_CurrentPosition != -1
//				&& (pref_CurrentPosition - 3) > 0) {
////			&& (pref_CurrentPosition - 3) < 0) {
//			
//			this.getListView().setSelection(pref_CurrentPosition - 3);
//			
//			// Log
//			String msg_Log = "Selection => set";
//			Log.d("ALActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		} else {
//			
//			// Log
//			String msg_Log = "Selectin => won't be set"
//							+ " // "
//							+ "position =" + pref_CurrentPosition;
//			Log.d("ALActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		}		
		
	}//_onCreate_SetSelection()


	private boolean _onCreate_Get_AIList() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Table name

		////////////////////////////////
		String tableName = 
				Methods.conv_CurrentPath_to_TableName(CONS.ALActv.currentPath);
		
		// Log
		String msg_Log = "table name = " + tableName;
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// Get items from: DB

		////////////////////////////////
		CONS.ALActv.list_AI = DBUtils.find_All_AI(this, tableName);
//		CONS.ALActv.list_AI = Methods.get_AIList(this, tableName);
		
		/******************************
			validate
		 ******************************/
		if (CONS.ALActv.list_AI == null) {
			
			// Log
			msg_Log = "Cant create AI list";
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// debug
			Toast.makeText(this, msg_Log, Toast.LENGTH_SHORT).show();
			
			return false;
			
		}
		
		return true;
		
	}//private void _onCreate_SetFileList()


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
		// TODO ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ黷ｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ\?�ｿｽ�ｿｽb?�ｿｽ�ｿｽh?�ｿｽ�ｿｽE?�ｿｽ�ｿｽX?�ｿｽ�ｿｽ^?�ｿｽ�ｿｽu
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
		
		////////////////////////////////

		// Set up

		////////////////////////////////
		_onCreate_Setup();

	}//protected void onStart()

	@Override
	protected void onStop() {
		// TODO ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ黷ｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ?�ｿｽ�ｿｽ\?�ｿｽ�ｿｽb?�ｿｽ�ｿｽh?�ｿｽ�ｿｽE?�ｿｽ�ｿｽX?�ｿｽ�ｿｽ^?�ｿｽ�ｿｽu
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
		CONS.Admin.vib.vibrate(Methods.vibLength_click);
		
		////////////////////////////////

		// Get: AI

		////////////////////////////////
		AI ai = _ItemClick_GetItem(lv, position);
		
		////////////////////////////////

		// Set pref: Current position

		////////////////////////////////
		_ItemClick_SetPref_CurrentPosition(position);
		
		////////////////////////////////

		// Start: PlayActv

		////////////////////////////////
		Methods.start_Activity_PlayActv(this, ai);
		
//		vib.vibrate(Methods.vibLength_click);

	}//protected void onListItemClick(ListView lv, View v, int position, long id)

	private void
	_ItemClick_SetPref_CurrentPosition(int position) {
		// TODO Auto-generated method stub
		
		Methods.set_Pref_Int(
				this,
				CONS.Pref.pname_ALActv,
				CONS.Pref.pkey_CurrentPosition_ALActv,
				position);
		
		// Log
		String msg_log = "Pref: " + CONS.Pref.pkey_CurrentPosition_ALActv
						+ " => "
						+ "Set to: " + position;
		
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
		CONS.ALActv.adp_AIList.notifyDataSetChanged();
	
	}//_ItemClick_SetPref_CurrentPosition(int position)
	


	private AI
	_ItemClick_GetItem(ListView lv, int position) {
		// TODO Auto-generated method stub
		AI ai = (AI) lv.getItemAtPosition(position);
		
//		if (ai != null) {
//			
//			// Log
//			Log.d("ALActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "ai =" + ai.getFile_name());
//			
//		} else {//if (item_)
//			
//			// Log
//			Log.d("ALActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "ai => null");
//			
//		}//if (item_)

		return ai;
		
	}//_ItemClick_GetItem(ListView lv, int position)

	
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
		case R.id.al_actv_menu_move_mode://---------------------------------------
			
			Methods.move_Mode(this, item);
			
			break;// case R.id.thumb_actv_menu_move_files
	
		
		
		}//switch (item.getItemId())
		
		
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSxelected(MenuItem item)

}//public class TNActv
