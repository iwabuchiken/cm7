package app.services;

import java.util.Timer;
import java.util.TimerTask;




import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import app.tasks.Task_Timer;
import app.utils.CONS;

public class Service_ShowProgress extends Service {

	Timer timer;

	int counter;
	
//	int period = 1000;	// in mill seconds
	int period;	// in mill seconds
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onBind()");
		
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onCreate()");
		
		////////////////////////////////

		// Get: Intent values

		////////////////////////////////
//		Intent i = this
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onDestroy()");

		/***************************************
		 * Cancel counting
		 ***************************************/
		timer.cancel();
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "Timer => Cancelled");
		
	}//public void onDestroy()

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onStart()");
		
		startCount();

	}//public void onStart(Intent intent, int startId)

	private void startCount() {
		
		final android.os.Handler handler = new android.os.Handler();
		
		counter = 0;
		
		//
		if (timer != null) {
			timer.cancel();
		}//if (timer != null)
		
		timer = new Timer();

		timer.schedule(new Task_Timer(handler), 0, period);
//		timer.schedule(
//				new TimerTask(){
//
//					@Override
//					public void run() {
//						
//
//						if (PlayActv.mp != null) {
//							
//							handler.post(new Runnable(){
//
////								@Override
//								public void run() {
//									/***************************************
//									 * Update: Progress label
//									 ***************************************/
////									PlayActv.updateProgressLabel(actv);
//									new PlayActv().updateProgressLabel();
//									
//									/***************************************
//									 * Update: Seekbar
//									 ***************************************/
//									if (PlayActv.mp != null
////											&& !PlayActv.sb.isInTouchMode()) {
//											&& !PlayActv.sb.isPressed()) {
//										
//										// Log
//										Log.d("Service_ShowProgress.java"
//												+ "["
//												+ Thread.currentThread()
//														.getStackTrace()[2]
//														.getLineNumber()
//												+ ":"
//												+ Thread.currentThread()
//														.getStackTrace()[2]
//														.getMethodName() + "]",
//												"PlayActv.mp != null && " +
////												"!PlayActv.sb.isInTouchMode()");
//												"!PlayActv.sb.isPressed()");
//										
//										int currentPosition = PlayActv.mp.getCurrentPosition();
//										long length = PlayActv.ai.getLength();
//										
//										int seekPositon = (int)
////													((currentPosition / length)
//													(((float)currentPosition / length)
//															* PlayActv.sb.getMax());
////										
//										PlayActv.sb.setProgress(seekPositon);
//										
//									} else {//if (PlayActv.mp == null)
//										
//										// Log
//										Log.d("Service_ShowProgress.java"
//												+ "["
//												+ Thread.currentThread()
//														.getStackTrace()[2]
//														.getLineNumber()
//												+ ":"
//												+ Thread.currentThread()
//														.getStackTrace()[2]
//														.getMethodName() + "]",
//												"NO");
//										
//									}//if (PlayActv.mp == null)
//									
//								}//public void run() // Runnable
//								
//							});//handler.post()
//							
//							
//						}//if (CONS.P == condition)
//						
//						counter += 1;
//						
//					}//public void run()
//					
//				},//new TimerTask()
//				0,
//				period
////				1000
//		);//timer.schedule
		
	}//private void startCount()

	//REF http://stackoverflow.com/questions/9999038/how-to-instantiate-android-service-with-an-constructor answered Apr 3 '12 at 18:33
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		int tmp = intent.getIntExtra(
						CONS.Intent.iKey_PlayActv_TaskPeriod, 
						CONS.Intent.dflt_IntExtra_value);
		
		if (tmp != CONS.Intent.dflt_IntExtra_value) {
			
			this.period = tmp;
			
			// Log
			String msg_Log = "period set to => " + this.period;
			Log.d("Service_ShowProgress.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
			
			this.period = 500;
			
			// Log
			String msg_Log = "period set to => " + this.period;
			Log.d("Service_ShowProgress.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	

}//public class Service_ShowProgress extends Service
