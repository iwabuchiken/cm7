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
		

	}//protected void onCreate(Bundle savedInstanceState)

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
