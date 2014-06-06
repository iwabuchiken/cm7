package app.listeners;

import cm7.main.R;
import android.app.Activity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import app.utils.CONS;
import app.utils.Methods;

public class SBL implements OnSeekBarChangeListener {

	Activity actv;
	SeekBar sb;
	
	public SBL(Activity actv) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
	}

	public SBL(Activity actv, SeekBar sb) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		this.sb = sb;
	}

	public void
	onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		
		// Log
		String msg_Log = "progress = " + progress;
		Log.d("SBL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		float length = 
				(float) Methods.conv_ClockLabel_to_MillSec(
							CONS.PlayActv.ai.getLength());
		
		int seekedPosition = (int)
				((float)progress * 1.000f / sb.getMax() * length);
//		((float)progress * 1.000f / CONS.PlayActv.sb.getMax() * length);
//		int seekedPosition = (int)
//				((float)progress * 1.000f / sb.getMax() 
//						* (float)PlayActv.ai.getLength());
		
		// Log
		msg_Log = "(double)progress / sb.getMax() = " 
					+ String.format("%.5f", ((double)progress / sb.getMax()));
		
		Log.d("SBL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", "(float)progress / sb.getMax() = " + ((float)progress / sb.getMax()));
				+ "]", msg_Log);
//		+ "]", "(double)progress / sb.getMax() = " + String.format("%.5f", ((double)progress / sb.getMax())));
		
		// Log
		msg_Log = "length = " + length;
		Log.d("SBL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Log
		Log.d("SBL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]",
				"seekedPosition=" + seekedPosition
				+ "("
				+ Methods.convert_intSec2Digits_lessThanHour(seekedPosition / 1000)
				+ ")");

		/***************************************
		 * Set: Current position to the view
		 ***************************************/
//		if (CONS.PlayActv.tvCurrentPosition == null) {
//			
//			// Log
//			msg_Log = "CONS.PlayActv.tvCurrentPosition => null";
//			Log.d("SBL.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//
//			CONS.PlayActv.tvCurrentPosition = 
//					(TextView) actv.findViewById(
//							R.id.actv_play_tv_current_position);
//			
//		}//if (CONS.PlayActv.tvCurrentPosition == null)
//		CONS.PlayActv.tvCurrentPosition = (TextView) this.findViewById(R.id.actv_play_tv_current_position);
		
		// Log
		msg_Log = "seekedPosition = " + seekedPosition
						+ " // "
						+ "Methods.conv_MillSec_to_ClockLabel(seekedPosition) = "
						+ Methods.conv_MillSec_to_ClockLabel(seekedPosition);
		Log.d("SBL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Log
		msg_Log = "CONS.PlayActv.tvCurrentPosition.getClass().getName() = "
					+ CONS.PlayActv.tvCurrentPosition.getClass().getName();
		Log.d("SBL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		//test
		// mill second value => round up
		int tmp = seekedPosition % 1000;
		
		if (tmp > 0) {
			
			seekedPosition = (seekedPosition / 1000) * 1000 + 1000;
			
			// Log
			msg_Log = "seekedPosition is now: " + seekedPosition;
			Log.d("SBL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
			
			// Log
			msg_Log = "seekedPosition remains to be: " + seekedPosition;
			Log.d("SBL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);

		}
		
		CONS.PlayActv.tvCurrentPosition.setText(
				Methods.conv_MillSec_to_ClockLabel(seekedPosition));
//		Methods.convert_intSec2Digits_lessThanHour(seekedPosition / 1000));

		String current = (String) CONS.PlayActv.tvCurrentPosition.getText();
		
		// Log
		msg_Log = "current time = " + current;
		Log.d("SBL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
	}//onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)

	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		// Log
		Log.d("SBL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onStartTrackingTouch()");
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		
		/***************************************
		 * Set: Current position to preference
		 ***************************************/
		long seekedPosition =
				(long) ((float) seekBar.getProgress() * 1.000f / sb.getMax()
							* (float) Methods.conv_ClockLabel_to_MillSec(
										CONS.PlayActv.ai.getLength()));

		// Log
		String msg_Log = "onStopTrackingTouch: seekedPosition = " + seekedPosition;
		Log.d("SBL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		boolean res = 
				Methods.setPref_Long(
						actv,
						CONS.Pref.pname_PlayActv,
						CONS.Pref.pkey_PlayActv_CurrentPosition,
						seekedPosition);
		
		/******************************
			validate
		 ******************************/
		if (res == false) {
			
			// Log
			Log.d("SBL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Position => Not stored: " + seekedPosition);
			
		}
		
		// Log
		msg_Log = "Pref: current position => set";
		Log.d("SBL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
	}//public void onStopTrackingTouch(SeekBar seekBar)

}//public class SeekBarChangeListener implements OnSeekBarChangeListener
