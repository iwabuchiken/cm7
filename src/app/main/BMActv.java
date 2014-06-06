package app.main;

import java.util.List;

import cm7.main.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.utils.CONS;
import app.utils.DBUtils;
import app.utils.Methods;

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

		// Get: AI

		////////////////////////////////
		_onCreate_GetAI();
		
		////////////////////////////////

		// Setup: view: textviews

		////////////////////////////////
		_onCreate_Setup_Textviews();

	}//protected void onCreate(Bundle savedInstanceState)

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
		TextView tvTitle = (TextView) findViewById(R.id.actv_bm_tv_title);
		
		tvTitle.setText(CONS.BMActv.ai.getTitle());
		
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
		
//		vib.vibrate(Methods.vibLength_click);
//		
//		/***************************************
//		 * Get: Item
//		 ***************************************/
//		BM bm = (BM) l.getItemAtPosition(position);
//		
//		// Log
//		Log.d("BMActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ ":"
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", "bm.getPosition()=" + bm.getPosition());
//		/***************************************
//		 * Set: Result
//		 ***************************************/
//		Intent i = new Intent();
//		
//		i.putExtra(CONS.Intent.bmactv_key_position, bm.getPosition());
//		
//		i.putExtra(CONS.Intent.bmactv_key_ai_id, this.getAi().getDb_id());
//		
//		i.putExtra(CONS.Intent.bmactv_key_table_name, this.getAi().getTable_name());
//		
//		setResult(CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_OK, i);
//		
//		/***************************************
//		 * Finish
//		 ***************************************/
//		finish();
		
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
		
//		/***************************************
//		 * Get: AI db id
//		 ***************************************/
//		AI ai = setup__getAI();
//
//		/***************************************
//		 * Set: File name and others to the text views
//		 ***************************************/
//		if (ai != null) {
//			
//			setup__2_setData2TextViews(ai);
//			
//		} else {//if (ai != null)
//			
//			// Log
//			Log.d("BMActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ ":"
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", "ai == null");
//			
//		}//if (ai != null)
//
//		/***************************************
//		 * Set: BM list
//		 * 1. Build a BM list
//		 * 2. Set the list to adapter
//		 ***************************************/
//		setup__3_setBMList();
//
//		/***************************************
//		 * Set: Listeners
//		 ***************************************/
//		setup__4_setListeners();
		
	}//protected void onStart()

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		/***************************************
		 * Set: Intent
		 ***************************************/
//		this.setResult(CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_CANCEL);
		
		return super.onKeyDown(keyCode, event);
	}

	
	
}
