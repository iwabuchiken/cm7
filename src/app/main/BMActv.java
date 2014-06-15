package app.main;

import java.util.List;

import cm7.main.R;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.adapters.Adp_BMList;
import app.items.BM;
import app.listeners.LOI_LCL;
import app.listeners.button.BO_CL;
import app.listeners.button.BO_TL;
import app.utils.CONS;
import app.utils.DBUtils;
import app.utils.Methods;
import app.utils.Tags;

public class BMActv extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.actv_bm);

		this.setTitle(this.getClass().getName());
	
//		vib = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
		
		// Log
		Log.d("BMActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onCreate()");

		////////////////////////////////

		// Init: vars

		////////////////////////////////
		if (CONS.Admin.vib == null) {
			
			CONS.Admin.vib = 
					(Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
			
		}
		
		
		////////////////////////////////

		// Get: AI

		////////////////////////////////
		_onCreate_GetAI();
		
		////////////////////////////////

		// Setup: view: textviews

		////////////////////////////////
		_onCreate_Setup_Textviews();

		////////////////////////////////

		// BM list

		////////////////////////////////
		_onCreate_Set_BMList();
		
		////////////////////////////////

		// Listeners

		////////////////////////////////
		_onCreate_SetListeners();
		
		
	}//protected void onCreate(Bundle savedInstanceState)

	private void 
	_onCreate_SetListeners() {
		// TODO Auto-generated method stub
		/***************************************
		 * 1. ListView
		 ***************************************/
		ListView lv = this.getListView();
		
		lv.setTag(Tags.ListTags.actv_bm_lv);
		
		lv.setOnItemLongClickListener(new LOI_LCL(this));
		
		/***************************************
		 * Button: "Back"
		 ***************************************/
		Button btBack = (Button) findViewById(R.id.actv_bm_bt_back);
		
		btBack.setTag(Tags.ButtonTags.actv_bm_bt_back);
		
		btBack.setOnTouchListener(new BO_TL(this));
		btBack.setOnClickListener(new BO_CL(this));

		////////////////////////////////

		// bottom

		////////////////////////////////
		ImageButton ib_Bottom = 
				(ImageButton) this.findViewById(R.id.actv_bm_ib_bottom);
		
		ib_Bottom.setTag(Tags.ButtonTags.actv_bm_ib_bottom);
		
		ib_Bottom.setOnTouchListener(new BO_TL(this));
		ib_Bottom.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// top
		
		////////////////////////////////
		ImageButton ib_Top = 
				(ImageButton) this.findViewById(R.id.actv_bm_ib_top);
		
		ib_Top.setTag(Tags.ButtonTags.actv_bm_ib_top);
		
		ib_Top.setOnTouchListener(new BO_TL(this));
		ib_Top.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// down
		
		////////////////////////////////
		ImageButton ib_Down = 
				(ImageButton) this.findViewById(R.id.actv_bm_ib_down);
		
		ib_Down.setTag(Tags.ButtonTags.actv_bm_ib_down);
		
//		ib_Down.setOnTouchListener(new BO_TL(this));
		ib_Down.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// up
		
		////////////////////////////////
		ImageButton ib_Up = 
				(ImageButton) this.findViewById(R.id.actv_bm_ib_up);
		
		ib_Up.setTag(Tags.ButtonTags.actv_bm_ib_up);
		
//		ib_Up.setOnTouchListener(new BO_TL(this));
		ib_Up.setOnClickListener(new BO_CL(this));
		
	}//_onCreate_SetListeners()

	private boolean _onCreate_Set_BMList() {
		// TODO Auto-generated method stub
		DBUtils dbu = new DBUtils(this, CONS.DB.dbName);
		
//		List<BM> bmList = dbu.getBMList(this, ai.getDb_id());
		CONS.BMActv.bmList = dbu.get_BMList(this, CONS.BMActv.ai.getDb_id());
		
//		// Log
//		Log.d("BMActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ ":"
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", "bmList=" + CONS.BMActv.bmList);

		/******************************
			validate
		 ******************************/
		if (CONS.BMActv.bmList == null) {
			
			// Log
			String msg_Log = "CONS.BMActv.bmList => null";
			Log.e("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return false;
			
		}
		
		Methods.sort_List_BM_List(
						CONS.BMActv.bmList, 
						CONS.Enums.SortType.POSITION,
						CONS.Enums.SortOrder.ASC);
		
		// Log
		String msg_Log = "bmList.size() => " + CONS.BMActv.bmList.size();
		Log.d("BMActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// Set the list to adapter

		////////////////////////////////
		if (CONS.BMActv.bmList != null) {
			
			CONS.BMActv.aAdapter = new Adp_BMList(
					this,
					R.layout.listrow_actv_bm,
	//				R.layout.actv_al,
					CONS.BMActv.bmList
					);
	
			setListAdapter(CONS.BMActv.aAdapter);

		} else {//if (bmList != null)

			// Log
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "bmList => null");
			
		}//if (bmList != null)

		////////////////////////////////

		// set selection

		////////////////////////////////
		int last_Position = Methods.get_Pref_Int(
						this, 
						CONS.Pref.pname_BMActv, 
						CONS.Pref.pkey_LastVisiblePosition_BMActv, 
						CONS.Pref.dflt_IntExtra_value);
		
		if (last_Position != CONS.Pref.dflt_IntExtra_value) {
			
			ListView lv = this.getListView();
			
			int child_Count = this.getListView().getChildCount();
			
			if (child_Count == 0) {
				
				child_Count = 4;
				
			}
			
			int new_Position = last_Position - child_Count;
//			int new_Position = last_Position - lv.getChildCount();
			
			if (new_Position < 0) {
				
				new_Position = 0;
				
			}
			
			lv.setSelection(new_Position);
			
			// Log
			msg_Log = "selection => set: " + new_Position;
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			msg_Log = "new_Position = " + new_Position
					+ " / "
					+ "last_Position = " + last_Position
					+ " / "
					+ "lv.getChildCount() = " + lv.getChildCount()
//					+ " / "
//					+ "child_Count = " + child_Count
					;
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			
			
		} else {
			
			// Log
			msg_Log = "last_Position => CONS.Pref.dflt_IntExtra_value";
			
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		////////////////////////////////

		// return

		////////////////////////////////
		return true;
		
	}//private void _onCreate_Set_BMList()

	private void _onCreate_Setup_Textviews() {
		// TODO Auto-generated method stub
		/***************************************
		 * Set: File name
		 ***************************************/
		TextView tvFileName = (TextView) findViewById(R.id.actv_bm_tv_file_name);
		
		tvFileName.setText(CONS.BMActv.ai.getFile_name());
		
		/***************************************
		 * Set: Memo
		 ***************************************/
//		TextView tvTitle = (TextView) findViewById(R.id.actv_bm_tv_title);
//		
//		tvTitle.setText(CONS.BMActv.ai.getTitle());
		
	}

	private boolean _onCreate_GetAI() {
		// TODO Auto-generated method stub
		/***************************************
		 * Get: db id
		 ***************************************/
		Intent i = this.getIntent();
		
		long aiDbId = i.getLongExtra(
							CONS.Intent.iKey_BMActv_AI_Id, 
							CONS.Intent.dflt_LongExtra_value);
		
		if (aiDbId == CONS.Intent.dflt_LongExtra_value) {
			
			// Log
			Log.e("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "aiDbId = " + CONS.Intent.dflt_LongExtra_value);
			
			return false;
			
		}//if (aiDbId == -1)
		
		/***************************************
		 * Get: Table name
		 ***************************************/
		String tableName = i.getStringExtra(CONS.Intent.iKey_BMActv_TableName);
		
		// Log
		Log.d("BMActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "aiDbId=" + aiDbId);
		
		// Log
		Log.d("BMActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "tableName=" + tableName);
		
		/***************************************
		 * Build an AI instance
		 ***************************************/
		CONS.BMActv.ai = DBUtils.find_AI_ById(this, aiDbId, tableName);
//		AI ai = Methods..get_data_ai(this, aiDbId, tableName);
		
		/******************************
			validate
		 ******************************/
		if (CONS.BMActv.ai == null) {
			
			return false;
			
		} else {
			
			return true;

		}
		
	}//private boolean _onCreate_GetAI()
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}

	@Override
	protected void
	onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
//		super.onListItemClick(l, v, position, id);
		
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		/***************************************
		 * Get: Item
		 ***************************************/
		BM bm = (BM) l.getItemAtPosition(position);

		////////////////////////////////

		// Set: pref: current position

		////////////////////////////////
		boolean res = Methods.set_Pref_Int(this, 
									CONS.Pref.pname_BMActv, 
									CONS.Pref.pkey_CurrentPosition_BMActv, 
									position);
		if (res == true) {
			
			// Log
			String msg_Log = String.format(
								"Pref => set: %s = %d", 
								CONS.Pref.pkey_CurrentPosition_BMActv, 
								position);
			
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
			
			// Log
			String msg_Log = "Pref => can't be set: " 
							+ CONS.Pref.pkey_CurrentPosition_BMActv;
			
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		////////////////////////////////
		
		// Set: pref: LastVisiblePosition
		
		////////////////////////////////
		int lastPosition = this.getListView().getLastVisiblePosition();
		
		res = Methods.set_Pref_Int(this, 
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

		// notify

		////////////////////////////////
		CONS.BMActv.aAdapter.notifyDataSetChanged();
		
		////////////////////////////////

		// Set: Result

		////////////////////////////////
		Intent i = new Intent();
		
		i.putExtra(CONS.Intent.iKey_BMActv_Position, bm.getPosition());
		
		i.putExtra(CONS.Intent.iKey_BMActv_AI_Id, bm.getAiId());
		
		i.putExtra(CONS.Intent.iKey_BMActv_TableName, bm.getAiId());
//		i.putExtra(CONS.Intent.bmactv_key_ai_id, this.getAi().getDb_id());
//		
//		i.putExtra(CONS.Intent.bmactv_key_table_name, this.getAi().getTable_name());
		
		setResult(CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_OK, i);
		
		/***************************************
		 * Finish
		 ***************************************/
		finish();
		
		overridePendingTransition(0, 0);
		
	}//onListItemClick(ListView l, View v, int position, long id)

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
//		int child_Count = this.getListView().getChildCount();
//		
//		// Log
//		String msg_Log = "child_Count = " + child_Count;
//		Log.d("BMActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		
		
	}//protected void onStart()

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
//		//debug
//		int child_Count = this.getListView().getChildCount();
//		
//		// Log
//		String msg_Log = "child_Count = " + child_Count;
//		Log.d("BMActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);

		
		/***************************************
		 * Set: Intent
		 ***************************************/
		this.setResult(CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_CANCEL);
		
		this.finish();
		
		overridePendingTransition(0, 0);
		
		return super.onKeyDown(keyCode, event);
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
//		// Log
//		String msg_Log = "onResume()";
//		Log.d("BMActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		
//		int child_Count = this.getListView().getChildCount();
//		
//		// Log
//		msg_Log = "child_Count = " + child_Count;
//		Log.d("BMActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
	}

	
	
}
