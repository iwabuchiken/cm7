package app.listeners.text;

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

public class TV_TL implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	public TV_TL(Activity actv) {
		//
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

//	@Override
	public boolean
	onTouch(View v, MotionEvent event) {
		// TODO ?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½ê‚½?¿½?¿½?¿½\?¿½b?¿½h?¿½E?¿½X?¿½^?¿½u
		Tags.TVTags tag = (Tags.TVTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN: //----------------------------
			
			switch (tag) {
			
			case PLAYACTV_TITLE:
				
				_onTouch_SetColors(v, Color.WHITE, Color.BLACK);
				
				break;

			}//switch (tag)
			
			break;//case MotionEvent.ACTION_DOWN:

			
		case MotionEvent.ACTION_UP: //----------------------------
			switch (tag) {
				
			case PLAYACTV_TITLE:
				
				_onTouch_SetColors(v, Color.BLACK, Color.WHITE);
				
				break;
				
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_UP:
			
		}//switch (event.getActionMasked())
		
		return false;
		
	}//onTouch(View v, MotionEvent event)

	/******************************
		private void _onTouch_SetColors()
	 ******************************/
	private void
	_onTouch_SetColors
	(View v, int color_Text, int color_Back) {
		// TODO Auto-generated method stub
		
		v.setBackgroundColor(color_Back);
//		v.setBackgroundColor(color_Text);
		
		TextView tv = (TextView) v;
		
		tv.setTextColor(color_Text);
//		tv.setTextColor(color_Back);
		
	}

}//public class BO_TL implements OnTouchListener
