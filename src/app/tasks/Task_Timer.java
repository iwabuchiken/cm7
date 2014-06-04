package app.tasks;

import java.util.TimerTask;

import android.os.Handler;
import android.util.Log;
import app.utils.CONS;
import app.utils.Methods;

public class Task_Timer extends TimerTask {

	Handler handler;
	
	public Task_Timer(Handler handler) {
		// TODO Auto-generated constructor stub
		
		this.handler	= handler;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (CONS.PlayActv.mp != null) {
			
			handler.post(new Runnable(){

//						@Override
				public void run() {
					/***************************************
					 * Update: Progress label
					 ***************************************/
//							PlayActv.updateProgressLabel(actv);
//					new PlayActv().updateProgressLabel();
					Methods.update_ProgressLable();
					
					/***************************************
					 * Update: Seekbar
					 ***************************************/
					if (CONS.PlayActv.mp != null
//									&& !CONS.PlayActv.sb.isInTouchMode()) {
							&& !CONS.PlayActv.sb.isPressed()) {
						
						// Log
						Log.d("Service_ShowProgress.java"
								+ "["
								+ Thread.currentThread()
										.getStackTrace()[2]
										.getLineNumber()
								+ ":"
								+ Thread.currentThread()
										.getStackTrace()[2]
										.getMethodName() + "]",
								"CONS.PlayActv.mp != null && " +
//										"!CONS.PlayActv.sb.isInTouchMode()");
								"!CONS.PlayActv.sb.isPressed()");
						
						int currentPosition = CONS.PlayActv.mp.getCurrentPosition();
						
						long length = Methods.conv_ClockLabel_to_MillSec(
												CONS.PlayActv.ai.getLength());
						
						int seekPositon = (int)
//											((currentPosition / length)
									(((float)currentPosition / length)
											* CONS.PlayActv.sb.getMax());
//								
						CONS.PlayActv.sb.setProgress(seekPositon);
						
					} else {//if (CONS.PlayActv.mp == null)
						
						// Log
						Log.d("Service_ShowProgress.java"
								+ "["
								+ Thread.currentThread()
										.getStackTrace()[2]
										.getLineNumber()
								+ ":"
								+ Thread.currentThread()
										.getStackTrace()[2]
										.getMethodName() + "]",
								"NO");
						
					}//if (CONS.PlayActv.mp == null)
					
				}//public void run() // Runnable
				
			});//handler.post()
			
			
		}//if (CONS.P == condition)
		
//		counter += 1;
		
	}//public void run()
			

}
