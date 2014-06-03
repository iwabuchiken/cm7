package app.listeners.button;

import cm7.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;
import app.utils.Tags;

public class BO_TL implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	public BO_TL(Activity actv) {
		//
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO ?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½ê‚½?¿½?¿½?¿½\?¿½b?¿½h?¿½E?¿½X?¿½^?¿½u
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN: //----------------------------
			
			switch (tag) {
			
			case actv_play_bt_play:
			case actv_play_bt_stop:
			case actv_play_bt_back:
			case actv_play_bt_see_bm:
			case actv_play_bt_add_bm:
				
				v.setBackgroundColor(Color.GRAY);
				break;
				
			case actv_play_tv_title:
			case actv_play_tv_memo:
			case actv_bm_bt_back:
				
				_onTouch_SetColors(v, Color.WHITE, Color.BLACK);
//				case_bcBlack_tcWhite(v);
//				v.setBackgroundColor(Color.BLACK);
//
//				((TextView)v).setTextColor(Color.WHITE);
				
				break;

			}//switch (tag)
			
			break;//case MotionEvent.ACTION_DOWN:

			
		case MotionEvent.ACTION_UP: //----------------------------
			switch (tag) {
				
			case actv_play_bt_play:
			case actv_play_bt_stop:
			case actv_play_bt_back:
			case actv_play_bt_see_bm:
			case actv_play_bt_add_bm:
				
				v.setBackgroundColor(Color.WHITE);
				break;

			case actv_play_tv_title:
			case actv_play_tv_memo:
			case actv_bm_bt_back:
				
				this._onTouch_SetColors(v, Color.BLACK, Color.WHITE);
//				case_bcWhite_tcBlack(v);
//				v.setBackgroundColor(Color.WHITE);
//				
//				TextView tv = (TextView) v;
//				
//				tv.setTextColor(Color.BLACK);
//				tv.setTextColor(Color.BLUE);
				
				break;

			}//switch (tag)
			
			break;//case MotionEvent.ACTION_UP:
		}//switch (event.getActionMasked())
		return false;
	}

	/******************************
		private void _onTouch_SetColors()
	 ******************************/
	private void
	_onTouch_SetColors
	(View v, int color_Text, int color_Back) {
		// TODO Auto-generated method stub
		
		v.setBackgroundColor(color_Text);
		
		TextView tv = (TextView) v;
		
		tv.setTextColor(color_Back);
		
	}

}//public class BO_TL implements OnTouchListener
