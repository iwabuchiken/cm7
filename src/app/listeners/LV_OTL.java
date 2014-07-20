package app.listeners;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import app.utils.Tags;

public class LV_OTL implements OnTouchListener {

	Activity actv;
	
	public LV_OTL(Activity actv) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN: //----------------------------
			
			v.setBackgroundColor(Color.GRAY);
			
//			switch (tag) {
//			
//
//			}//switch (tag)
			
			break;//case MotionEvent.ACTION_DOWN:

			
		case MotionEvent.ACTION_UP: //----------------------------
			
			v.setBackgroundColor(Color.BLACK);
			
//			switch (tag) {
//				
//				
//			}//switch (tag)
			
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

}
