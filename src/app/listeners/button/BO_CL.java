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
import app.utils.CONS;
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

		}//switch (tag)
		
	}//public void onClick(View v)

	private void case_ActvPlay_BtBack() {
		// TODO Auto-generated method stub
		actv.finish();
		
		actv.overridePendingTransition(0, 0);

	}

	private void case_ActvPlay_BtStop() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "Stop";
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

	}

	private void case_ActvPlay_BtPlay() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "Play";
		
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

	}

}//public class ButtonOnClickListener implements OnClickListener

	